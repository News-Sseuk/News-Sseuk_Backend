package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.relatedArticle.dto.ArticleIssueThumbnailDTO;
import backend.newssseuk.springbootmongodb.ArticleService;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatedArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final RelatedArticleRepository relatedArticleRepository;
    private final ArticleService articleService;


    public List<ArticleIssueThumbnailDTO> getArticleIssueThumbnailDTO(List<ArticleThumbnailDTO> articleThumbnailDTOs, List<Article> articles) {
        return articleThumbnailDTOs.stream()
                .map(thumbnailDTO -> {
                    // articleList에서 하나씩 기사 매칭.
                    Article correspondingArticle = articles.stream()
                            .filter(article -> article.getNosqlId().equals(thumbnailDTO.getId())).findFirst().get();

                    // 찾은 기사에 따라 ArticleIssueThumnailDTO 생성
                    return ArticleIssueThumbnailDTO.builder()
                            .id(thumbnailDTO.getId())
                            .category(thumbnailDTO.getCategory())
                            .title(thumbnailDTO.getTitle())
                            .description(thumbnailDTO.getDescription())
                            .publishedDate(thumbnailDTO.getPublishedDate())
                            .reliability(thumbnailDTO.getReliability())
                            .hashTagList(thumbnailDTO.getHashTagList())
                            .issue(correspondingArticle.getIssue())
                            .build();
                })
                .toList();
    }


    @Transactional
    public List<ArticleIssueThumbnailDTO> collectingRelatedArticles(String nosql_article_id) throws Exception {
        Article article = jpaArticleRepository.findByNosqlId(nosql_article_id).orElse(null);
        String fullUrl = "AI 서버 API 엔드포인트?nosql_id=" + URLEncoder.encode(String.valueOf(nosql_article_id), "UTF-8");
        List<String> relatedArticleIds = saveRelatedArticleId(fullUrl);
        if (relatedArticleIds == null){
            return null;
        }
        List<Long> mysqlIdList = relatedArticleIds
                .stream()
                .map(nosql_id -> jpaArticleRepository.findByNosqlId(nosql_id).get().getId()).toList();

        try {
            RelatedArticle relatedArticle = relatedArticleRepository.findByArticle(article);
            relatedArticle.setArticleList(mysqlIdList);
        } catch (Exception e) {
            relatedArticleRepository.save(RelatedArticle.builder()
                    .article(article)
                    .articleList(mysqlIdList)
                    .build());
        }
        List<Article> articles = mysqlIdList.stream()
                .map(mysqlId -> jpaArticleRepository.findById(mysqlId).orElseThrow())
                .toList();
        List<ArticleThumbnailDTO> articleThumbnailDTOS = articleService.getArticleThumbnailsByJpa(articles);
        return getArticleIssueThumbnailDTO(articleThumbnailDTOS, articles);
    }

    @Transactional
    public void addRelatedArticle(Long articleId, List<Long> articleList){
        Article article = jpaArticleRepository.findById(articleId).orElse(null);
        RelatedArticle relatedArticle = relatedArticleRepository.save(RelatedArticle.builder()
                .article(article)
                .articleList(articleList)
                .build());
        relatedArticle.setArticle(article);
    }

    public List<String> saveRelatedArticleId(String fullUrl) throws Exception {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(fullUrl);
            // url 연결
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            // 서버에서 온 데이터를 출력할 수 있는 상태인지
            conn.setDoOutput(true);

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                // 에러 처리
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            // 응답 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 응답이 비어 있거나 null일 경우 빈 리스트 반환
            if (response.length() == 0) {
                return new ArrayList<>();  // 빈 리스트 반환
            }

            // JSON 응답을 List<String>으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> relatedArticleIds = objectMapper.readValue(response.toString(), List.class);

            return relatedArticleIds;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}

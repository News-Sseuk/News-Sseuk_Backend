package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisCachingService;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisEntity;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisRepository;
import backend.newssseuk.springbootmongodb.ArticleService;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatedArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final RelatedArticleRepository relatedArticleRepository;
    private final RelatedArticleRedisRepository relatedArticleRedisRepository;
    private final ArticleService articleService;

    @Transactional
    public List<ArticleThumbnailDTO> collectingRelatedArticles(String nosql_article_id) throws Exception {
        Article article = jpaArticleRepository.findByNosqlId(nosql_article_id).orElse(null);
        String fullUrl = "http://52.78.251.30:80/article/each?nosql_id=" + URLEncoder.encode(String.valueOf(nosql_article_id), "UTF-8");
        List<Long> mysqlIdList = saveRelatedArticleId(fullUrl)
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
        return articleService.getArticleThumbnailsByJpa(mysqlIdList.stream()
                .map(mysqlId -> jpaArticleRepository.findById(mysqlId).orElseThrow())
                .toList());
    }
//        RelatedArticleRedisEntity relatedArticleRedisEntity = relatedArticleRedisRepository.findByArticleId(article.getId());
//        // 레디스에 저장된 값이 있을 때
//        if (relatedArticleRedisEntity != null){
//            return relatedArticleRedisEntity.getArticleList();
//        } else {  // 레디스에 저장된 값이 없을 때
//            List<Article> articleResponseList = relatedArticle.getArticleList().stream()
//                    .map(articleId -> jpaArticleRepository.findById(articleId)
//                            .orElseThrow()) // 예외 발생 시, 기본적으로 NoSuchElementException 던짐.
//                    .toList();
//            List<ArticleRedisEntity> articleList = articleResponseList.stream()
//                    .map(mysqlArticle -> mysqlArticle.getMongoEntity(articleHelper))
//                    .collect(Collectors.toList());
//            return relatedArticleRedisCachingService.cashingRelatedArticle(article, articleList).getArticleList();

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

package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.articleHashTag.service.ArticleHashTagService;
import backend.newssseuk.domain.recommendedArticle.dto.RecommendedArticleUpdateDto;
import backend.newssseuk.domain.relatedArticle.RelatedArticleRepository;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.userHistory.UserHistoryService;
import backend.newssseuk.springbootmongodb.ArticleService;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendedArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final RecommendedArticleRepository recommendedArticleRepository;
    private final RelatedArticleRepository relatedArticleRepository;
    private final UserHistoryService userHistoryService;
    private final ArticleService articleService;
    private final ArticleHashTagService articleHashTagService;

    @Transactional
    public List<Article> collectingPersonalRecommendedArticles(User user) {      // 개인 추천기사 (검색 화면)
        List<Long> relatedArticleList = new ArrayList<>();
        List<Article> articleList = userHistoryService.getArticleHistory(user);

        for (Article article : articleList) {
            if (relatedArticleRepository.findByArticle(article) != null) {
                List<Long> eachRelatedArticleList = article.getRelatedArticle().getArticleList();
                relatedArticleList.addAll(eachRelatedArticleList);
            }
        }

        List<Article> articleResponseList = relatedArticleList.stream()
                .distinct() // 중복 제거
                .map(articleId -> jpaArticleRepository.findById(articleId)
                        .orElseThrow()) // 예외 발생 시, 기본적으로 NoSuchElementException 던짐.
                .collect(Collectors.toList());

        // recommendedArticle 엔티티가 존재하면, 수정
        try{
            RecommendedArticleUpdateDto recommendedArticleUpdateDto = new RecommendedArticleUpdateDto(articleResponseList);
            RecommendedArticle recommendedArticle = recommendedArticleRepository.findByUser(user);
            recommendedArticle.update(recommendedArticleUpdateDto);
            recommendedArticleRepository.save(recommendedArticle);
        } catch (NullPointerException e) {
            // recommendedArticle 엔티티가 없으면, 생성
            RecommendedArticle recommendedArticle = recommendedArticleRepository.save(RecommendedArticle.builder()
                    .user(user)
                    .build());
            recommendedArticle.setArticleList(articleResponseList);
        }
        return articleResponseList;
    }

    @Transactional
    public List<ArticleThumbnailDTO> findPersonalRecommendedArticles(User user) throws Exception {
        List<Article> articleList = userHistoryService.getArticleHistory(user);
        List<String> keywords_list = articleList.stream()
                .flatMap(article -> articleHashTagService.getHashTagListByArticleId(article.getId()).stream())
                .distinct()
                .collect(Collectors.toList());

        String fullUrl = "http://52.78.251.30:80/article/personalizing";
        List<Long> mysqlIdList = saveRecommendArticleId(fullUrl, keywords_list)
                .stream()
                .map(nosql_id -> jpaArticleRepository.findByNosqlId(nosql_id).get().getId()).toList();

        try {
            RecommendedArticle recommendedArticle = recommendedArticleRepository.findByUser(user);
        } catch (Exception e) {
            recommendedArticleRepository.save(RecommendedArticle.builder()
                    .user(user)
                    .articleList(mysqlIdList.stream()
                            .map(sqlId-> jpaArticleRepository.findById(sqlId).orElseThrow()).toList())
                    .build());
        }

        try{
            return articleService.getArticleThumbnailsByJpa(mysqlIdList.stream()
                    .map(mysqlId -> jpaArticleRepository.findById(mysqlId).orElseThrow())
                    .toList());
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    public List<String> saveRecommendArticleId(String fullUrl, List<String> keywords_list) throws Exception {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(fullUrl);
            // url 연결
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            // 서버에서 온 데이터를 출력할 수 있는 상태인지
            conn.setDoOutput(true);

            // Map 객체를 JSON으로 변환
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("keywords_list", keywords_list);

            // Jackson ObjectMapper를 사용하여 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(requestBody);

            // Request Body 작성
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
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
            ObjectMapper resultObjectMapper = new ObjectMapper();
            List<String> relatedArticleIds = resultObjectMapper.readValue(response.toString(), List.class);

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
package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.recommendedArticle.redis.RecommendedArticleRedisRepository;
import backend.newssseuk.domain.userHistory.UserHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class RecommendedArticleServiceTest {
    @MockBean
    private JpaArticleRepository jpaArticleRepository;

    @MockBean
    private UserHistoryRepository userHistoryRepository;

    @MockBean
    private RecommendedArticleRedisRepository recommendedArticleRedisRepository;

    @MockBean
    private RecommendedArticleRepository recommendedArticleRepository;

    @MockBean
    private ArticleHelper articleHelper;

    @Test
    @DisplayName("개인 추천 기사 캐싱")
    void cashingPersonalRecommendedArticles() {
        //Given

        //When

        //Then

    }

    @Test
    void collectingPersonalRecommendedArticles() {
    }

    @Test
    void findPersonalRecommendedArticles() {
    }
}
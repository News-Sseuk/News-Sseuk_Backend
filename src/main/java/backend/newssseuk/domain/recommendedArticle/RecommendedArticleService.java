package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.recommendedArticle.dto.RecommendedArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendedArticleService {
    private final ArticleHelper articleHelper;

// Todo : 추천 기사 캐싱 CRUD 구현

//    public RecommendedArticleResponseDto findRecommendedArticles(Article article) {
//
//    }
}

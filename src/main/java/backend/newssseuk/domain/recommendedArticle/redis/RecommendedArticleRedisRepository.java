package backend.newssseuk.domain.recommendedArticle.redis;


import backend.newssseuk.domain.recommendedArticle.RecommendedArticle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendedArticleRedisRepository extends CrudRepository<RecommendedArticle, String> {
    RecommendedArticle save(RecommendedArticle recommendedArticle);
    //List<Article> findByArticle(Article article);
}

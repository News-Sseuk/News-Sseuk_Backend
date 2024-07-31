package backend.newssseuk.domain.recommendedArticle.redis;


import backend.newssseuk.domain.recommendedArticle.RecommendedArticle;
import backend.newssseuk.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendedArticleRedisRepository extends CrudRepository<RecommendedArticleRedisEntity, String> {
    RecommendedArticleRedisEntity save(RecommendedArticleRedisEntity recommendedArticleRedisEntity);
    Optional<RecommendedArticleRedisEntity> findById(User user);
}

package backend.newssseuk.domain.relatedArticle.redis;

import backend.newssseuk.domain.article.Article;
import org.springframework.data.repository.CrudRepository;

public interface RelatedArticleRedisRepository extends CrudRepository<RelatedArticleRedisEntity, Long> {
    RelatedArticleRedisEntity findByArticleId(Long articleId);
}

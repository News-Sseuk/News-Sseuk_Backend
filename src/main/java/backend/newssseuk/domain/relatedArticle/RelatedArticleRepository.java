package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedArticleRepository extends JpaRepository<RelatedArticle,Long> {
    RelatedArticle findByArticle(Article article);
}

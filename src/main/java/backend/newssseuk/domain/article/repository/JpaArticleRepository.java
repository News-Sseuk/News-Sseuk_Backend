package backend.newssseuk.domain.article.repository;

import backend.newssseuk.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {
}

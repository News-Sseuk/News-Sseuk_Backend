package backend.newssseuk.domain.article.repository;

import backend.newssseuk.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long articleId);
}

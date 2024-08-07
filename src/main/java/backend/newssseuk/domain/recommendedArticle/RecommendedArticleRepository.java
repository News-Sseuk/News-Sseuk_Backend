package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedArticleRepository extends JpaRepository<RecommendedArticle, Long> {
    RecommendedArticle findByUser(User user);
}

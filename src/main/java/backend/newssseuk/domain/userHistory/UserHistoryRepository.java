package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    Optional<UserHistory> findUserHistoryByArticleAndUser(Article article, User user);

    List<UserHistory> findTop10ByUserOrderByReadAtDesc(User user);

    long countByUser(User user);
}

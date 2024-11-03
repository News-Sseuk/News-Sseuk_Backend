package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;
    private final JpaArticleRepository jpaArticleRepository;

    @Transactional
    public void addUserHistory(User user, String articleId) {
        Article mysqlArticle = jpaArticleRepository.findByNosqlId(articleId).orElseThrow();

        long count = userHistoryRepository.countByUser(user);
        if (count >= 10) {
            List<UserHistory> oldestArticles = userHistoryRepository.findTop10ByUserOrderByReadAtDesc(user);
            userHistoryRepository.delete(oldestArticles.get(9));
        }

        UserHistory userHistory = new UserHistory();
        userHistory.setUser(user);
        userHistory.setArticle(mysqlArticle);
        userHistory.setReadAt(LocalDateTime.now());

        userHistoryRepository.save(userHistory);
    }

    public List<Article> getArticleHistory(User user) {
        return userHistoryRepository.findTop10ByUserOrderByReadAtDesc(user)
                .stream()
                .map(UserHistory::getArticle)
                .toList();
    }

}

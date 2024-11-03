package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;
    private final JpaArticleRepository jpaArticleRepository;

    @Transactional
    public void addUserHistory(User user, String articleId) {
        Article mysqlArticle = jpaArticleRepository.findByNosqlId(articleId).orElseThrow();

        UserHistory userHistory = userHistoryRepository.findByUser(user);
        if (userHistory == null) {
            userHistory = userHistoryRepository.save(UserHistory.builder()
                    .user(user)
                    .build());
            List<Article> articles = new ArrayList<>();
            articles.add(mysqlArticle);
            userHistory.setArticleList(articles);
        } else {
            List<Article> articleList = userHistory.getArticleList();
            if (!articleList.contains(mysqlArticle)) {
                articleList.add(0, mysqlArticle);
                if (articleList.size() > 3) {
                    articleList = articleList.subList(0, 3);
                }
                userHistory.setArticleList(articleList);
            }
        }
    }
}

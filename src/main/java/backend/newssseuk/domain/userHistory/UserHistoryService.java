package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.relatedArticle.RelatedArticle;
import backend.newssseuk.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;
    private final JpaArticleRepository jpaArticleRepository;

    @Transactional
    public void addUserHistory(User user, List<String> articleList){
        List<Article> MysqlArticleList = articleList.stream().map(articleId -> jpaArticleRepository.findByNosqlId(articleId).orElseThrow()).toList();

        if (userHistoryRepository.findByUser(user) == null){
            UserHistory userHistory = userHistoryRepository.save(UserHistory.builder()
                    .user(user)
                    .build());
            userHistory.setArticleList(MysqlArticleList);
        }
        else {
            userHistoryRepository.findByUser(user).update(MysqlArticleList);
        }
    }
}

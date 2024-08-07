package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.recommendedArticle.dto.RecommendedArticleUpdateDto;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.userHistory.dto.UserHistorySaveDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="user_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "userHistory", cascade = CascadeType.ALL)
    List<Article> articleList = new ArrayList<>(); // 최대 10개 저장

    @ElementCollection
    List<String> searchHistoryList;

    public void update(List<Article> newArticleList){
        this.articleList = newArticleList;
    }
    public void setArticleList(List<Article> articleSettingList){
        this.articleList=articleSettingList;
        for (Article article : articleSettingList){
            article.setUserHistory(this);
        }
    }
}

package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.recommendedArticle.dto.RecommendedArticleUpdateDto;
import backend.newssseuk.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="recommended_article")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecommendedArticle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "recommendedArticle", cascade = CascadeType.ALL)
    private List<Article> articleList = new ArrayList<>();

    public void update(RecommendedArticleUpdateDto recommendedArticleUpdateDto){
        if(recommendedArticleUpdateDto.getArticleList() != null)
            this.articleList = recommendedArticleUpdateDto.getArticleList();
    }
}

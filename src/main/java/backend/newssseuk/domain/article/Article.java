package backend.newssseuk.domain.article;

import backend.newssseuk.domain.recommendedArticle.RecommendedArticle;
import backend.newssseuk.domain.articleHashTag.ArticleHashTag;
import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.userHistory.UserHistory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nosqlId;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleHashTag> articleHashTagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_article_id")
    private RecommendedArticle recommendedArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_history_id")
    private UserHistory userHistory;

    /*@ElementCollection
    private List<String> keywordList = new ArrayList<>();

    @ElementCollection
    private List<String> imageList = new ArrayList<>();

    private float reliability;

    private String summary;

    private Category category;

    private Trending trending;*/
}

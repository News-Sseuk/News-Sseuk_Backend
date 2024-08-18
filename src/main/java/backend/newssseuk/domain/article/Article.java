package backend.newssseuk.domain.article;

import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.recommendedArticle.RecommendedArticle;
import backend.newssseuk.domain.articleHashTag.ArticleHashTag;
import backend.newssseuk.domain.relatedArticle.RelatedArticle;
import backend.newssseuk.domain.userHistory.UserHistory;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nosqlId;

    private Integer reliability;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    private LocalDateTime crawledTime;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleHashTag> articleHashTagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_article_id")
    private RecommendedArticle recommendedArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_history_id")
    private UserHistory userHistory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="related_article_id")
    private RelatedArticle relatedArticle;

    // ArticleHelper를 통해 MongoDB 객체를 가져오는 메서드
    public ArticleRedisEntity getMongoEntity(ArticleHelper articleHelper) {
        return articleHelper.getRedisEntityByNosqlId(this.nosqlId);
    }
    /*@ElementCollection
    private List<String> keywordList = new ArrayList<>();

    @ElementCollection
    private List<String> imageList = new ArrayList<>();

    private float reliability;

    private String summary;

    private Category category;

    private Trending trending;*/
}

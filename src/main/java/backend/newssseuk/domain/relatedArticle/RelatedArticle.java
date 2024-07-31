package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="related_article")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RelatedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    @ElementCollection
    private List<Long> articleList = new ArrayList<>(); // article_id 저장
}

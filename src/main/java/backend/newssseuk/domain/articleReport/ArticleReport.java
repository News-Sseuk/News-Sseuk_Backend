package backend.newssseuk.domain.articleReport;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.enums.ReportingReason;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    @Enumerated(EnumType.STRING)
    private ReportingReason reason;
}

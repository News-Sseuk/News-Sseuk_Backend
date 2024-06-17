package backend.newssseuk.domain.articleReport;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleReport is a Querydsl query type for ArticleReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleReport extends EntityPathBase<ArticleReport> {

    private static final long serialVersionUID = -923345220L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleReport articleReport = new QArticleReport("articleReport");

    public final backend.newssseuk.domain.common.QBaseEntity _super = new backend.newssseuk.domain.common.QBaseEntity(this);

    public final backend.newssseuk.domain.article.QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath reportedReason = createString("reportedReason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QArticleReport(String variable) {
        this(ArticleReport.class, forVariable(variable), INITS);
    }

    public QArticleReport(Path<? extends ArticleReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleReport(PathMetadata metadata, PathInits inits) {
        this(ArticleReport.class, metadata, inits);
    }

    public QArticleReport(Class<? extends ArticleReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new backend.newssseuk.domain.article.QArticle(forProperty("article")) : null;
    }

}


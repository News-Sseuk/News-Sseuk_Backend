package backend.newssseuk.domain.RecommendedArticle;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendedArticle is a Querydsl query type for RecommendedArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendedArticle extends EntityPathBase<RecommendedArticle> {

    private static final long serialVersionUID = -823931004L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendedArticle recommendedArticle = new QRecommendedArticle("recommendedArticle");

    public final backend.newssseuk.domain.common.QBaseEntity _super = new backend.newssseuk.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<backend.newssseuk.domain.article.Article, backend.newssseuk.domain.article.QArticle> recommendArticleList = this.<backend.newssseuk.domain.article.Article, backend.newssseuk.domain.article.QArticle>createList("recommendArticleList", backend.newssseuk.domain.article.Article.class, backend.newssseuk.domain.article.QArticle.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final backend.newssseuk.domain.user.QUser user;

    public QRecommendedArticle(String variable) {
        this(RecommendedArticle.class, forVariable(variable), INITS);
    }

    public QRecommendedArticle(Path<? extends RecommendedArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendedArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendedArticle(PathMetadata metadata, PathInits inits) {
        this(RecommendedArticle.class, metadata, inits);
    }

    public QRecommendedArticle(Class<? extends RecommendedArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new backend.newssseuk.domain.user.QUser(forProperty("user")) : null;
    }

}


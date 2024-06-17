package backend.newssseuk.domain.userHistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHistory is a Querydsl query type for UserHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserHistory extends EntityPathBase<UserHistory> {

    private static final long serialVersionUID = -308235462L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHistory userHistory = new QUserHistory("userHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<backend.newssseuk.domain.article.Article, backend.newssseuk.domain.article.QArticle> readingHistoryList = this.<backend.newssseuk.domain.article.Article, backend.newssseuk.domain.article.QArticle>createList("readingHistoryList", backend.newssseuk.domain.article.Article.class, backend.newssseuk.domain.article.QArticle.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> searchHistoryList = this.<String, StringPath>createList("searchHistoryList", String.class, StringPath.class, PathInits.DIRECT2);

    public final backend.newssseuk.domain.user.QUser user;

    public QUserHistory(String variable) {
        this(UserHistory.class, forVariable(variable), INITS);
    }

    public QUserHistory(Path<? extends UserHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHistory(PathMetadata metadata, PathInits inits) {
        this(UserHistory.class, metadata, inits);
    }

    public QUserHistory(Class<? extends UserHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new backend.newssseuk.domain.user.QUser(forProperty("user")) : null;
    }

}


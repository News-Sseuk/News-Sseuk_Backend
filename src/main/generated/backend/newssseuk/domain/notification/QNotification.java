package backend.newssseuk.domain.notification;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = -1192686364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotification notification = new QNotification("notification");

    public final backend.newssseuk.domain.common.QBaseEntity _super = new backend.newssseuk.domain.common.QBaseEntity(this);

    public final backend.newssseuk.domain.article.QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final backend.newssseuk.domain.user.QUser user;

    public QNotification(String variable) {
        this(Notification.class, forVariable(variable), INITS);
    }

    public QNotification(Path<? extends Notification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotification(PathMetadata metadata, PathInits inits) {
        this(Notification.class, metadata, inits);
    }

    public QNotification(Class<? extends Notification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new backend.newssseuk.domain.article.QArticle(forProperty("article")) : null;
        this.user = inits.isInitialized("user") ? new backend.newssseuk.domain.user.QUser(forProperty("user")) : null;
    }

}


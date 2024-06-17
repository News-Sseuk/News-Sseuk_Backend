package backend.newssseuk.domain.trendingKeywords;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrendingKeywords is a Querydsl query type for TrendingKeywords
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrendingKeywords extends EntityPathBase<TrendingKeywords> {

    private static final long serialVersionUID = 1902904804L;

    public static final QTrendingKeywords trendingKeywords = new QTrendingKeywords("trendingKeywords");

    public final backend.newssseuk.domain.common.QBaseEntity _super = new backend.newssseuk.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> trendingKeyWords = this.<String, StringPath>createList("trendingKeyWords", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QTrendingKeywords(String variable) {
        super(TrendingKeywords.class, forVariable(variable));
    }

    public QTrendingKeywords(Path<? extends TrendingKeywords> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrendingKeywords(PathMetadata metadata) {
        super(TrendingKeywords.class, metadata);
    }

}


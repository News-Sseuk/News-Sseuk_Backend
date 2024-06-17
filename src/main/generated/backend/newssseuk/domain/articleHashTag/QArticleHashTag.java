package backend.newssseuk.domain.articleHashTag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleHashTag is a Querydsl query type for ArticleHashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleHashTag extends EntityPathBase<ArticleHashTag> {

    private static final long serialVersionUID = 1642228164L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleHashTag articleHashTag = new QArticleHashTag("articleHashTag");

    public final backend.newssseuk.domain.article.QArticle article;

    public final backend.newssseuk.domain.hashTag.QHashTag hashTag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QArticleHashTag(String variable) {
        this(ArticleHashTag.class, forVariable(variable), INITS);
    }

    public QArticleHashTag(Path<? extends ArticleHashTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleHashTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleHashTag(PathMetadata metadata, PathInits inits) {
        this(ArticleHashTag.class, metadata, inits);
    }

    public QArticleHashTag(Class<? extends ArticleHashTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new backend.newssseuk.domain.article.QArticle(forProperty("article")) : null;
        this.hashTag = inits.isInitialized("hashTag") ? new backend.newssseuk.domain.hashTag.QHashTag(forProperty("hashTag")) : null;
    }

}


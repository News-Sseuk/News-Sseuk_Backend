package backend.newssseuk.domain.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -1992397036L;

    public static final QArticle article = new QArticle("article");

    public final backend.newssseuk.domain.common.QBaseEntity _super = new backend.newssseuk.domain.common.QBaseEntity(this);

    public final ListPath<backend.newssseuk.domain.articleHashTag.ArticleHashTag, backend.newssseuk.domain.articleHashTag.QArticleHashTag> articleHashTagList = this.<backend.newssseuk.domain.articleHashTag.ArticleHashTag, backend.newssseuk.domain.articleHashTag.QArticleHashTag>createList("articleHashTagList", backend.newssseuk.domain.articleHashTag.ArticleHashTag.class, backend.newssseuk.domain.articleHashTag.QArticleHashTag.class, PathInits.DIRECT2);

    public final EnumPath<backend.newssseuk.domain.enums.Category> category = createEnum("category", backend.newssseuk.domain.enums.Category.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageList = this.<String, StringPath>createList("imageList", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath journalist = createString("journalist");

    public final ListPath<String, StringPath> keywordList = this.<String, StringPath>createList("keywordList", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath press = createString("press");

    public final NumberPath<Float> reliability = createNumber("reliability", Float.class);

    public final StringPath summary = createString("summary");

    public final StringPath title = createString("title");

    public final EnumPath<backend.newssseuk.domain.enums.Trending> trending = createEnum("trending", backend.newssseuk.domain.enums.Trending.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}


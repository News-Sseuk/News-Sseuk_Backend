package backend.newssseuk.domain.hashTag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashTag is a Querydsl query type for HashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashTag extends EntityPathBase<HashTag> {

    private static final long serialVersionUID = 1300514432L;

    public static final QHashTag hashTag = new QHashTag("hashTag");

    public final ListPath<backend.newssseuk.domain.articleHashTag.ArticleHashTag, backend.newssseuk.domain.articleHashTag.QArticleHashTag> articleHashTagList = this.<backend.newssseuk.domain.articleHashTag.ArticleHashTag, backend.newssseuk.domain.articleHashTag.QArticleHashTag>createList("articleHashTagList", backend.newssseuk.domain.articleHashTag.ArticleHashTag.class, backend.newssseuk.domain.articleHashTag.QArticleHashTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QHashTag(String variable) {
        super(HashTag.class, forVariable(variable));
    }

    public QHashTag(Path<? extends HashTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashTag(PathMetadata metadata) {
        super(HashTag.class, metadata);
    }

}


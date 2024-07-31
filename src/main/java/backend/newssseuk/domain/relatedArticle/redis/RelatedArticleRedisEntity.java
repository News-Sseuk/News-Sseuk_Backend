package backend.newssseuk.domain.relatedArticle.redis;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value="related-article")
public class RelatedArticleRedisEntity {
    @Id
    private Long id;

    @Indexed
    private Article article;

    private List<ArticleRedisEntity> articleList; // mongoDB Article
}

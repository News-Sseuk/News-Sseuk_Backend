package backend.newssseuk.domain.relatedArticle.redis;

import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
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
    private Long articleId;

    private List<ArticleResponseDto> articleList; // mongoDB Article
}

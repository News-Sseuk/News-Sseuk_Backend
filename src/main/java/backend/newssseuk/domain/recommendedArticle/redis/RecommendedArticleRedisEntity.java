package backend.newssseuk.domain.recommendedArticle.redis;

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
@RedisHash(value="recommended-article")
public class RecommendedArticleRedisEntity {
    @Id
    private Long id;

    @Indexed
    private Long userId;

    private List<ArticleResponseDto> articleList; // mongoDB Article
}

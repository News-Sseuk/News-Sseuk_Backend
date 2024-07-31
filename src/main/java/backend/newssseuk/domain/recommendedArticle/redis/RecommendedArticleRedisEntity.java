package backend.newssseuk.domain.recommendedArticle.redis;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
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
    private User user;

    private List<ArticleRedisEntity> articleList = new ArrayList<>(); // mongoDB Article
}

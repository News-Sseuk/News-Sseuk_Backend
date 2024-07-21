package backend.newssseuk.domain.recommendedArticle.redis;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@RedisHash(value="recommended-article", timeToLive=172800) // 2일 단위로 캐싱
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedArticleRedisEntity {
    @Id
    private Long id;

    @Indexed
    private Long articleid;

    List<Article> articleList = new ArrayList<>();
}

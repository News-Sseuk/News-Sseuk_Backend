package backend.newssseuk.springbootmongodb.redis;

import com.mongodb.lang.Nullable;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@RedisHash(value="article", timeToLive=86400) // 1일 단위로 캐싱
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRedisEntity {
    @Id
    private String id;

    private String title;

    private String press;

    @Nullable
    private String journalist;

    private List<String> image;

    private String content;

    @Indexed
    private String category;
}

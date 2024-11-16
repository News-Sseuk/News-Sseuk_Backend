package backend.newssseuk.springbootmongodb.redis;

import backend.newssseuk.domain.articleHashTag.ArticleHashTag;
import com.mongodb.lang.Nullable;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Builder
@Getter
@Setter
@ToString(exclude = "hashTagList")
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value="article")
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

    private List<ArticleHashTagDTO> hashTagList;

    private Integer reliability;

    private String summary;

    private String publishedDate;
}

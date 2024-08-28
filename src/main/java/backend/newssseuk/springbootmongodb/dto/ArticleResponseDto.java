package backend.newssseuk.springbootmongodb.dto;

import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import com.mongodb.lang.Nullable;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class ArticleResponseDto {
    private String id;

    private String title;

    private String press;

    @Nullable
    private String journalist;

    private List<String> image;

    private String content;

    private String category;

    public ArticleResponseDto(ArticleRedisEntity article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.press = article.getPress();
        this.journalist = article.getJournalist();
        this.image = article.getImage();
        this.content = article.getContent();
        this.category = article.getCategory();
    }
}

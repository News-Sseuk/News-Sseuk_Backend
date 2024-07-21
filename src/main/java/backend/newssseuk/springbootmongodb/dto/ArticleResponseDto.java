package backend.newssseuk.springbootmongodb.dto;

import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import com.mongodb.lang.Nullable;
import lombok.*;

import java.util.List;

@Data
public class ArticleResponseDto {
    private String title;

    private String press;

    @Nullable
    private String journalist;

    private List<String> image;

    private String content;

    private String category;

    public ArticleResponseDto(Article article) {
        this.title = article.getTitle();
        this.press = article.getPress();
        this.journalist = article.getJournalist();
        this.image = article.getImage();
        this.content = article.getContent();
        this.category = article.getCategory();
    }
}

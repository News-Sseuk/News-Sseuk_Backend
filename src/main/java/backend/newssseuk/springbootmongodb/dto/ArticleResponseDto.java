package backend.newssseuk.springbootmongodb.dto;

import backend.newssseuk.springbootmongodb.redis.ArticleHashTagDTO;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import com.mongodb.lang.Nullable;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<String> hashTagList;

    private Integer reliability;

    private String summary;

    private String publishedDate;

    public ArticleResponseDto(ArticleRedisEntity article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.press = article.getPress();
        this.journalist = article.getJournalist();
        this.image = article.getImage();
        this.content = article.getContent();
        this.category = article.getCategory();
        this.hashTagList = article.getHashTagList() == null
                ? Collections.singletonList("더미")
                : article.getHashTagList().stream()
                .map(ArticleHashTagDTO::getHashTagName)
                .distinct()
                .collect(Collectors.toList());
        this.reliability = article.getReliability();
        this.summary = article.getSummary();
        this.publishedDate = article.getPublishedDate();
    }
}

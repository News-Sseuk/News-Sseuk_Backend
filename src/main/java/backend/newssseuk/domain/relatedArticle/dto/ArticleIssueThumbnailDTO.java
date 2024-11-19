package backend.newssseuk.domain.relatedArticle.dto;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class ArticleIssueThumbnailDTO {
    private String id;
    private String title;
    private String description;
    private String publishedDate;
    private String category;
    private List<String> hashTagList;
    private Integer reliability;
    private String issue;
}

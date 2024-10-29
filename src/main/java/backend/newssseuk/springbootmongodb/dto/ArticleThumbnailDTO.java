package backend.newssseuk.springbootmongodb.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleThumbnailDTO {
    private String id;
    private String title;
    private String description;
    private String publishedDate;
    private String category;
    private List<String> hashTagList;
    private Integer reliability;
}

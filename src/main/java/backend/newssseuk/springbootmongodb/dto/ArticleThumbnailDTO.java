package backend.newssseuk.springbootmongodb.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ArticleThumbnailDTO {
    private String id;
    private String title;
    private String description;
    private LocalDateTime publishedDate;
    private String image;
}

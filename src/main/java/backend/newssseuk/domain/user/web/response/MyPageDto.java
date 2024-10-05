package backend.newssseuk.domain.user.web.response;

import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDto {
    private String name;
    private String grade;
    private Integer days;
    private List<ArticleThumbnailDTO> articleThumbnailDTOs;
}

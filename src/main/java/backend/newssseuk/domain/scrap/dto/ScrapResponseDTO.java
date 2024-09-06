package backend.newssseuk.domain.scrap.dto;

import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScrapResponseDTO {
    private boolean hasNext;
    private List<ArticleThumbnailDTO> articleList;
}

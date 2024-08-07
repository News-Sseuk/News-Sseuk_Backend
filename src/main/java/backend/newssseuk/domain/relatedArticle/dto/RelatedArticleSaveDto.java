package backend.newssseuk.domain.relatedArticle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RelatedArticleSaveDto {
    private Long articleId;
    private List<Long> articleList;

    public RelatedArticleSaveDto(Long Id, List<Long> List){
        this.articleId=Id;
        this.articleList=List;
    }

}

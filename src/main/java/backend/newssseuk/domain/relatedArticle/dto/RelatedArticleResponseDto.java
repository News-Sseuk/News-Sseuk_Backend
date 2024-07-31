package backend.newssseuk.domain.relatedArticle.dto;

import backend.newssseuk.domain.recommendedArticle.redis.RecommendedArticleRedisEntity;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisEntity;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RelatedArticleResponseDto {
    private List<ArticleResponseDto> relatedArticleList;

    public RelatedArticleResponseDto(RelatedArticleRedisEntity relatedArticleRedisEntity){
        this.relatedArticleList = relatedArticleRedisEntity.getArticleList().stream()
                .map(article -> new ArticleResponseDto(article))
                .collect(Collectors.toList());
    }
}

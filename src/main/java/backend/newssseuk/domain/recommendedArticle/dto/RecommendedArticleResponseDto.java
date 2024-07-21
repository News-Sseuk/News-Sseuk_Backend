package backend.newssseuk.domain.recommendedArticle.dto;

import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.recommendedArticle.RecommendedArticle;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecommendedArticleResponseDto {
    List<ArticleResponseDto> recommendedArticleList = new ArrayList<>();

    public RecommendedArticleResponseDto(RecommendedArticle recommendedArticle, ArticleHelper articleHelper){
        this.recommendedArticleList = recommendedArticle.getArticleList().stream()
                .map(article -> new ArticleResponseDto(article.getMongoEntity(articleHelper)))
                .collect(Collectors.toList());
    }
}

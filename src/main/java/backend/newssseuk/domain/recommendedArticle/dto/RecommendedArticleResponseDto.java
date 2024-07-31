package backend.newssseuk.domain.recommendedArticle.dto;

import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.recommendedArticle.RecommendedArticle;
import backend.newssseuk.domain.recommendedArticle.redis.RecommendedArticleRedisEntity;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.userHistory.UserHistory;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecommendedArticleResponseDto {
    private List<ArticleResponseDto> recommendedArticleList;

    public RecommendedArticleResponseDto(RecommendedArticleRedisEntity recommendedArticleRedisEntity){
        this.recommendedArticleList = recommendedArticleRedisEntity.getArticleList().stream()
                .map(article -> new ArticleResponseDto(article))
                .collect(Collectors.toList());
    }
}

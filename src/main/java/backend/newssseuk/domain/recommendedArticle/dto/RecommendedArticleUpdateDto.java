package backend.newssseuk.domain.recommendedArticle.dto;

import backend.newssseuk.domain.article.Article;
import lombok.Data;

import java.util.List;

@Data
public class RecommendedArticleUpdateDto {
    private List<Article> articleList;

    public RecommendedArticleUpdateDto(List<Article> articleList){
        this.articleList=articleList;
    }
}

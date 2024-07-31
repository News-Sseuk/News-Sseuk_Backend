package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RelatedArticleController {
    private final RelatedArticleService relatedArticleService;
    @GetMapping("api/personalrecommend/{articleId}")
    public List<ArticleResponseDto> findRelatedArticles(@PathVariable("articleId") Long articleId){
        return relatedArticleService.collectingRelatedArticles(articleId);
    }
}

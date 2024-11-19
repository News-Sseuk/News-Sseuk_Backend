package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.relatedArticle.dto.ArticleIssueThumbnailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RelatedArticleController {
    private final RelatedArticleService relatedArticleService;
    @GetMapping("api/personalrecommend/find/{articleId}")
    public List<ArticleIssueThumbnailDTO> findRelatedArticles(@PathVariable("articleId") String nosql_article_id) throws Exception {
        return relatedArticleService.collectingRelatedArticles(nosql_article_id);
    }

//    @PostMapping("api/personalrecommend/")
//    public void saveRelatedArticle(@RequestBody RelatedArticleSaveDto request){
//        relatedArticleService.addRelatedArticle(request.getArticleId(),request.getArticleList());
//    }
}

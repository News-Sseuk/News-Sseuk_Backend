package backend.newssseuk.springbootmongodb;

import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("api/crawling")
    public void crawling(){
        String url = "https://news.naver.com/breakingnews/section/100/268";
        articleService.getCrawlingInfos(url);
    }

    @GetMapping("redis/article/{id}")
    public ArticleResponseDto findByArticleId(@PathVariable("id") String id){
        return articleService.findArticles(id);
    }
}

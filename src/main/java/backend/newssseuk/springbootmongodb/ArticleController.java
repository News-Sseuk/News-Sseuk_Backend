package backend.newssseuk.springbootmongodb;

import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService crawlingService;
    private final ArticleRepository articleRepository;

    @GetMapping("api/crawling")
    public void crawling(){
        String url = "https://news.naver.com/section/100";
        crawlingService.getCrawlingInfos(url);
    }

    @GetMapping("redis/article/{id}")
    public ArticleResponseDto findByArticleId(@PathVariable("id") String id){
        ArticleResponseDto result = crawlingService.findArticles(id);
        return result;
    }
}

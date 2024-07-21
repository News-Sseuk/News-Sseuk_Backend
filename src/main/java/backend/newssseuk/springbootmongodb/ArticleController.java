package backend.newssseuk.springbootmongodb;

import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService crawlingService;

    @GetMapping("api/crawling")
    public void crawling(){
        // 100 : 정치, 101 : 경제, 102 : 사회, 103 : 생활/문화, 104 : 세계, 105 : IT/과학
        String url = "https://news.naver.com/section/100";
        crawlingService.getCrawlingInfos(url);
    }

    @GetMapping("redis/article/{id}")
    public ArticleResponseDto findByArticleId(@PathVariable("id") String id){
        ArticleResponseDto result = crawlingService.findArticles(id);
        return result;
    }
}

package backend.newssseuk.springbootmongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final CrawlingService crawlingService;

    @GetMapping("api/crawling")
    public void crawling(){
        String url = "https://news.naver.com/section/102";
        crawlingService.getCrawlingInfos(url);
    }
}

package backend.newssseuk.springbootmongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticleController {
    private final CrawlingService crawlingService;
    @GetMapping("/api/crawling")
    public List<Article> crawling(){
        System.out.println("컨트롤러 실행");
        return crawlingService.getCrawlingInfos();
    }
}

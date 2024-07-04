package backend.newssseuk.springbootmongodb;

import backend.newssseuk.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final CrawlingService crawlingService;

    @GetMapping("api/crawling")
    public void crawling(){
        crawlingService.getCrawlingInfos();
    }
}

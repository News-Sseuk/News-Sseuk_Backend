package backend.newssseuk.springbootmongodb;

import backend.newssseuk.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final CrawlingService crawlingService;

    @GetMapping("/api/crawling")
    public ApiResponse<Void> crawling(){
        crawlingService.getCrawlingInfos();
        return ApiResponse.onSuccess();
    }
}

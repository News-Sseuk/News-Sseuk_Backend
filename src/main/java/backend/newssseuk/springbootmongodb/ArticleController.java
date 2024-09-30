package backend.newssseuk.springbootmongodb;

import backend.newssseuk.payload.ApiResponse;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // 상위 카테고리 숫자의 범위 : 100 ~ 105
    @Scheduled(cron = "0 0/10 * * * *", zone = "Asia/Seoul") // 10분마다 실행 (참고:https://dev-coco.tistory.com/176)
    @GetMapping("api/crawling")
    public void crawling(){
        articleService.getCrawlingInfos();
    }

    @GetMapping("redis/article/{id}")
    public ApiResponse<ArticleResponseDto> findByArticleId(@PathVariable("id") String id){
        return ApiResponse.onSuccess(articleService.findArticles(id));
    }

    @GetMapping("/article/{category}/{cursor_time}")
    public ApiResponse<List<ArticleThumbnailDTO>> getArticleThumbnail(@PathVariable("category") String category,
                                                                     @PathVariable("cursor_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorTime){
        return ApiResponse.onSuccess(articleService.findArticleThumbnails(category, cursorTime));
    }
}

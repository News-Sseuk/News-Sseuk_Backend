package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // 상위 카테고리 숫자의 범위 : 100 ~ 105
    @Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul") // 30분마다 실행 (참고:https://dev-coco.tistory.com/176)
    @GetMapping("api/crawling")
    public void crawling(){
        for (int i=1; i<=6; i++) {
            String url = String.format("https://news.naver.com/section/%d", 99 + i);
            articleService.getCrawlingInfos(url);
            log.trace("--> 카테고리 {}번 실행 중", 99+i);
        }
    }

    @GetMapping("redis/article/{id}")
    public ArticleResponseDto findByArticleId(@PathVariable("id") String id){
        return articleService.findArticles(id);
    }
}

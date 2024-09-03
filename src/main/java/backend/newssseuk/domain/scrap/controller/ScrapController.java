package backend.newssseuk.domain.scrap.controller;

import backend.newssseuk.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;
    @PostMapping("scrap/{user_id}/{article_id}")
    public String scrapArticle(@PathVariable Long user_id, @PathVariable Long article_id){
        return scrapService.scrapArticleByArticleId(user_id, article_id);
    }
}
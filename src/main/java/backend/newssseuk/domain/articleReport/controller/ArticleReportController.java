package backend.newssseuk.domain.articleReport.controller;

import backend.newssseuk.domain.articleReport.service.ArticleReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ArticleReportController {
    private final ArticleReportService articleReportService;
    @PostMapping("report/{article_id}")
    public void reportArticleByReason(@PathVariable Long article_id, @RequestBody String reason){
        articleReportService.updateArticleReport(article_id, reason);
    }
}
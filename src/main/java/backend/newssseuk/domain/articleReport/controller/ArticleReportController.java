package backend.newssseuk.domain.articleReport.controller;

import backend.newssseuk.domain.articleReport.dto.ArticleReportDTO;
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
    public void reportArticleByReason(@PathVariable("article_id") String nosql_article_id, @RequestBody ArticleReportDTO req){
        articleReportService.updateArticleReport(nosql_article_id, req.getReason());
    }
}

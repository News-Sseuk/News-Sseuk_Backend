package backend.newssseuk.domain.articleReport.service;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.articleReport.ArticleReport;
import backend.newssseuk.domain.articleReport.repository.ArticleReportRepository;
import backend.newssseuk.domain.enums.ReportingReason;
import backend.newssseuk.domain.enums.converter.ReportingReasonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleReportService {
    private final JpaArticleRepository jpaArticleRepository;
    private final ReportingReasonConverter reportingReasonConverter;
    private final ArticleReportRepository articleReportRepository;

    public void updateArticleReport(Long article_id, String reason){
        Article article = jpaArticleRepository.findById(article_id).get();
        ReportingReason reportingReason = reportingReasonConverter.fromReasonReportingReason(reason);

        ArticleReport articleReport = ArticleReport.builder()
                .article(article)
                .reason(reportingReason)
                .build();
        articleReportRepository.save(articleReport);

    }
}

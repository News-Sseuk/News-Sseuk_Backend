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

    public void applyToReliabilityScore(Article article, ReportingReason reportingReason) {
        Integer reportCount = articleReportRepository.findArticleReportCntByReason(article, reportingReason);
        if (reportCount%5 == 0 && reportCount != 0) {
            article.setReliability(article.getReliability()-1);
            jpaArticleRepository.save(article);
        }
    }

    public void updateArticleReport(String nosql_article_id, String reason){
        Article article = jpaArticleRepository.findByNosqlId(nosql_article_id).get();
        ReportingReason reportingReason = reportingReasonConverter.fromReasonReportingReason(reason);

        ArticleReport articleReport = ArticleReport.builder()
                .article(article)
                .reason(reportingReason)
                .build();
        articleReportRepository.save(articleReport);
        applyToReliabilityScore(article, reportingReason);
    }
}

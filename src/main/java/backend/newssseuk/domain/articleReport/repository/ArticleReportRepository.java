package backend.newssseuk.domain.articleReport.repository;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.articleReport.ArticleReport;
import backend.newssseuk.domain.enums.ReportingReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, Long> {
    @Query(value="select count(*) as count " +
            "from ArticleReport r " +
            "where r.article= :article " +
            "group by r.reason " +
            "having r.reason= :reason")
    Integer findArticleReportCntByReason(@Param("article") Article article, @Param("reason") ReportingReason reportingReason);
}

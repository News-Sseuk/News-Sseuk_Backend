package backend.newssseuk.domain.articleReport.repository;

import backend.newssseuk.domain.articleReport.ArticleReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, Long> {
}

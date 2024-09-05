package backend.newssseuk.domain.scrap.repository;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.scrap.dto.ScrapResponseDTO;
import backend.newssseuk.domain.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScrapRepositoryCustom {
    List<Article> getUserArticleByCategory(User user, Category category, Pageable pageable, Long lastArticleId);
}

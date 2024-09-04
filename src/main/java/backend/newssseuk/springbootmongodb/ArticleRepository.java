package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.enums.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String> {
    @Override
    List<Article> findAll();

    @Override
    Optional<Article> findById(String id);

    List<Article> findAllByCategory(Category category);

    List<Article> findByContentContainingAndPublishedDateLessThanOrderByPublishedDateDesc(String word, LocalDateTime cursorTime, Pageable pageable);
}

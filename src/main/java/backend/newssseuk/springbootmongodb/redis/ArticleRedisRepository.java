package backend.newssseuk.springbootmongodb.redis;

import backend.newssseuk.springbootmongodb.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleRedisRepository extends CrudRepository<Article,String> {

    Article save(Article article);
    Optional<Article> findById(String id);
}

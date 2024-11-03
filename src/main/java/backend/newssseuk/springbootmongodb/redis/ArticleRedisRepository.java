package backend.newssseuk.springbootmongodb.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleRedisRepository extends CrudRepository<ArticleRedisEntity,String> {

    ArticleRedisEntity save(ArticleRedisEntity article);
    Optional<ArticleRedisEntity> findById(String id);
}

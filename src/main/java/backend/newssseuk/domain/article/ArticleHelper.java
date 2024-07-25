package backend.newssseuk.domain.article;

import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// ArticleHelper를 통해 Mysql 객체로부터 MongoDB 객체를 가져오는 메서드
@Component
@RequiredArgsConstructor
public class ArticleHelper {
    private final ArticleRedisRepository articleRedisRepository;

    public ArticleRedisEntity getRedisEntityByNosqlId(String nosqlId) {
        return articleRedisRepository.findById(nosqlId).orElse(null);
    }
}

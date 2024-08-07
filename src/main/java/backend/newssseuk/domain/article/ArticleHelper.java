package backend.newssseuk.domain.article;

import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

// ArticleHelper를 통해 Mysql 객체로부터 MongoDB 객체를 가져오는 메서드
@Component
@RequiredArgsConstructor
public class ArticleHelper {
    private final ArticleRepository articleRepository;

    public ArticleRedisEntity getRedisEntityByNosqlId(String nosqlId) {
        Optional<Article> article = articleRepository.findById(nosqlId);
        return ArticleRedisEntity.builder()
                .title(article.get().getTitle())
                .press(article.get().getPress())
                .journalist(article.get().getJournalist())
                .image(article.get().getImage())
                .content(article.get().getContent())
                .category(article.get().getCategory().toString())
                .build();
    }
}

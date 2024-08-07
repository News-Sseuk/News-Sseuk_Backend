package backend.newssseuk.springbootmongodb.redis;

import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleRedisCachingService {
    private final ArticleRepository articleRepository;
    private final ArticleRedisRepository articleRedisRepository;

    @Transactional
    @Cacheable(cacheNames="article", key = "#id", cacheManager = "cacheManager")
    public ArticleRedisEntity cashingArticles(String id) {
        // mongodb에서 기사 데이터 가져옴
        Optional<Article> article = articleRepository.findById(id);
        try {
            return articleRedisRepository.save(ArticleRedisEntity.builder()
                    .title(article.get().getTitle())
                    .press(article.get().getPress())
                    .journalist(article.get().getJournalist())
                    .image(article.get().getImage())
                    .content(article.get().getContent())
                    .category(article.get().getCategory().toString())
                    .build());
        }
        catch (Exception e) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
    }
}

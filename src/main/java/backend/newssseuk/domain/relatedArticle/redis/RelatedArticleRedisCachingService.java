package backend.newssseuk.domain.relatedArticle.redis;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatedArticleRedisCachingService {

    private final RelatedArticleRedisRepository relatedArticleRedisRepository;

    @Cacheable(cacheNames = "RecommendedArticleByContent", key = "#article.id", cacheManager = "cacheManager")
    public RelatedArticleRedisEntity cashingRelatedArticle(Article article, List<ArticleRedisEntity> articleList){
        try{
            List<ArticleResponseDto> articleResponseDtos = articleList.stream().map(redisArticle -> new ArticleResponseDto(redisArticle))
                    .collect(Collectors.toList());

            return relatedArticleRedisRepository.save(RelatedArticleRedisEntity.builder()
                    .articleId(article.getId())
                    .articleList(articleResponseDtos)
                    .build());
        } catch (Exception e) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
    }
}

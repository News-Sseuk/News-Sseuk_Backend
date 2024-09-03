package backend.newssseuk.domain.recommendedArticle.redis;

import backend.newssseuk.domain.user.User;
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
public class RecommendedArticleRedisCachingService {
    private final RecommendedArticleRedisRepository recommendedArticleRedisRepository;

    @Transactional
    @Cacheable(cacheNames = "RecommendedArticleByHistory", key = "#user.id",cacheManager = "cacheManager")
    public RecommendedArticleRedisEntity cashingPersonalRecommendedArticles(User user, List<ArticleRedisEntity> articleList){
        try {
            List<ArticleResponseDto> articleResponseDtos = articleList.stream().map(redisArticle -> new ArticleResponseDto(redisArticle))
                    .collect(Collectors.toList());
            return recommendedArticleRedisRepository.save(RecommendedArticleRedisEntity.builder()
                    .userId(user.getId())
                    .articleList(articleResponseDtos)
                    .build());
        }
        catch (Exception e) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
    }
}

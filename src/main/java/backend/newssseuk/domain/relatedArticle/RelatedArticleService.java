package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisCachingService;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisEntity;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisRepository;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatedArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final RelatedArticleRepository relatedArticleRepository;
    private final RelatedArticleRedisRepository relatedArticleRedisRepository;
    private final RelatedArticleRedisCachingService relatedArticleRedisCachingService;
    private final ArticleHelper articleHelper;

    @Transactional
    public List<ArticleResponseDto> collectingRelatedArticles(Long Id){
        Article article = jpaArticleRepository.findById(Id).orElse(null);

        RelatedArticleRedisEntity relatedArticleRedisEntity = relatedArticleRedisRepository.findByArticleId(article.getId());
        // 레디스에 저장된 값이 있을 때
        if (relatedArticleRedisEntity != null){
            return relatedArticleRedisEntity.getArticleList();
        } else {  // 레디스에 저장된 값이 없을 때
            RelatedArticle relatedArticle = relatedArticleRepository.findByArticle(article);
            List<Article> articleResponseList = relatedArticle.getArticleList().stream()
                    .map(articleId -> jpaArticleRepository.findById(articleId)
                            .orElseThrow()) // 예외 발생 시, 기본적으로 NoSuchElementException 던짐.
                    .toList();
            List<ArticleRedisEntity> articleList = articleResponseList.stream()
                    .map(mysqlArticle -> mysqlArticle.getMongoEntity(articleHelper))
                    .collect(Collectors.toList());
            return relatedArticleRedisCachingService.cashingRelatedArticle(article, articleList).getArticleList();
        }
    }

    @Transactional
    public void addRelatedArticle(Long articleId, List<Long> articleList){
        Article article = jpaArticleRepository.findById(articleId).orElse(null);
        RelatedArticle relatedArticle = relatedArticleRepository.save(RelatedArticle.builder()
                .article(article)
                .articleList(articleList)
                .build());
        relatedArticle.setArticle(article);
    }
}

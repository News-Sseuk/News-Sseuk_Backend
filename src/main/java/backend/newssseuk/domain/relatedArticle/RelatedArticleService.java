package backend.newssseuk.domain.relatedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.relatedArticle.dto.RelatedArticleResponseDto;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisEntity;
import backend.newssseuk.domain.relatedArticle.redis.RelatedArticleRedisRepository;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatedArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final RelatedArticleRepository relatedArticleRepository;
    private final RelatedArticleRedisRepository relatedArticleRedisRepository;
    private final ArticleHelper articleHelper;

    public List<ArticleResponseDto> collectingRelatedArticles(Long Id){
        RelatedArticleResponseDto relatedArticleResponseDto;
        Article article = jpaArticleRepository.findById(Id).orElse(null);

        RelatedArticleRedisEntity relatedArticleRedisEntity = relatedArticleRedisRepository.findByArticle(article);
        // 레디스에 저장된 값이 있을 때
        if (relatedArticleRedisEntity != null){
            relatedArticleResponseDto = new RelatedArticleResponseDto(relatedArticleRedisEntity);
        } else {  // 레디스에 저장된 값이 없을 때
            RelatedArticle relatedArticle = relatedArticleRepository.findByArticle(article);
            List<Article> articleResponseList = relatedArticle.getArticleList().stream()
                    .map(articleId -> jpaArticleRepository.findById(articleId)
                            .orElseThrow()) // 예외 발생 시, 기본적으로 NoSuchElementException 던짐.
                    .collect(Collectors.toList());
            relatedArticleResponseDto = new RelatedArticleResponseDto(cashingRelatedArticle(article, articleResponseList));
        }

        return relatedArticleResponseDto.getRelatedArticleList();
    }

    @Transactional
    @Cacheable(cacheNames = "RecommendedArticleByContent", key = "#id", cacheManager = "cacheManager")
    public RelatedArticleRedisEntity cashingRelatedArticle(Article article, List<Article> articleList){
        try{
            return relatedArticleRedisRepository.save(RelatedArticleRedisEntity.builder()
                    .article(article)
                    .articleList(articleList.stream().map(MysqlArticle -> MysqlArticle.getMongoEntity(articleHelper))
                            .collect(Collectors.toList()))
                    .build());
        } catch (Exception e) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
    }
}

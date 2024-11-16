package backend.newssseuk.springbootmongodb.redis;

import backend.newssseuk.domain.article.service.JpaArticleService;
import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleRedisCachingService {
    private final ArticleRepository articleRepository;
    private final ArticleRedisRepository articleRedisRepository;
    private final JpaArticleService jpaArticleService;

    @Transactional
    @Cacheable(cacheNames="article", key = "#id", cacheManager = "cacheManager")
    public ArticleRedisEntity cashingArticles(String id) {
        // mongodb에서 기사 데이터 가져옴
        Optional<Article> article = articleRepository.findById(id);
        backend.newssseuk.domain.article.Article jpaArticle = jpaArticleService.findByMongoId(id);

        try {
            return articleRedisRepository.save(ArticleRedisEntity.builder()
                    .id(article.get().getId())
                    .title(article.get().getTitle())
                    .press(article.get().getPress())
                    .journalist(article.get().getJournalist())
                    .image(article.get().getImage())
                    .content(article.get().getContent())
                    .category(article.get().getCategory().getKorean())
                    .publishedDate(article.get().getPublishedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))    // yyyy.MM.dd HH:mm 형식으로 변경하기
                    .hashTagList(Optional.ofNullable(jpaArticle.getArticleHashTagList())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(articleHashTag -> ArticleHashTagDTO.builder()
                                    .id(articleHashTag.getId())
                                    .articleId(articleHashTag.getArticle().getId()) // Article ID만 사용
                                    .hashTagName(articleHashTag.getHashTag().getName()) // HashTag 이름 사용
                                    .createdTime(articleHashTag.getCreatedTime())
                                    .build())
                            .collect(Collectors.toList()))
                    .reliability(jpaArticle.getReliability())
                    .summary(jpaArticle.getSummary())
                    .build());
        }
        catch (Exception e) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
    }
}

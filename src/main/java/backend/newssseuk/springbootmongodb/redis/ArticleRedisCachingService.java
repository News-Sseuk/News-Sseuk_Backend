package backend.newssseuk.springbootmongodb.redis;

import backend.newssseuk.domain.article.service.JpaArticleService;
import backend.newssseuk.domain.articleHashTag.ArticleHashTag;
import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import backend.newssseuk.domain.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

        if (article.isEmpty()) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
        String articleId = article.get().getId();
        String articleTitle = article.map(Article::getTitle).orElse(null);
        String press = article.map(Article::getPress).orElse(null);
        String journalist = article.map(Article::getJournalist).orElse(null);
        List<String> image = article.map(Article::getImage).orElse(null);
        String content = article.map(Article::getContent).orElse(null);
        String category = Optional.ofNullable(article.get().getCategory())
                .map(Category::getKorean)
                .orElse(null);
        String publishedDate = Optional.ofNullable(article.get().getPublishedDate())
                .map(date -> {
                    try {
                        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
                    } catch (DateTimeException e) {
                        return null;
                    }
                })
                .orElse(null);
        List<ArticleHashTag> hashTagList = jpaArticle.getArticleHashTagList();
        Integer reliability = jpaArticle.getReliability();
        String summary = jpaArticle.getSummary();

        return articleRedisRepository.save(ArticleRedisEntity.builder()
                .id(articleId)
                .title(articleTitle)
                .press(press)
                .journalist(journalist)
                .image(image)
                .content(content)
                .category(category)
                .publishedDate(publishedDate)
                .hashTagList(hashTagList)
                .reliability(reliability)
                .summary(summary)
                .build());
    }
}

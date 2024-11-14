package backend.newssseuk.domain.article.service;

import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import backend.newssseuk.springbootmongodb.ArticleService;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final JpaArticleService jpaArticleService;

    public List<ArticleThumbnailDTO> searchByKeyword(String cursorTime,String keyword, String onOff, String sort) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime cursorDateTime = LocalDateTime.parse(cursorTime, formatter);

        Pageable pageable = PageRequest.of(0, 20);
        List<Article> articleList = articleRepository.findByContentContainingAndPublishedDateLessThanOrderByPublishedDateDesc(keyword, cursorDateTime, pageable);
        boolean isJpaRequired = onOff.toLowerCase(Locale.ROOT).equals("on");

        Stream<backend.newssseuk.domain.article.Article> articleStream = articleList.stream()
                .map(article -> jpaArticleService.findByMongoId(article.getId()));

        if (isJpaRequired) {
            articleStream = articleStream.filter(article -> article.getReliability() > 60);
        }

        if (!sort.toLowerCase(Locale.ROOT).equals("latest")) {
            articleStream = articleStream.sorted(Comparator.comparingInt(backend.newssseuk.domain.article.Article::getReliability));
        }

        List<backend.newssseuk.domain.article.Article> articles = articleStream.toList();

        return isJpaRequired
                ? articleService.getArticleThumbnailsByJpa(articles)
                : articleService.getArticleThumbnailsByMongo(articleList);
    }
}

package backend.newssseuk.domain.article.service;

import backend.newssseuk.payload.exception.GeneralException;
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
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final JpaArticleService jpaArticleService;

    public List<ArticleThumbnailDTO> searchByKeyword(String time,String keyword, String onOff, String sort) {
        Pageable pageable = PageRequest.of(0, 40);
        time = time.replace(" ", "T");
        LocalDateTime cursorTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<Article> articleList = articleRepository.findByContentContainingAndPublishedDateLessThanOrderByPublishedDateDesc(keyword, cursorTime, pageable);
        boolean isJpaRequired = onOff.toLowerCase(Locale.ROOT).equals("on");

        Stream<backend.newssseuk.domain.article.Article> articleStream = articleList.stream()
                .map(article -> {
                    try {
                        backend.newssseuk.domain.article.Article jpaArticle = jpaArticleService.findByMongoId(article.getId());
                        if (jpaArticle.getReliability() == null) {
                            return null;
                        }
                        return jpaArticle;
                    } catch (GeneralException | NullPointerException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull);

        if (isJpaRequired) {
            articleStream = articleStream.filter(article -> article.getReliability() > 60);
        }

        if (!sort.toLowerCase(Locale.ROOT).equals("latest")) {
            articleStream = articleStream.sorted(Comparator.comparingInt(backend.newssseuk.domain.article.Article::getReliability).reversed());
        }

        List<backend.newssseuk.domain.article.Article> articles = articleStream.toList();

        return articleService.getArticleThumbnailsByJpa(articles);
    }
}

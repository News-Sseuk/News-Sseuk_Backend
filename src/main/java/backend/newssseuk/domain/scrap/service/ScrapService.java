package backend.newssseuk.domain.scrap.service;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.scrap.Scrap;
import backend.newssseuk.domain.scrap.dto.ScrapResponseDTO;
import backend.newssseuk.domain.scrap.repository.ScrapRepository;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.springbootmongodb.ArticleService;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScrapService {
    private final JpaArticleRepository jpaArticleRepository;
    private final ScrapRepository scrapRepository;
    private final ArticleService articleService;

    public String scrapArticleByArticleId(User user, String article_id){
        Article article = jpaArticleRepository.findByNosqlId(article_id).get();

        Scrap scrapEntity = Scrap.builder()
                .user(user)
                .article(article)
                .build();
        scrapRepository.save(scrapEntity);
        return article.getCategory().getKorean();
    }

    public ScrapResponseDTO getScrapArticlesByUser(User user, Category category, Long lastArticleId){
        List<Article> article_list =  scrapRepository.getUserArticleByCategory(user, category, lastArticleId);
        List<ArticleThumbnailDTO> thumbnailDTOS = articleService.getArticleThumbnailsByJpa(article_list);

        boolean hasNext = true;
        // 조회한 결과 개수가 요청한 페이지 사이즈보다 작을 경우, next = false
        if (article_list.size() < 3) {
            hasNext = false;
        }

        return ScrapResponseDTO.builder()
                .hasNext(hasNext)
                .articleList(thumbnailDTOS)
                .build();
    }

    public List<String> getScrapCategories(User user) {
        List<Category> categoryList = scrapRepository.getCategoryByUser(user);
        return categoryList.stream()
                .map(Category::getKorean)
                .collect(Collectors.toList());
    }
}
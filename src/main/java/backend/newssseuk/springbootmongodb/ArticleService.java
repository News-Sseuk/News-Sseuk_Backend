package backend.newssseuk.springbootmongodb;

import backend.newssseuk.config.ArticlesConfig;
import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.service.JpaArticleService;
import backend.newssseuk.domain.articleHashTag.service.ArticleHashTagService;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.enums.converter.CategoryConverter;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisCachingService;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRedisCachingService articleRedisCachingService;
    private final ArticleRedisRepository articleRedisRepository;
    private final CategoryConverter categoryConverter;
    private final EachArticleService eachArticleService;
    private final ThreadLocalService threadLocalService;
    private final ArticleHashTagService articleHashTagService;
    private final JpaArticleService jpaArticleService;
    private final ArticlesConfig articlesConfig;
    WebDriver webDriver;

    public void getCrawlingInfos() {
        List<String> urls = articlesConfig.getUrls();
        // aws의 t3micro 서버로 인한 카테고리별 순차 진행
        for (String url : urls) {
            crawlingByCategory(url);
        }
    }

    private void crawlingByCategory(String url) {
        int i=1;
        int div=1;
        webDriver = threadLocalService.getDriver();

        webDriver.get(url);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        wait.withTimeout(Duration.ofSeconds(5));  //5초 대기
        String categoryName = webDriver.findElement(By.xpath("//*[@id=\"newsct\"]/div[1]/div[1]/h3")).getAccessibleName();
        Category category = categoryConverter.fromKrCategory(categoryName);

        List<String> urlList = new ArrayList<>();

        // 기사들 url 수집
        while(true) {
            if (i > 6) {
                i = 1;
                div++;
            }

            WebElement timeElement = null;
            try {
                timeElement = webDriver.findElement(By.xpath(String.format("//*[@id=\"newsct\"]/div[2]/div/div[1]/div[%d]/ul/li[%d]/div/div/div[2]/div[2]/div[1]/div[2]/b",div , i)));
            } catch (Exception e) {
                timeElement = webDriver.findElement(By.xpath(String.format("//*[@id=\"newsct\"]/div[2]/div/div[1]/div[%d]/ul/li[%d]/div/div/div/div[2]/div[1]/div[2]/b",div , i)));
            }
            String timeText = timeElement.getText();
            int minutesAgo = Integer.parseInt(timeText.replaceAll("[^0-9]", ""));
            if (minutesAgo > 30 || timeText.substring(timeText.length() - 3).equals("시간전")) {
                break;
            } else {
                urlList.add(webDriver.findElement(By.xpath(String.format("//*[@id=\"newsct\"]/div[2]/div/div[1]/div[%d]/ul/li[%d]/div/div/div[2]/a",div ,i))).getAttribute("href"));
                i++;
                //디버깅  //*[@id="newsct"]/div[2]/div/div[1]/div[1]/ul/li[4]/div/div/div/div[2]/div[1]/div[2]/b
                System.out.println(category.getKorean());
            }
        }
        eachArticleService.getEachArticles(category, urlList);
        threadLocalService.quitDriver();
    }


    public ArticleResponseDto findArticles(String id) {
        // redis에 있는 지 찾아보고
        // 등록 안되어있으면, mongodb에서 findById 해서 등록 (cashingArticles 함수 실행)
        Optional<ArticleRedisEntity> articleRedisEntity = articleRedisRepository.findById(id);
        if (articleRedisEntity.isPresent()) {
            return new ArticleResponseDto(articleRedisEntity.get());
        } else {
            ArticleRedisEntity cashed_article = articleRedisCachingService.cashingArticles(id);
            return new ArticleResponseDto(cashed_article);}
    }

    public List<ArticleThumbnailDTO> findArticleThumbnails(String categoryString, LocalDateTime cursorTime) {
        if(cursorTime == null) {
            cursorTime = LocalDateTime.now();
        }
        Category category = categoryConverter.fromKrCategory(categoryString);
        List<Article> jpaArticleList = jpaArticleService.findAllByCategoryOrderByDate(category, cursorTime);
        return getArticleThumbnailsByJpa(jpaArticleList);
    }

    public List<ArticleThumbnailDTO> getArticleThumbnailsByJpa(List<Article> jpaArticleList) {
        List<ArticleThumbnailDTO> articleThumbnailDTOList = new ArrayList<>();
        for(Article jpaArticle : jpaArticleList) {
            ArticleResponseDto articleDto = findArticles(jpaArticle.getNosqlId());
            ArticleThumbnailDTO articleThumbnailDTO = ArticleThumbnailDTO.builder()
                    .id(articleDto.getId())
                    .title(articleDto.getTitle())
                    .description((articleDto.getContent().length() > 80) ? articleDto.getContent().substring(0, 80) : articleDto.getContent())
                    .publishedDate(jpaArticle.getCrawledTime())
                    .category(jpaArticle.getCategory().getKorean())
                    .hashTagList(articleHashTagService.getHashTagListByArticleId(jpaArticle.getId()))
                    .reliability(jpaArticle.getReliability())
                    .build();
            articleThumbnailDTOList.add(articleThumbnailDTO);
        }
        return articleThumbnailDTOList;
    }

    public List<ArticleThumbnailDTO> getArticleThumbnailsByMongo(List<backend.newssseuk.springbootmongodb.Article> articleList) {
        List<ArticleThumbnailDTO> articleThumbnailDTOList = new ArrayList<>();
        for(backend.newssseuk.springbootmongodb.Article article : articleList) {
            Article jpaArticle = jpaArticleService.findByMongoId(article.getId());
            ArticleResponseDto articleDto = findArticles(article.getId());
            ArticleThumbnailDTO articleThumbnailDTO = ArticleThumbnailDTO.builder()
                    .id(articleDto.getId())
                    .title(articleDto.getTitle())
                    .description((articleDto.getContent().length() > 80) ? articleDto.getContent().substring(0, 80) : articleDto.getContent())
                    .publishedDate(article.getPublishedDate())
                    .category(article.getCategory().getKorean())
                    .hashTagList(articleHashTagService.getHashTagListByArticleId(jpaArticle.getId()))
                    .reliability(jpaArticle.getReliability())
                    .build();
            articleThumbnailDTOList.add(articleThumbnailDTO);
        }
        return articleThumbnailDTOList;
    }


}

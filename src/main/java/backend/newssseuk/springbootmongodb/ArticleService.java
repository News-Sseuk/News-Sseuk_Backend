package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.springbootmongodb.converter.CategoryConverter;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisRepository;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    @Value("${chrome.driver.path}")
    private String chromeDriverPath;

    private final ArticleRepository articleRepository;
    private final ArticleRedisRepository articleRedisRepository;
    private final JpaArticleRepository jpaArticleRepository;
    private final CategoryConverter categoryConverter;

    WebDriver webDriver;

    //@Scheduled(fixedDelay = 2000) // 밀리세컨 단위
    public void getCrawlingInfos(String url) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        webDriver = new ChromeDriver();
        webDriver.get(url);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        wait.withTimeout(Duration.ofSeconds(5));  //5초 대기

        String name = webDriver.findElement(By.xpath("//*[@id=\"newsct\"]/div[1]/div[1]/h3")).getText();
        Category category = categoryConverter.fromKrCategory(name);
        List<WebElement> articleElementList = webDriver.findElements(By.cssSelector(".sa_text"));
        List<String> urlList = new ArrayList<>();

        // 기사들 url 수집
        for (WebElement articleEl : articleElementList) {
            WebElement timeElement = articleEl.findElement(By.cssSelector(".sa_text_datetime.is_recent b"));
            String timeText = timeElement.getText();
            int minutesAgo = Integer.parseInt(timeText.replaceAll("[^0-9]", ""));
            if (minutesAgo > 30) {
                break;
            }
            urlList.add(articleEl.findElement(By.cssSelector(".sa_text_title")).getAttribute("href"));
        }

        // 개별 기사 데이터 수집
        for (String articleUrl : urlList){
            webDriver.get(articleUrl);
            WebElement elementTitle = webDriver.findElement(By.cssSelector(".media_end_head_headline"));

            WebElement elementJournalist = null;
            try {
                elementJournalist = webDriver.findElement(By.cssSelector(".media_end_head_journalist_box"));
            } catch (Exception e1) {
                try {
                    elementJournalist = webDriver.findElement(By.cssSelector(".media_end_head_journalist_name"));
                }
                // 아예 기자 데이터가 없을 때 (본문 안에 포함되어 있는 경우)
                catch (Exception e2) {
                }
            }

            WebElement elementPress = webDriver.findElement(By.cssSelector(".media_end_head_top_logo_img"));
            WebElement elementContent = webDriver.findElement(By.cssSelector(".newsct_article"));
            List<WebElement> elementImage = webDriver.findElements(By.cssSelector(".nbd_a img"));

            List<String> imageList = new ArrayList<>();
            for (WebElement imageEl : elementImage){
                imageList.add(imageEl.getAttribute("src"));
            }

            // 기자 데이터가 없는 경우 null 처리
            String journalistName = (elementJournalist != null) ? elementJournalist.getText() : null;

            Article article = Article.builder()
                    .category(category)
                    .title(elementTitle.getText())
                    .journalist(journalistName)
                    .press(elementPress.getAttribute("alt"))
                    .content(elementContent.getText())
                    .image(imageList)
                    .build();
            Article savedArticle = articleRepository.save(article);
            backend.newssseuk.domain.article.Article jpaArticle = backend.newssseuk.domain.article.Article.builder()
                    .nosqlId(savedArticle.getId())
                    .build();
            jpaArticleRepository.save(jpaArticle);
        }

        webDriver.quit();
    }

    @Transactional
    @Cacheable(value = "Article", key = "#articleId", cacheManager = "cacheManager")
    public ArticleRedisEntity cashingArticles(String id) {
        // mongodb에서 기사 데이터 가져옴
        Optional<Article> article = articleRepository.findById(id);
        try {
            return articleRedisRepository.save(ArticleRedisEntity.builder()
                    .title(article.get().getTitle())
                    .press(article.get().getPress())
                    .journalist(article.get().getJournalist())
                    .image(article.get().getImage())
                    .content(article.get().getContent())
                    .category(article.get().getCategory())
                    .build());
        }
        catch (Exception e) {
            throw new NoSuchElementException("해당 데이터가 없습니다.");
        }
    }


    public ArticleResponseDto findArticles(String id) {
        // redis에 있는 지 찾아보고
        // 등록 안되어있으면, mongodb에서 findById 해서 등록 (cashingArticles 함수 실행)
        Optional<ArticleRedisEntity> articleRedisEntity = articleRedisRepository.findById(id);
        if (articleRedisEntity.isPresent()) {
            return new ArticleResponseDto(articleRedisEntity.get());
        } else {
            ArticleRedisEntity cashed_article = cashingArticles(id);
            return new ArticleResponseDto(cashed_article);}
    }
}

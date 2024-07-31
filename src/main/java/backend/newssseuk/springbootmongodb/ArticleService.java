package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.springbootmongodb.converter.CategoryConverter;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleRedisRepository articleRedisRepository;
    private final CategoryConverter categoryConverter;
    private final EachArticleService eachArticleService;
    private final ThreadLocalService threadLocalService;

    WebDriver webDriver;

    public void getCrawlingInfos(String url) {
        webDriver = threadLocalService.getDriver();

        webDriver.get(url);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        wait.withTimeout(Duration.ofSeconds(5));  //5초 대기

        List<WebElement> categoryList = webDriver.findElements(By.cssSelector("#ct_wrap > div.ct_scroll_wrapper > div.column0 > div > ul > li > a"));


        for (int i=1; i<=categoryList.size(); i++) {
            String category_xpath = String.format("//*[@id=\"ct_wrap\"]/div[2]/div[1]/div/ul/li[%d]/a", i);
            String category_button_url = webDriver.findElement(By.xpath(category_xpath)).getAttribute("href");
            String category_name = webDriver.findElement(By.xpath(category_xpath)).getText();
            Category category = categoryConverter.fromKrCategory(category_name);

            webDriver.get(category_button_url);

            List<WebElement> articleElementList = webDriver.findElements(By.cssSelector(".sa_text"));
            List<String> urlList = new ArrayList<>();

            // 기사들 url 수집
            for (WebElement articleEl : articleElementList) {
                WebElement timeElement = articleEl.findElement(By.cssSelector(".sa_text_datetime b"));
                String timeText = timeElement.getText();
                int minutesAgo = Integer.parseInt(timeText.replaceAll("[^0-9]", ""));
                if (minutesAgo > 30) {
                    continue;
                }
                urlList.add(articleEl.findElement(By.cssSelector(".sa_text_title")).getAttribute("href"));
            }
            eachArticleService.getEachArticles(category, urlList);
            //디버깅
            System.out.println(category.getKorean());
        }
        threadLocalService.quitDriver();
    }


    @Transactional
    @Cacheable(key = "#id", cacheManager = "cacheManager")
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

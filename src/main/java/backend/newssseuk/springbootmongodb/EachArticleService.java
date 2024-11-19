package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.article.service.JpaArticleService;
import backend.newssseuk.domain.enums.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EachArticleService {

    private final ArticleRepository articleRepository;
    private final JpaArticleRepository jpaArticleRepository;
    private final ThreadLocalService threadLocalService;
    private final JpaArticleService jpaArticleService;

    WebDriver webDriver;

    @Async("executor")
    public void getEachArticles(Category category, List<String> urlList) throws Exception {
        webDriver = threadLocalService.getDriver();

        for (String articleUrl : urlList) {
            // 개별 기사 데이터 수집
            webDriver.get(articleUrl);

            WebElement elementTitle;
            try {
                elementTitle = webDriver.findElement(By.cssSelector(".media_end_head_headline"));
            } catch (Exception e1) {
                // 게임 ・ 리뷰 카테고리 예외처리 (데일리e스포츠)
                elementTitle = webDriver.findElement(By.cssSelector(".NewsEndMain_article_title__kqEzS"));
            }


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
            WebElement elementTime = webDriver.findElement(By.cssSelector("span.media_end_head_info_datestamp_time"));
            String date = elementTime.getAttribute("data-date-time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            List<String> imageList = new ArrayList<>();
            for (WebElement imageEl : elementImage) {
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
                    .publishedDate(dateTime)
                    .build();
            Article savedArticle = articleRepository.save(article);
            backend.newssseuk.domain.article.Article jpaArticle = backend.newssseuk.domain.article.Article.builder()
                    .category(category)
                    .crawledTime(LocalDateTime.now())
                    .nosqlId(savedArticle.getId())
                    .build();
            backend.newssseuk.domain.article.Article savedJpaArticle = jpaArticleRepository.save(jpaArticle);
            jpaArticleService.saveArticleDetailByAI("http://52.78.251.30:80/article/detail",savedJpaArticle.getId());
            /*retryTemplate.execute(context -> {
                saveArticleDetailWithRetry(savedJpaArticle.getId());
                return null;
            });*/
        }
        threadLocalService.quitDriver();
    }

    @Async("executor")
    public void getArticle(List<String> urlList) throws Exception {
        webDriver = threadLocalService.getDriver();

        for (String articleUrl : urlList) {
            webDriver.get(articleUrl);

            WebElement elementTitle = null;
            try {
                elementTitle = webDriver.findElement(By.xpath("//*[@id=\"articleView\"]/h1"));
            } catch (Exception e) {
            }

            WebElement elementPress = webDriver.findElement(By.xpath("//*[@id=\"articleView\"]/p/span[1]/a[1]"));

            WebElement elementImage = null;
            try {
                elementImage = webDriver.findElement(By.xpath("//*[@id=\"articleImage0\"]/span/span/img"));
            } catch (Exception e) {
                elementImage = webDriver.findElement(By.xpath("//*[@id=\"mainimg0\"]"));
            }

            WebElement articleBody = webDriver.findElement(By.id("realArtcContents"));

            Article article = Article.builder()
                    .category(Category.MOBILE)
                    .title(elementTitle.getText())
                    .press(elementPress.getText())
                    .content(articleBody.getText())
                    .image(Collections.singletonList(elementImage.getAttribute("src")))
                    .publishedDate(LocalDateTime.now())
                    .build();
            Article savedArticle = articleRepository.save(article);

            backend.newssseuk.domain.article.Article jpaArticle = backend.newssseuk.domain.article.Article.builder()
                    .category(Category.MOBILE)
                    .crawledTime(LocalDateTime.now())
                    .nosqlId(savedArticle.getId())
                    .build();
            backend.newssseuk.domain.article.Article savedJpaArticle = jpaArticleRepository.save(jpaArticle);
            jpaArticleService.saveArticleDetailByAI("http://52.78.251.30:80/article/detail",savedJpaArticle.getId());
/*
            retryTemplate.execute(context -> {
                saveArticleDetailWithRetry(savedJpaArticle.getId());
                return null;
            });
*/
        }
        threadLocalService.quitDriver();
    }

/*    @Retryable(value = {HttpServerErrorException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    private void saveArticleDetailWithRetry(Long articleId) throws Exception {
        jpaArticleService.saveArticleDetailByAI("http://52.78.251.30:80/article/detail", articleId);
    }*/
}
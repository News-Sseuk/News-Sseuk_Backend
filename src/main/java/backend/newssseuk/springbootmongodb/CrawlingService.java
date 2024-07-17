package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    @Value("${chrome.driver.path}")
    private String chromeDriverPath;
    private final ArticleRepository articleRepository;
    private final JpaArticleRepository jpaArticleRepository;

    WebDriver webDriver;
    public void getCrawlingInfos(String url) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        webDriver = new ChromeDriver();
        webDriver.get(url);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        wait.withTimeout(Duration.ofSeconds(5));  //5초 대기

        List<WebElement> articleElementList = webDriver.findElements(By.xpath("//*[@id=\"newsct\"]/div[4]"));
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
                    .title(elementTitle.getText())
                    .journalist(journalistName)
                    .press(elementPress.getAttribute("alt"))
                    .content(elementContent.getText())
                    .image(imageList)
                    .build();
            Article savedArticle = articleRepository.save(article);
            System.out.println("!!!!!!!!!!!"+savedArticle.getId());
            backend.newssseuk.domain.article.Article jpaArticle = backend.newssseuk.domain.article.Article.builder()
                    .nosqlId(savedArticle.getId())
                    .build();
            jpaArticleRepository.save(jpaArticle);
        }

        webDriver.quit();
    }
}

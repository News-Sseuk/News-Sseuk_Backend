package backend.newssseuk.springbootmongodb;

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
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    @Value("${chrome.driver.path}")
    private String chromeDriverPath;
    private final ArticleRepository articleRepository;

    WebDriver webDriver;
    public void getCrawlingInfos() {
        int i = 1;

        // 네이버 뉴스 (정치 section 중 헤드라인 뉴스)
        String url = "https://news.naver.com/section/100";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        webDriver = new ChromeDriver();
        webDriver.get(url);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        wait.withTimeout(Duration.ofSeconds(5));  // 5초 대기

        List<WebElement> articleElementList = webDriver.findElements(By.cssSelector(".sa_text_title"));
        List<String> urlList = new ArrayList<>();

        // 기사들 url 수집
        for (WebElement articleEl : articleElementList){
            urlList.add(articleEl.getAttribute("href"));
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

            articleRepository.save(article);
        }

        webDriver.quit();
    }
}

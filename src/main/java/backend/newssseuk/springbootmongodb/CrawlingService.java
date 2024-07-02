package backend.newssseuk.springbootmongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    @Value("${chrome.driver.path}")
    private String chromeDriverPath;

    private final ArticleRepository articleRepository;
    WebDriver driver;
    public List<Article> getCrawlingInfos() {
        int i = 1;

        // 네이버 뉴스 (정치 section 중 헤드라인 뉴스)
        String url = "https://news.naver.com/section/100";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        while (true) {
            try {
                WebElement timeElement = driver.findElement(By.xpath(String.format("//*[@id=\"newsct\"]/div[4]/div/div[1]/div[1]/ul/li[%d]/div/div/div[2]/div[2]/div[1]/div[2]/b", i)));
                String timeText = timeElement.getText();
                int minutesAgo = Integer.parseInt(timeText.replaceAll("[^0-9]", ""));

                if (minutesAgo <= 30) {
                    WebElement articleLink = driver.findElement(By.xpath(String.format("//*[@id=\"newsct\"]/div[4]/div/div[1]/div[1]/ul/li[%d]/div/div/div[2]/a/strong", i)));
                    articleLink.click();

                    WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"title_area\"]/span")));
                    WebElement pressElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ct\"]/div[1]/div[1]/a/img[1]")));
                    WebElement contentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dic_area\"]")));

                    Article article = Article.builder()
                            .title(titleElement.getText())
                            .press(pressElement.getAccessibleName())
                            .content(contentElement.getText())
                            .build();

                    articleRepository.save(article);

                    driver.navigate().back();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"newsct\"]")));
                } else {
                    break;
                }
                i++;
            } catch (NoSuchElementException e) {
                System.out.println("No more articles found or an element was not found.");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        driver.quit();
        return null;
    }
}

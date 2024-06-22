package backend.newssseuk.springbootmongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final ArticleRepository articleRepository;
    private WebDriver webDriver;
    public List<Article> getCrawlingInfos() {
        List<Article> crawlingInfoList = new ArrayList<>();

        // 네이버 뉴스 (정치 section 중 헤드라인 뉴스)
        String url = "https://news.naver.com/section/100";

        System.setProperty("chromedriver", "/Users/igahyeon/Desktop/News_seuk/News-Sseuk_Backend/src/main/resources/static/chromedriver-mac-arm64");
        webDriver = new ChromeDriver();
        webDriver.get(url);
        System.out.println("url 접근 완료");

        List<WebElement> articleElementList = webDriver.findElements(By.cssSelector("#_SECTION_HEADLINE_LIST_e3h8a > li:nth-child(1) > div > div > div.sa_text"));
        List<String> urlList = new ArrayList<>();

        for (WebElement articleEl : articleElementList){
            urlList.add(articleEl.getAttribute("href"));
        }

        for (String articleUrl : urlList){
            webDriver.get(articleUrl);
            WebElement elementTitle = webDriver.findElement(By.cssSelector("#title_area > span"));
            WebElement elementPress = webDriver.findElement(By.cssSelector("#ct > div.media_end_head.go_trans > div.media_end_head_top._LAZY_LOADING_WRAP > a > img.media_end_head_top_logo_img.light_type._LAZY_LOADING._LAZY_LOADING_INIT_HIDE"));
            WebElement elementContent = webDriver.findElement(By.cssSelector("#dic_area"));
            WebElement elementImage = webDriver.findElement(By.cssSelector("#img1"));

            Article article = Article.builder()
                    .title(elementTitle.getText())
                    .press(elementPress.getText())
                    .content(elementContent.getText())
                    .image(elementImage.getAttribute("alt style src"))
                    .build();

            articleRepository.save(article);
            crawlingInfoList.add(article);
        }

        webDriver.close();    //탭 닫기
        webDriver.quit();    //브라우저 닫기

        return crawlingInfoList;
    }
}

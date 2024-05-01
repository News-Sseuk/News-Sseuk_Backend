package backend.newssseuk.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;
@Component
//테스트용 코드
public class CrawlingEx {

    public static void main(String[] args) {
        WebDriver driver;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\MIRAEEN\\Downloads\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://n.news.naver.com/article/094/0000011734?cds=news_media_pc&type=editn");

        WebElement metaTag = driver.findElement(By.cssSelector("meta[property='og:title']"));
        String titleContent = metaTag.getAttribute("content");

        System.out.println("og:title 속성의 값: " + titleContent);


        driver.close();    //탭 닫기
        driver.quit();    //브라우저 닫기
    }
}

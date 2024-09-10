package backend.newssseuk.springbootmongodb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThreadLocalService {
    @Value("${chrome.driver.path}")
    private String chromeDriverPath;
    private final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public WebDriver getDriver(){
        if (webDriver.get() == null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);

            //성능용
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");

            webDriver.set(new ChromeDriver());
        }
        return webDriver.get();
    }
    public void quitDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }
}

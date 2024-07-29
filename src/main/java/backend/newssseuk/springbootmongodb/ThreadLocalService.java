package backend.newssseuk.springbootmongodb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class DriverFactory {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }
    // инициация браузера
    public void initDrive(){
        if ("firefox".equals(System.getProperty("browser"))) {
        startFirefox();
        }
        else {
            startChrome();
        }
    }
    // запуск Chrome
    public void startChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-first-run", "--no-default-browser-check", "--disable-default-apps", "--start-maximized");
        options.addArguments("--disable-features=DefaultBrowserSettingEnabled");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

// запуск Firefox
    public void startFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}

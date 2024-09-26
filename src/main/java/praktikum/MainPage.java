package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    protected String mainPageURL = "https://qa-scooter.praktikum-services.ru/";
    // локаторы кнопок "Заказать" (в хедере и футере)
    private final By HeaderOrderButton = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']");
    private final By FooterOrderButton = By.cssSelector(".Button_Button__ra12g.Button_UltraBig__UU3Lp");
    // локатор кнопки для закрытия окна с куками
    private final By cookieConfirmButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void startMainPage() {
        driver.get(mainPageURL);
        // ожидание и нажатие на кнопку "да все привыкли", если она присутствует   try-catch: если кнопка с куками не появляется (если она уже была закрыта в предыдущем тесте), тест продолжит работу
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(cookieConfirmButton));
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Кнопка подтверждения куки не найдена, продолжаем тестирование.");
        }
}

    // метод для скролинга страницы
    private void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // клик на кнопку "Заказать" в хедере
    public OrderPage clickOnHeaderOrder() {
        driver.findElement(HeaderOrderButton).click();
        return new OrderPage(driver);
    }

    // клик на кнопку "Заказать" в футере с прокруткой до кнопки
    public OrderPage clickOnFooterOrder() {
        scrollToElement(FooterOrderButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(FooterOrderButton)).click();
        return new OrderPage(driver);
    }

    // метод для клика на вопрос
    public void clickOnQuestion(By questionLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(questionLocator)).click();
    }

    // метод для получения текста ответа на вопрос + скролинг
    public String getAnswerText(By answerLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        scrollToElement(answerLocator); // скролинг
        return driver.findElement(answerLocator).getText();
    }
}

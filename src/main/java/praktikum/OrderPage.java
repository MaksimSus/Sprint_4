package praktikum;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    // поля для страницы OrderPageOne
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By placeField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");
    // поля для страницы OrderPageTwo
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentPeriodDropdown = By.className("Dropdown-control");
    private final By rentPeriodOption = By.xpath(".//div[@class='Dropdown-menu']/div[text()='двое суток']");
    private final By scooterColorBlack = By.id("black");
    private final By scooterColorGrey = By.id("grey");
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    // поля для всплывающего окна подтверждения
    private final By confirmOrderModal = By.className("Order_Modal__YZ-d3");
    private final By confirmOrderYesButton = By.xpath(".//button[text()='Да']");
    private final By confirmOrderSuccessMessage = By.xpath(".//div[contains(text(),'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // заполнение данных на первой странице
    public void inputOrderDetailsPageOne(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(placeField).sendKeys(address);
        driver.findElement(metroField).click();
        // ожидание, пока выпадающий список не станет видимым
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By metroOption = By.xpath("//button[contains(@class, 'select-search__option') and .//div[text()='" + metro + "']]");
        wait.until(ExpectedConditions.elementToBeClickable(metroOption));
        driver.findElement(metroOption).click();
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // заполнение данных на второй странице
    public void inputOrderDetailsPageTwo(String date, boolean chooseBlack, String comment) {
        driver.findElement(dateField).click();
        // ожидание появления календаря и кликаем на нужную дату
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By calendarDay = By.xpath("//div[contains(@class, 'react-datepicker__day') and @aria-label='Choose " + date + "']");
        wait.until(ExpectedConditions.elementToBeClickable(calendarDay));
        driver.findElement(calendarDay).click();
        driver.findElement(rentPeriodDropdown).click();
        driver.findElement(rentPeriodOption).click();

        if (chooseBlack) {
            driver.findElement(scooterColorBlack).click();
        } else {
            driver.findElement(scooterColorGrey).click();
        }

        driver.findElement(commentField).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        driver.findElement(orderButton).click();
    }

    // подтверждение заказа
    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrderModal)); // ожидание появления  окна
        driver.findElement(confirmOrderYesButton).click();

        // проверка произошло ли одно из двух событий (появление сообщения об успешном заказе или исчезновение окна)
        boolean isOrderSuccessful;
        try {
            isOrderSuccessful = wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(confirmOrderSuccessMessage),
                    ExpectedConditions.invisibilityOfElementLocated(confirmOrderModal)
            ));
        } catch (TimeoutException e) {
            // Ничего не произошло в течение 5 секунд
            System.out.println("Возникла ошибка, не сработало подтверждение заказа");
        }
    }
}

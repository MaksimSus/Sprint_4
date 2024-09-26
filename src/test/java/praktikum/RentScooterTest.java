package praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class RentScooterTest {
    private WebDriver driver;
    private DriverFactory factory = new DriverFactory();

    // параметры для тестов
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final boolean chooseBlack;
    private final String comment;
    // конструктор для получения параметров
    public RentScooterTest(String name, String surname, String address, String metro, String phone, String date, boolean chooseBlack, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.chooseBlack = chooseBlack;
        this.comment = comment;
    }

    // метод для получения данных для тестов
    @Parameterized.Parameters
    public static Collection<Object[]> getOrderData() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "ул. Городская", "Бульвар Рокоссовского", "+9876543210", "среда, 2-е октября 2024 г.", true, "Позвонить по приезду"},
                {"Петр", "Петров", "ул. Садовая", "Сокольники", "+9123456789", "пятница, 4-е октября 2024 г.", false, "Оставить у двери"}
        });
    }

    @Before
    public void initDrive() {
      factory.initDrive();
      driver = factory.getDriver();
        }

    // тест на заказ через верхнюю кнопку
    @Test
    public void testFullOrderFlowFromHeaderButton() throws Exception {
        var mainPage = new MainPage(driver);

        // главная страница
        mainPage.startMainPage();

        // переход на страницу заказа через верхнюю кнопку
        var orderPage = mainPage.clickOnHeaderOrder();

        // заполнение первой страницы заказа
        orderPage.inputOrderDetailsPageOne(name, surname, address, metro, phone);

        // заполнение второй страницы заказа
        orderPage.inputOrderDetailsPageTwo(date, chooseBlack, comment);

        // подтверждение заказа
        orderPage.confirmOrder();
    }

    // тест на заказ через нижнюю кнопку
    @Test
    public void testFullOrderFlowFromFooterButton() throws Exception {
        var mainPage = new MainPage(driver);

        // главная страница
        mainPage.startMainPage();

        // переход на страницу заказа через нижнюю кнопку
        var orderPage = mainPage.clickOnFooterOrder();

        // заполнение первой страницы заказа
        orderPage.inputOrderDetailsPageOne(name, surname, address, metro, phone);

        // заполнение второй страницы заказа
        orderPage.inputOrderDetailsPageTwo(date, chooseBlack, comment);

        // подтверждение заказа
        orderPage.confirmOrder();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
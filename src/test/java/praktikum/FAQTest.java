package praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FAQTest {
    private WebDriver driver;
    private DriverFactory factory = new DriverFactory();
    private final String question;
    public static String expectedAnswer;
    public static By questionLocator;
    public static By answerLocator;

    // конструктор для параметризации FAQ
    public FAQTest(String question, String expectedAnswer, By questionLocator, By answerLocator) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
        this.questionLocator = questionLocator;
        this.answerLocator = answerLocator;
    }

    // параметры для тестов FAQ
    @Parameterized.Parameters
    public static Collection<Object[]> getFAQData() {
        return Arrays.asList(new Object[][]{
                // 1 вопрос и ответ
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                        By.id("accordion__heading-0"), By.id("accordion__panel-0")},
                // 2 вопрос и ответ
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
                        By.id("accordion__heading-1"), By.id("accordion__panel-1")},
                // 3 вопрос и ответ
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
                        By.id("accordion__heading-2"), By.id("accordion__panel-2")},
                // 4 вопрос и ответ
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
                        By.id("accordion__heading-3"), By.id("accordion__panel-3")},
                // 5 вопрос и ответ
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
                        By.id("accordion__heading-4"), By.id("accordion__panel-4")},
                // 6 вопрос и ответ
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
                        By.id("accordion__heading-5"), By.id("accordion__panel-5")},
                // 7 вопрос и ответ
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
                        By.id("accordion__heading-6"), By.id("accordion__panel-6")},
                // 8 вопрос и ответ
                {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.",
                        By.id("accordion__heading-7"), By.id("accordion__panel-7")}
        });
    }

    @Before
    public void initDrive() {
        factory.initDrive();
        driver = factory.getDriver();
    }

    // тест на проверку раскрытия вопроса и отображения корректного текста ответа
    @Test
    public void testFAQAnswers() throws Exception {
        var mainPage = new MainPage(driver);

        // открытие главной страницы
        mainPage.startMainPage();

        // клик на вопрос
        mainPage.clickOnQuestion(questionLocator);

        // получение текста ответа
        String actualAnswer = mainPage.getAnswerText(answerLocator);

        // сверка текст ответа с ожидаением
        assert actualAnswer.equals(expectedAnswer);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
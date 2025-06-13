import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;


public class CreditbankTest {
    SelenideElement searchInput = $("[name='query']");
    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        open("http://37.203.243.26:5000/search"); }

    @Test
    @DisplayName("Поле поиска не должно быть типа password")
    void searchInputShouldNotBePasswordType() {
        searchInput.shouldBe(visible);
        String inputType = searchInput.getAttribute("type");
        assert !inputType.equalsIgnoreCase("password") : "Ожидалось обычное текстовое поле, но найдено поле типа password";
    }

    @Test
    @DisplayName("Кнопка ведёт не к результатам, а на новую страницу поиска")
    void backToListButtonShouldReturnToResultsNotSearchPage() {
        searchInput.setValue("Иван");
        $x("//button[@type='submit']").click();
        $x("//div[contains(@class,'card')][.//h5[contains(text(),'Иванов Иван')]]//a[contains(text(),'Подробнее')]").click();
        $(".btn-outline-primary").click();
        webdriver().shouldHave(urlContaining("/search"));
    }
}

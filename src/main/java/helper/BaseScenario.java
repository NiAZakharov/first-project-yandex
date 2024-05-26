package helper;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.junit5.TextReportExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

import java.util.Optional;

/**
 * Базовый тестовый класс. Его логику наследуют все тесты
 */
@ExtendWith({SoftAssertsExtension.class, TextReportExtension.class})
@Resource.Classpath("./config.properties")
public class BaseScenario {
    @Property("base_url")
    public static String URL;

    static {
        PropertyLoaderStatic.populate(BaseScenario.class);
    }

    @BeforeEach
    public void initialize() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";

//        WebDriverManager.chromedriver().setup();
//            Configuration.browser = "chrome";

//        if ("firefox".equals(System.getProperty("browser"))) {
//            WebDriverManager.firefoxdriver().setup();
//            Configuration.browser = "firefox";
//
//        } else {
//            WebDriverManager.chromedriver().setup();
//            Configuration.browser = "chrome";
//        }

        Configuration.headless = Optional.ofNullable(System.getProperty("headless"))
                .map(Boolean::parseBoolean)
                .orElse(false);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Selenide.open(URL);
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWindow();
    }
}

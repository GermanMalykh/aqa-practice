package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.Attach;

import java.util.List;
import java.util.Map;

public class TestBase {

    @BeforeAll
    public static void setupBrowser() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "eager";
        
        // CI/CD настройки
        if (System.getenv("CI") != null) {
            // Подключаемся к Selenoid
            Configuration.remote = System.getProperty("selenide.remote", "http://localhost:4444/wd/hub");
            
            // Уникальный профиль для каждого запуска
            String uniqueProfileDir = "/tmp/chrome-profile-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId();
            
            // Базовые Chrome capabilities для CI
            Configuration.browserCapabilities.setCapability("goog:chromeOptions", Map.of(
                "args", List.of(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--disable-web-security",
                    "--disable-extensions",
                    "--disable-plugins",
                    "--disable-images",
                    "--user-data-dir=" + uniqueProfileDir
                ),
                "prefs", Map.of(
                    "profile.default_content_setting_values.notifications", 2,
                    "profile.default_content_setting_values.media_stream", 2,
                    "profile.default_content_setting_values.geolocation", 2,
                    "profile.password_manager_enabled", false
                )
            ));
            
            System.out.println("CI environment detected. Using remote Selenoid at: " + Configuration.remote);
            System.out.println("Using unique Chrome profile: " + uniqueProfileDir);
        }
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.pageSource();
        Attach.pageHtml();
        Attach.screenshotAs("Last screenshot");
        Attach.browserConsoleLogs();
    }

    @AfterAll
    static void afterAll() {
        utils.AllureEnv.writeAllureEnvironment();
    }
}

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
    public static void localBrowser() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "eager";
        
        // Настройки для CI/CD окружения
        if (System.getenv("CI") != null) {
            // Загружаем конфигурацию из файла
            try {
                Configuration.loadProperties("selenide-ci.properties");
            } catch (Exception e) {
                System.out.println("Warning: Could not load selenide-ci.properties: " + e.getMessage());
            }
            
            // Устанавливаем remote URL
            Configuration.remote = System.getProperty("selenide.remote", "http://localhost:4444/wd/hub");
            
            // Генерируем уникальный профиль для каждого запуска
            String uniqueProfileDir = "/tmp/chrome-profile-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId();
            
            // Дополнительные настройки для предотвращения конфликта профилей
            if (Configuration.browserCapabilities.getCapability("goog:chromeOptions") == null) {
                Configuration.browserCapabilities.setCapability("goog:chromeOptions", Map.of(
                    "args", List.of(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--disable-web-security",
                        "--disable-features=VizDisplayCompositor",
                        "--disable-extensions",
                        "--disable-plugins",
                        "--disable-images",
                        "--disable-javascript",
                        "--disable-background-timer-throttling",
                        "--disable-backgrounding-occluded-windows",
                        "--disable-renderer-backgrounding",
                        "--disable-features=TranslateUI",
                        "--disable-ipc-flooding-protection",
                        "--disable-hang-monitor",
                        "--disable-prompt-on-repost",
                        "--disable-client-side-phishing-detection",
                        "--disable-component-extensions-with-background-pages",
                        "--disable-default-apps",
                        "--disable-sync",
                        "--metrics-recording-only",
                        "--no-first-run",
                        "--safebrowsing-disable-auto-update",
                        "--disable-blink-features=AutomationControlled",
                        "--user-data-dir=" + uniqueProfileDir,
                        "--remote-debugging-port=0",
                        "--disable-background-networking",
                        "--disable-default-apps",
                        "--disable-extensions",
                        "--disable-sync",
                        "--disable-translate",
                        "--hide-scrollbars",
                        "--mute-audio",
                        "--no-first-run",
                        "--safebrowsing-disable-auto-update",
                        "--ignore-certificate-errors",
                        "--ignore-ssl-errors",
                        "--ignore-certificate-errors-spki-list"
                    ),
                    "prefs", Map.of(
                        "profile.default_content_setting_values.notifications", 2,
                        "profile.default_content_setting_values.media_stream", 2,
                        "profile.default_content_setting_values.geolocation", 2,
                        "profile.default_content_setting_values.automatic_downloads", 1,
                        "profile.password_manager_enabled", false,
                        "profile.default_content_setting_values.popups", 2,
                        "profile.default_content_settings.popups", 0,
                        "profile.managed_default_content_settings.images", 2,
                        "profile.default_content_setting_values.images", 2
                    )
                ));
            }
            
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

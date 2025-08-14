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
import java.util.Properties;
import java.io.InputStream;

public class TestBase {

    @BeforeAll
    public static void localBrowser() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "eager";
        
        // Настройки для CI/CD окружения
        // Локально: использует локальный Chrome
        // В CI/CD: подключается к Selenoid на localhost:4444
        if (System.getenv("CI") != null) {
            // Загружаем конфигурацию из файла через системные свойства
            try {
                // Загружаем свойства из файла
                Properties props = new Properties();
                InputStream input = ClassLoader.getSystemResourceAsStream("selenide-ci.properties");
                if (input != null) {
                    props.load(input);
                    input.close();
                    
                    // Применяем свойства к Selenide Configuration
                    for (String key : props.stringPropertyNames()) {
                        String value = props.getProperty(key);
                        if (key.startsWith("selenide.")) {
                            String configKey = key.substring("selenide.".length());
                            try {
                                switch (configKey) {
                                    case "remote":
                                        Configuration.remote = value;
                                        break;
                                    case "timeout":
                                        Configuration.timeout = Long.parseLong(value);
                                        break;
                                    case "pollingInterval":
                                        Configuration.pollingInterval = Long.parseLong(value);
                                        break;
                                    case "holdBrowserOpen":
                                        Configuration.holdBrowserOpen = Boolean.parseBoolean(value);
                                        break;
                                    case "reopenBrowserOnFail":
                                        Configuration.reopenBrowserOnFail = Boolean.parseBoolean(value);
                                        break;
                                    case "remoteReadTimeout":
                                        Configuration.remoteReadTimeout = Long.parseLong(value);
                                        break;
                                    case "remoteConnectionTimeout":
                                        Configuration.remoteConnectionTimeout = Long.parseLong(value);
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Warning: Invalid numeric value for " + configKey + ": " + value);
                            }
                        }
                    }
                    System.out.println("Successfully loaded selenide-ci.properties");
                } else {
                    System.out.println("Warning: selenide-ci.properties not found in classpath, using system properties");
                    // Fallback: загружаем из системных свойств напрямую
                    loadConfigurationFromSystemPropertiesDirectly();
                }
            } catch (Exception e) {
                System.out.println("Warning: Could not load selenide-ci.properties: " + e.getMessage() + ", using system properties");
                // Fallback: загружаем из системных свойств напрямую
                loadConfigurationFromSystemPropertiesDirectly();
            }
            
            // Устанавливаем remote URL (если не был загружен из файла)
            if (Configuration.remote == null) {
                Configuration.remote = System.getProperty("selenide.remote", "http://localhost:4444/wd/hub");
            }
            
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
    
    /**
     * Загружает конфигурацию Selenide из системных свойств
     */
    private static void loadConfigurationFromSystemPropertiesDirectly() {
        try {
            // Устанавливаем основные настройки из системных свойств
            String remote = System.getProperty("selenide.remote");
            if (remote != null) {
                Configuration.remote = remote;
            }
            
            String timeout = System.getProperty("selenide.timeout");
            if (timeout != null) {
                Configuration.timeout = Long.parseLong(timeout);
            }
            
            String pollingInterval = System.getProperty("selenide.pollingInterval");
            if (pollingInterval != null) {
                Configuration.pollingInterval = Long.parseLong(pollingInterval);
            }
            
            String holdBrowserOpen = System.getProperty("selenide.holdBrowserOpen");
            if (holdBrowserOpen != null) {
                Configuration.holdBrowserOpen = Boolean.parseBoolean(holdBrowserOpen);
            }
            
            String reopenBrowserOnFail = System.getProperty("selenide.reopenBrowserOnFail");
            if (reopenBrowserOnFail != null) {
                Configuration.reopenBrowserOnFail = Boolean.parseBoolean(reopenBrowserOnFail);
            }
            
            String remoteReadTimeout = System.getProperty("selenide.remoteReadTimeout");
            if (remoteReadTimeout != null) {
                Configuration.remoteReadTimeout = Long.parseLong(remoteReadTimeout);
            }
            
            String remoteConnectionTimeout = System.getProperty("selenide.remoteConnectionTimeout");
            if (remoteConnectionTimeout != null) {
                Configuration.remoteConnectionTimeout = Long.parseLong(remoteConnectionTimeout);
            }
            
            System.out.println("Configuration loaded from system properties");
        } catch (Exception e) {
            System.out.println("Warning: Error loading configuration from system properties: " + e.getMessage());
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

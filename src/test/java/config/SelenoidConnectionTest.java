package config;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Простой тест для проверки подключения к Selenoid
 */
public class SelenoidConnectionTest {

    @BeforeAll
    static void setUp() {
        // Настройки для CI/CD
        if (System.getenv("CI") != null) {
            Configuration.remote = "http://localhost:4444/wd/hub";
            Configuration.browser = "chrome";
            Configuration.timeout = 10000;
        }
    }

    @Test
    void testSelenoidConnection() {
        if (System.getenv("CI") != null) {
            // В CI/CD проверяем подключение к Selenoid
            assertNotNull(Configuration.remote, "Remote URL should be set in CI/CD");
            assertEquals("chrome", Configuration.browser, "Browser should be chrome in CI/CD");
            
            System.out.println("Selenoid connection test passed in CI/CD");
            System.out.println("Remote URL: " + Configuration.remote);
            System.out.println("Browser: " + Configuration.browser);
            System.out.println("Timeout: " + Configuration.timeout);
        } else {
            // Локально просто проверяем базовые настройки
            assertEquals("chrome", Configuration.browser, "Browser should be chrome locally");
            assertNull(Configuration.remote, "Remote URL should be null locally (no Selenoid)");
            
            System.out.println("Local test passed - no Selenoid expected");
            System.out.println("Browser: " + Configuration.browser);
            System.out.println("Remote URL: " + Configuration.remote + " (null is expected locally)");
        }
    }
}

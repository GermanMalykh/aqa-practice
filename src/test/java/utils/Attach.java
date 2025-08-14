package utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Attach {
    private static final Logger logger = LoggerFactory.getLogger(Attach.class);
    public static final String CONFIG_REMOTE_URL_VIDEO = "https://selenoid.autotests.cloud/video/";

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                logger.warn("WebDriver не запущен, невозможно сделать скриншот: {}", attachName);
                return new byte[0];
            }
            return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (ClassCastException e) {
            logger.error("WebDriver не поддерживает создание скриншотов: {}", e.getMessage(), e);
            return new byte[0];
        } catch (Exception e) {
            logger.error("Ошибка при создании скриншота '{}': {}", attachName, e.getMessage(), e);
            return new byte[0];
        }
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                logger.warn("WebDriver не запущен, невозможно получить исходный код страницы");
                return "WebDriver not started".getBytes(StandardCharsets.UTF_8);
            }
            return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Ошибка при получении исходного кода страницы: {}", e.getMessage(), e);
            return "Error getting page source".getBytes(StandardCharsets.UTF_8);
        }
    }

    @Attachment(value = "Page (HTML)", type = "text/html", fileExtension = ".html")
    public static byte[] pageHtml() {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                logger.warn("WebDriver не запущен, невозможно получить HTML страницы");
                return "<html><body>WebDriver not started</body></html>".getBytes(StandardCharsets.UTF_8);
            }
            String html = WebDriverRunner.source(); // то же, что getWebDriver().getPageSource()
            String url = WebDriverRunner.url();

            // Вставим <base href="..."> сразу после <head>, чтобы относительные пути заработали
            String baseTag = "<base href=\"" + url + "\">";
            if (html.contains("<head")) {
                html = html.replaceFirst("(?i)<head(\\b[^>]*)>", "<head$1>" + baseTag);
            } else {
                // если вдруг <head> нет — просто добавим его
                html = "<head>" + baseTag + "</head>" + html;
            }

            // (опционально) чуть поможем со стилями, чтобы не разъезжалось
            String style = "<style>html,body{margin:0;padding:0;}</style>";
            html = html.replaceFirst("(?i)</head>", style + "</head>");

            return html.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Ошибка при получении HTML страницы: {}", e.getMessage(), e);
            return "<html><body>Error getting HTML</body></html>".getBytes(StandardCharsets.UTF_8);
        }
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message == null ? "" : message;
    }

    public static void browserConsoleLogs() {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                logger.warn("WebDriver не запущен, невозможно получить логи браузера");
                attachAsText("Browser console logs", "WebDriver not started");
                return;
            }
            List<String> logs = Selenide.getWebDriverLogs(LogType.BROWSER);
            if (logs != null && !logs.isEmpty()) {
                attachAsText("Browser console logs", String.join("\n", logs));
            } else {
                attachAsText("Browser console logs", "No browser logs available");
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении логов браузера: {}", e.getMessage(), e);
            attachAsText("Browser console logs", "Error getting browser logs: " + e.getMessage());
        }
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String videoAs() {
        // Простая проверка: добавляем видео только при CI/CD запуске
        if (System.getenv("CI") == null && System.getProperty("selenide.remote") == null) {
            logger.info("Локальный запуск - видео не добавляется");
            return "<html><body>Video skipped for local execution</body></html>";
        }
        
        try {
            URL url = videoUrl();
            if (url == null) {
                logger.info("URL видео недоступен");
                return "<html><body>No video url available</body></html>";
            }
            return "<html><body><video width='100%' height='100%' controls autoplay>" +
                    "<source src='" + url + "' type='video/mp4'></video></body></html>";
        } catch (Exception e) {
            logger.error("Ошибка при добавлении видео: {}", e.getMessage(), e);
            return "<html><body>Error adding video</body></html>";
        }
    }

    public static URL videoUrl() {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                logger.debug("WebDriver не запущен, видео недоступно");
                return null;
            }
            String sessionId = sessionId();
            if (sessionId == null) {
                logger.debug("Session ID недоступен, видео недоступно");
                return null;
            }
            return URI.create(CONFIG_REMOTE_URL_VIDEO + sessionId + ".mp4").toURL();
        } catch (MalformedURLException e) {
            logger.error("Некорректный URL для видео: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Ошибка при получении URL видео: {}", e.getMessage(), e);
            return null;
        }
    }

    public static String sessionId() {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                logger.debug("WebDriver не запущен, Session ID недоступен");
                return null;
            }
            return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        } catch (ClassCastException e) {
            logger.warn("WebDriver не является RemoteWebDriver: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Ошибка при получении Session ID: {}", e.getMessage(), e);
            return null;
        }
    }
}

package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AllureEnv {

    /** Сгенерировать build/allure-results/environment.properties */
    public static void writeAllureEnvironment() {
        Properties props = new Properties();

        // System
        props.setProperty("OS", System.getProperty("os.name") + " " + System.getProperty("os.version"));
        props.setProperty("Java", System.getProperty("java.version"));
        props.setProperty("Gradle", System.getProperty("Gradle", "unknown"));
        props.setProperty("Selenide", versionOf("com.codeborne.selenide.Selenide"));
        props.setProperty("BaseUrl", String.valueOf(Configuration.baseUrl));
        props.setProperty("Headless", String.valueOf(Configuration.headless));
        props.setProperty("Browser.Config", String.valueOf(Configuration.browser));

        // Capabilities (если драйвер стартовал)
        if (WebDriverRunner.hasWebDriverStarted()) {
            Capabilities caps = extractCapabilities();
            if (caps != null) {
                props.setProperty("Browser.Name", safe(caps.getBrowserName()));
                String bv = caps.getBrowserVersion() != null
                        ? caps.getBrowserVersion()
                        : String.valueOf(caps.getCapability("browserVersion"));
                props.setProperty("Browser.Version", safe(bv));
                Object platform = caps.getPlatformName() != null ? caps.getPlatformName() : caps.getCapability("platformName");
                props.setProperty("Platform", safe(String.valueOf(platform)));
            }
        }

        // Запись файла
        File out = new File("build/allure-results/environment.properties");
        //noinspection ResultOfMethodCallIgnored
        out.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(out)) {
            props.store(fos, "Allure Environment");
        } catch (IOException ignored) { /* ничего, просто не будет вкладки */ }
    }

    private static Capabilities extractCapabilities() {
        try {
            return ((HasCapabilities) WebDriverRunner.getWebDriver()).getCapabilities();
        } catch (ClassCastException e) {
            try {
                return ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getCapabilities();
            } catch (ClassCastException ex) {
                return null;
            }
        }
    }

    private static String versionOf(String clazz) {
        try {
            Package p = Class.forName(clazz).getPackage();
            return p != null ? p.getImplementationVersion() : "unknown";
        } catch (ClassNotFoundException e) {
            return "unknown";
        }
    }

    private static String safe(String s) {
        return s == null ? "unknown" : s;
    }
}

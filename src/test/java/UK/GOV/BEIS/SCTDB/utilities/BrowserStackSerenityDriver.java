package UK.GOV.BEIS.SCTDB.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.DriverSource;

public class BrowserStackSerenityDriver implements DriverSource {
    EnvironmentVariables environmentVariables;
    public WebDriver newDriver() {
         environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) environmentVariables.getProperty("browserstack.user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) environmentVariables.getProperty("browserstack.key");
        }

        String environment = System.getProperty("environment");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        String Platform =(String) environmentVariables.getProperty("test.platform");;
        if(Platform.toUpperCase().contentEquals("DESKTOP")){
                capabilities.setCapability("os", "Windows");
                capabilities.setCapability("os_version", "10");
        }
        else if(Platform.toUpperCase().contentEquals("MOBILE")) {
                capabilities.setCapability("os_version", "14");
                capabilities.setCapability("device", "iPhone 12");
                capabilities.setCapability("real_mobile", "true");
                capabilities.setCapability("browser", "Safari");
                //capabilities.setCapability("safariInitialUrl", "");
                capabilities.setCapability("browserstack.appium_version", "1.20.2");
                capabilities.setCapability("browserstack.networkProfile", "4g-lte-advanced-good");

        }

        //capabilities.setCapability("browserstack.local", "false");
        //capabilities.setCapability("browserstack.debug", "true");
        capabilities.setCapability("project", "BEIS Search Portal");
        //capabilities.setCapability("browserstack.console", "verbose");
        capabilities.setCapability("build", "TestRun - 20210329 002");

        Iterator it = environmentVariables.getKeys().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();

            if (key.equals("browserstack.user") || key.equals("browserstack.key")
                    || key.equals("browserstack.server")) {
                continue;
            } else if (key.startsWith("bstack_")) {
                capabilities.setCapability(key.replace("bstack_", ""), environmentVariables.getProperty(key));
                if (key.equals("bstack_browserstack.local")
                        && environmentVariables.getProperty(key).equalsIgnoreCase("true")) {
                    System.setProperty("browserstack.local", "true");
                }
            } else if (environment != null && key.startsWith("environment." + environment)) {
                capabilities.setCapability(key.replace("environment." + environment + ".", ""),
                        environmentVariables.getProperty(key));
                if (key.equals("environment." + environment + ".browserstack.local")
                        && environmentVariables.getProperty(key).equalsIgnoreCase("true")) {
                    System.setProperty("browserstack.local", "true");
                }
            }
        }

        try {
            return new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@"
                    + environmentVariables.getProperty("browserstack.server") + "/wd/hub"), capabilities);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }



    public boolean takesScreenshots() {
        return true;
    }
}

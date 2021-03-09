package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

//@DefaultUrl("https://integ-transparency-db-publicsearch-portal.azurewebsites.net/")
@DefaultUrl("page:search.url")
public class SearchHomePage extends PageObject {

    @FindBy(xpath = "//button[contains(text(),'Start now')]")
    WebElement Search;

    //@WhenPageOpens
    public void Start(){
        withTimeoutOf(Duration.ofSeconds(10))
                //.find(By.xpath("//button[contains(text(),'Start now')]"))
                .find(By.id("homepage_button_start_now"))
                .click();
    }

    public String getBSID() {

        return ThucydidesWebDriverSupport.getSessionId().toString();
    }
}

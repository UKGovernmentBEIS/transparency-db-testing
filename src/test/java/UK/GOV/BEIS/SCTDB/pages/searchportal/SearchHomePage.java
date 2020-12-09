package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;

@DefaultUrl("https://public-user-search.azurewebsites.net/")
public class SearchHomePage extends PageObject {

    @FindBy(xpath = "//button[contains(text(),'Start now')]")
    @CacheLookup
    WebElement Search;

    public void Start(){
        Search.click();
    }
}

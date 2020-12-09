package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class TypesPage extends PageObject {


    @FindBy(xpath = "//input[@id='subsidyinstrument-8']")
    @CacheLookup
    WebElementFacade Other;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    @CacheLookup
    WebElement btn_Continue;

    public void SearchBySubsidyType(String Instruments, String OtherInstrument){
        if(!Instruments.contentEquals("_BLANK"))
        {    List<String> items = Arrays.asList(Instruments.split("\\|"));
            for(String item : items){
                //System.out.println("//label[contains(text(),'"+item.trim()+"')]");
                $("//label[contains(text(),'"+item.trim()+"')]").click();
            }
            if(items.contains("Other")){
                Other.sendKeys(OtherInstrument);
            }
        }
        Serenity.takeScreenshot();
        btn_Continue.click();
    }
}

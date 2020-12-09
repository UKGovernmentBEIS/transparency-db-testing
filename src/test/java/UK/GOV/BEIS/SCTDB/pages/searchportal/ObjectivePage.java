package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.SerenityReports;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ObjectivePage extends PageObject {


    @FindBy(xpath = "//input[@id='subsidyobjective-12']")
    @CacheLookup
    WebElementFacade Other;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    @CacheLookup
    WebElement btn_Continue;

    public void SearchByPurpose(String Objective, String OtherObj){
        if(!Objective.contentEquals("_BLANK"))
        {    List<String> items = Arrays.asList(Objective.split("\\|"));
            for(String item : items){
                $("//label[contains(text(),'"+item.trim()+"')]").
                        click();
            }
            if(items.contains("Other")){
                Other.sendKeys(OtherObj);
            }
        }
        Serenity.takeScreenshot();
        btn_Continue.click();


    }


}

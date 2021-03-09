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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ObjectivePage extends PageObject {


    @FindBy(xpath = "//label/following-sibling::input[@id='subsidyobjective-12']")
    @CacheLookup
    WebElementFacade Other;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElement btn_Continue;

    public void SearchByPurpose(String Purpose, String OtherObj, String Page){
        if(Page.contentEquals("refine")){
         withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Search results')]"));
        }
        else
        {
         withTimeoutOf(Duration.ofSeconds(10)).find(By.xpath("//h1[contains(text(),'Which subsidy purposes')]"));
        }

        if(!Purpose.contentEquals("_BLANK"))
        {    List<String> items = Arrays.asList(Purpose.split("\\|"));
            for(String item : items){
                $("//label[contains(@for,'subsidyobjective')][contains(text(),'"+item.trim()+"')]").click();
            }
            if(items.contains("Other")){
                Other.sendKeys(OtherObj);
            }
        }

    }

    public void validateSelections(String Purpose, String OtherPurpose){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which subsidy purposes')]"));
        if(!Purpose.contentEquals("_BLANK")){
            List<String> items = Arrays.asList(Purpose.split("\\|"));
            boolean flag =true;
            boolean textflag=true;
            for(String item : items){
                System.out.println("//input[@value='"+item.trim()+"']");
                if(!($("//input[@value='"+item.trim()+"']").isSelected())){
                    flag =false;
                }
            }
            if(items.contains("Other")){

                String text = $("//input[@id='subsidyobjective-12']").getAttribute("value");
                textflag = (text.contentEquals(OtherPurpose));
            }

            if(!(flag && textflag)){
                Assert.fail("Selected/Entered value is not retained");
            }

        }

    }



    public void proceed(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which subsidy purposes')]"));
        Serenity.takeScreenshot();
        btn_Continue.click();
    }

    public void previous(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which subsidy purposes')]"));

        if(findAll("//a[contains(text(),'Back')]").size()>0){
            $("//a[contains(text(),'Back')]").click();
        }

    }


}

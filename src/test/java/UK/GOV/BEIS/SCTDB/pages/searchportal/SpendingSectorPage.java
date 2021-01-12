package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class SpendingSectorPage extends PageObject {

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElement btn_Continue;

    public void SearchBySector(String Sector){
        if(!Sector.contentEquals("_BLANK"))
        {    List<String> items = Arrays.asList(Sector.split("\\|"));
            for(String item : items){
                System.out.println("//label[contains(text(),'"+item.trim()+"')]");
                $("//label[contains(text(),'"+item.trim()+"')]").click();
            }
        }
    }

    public void validateSelections(String Sector){
        if(!Sector.contentEquals("_BLANK")){
            List<String> items = Arrays.asList(Sector.split("\\|"));
            boolean flag =true;
            for(String item : items){
                System.out.println("//input[@value='"+item.trim()+"']");
                if(!($("//input[@value='"+item.trim()+"']").isSelected())){
                    flag =false;
                }
            }

            if(!(flag)){
                Assert.fail("Selected/Entered value is not retained");
            }

        }
    }

    public void proceed(){
        Serenity.takeScreenshot();
        btn_Continue.click();
    }
}

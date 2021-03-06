package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class SpendingSectorPage extends PageObject {

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElement btn_Continue;

    ObjectivePage Obj_ObjectivePage;

    public void SearchBySector(String Sector, String Page){

        if(Page.contentEquals("refine")){
            withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Search results')]"));
        }
        else
        {
            withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which sectors')]"));
        }

        if(!Sector.contentEquals("_BLANK"))
        {    List<String> items = Arrays.asList(Sector.split("\\|"));
            for(String item : items){
                System.out.println("//label[contains(text(),'"+item.trim()+"')]");
                $("//label[contains(@for,'spendingsector')][contains(text(),'"+item.trim()+"')]").click();
            }
        }
    }

    public void validateSelections(String Sector){

        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which sectors')]"));
        if(!Sector.contentEquals("_BLANK")){
            List<String> items = Arrays.asList(Sector.split("\\|"));
            boolean flag =true;
            for(String item : items){
                System.out.println("//input[contains(@value,'"+item.trim()+"')]");
                if(!$("//input[contains(@value,'"+item.trim()+"')]").isSelected()){
                    flag =false;
                }
            }

            if(!(flag)){
                Assert.fail("Selected/Entered value is not retained");
            }

        }
    }

    public void proceed(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which sectors')]"));
        Serenity.takeScreenshot();
        btn_Continue.click();
    }

    public void previous(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which sectors')]"));

        if(findAll("//a[contains(text(),'Back')]").size()>0){
            $("//a[contains(text(),'Back')]").click();
        }


    }
}

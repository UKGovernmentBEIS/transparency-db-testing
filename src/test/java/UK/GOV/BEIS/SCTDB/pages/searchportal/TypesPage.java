package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class TypesPage extends PageObject {


    @FindBy(xpath = "//label/following-sibling::input[@type='subsidyinstrument']")
    @CacheLookup
    WebElementFacade Other;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElement btn_Continue;

    public void SearchBySubsidyType(String Instruments, String OtherInstrument, String Page){

        if(Page.contentEquals("refine")){
            withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Search results')]"));
        }
        else
        {
            withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which types of subsidies')]"));
        }


        if(!Instruments.contentEquals("_BLANK"))
        {    List<String> items = Arrays.asList(Instruments.split("\\|"));
            for(String item : items){
                System.out.println("//label[contains(text(),'"+item.trim()+"')]");
                $("//label[contains(@for,'subsidyinstrument')][contains(text(),'"+item.trim()+"')]").click();


            }
            if(items.contains("Other")){
                Other.sendKeys(OtherInstrument);
            }
        }
    }
    public void validateSelections(String Type, String OtherType){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which types of subsidies')]"));
        if(!Type.contentEquals("_BLANK")){
            List<String> items = Arrays.asList(Type.split("\\|"));
            boolean flag =true;
            boolean textflag=true;
            for(String item : items){
                System.out.println("//input[@value='"+item.trim()+"']");
                if(!$("//input[@value='"+item.trim()+"']").isSelected()){
                    flag =false;
                }
            }
            if(items.contains("Other")){
                String text = $("//input[@id='subsidyinstrument-8']").getAttribute("value");
                textflag = (text.contentEquals(OtherType));
            }

            if(!(flag && textflag)){
                Assert.fail("Selected/Entered value is not retained");
            }

        }

    }
    public void proceed(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which types of subsidies')]"));
        Serenity.takeScreenshot();
        btn_Continue.click();
    }

    public void previous(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'Which types of subsidies')]"));

        if(findAll("//a[contains(text(),'Back')]").size()>0){
            $("//a[contains(text(),'Back')]").click();
        }


    }
}

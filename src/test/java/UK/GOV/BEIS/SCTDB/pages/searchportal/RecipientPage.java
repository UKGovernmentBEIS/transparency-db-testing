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

public class RecipientPage extends PageObject {


    //@FindBy(xpath = "//input[@value='Yes']")
    //@FindBy(id = "beneficiary_name_radio_button")
    @FindBy(xpath = "//label[contains(text(),'Yes')]")
    @CacheLookup
    WebElementFacade rbtn_Yes;

    //@FindBy(id = "beneficiaryname-2")
    @FindBy(xpath = "//label[contains(text(),'No')]")
    @CacheLookup
    WebElementFacade rbtn_No;


    //@FindBy(id = "Beneficiary_name_text")
    @FindBy(xpath = "//input[@type='beneficiaryname']")
    @CacheLookup
    WebElementFacade txt_BeneficiaryName;

    //@FindBy(className = "govuk-button")
    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElementFacade btn_Continue;

    public void SearchByRecipient(String Recipient){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'subsidy recipient?')]"));
        if(Recipient.trim().equalsIgnoreCase("No")){
            rbtn_No.click();
            //rbtn_No.select();
        }
        else if(!Recipient.trim().equalsIgnoreCase("_BLANK"))
        {
            rbtn_Yes.click();
            //rbtn_Yes.select();
            txt_BeneficiaryName.sendKeys(Recipient);
        }
        Serenity.takeScreenshot();
        btn_Continue.click();

    }

    public void validateSelections(String Recipient){

        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'subsidy recipient?')]"));
        boolean flag=true;
        if(Recipient.trim().equalsIgnoreCase("No")){
            flag =  $("//input[@value='No']").isSelected();
        }
        else if(!Recipient.trim().equalsIgnoreCase("_BLANK"))
            {
            String text = $("//input[@type='beneficiaryname']").getAttribute("value");
            flag = ($("//input[@value='Yes']").isSelected() && text.contentEquals(Recipient));
        }
        if(!flag){
            Assert.fail("Selected/Entered value is not retained");
        }
        btn_Continue.click();
    }
}

package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class RecipientPage extends PageObject {

    @FindBy(xpath = "//label[contains(text(),'Yes')]")
    @CacheLookup
    WebElementFacade rbtn_Yes;

    @FindBy(xpath = "//label[contains(text(),'No')]")
    @CacheLookup
    WebElementFacade rbtn_No;

    @FindBy(xpath = "//input[@type='beneficiaryname']")
    @CacheLookup
    WebElementFacade txt_BeneficiaryName;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElementFacade btn_Continue;

    public void SearchByRecipient(String Recipient){
        
        if(Recipient.trim().equalsIgnoreCase("No")){
            rbtn_No.click();
        }
        else if(!Recipient.trim().equalsIgnoreCase("_BLANK"))
        {
            rbtn_Yes.click();
            txt_BeneficiaryName.sendKeys(Recipient);
        }
        Serenity.takeScreenshot();
        btn_Continue.click();

    }

    public void validateSelections(String Recipient){
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

package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class RecipientPage extends PageObject {

    @FindBy(xpath = "//label[contains(text(),'Yes')]")
    @CacheLookup
    WebElement rbtn_Yes;

    @FindBy(xpath = "//label[contains(text(),'No')]")
    @CacheLookup
    WebElement rbtn_No;

    @FindBy(xpath = "//div[@id='conditional-beneficiaryname']//input[@id='beneficiaryname']")
    @CacheLookup
    WebElement txt_BeneficiaryName;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    @CacheLookup
    WebElement btn_Continue;

    public void SearchByRecipient(String BenName){
        
        if(BenName.trim().equalsIgnoreCase("No")){
            rbtn_No.click();
        }
        else if(!BenName.trim().equalsIgnoreCase("_BLANK"))
        {
            rbtn_Yes.click();
            txt_BeneficiaryName.sendKeys(BenName);
        }
        Serenity.takeScreenshot();
        btn_Continue.click();

    }
}

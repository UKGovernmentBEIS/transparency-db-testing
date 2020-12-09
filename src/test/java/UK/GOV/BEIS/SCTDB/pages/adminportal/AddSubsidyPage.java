package UK.GOV.BEIS.SCTDB.pages.adminportal;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddSubsidyPage extends PageObject {

    @FindBy(xpath = "//a[contains(text(),'Add a subsidy award')]")
    @CacheLookup
    WebElement AddAward;

    @FindBy(xpath = "//input[@name='Subsidy_Control_Number']")
    @CacheLookup
    WebElement Subsidy_Control_Number;

    @FindBy(xpath = "//input[@name='Subsidy_Measure_Title']")
    @CacheLookup
    WebElement Subsidy_Measure_Title;

    @FindBy(xpath = "//select[@name='Subsidy_Objective']")
    @CacheLookup
    WebElement Subsidy_Objective;
    //Select Subsidy_Objective;

    @FindBy(xpath = "//select[@name='Subsidy_Instrument']")
    @CacheLookup
    WebElement Subsidy_Instrument;
    //Select Subsidy_Instrument;

    @FindBy(xpath = "//input[@name='Subsidy_Element_Full_Amount']")
    @CacheLookup
    WebElement Subsidy_Amount;

    @FindBy(xpath = "//label[contains(text(),'Company Registration Number')]")
    @CacheLookup
    WebElement Comp_Reg_number;

    @FindBy(xpath = "//label[contains(text(),'Charity Number')]")
    @CacheLookup
    WebElement Charity_Number;

    @FindBy(xpath = "//label[contains(text(),'VAT number')]")
    @CacheLookup
    WebElement VAT_Number;

    @FindBy(xpath = "//label[contains(text(),'UTR Number')]")
    @CacheLookup
    WebElement UTR_Number;

    @FindBy(xpath = "//input[@name='National_ID_Number']")
    @CacheLookup
    WebElement National_ID_Number;

    @FindBy(xpath = "//input[@name='Beneficiary_Name']")
    @CacheLookup
    WebElement Beneficiary_Name;

    @FindBy(xpath = "//label[contains(text(),'Micro organisation')]")
    @CacheLookup
    WebElement Micro_org;

    @FindBy(xpath = "//label[contains(text(),'Small organisation')]")
    @CacheLookup
    WebElement Small_org;

    @FindBy(xpath = "//label[contains(text(),'Medium organisation')]")
    @CacheLookup
    WebElement Medium_org;

    @FindBy(xpath = "//label[contains(text(),'Large organisation')]")
    @CacheLookup
    WebElement Large_org;

    @FindBy(xpath = "//input[@name='Granting_Authority_Name']")
    @CacheLookup
    WebElement Granting_Authority;

    @FindBy(xpath = "//input[@name='Legal_Granting_Date_Day']")
    @CacheLookup
    WebElement Day;

    @FindBy(xpath = "//input[@name='Legal_Granting_Date_Month']")
    @CacheLookup
    WebElement Month;

    @FindBy(xpath = "//input[@name='Legal_Granting_Date_Year']")
    @CacheLookup
    WebElement Year;

    @FindBy(xpath = "//label[contains(text(),'Goods')]")
    @CacheLookup
    WebElement Goods;

    @FindBy(xpath = "//label[contains(text(),'Services')]")
    @CacheLookup
    WebElement Services;

    @FindBy(xpath = "//select[@name='Spending_Region']")
    @CacheLookup
    WebElement Spending_Region;
    //Select Spending_Region;

    @FindBy(xpath = "//button[contains(text(), 'Review details')]")
    @CacheLookup
    WebElement btn_Review;

    @FindBy(xpath = "//button[contains(text(), 'Submit for Approval')]")
    @CacheLookup
    WebElement btn_Submit;

    @FindBy(xpath = "//button[contains(text(), 'Cancel')]")
    @CacheLookup
    WebElement btn_Cancel;



    public void enterValues(String TDID, String SheetName){

        AddAward.click();

        ArrayList<Object> alo = new Reusable().readExcelData("./src/test/resources/data/sample.xlsx",SheetName,TDID);
        if(alo.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }

        Object arr[] = alo.toArray();
        Subsidy_Control_Number.sendKeys(arr[0].toString());
        //Subsidy_Control_Number.sendKeys(alo.get(0).toString());
        Subsidy_Measure_Title.sendKeys(arr[1].toString());

        /*Subsidy_Objective.selectByValue(arr[2].toString());
        Subsidy_Instrument.selectByValue(arr[3].toString());*/

        Subsidy_Objective.sendKeys(arr[2].toString());
        Subsidy_Instrument.sendKeys(arr[3].toString());

        Subsidy_Amount.sendKeys(arr[4].toString());
        switch (arr[6].toString()){
            case "Charity Number":
                Charity_Number.click();
                break;
            case "Company Registration Number":
                Comp_Reg_number.click();
                break;
            case "VAT Number":
                VAT_Number.click();
                break;
            case "UTR Number":
                UTR_Number.click();
                break;
            default:
        }
        National_ID_Number.sendKeys(arr[7].toString());
        Beneficiary_Name.sendKeys(arr[8].toString());
        switch (arr[9].toString()){
            case "Large organisation":
                Large_org.click();
                break;
            case "Medium organisation":
                Medium_org.click();
                break;
            case "Small organisation":
                Small_org.click();
                break;
            case "Micro organisation":
                Micro_org.click();
                break;
            default:
        }
        Granting_Authority.sendKeys(arr[10].toString());
        LocalDate dt= LocalDate.parse(arr[11].toString());
        Day.sendKeys(Integer.toString(dt.getDayOfMonth()));
        Month.sendKeys(Integer.toString(dt.getMonthValue()));
        Year.sendKeys(Integer.toString(dt.getYear()));
        switch (arr[12].toString()){
            case "Goods":
                Goods.click();
                break;
            case "Services":
                Services.click();
                break;
            default:
        }
        //Spending_Region.selectByValue(arr[13].toString());
        Spending_Region.sendKeys(arr[13].toString());
        btn_Review.click();
        btn_Submit.click();
    }
}

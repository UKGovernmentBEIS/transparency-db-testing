package UK.GOV.BEIS.SCTDB.pages.searchportal;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDate;

public class GrantingDatePage extends PageObject {

    @FindBy(xpath = "//label[contains(text(),'Yes')]")
    @CacheLookup
    WebElement rbtn_Yes;

    @FindBy(xpath = "//label[contains(text(),'No')]")
    @CacheLookup
    WebElement rbtn_No;

    @FindBy(xpath = "//input[@name='legal_granting_date_day']")
    @CacheLookup
    WebElement FromDay;

    @FindBy(xpath = "//input[@name='legal_granting_date_month']")
    @CacheLookup
    WebElement FromMonth;

    @FindBy(xpath = "//input[@name='legal_granting_date_year']")
    @CacheLookup
    WebElement FromYear;

    @FindBy(xpath = "//input[@name='legal_granting_date_day1']")
    @CacheLookup
    WebElement ToDay;

    @FindBy(xpath = "//input[@name='legal_granting_date_month1']")
    @CacheLookup
    WebElement ToMonth;

    @FindBy(xpath = "//input[@name='legal_granting_date_year1']")
    @CacheLookup
    WebElement ToYear;

    @FindBy(xpath = "//button[contains(text(),'Show results')]")
    @CacheLookup
    WebElement btn_ShowResults;

    public void SearchByDate(String From, String To){

        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'specific period?')]"));


       if(!From.trim().equalsIgnoreCase("_BLANK") && !From.trim().equalsIgnoreCase("No"))
        {
            LocalDate FromDate= LocalDate.parse(From);
            rbtn_Yes.click();
            FromDay.sendKeys(String.format("%02d",FromDate.getDayOfMonth()));
            FromMonth.sendKeys(String.format("%02d",FromDate.getMonthValue()));
            FromYear.sendKeys(Integer.toString(FromDate.getYear()));
        }

        if(!To.trim().equalsIgnoreCase("_BLANK") && !To.trim().equalsIgnoreCase("No"))
        {
            LocalDate ToDate= LocalDate.parse(To);
            rbtn_Yes.click();
            ToDay.sendKeys(String.format("%02d",ToDate.getDayOfMonth()));
            ToMonth.sendKeys(String.format("%02d",ToDate.getMonthValue()));
            ToYear.sendKeys(Integer.toString(ToDate.getYear()));
        }

        if(From.trim().equalsIgnoreCase("No") && To.trim().equalsIgnoreCase("No") ){
            rbtn_No.click();
        }
    }

    public void proceed(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'specific period?')]"));
        Serenity.takeScreenshot();
        btn_ShowResults.click();

    }
    public void previous(){
        withTimeoutOf(Duration.ofSeconds(5)).find(By.xpath("//h1[contains(text(),'specific period?')]"));

        if(findAll("//a[contains(text(),'Back')]").size()>0){
            $("//a[contains(text(),'Back')]").click();
        }


    }
}

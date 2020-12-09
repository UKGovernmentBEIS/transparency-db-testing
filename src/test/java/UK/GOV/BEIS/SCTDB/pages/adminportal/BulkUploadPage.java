package UK.GOV.BEIS.SCTDB.pages.adminportal;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BulkUploadPage extends PageObject {

    @FindBy(xpath = "//a[contains(text(),'Bulk upload subsidy awards')]")
    @CacheLookup
    WebElement BulkUpload;

    @FindBy(xpath = "//h1[text()='Bulk upload subsidy awards']")
    @CacheLookup
    WebElement PageTitle;

    @FindBy(xpath = "//a[contains(text(),'Upload file')]")
    @CacheLookup
    WebElement UploadFile;

    @FindBy(xpath = "//input[@name='file_upload_1']")
    @CacheLookup
    WebElement ChooseFile;

    @FindBy(xpath = "//button[contains(text(),'Upload the file')]")
    @CacheLookup
    WebElement btn_Upload;

    @FindBy(xpath = "//h1[contains(text(),'File uploaded successfully')]")
    @CacheLookup
    WebElement Success;

    @FindBy(xpath = "//button[contains(text(),'Proceed to My subsidy awards')]")
    @CacheLookup
    WebElement btn_Proceed;




    public void Upload(String FileName){

        String UploadFilePath="";
        try {
            UploadFilePath = new File("./src/test/resources/data/"+FileName+".xlsx").getCanonicalPath();


        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Error in getting full path of the upload file");
        }

        BulkUpload.click();
        PageTitle.isDisplayed();
        UploadFile.click();
        ChooseFile.sendKeys(UploadFilePath);
        /*try {
         UploadToolPath  =  new File("./src/test/resources/Upload.exe").getCanonicalPath();
            Runtime.getRuntime().exec(UploadToolPath+" "+UploadFilePath);
            Thread.sleep(3000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/

        btn_Upload.click();

        Success.isDisplayed();
        btn_Proceed.click();



    }
}

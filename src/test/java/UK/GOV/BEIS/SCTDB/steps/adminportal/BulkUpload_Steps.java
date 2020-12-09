package UK.GOV.BEIS.SCTDB.steps.adminportal;

import UK.GOV.BEIS.SCTDB.pages.adminportal.AddSubsidyPage;
import UK.GOV.BEIS.SCTDB.pages.adminportal.BulkUploadPage;
import io.cucumber.java.en.Given;

public class BulkUpload_Steps {

    BulkUploadPage Obj_BulkUpload;

    @Given("I am able to Bulk Upload Subsidy Awards from the spreadsheet {string}")
    public void I_am_able_to_Bulk_Upload_Subsidy_Awards_from_the_spreadsheet(String UploadFile) {

        Obj_BulkUpload.Upload(UploadFile);
    }

}

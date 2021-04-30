package UK.GOV.BEIS.SCTDB.steps.api;

import UK.GOV.BEIS.SCTDB.utilities.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class SearchAPI_Steps extends ApiUtils {

    @Steps

    Reusable ReusableObj;
    String res;
    RequestSpecification requestspec;
    ResponseSpecification requestspecone;
    ResponseSpecification responsespecificationone;
    Response apiresponse;
    String responseString;
    String awardnumber;
    String TestdataId;
    String DataSheetName;
    String apiEndpoint;
    String awardid;
    Integer apistatuscode;
    Integer pagenumber = 1;
    Integer totalrecordsinapage;
    String Scenariocategory;
    String exportallsheetname;
    HashMap<String, String> DataFromExcel;

    @Given("Payload is created with details from datasheet by passing {string} & {string}")
    public void payloadIsCreatedWithDetailsFromDatasheetByPassing(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        if (DataSheetName.equalsIgnoreCase("PublicSearch")){
            HashMap<String, Object> payload= body.Payloadbuilder("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
            //System.out.println(payload);
            apistatuscode = body.FetchStatusCode("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().spec(requestSpecification("publicsearchbasepath.uri")).body(payload);
            //.log().all()
        }
        else if(DataSheetName.equalsIgnoreCase("AddSingleNewSubsidyAward")){
            HashMap<String, String> DataFromExcel = new Reusable().readExcelDataNew("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx",SheetName,TDID);
            //HashMap<String, Object> payload= body.AddSingleSubsidyPayloadbuilder("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx",SheetName,TDID);
            HashMap<String, Object> UPOMap = body.headerbuilder(DataFromExcel);
            HashMap<String, Object> payload= body.AddSingleSubsidyPayloadbuilder(DataFromExcel);
            String sc = (String) payload.get("statuscode");
            apistatuscode= (int)Double.parseDouble(sc);
            payload.remove("statuscode");
            //apistatuscode = body.FetchStatusCode("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().spec(requestSpecification("publishingsubsidybasepath.uri")).body(payload).header("userPrinciple", UPOMap);
        }
        else if(DataSheetName.equalsIgnoreCase("ApprovalWorkflow")){
            HashMap<String, Object> payload= body.ApprovalworkflowPayloadbuilder("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
            HashMap<String, String> DataFromExcel = new Reusable().readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
            HashMap<String, Object> UPOMap = body.headerbuilder(DataFromExcel);
            awardnumber = payload.get("AwardNumber").toString();
            if(awardnumber.contains(".")){
                awardnumber=   awardnumber.substring(0, awardnumber.indexOf("."));
            }
            String sc = (String) payload.get("StatusCode");
            apistatuscode= (int)Double.parseDouble(sc);
            payload.remove("AwardNumber");
            payload.remove("StatusCode");
//            awardnumber = body.Fetchawardnumber("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
//            apistatuscode = body.FetchStatusCode("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().spec(requestSpecifications(awardnumber,"accessmanagementbasepath.uri")).body(payload).header("userPrinciple", UPOMap);
        }
    }

    @Given("A award exists with award number from datasheet by passing {string} & {string}")
    public void aAwardExistsWithAwardNumberFromDatasheetByPassing(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        apistatuscode = body.FetchStatusCode("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
        awardnumber = body.Fetchawardnumber("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
        awardid = fetchawardnumber(awardnumber);
        requestspec = SerenityRest.given().spec(requestSpecifications(awardid,"publicsearchbasepath.uri"));
        //.log().all()
    }

    @Given("Payload is created with details from datasheet by passing values {string} & {string}")
    public void payloadIsCreatedWithDetailsFromDatasheetByPassingValues(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        if (DataSheetName.equalsIgnoreCase("PublicSearch")){
            HashMap<String, Object> payload= body.PayloadbuilderTestData("./src/test/resources/data/SearchUIDatasheet.xlsx",SheetName,TDID);
            apistatuscode = body.FetchStatusCode("./src/test/resources/data/SearchUIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().spec(requestSpecification("publicsearchbasepath.uri")).body(payload);
        }
    }

    @Given("Payload is created with details from datasheet by passing {string} and {string}")
    public void payloadIsCreatedWithDetailsFromDatasheetByPassingAnd(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        HashMap<String, Object> payload= body.Payloadbuilderexportall("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
        requestspec = SerenityRest.given().spec(requestSpecification("publicsearchbasepath.uri")).body(payload);
    }

    @Given("Bulkupload sheet {string} & {string} is updated with {string} subsidy award details")
    public void bulkuploadSheetIsUpdatedWithSubsidyAwardDetails(String TDID, String SheetName, String Scenariotype) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Scenariocategory = Scenariotype;
        Requestdetails body = new Requestdetails();
        HashMap<String, String> UPOHeader = new HashMap<>();
        UPOHeader.put("userName", "SYSTEM");
        UPOHeader.put("password", "password123");
        UPOHeader.put("role", "Granting Authority Encoder");
        UPOHeader.put("grantingAuthorityGroupId", "137");
        UPOHeader.put("grantingAuthorityGroupName", "HMRC");
        if (Scenariocategory.equalsIgnoreCase("valid")) {
            File file = new File("./src/test/resources/data/BulkUpload_API_Valid.xlsx");
            apistatuscode = body.FetchStatusCode("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().multiPart("file",file,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").header("userPrinciple",UPOHeader);
        }
        else if (Scenariocategory.equalsIgnoreCase("invalid")) {
            File file = new File("./src/test/resources/data/BulkUpload_API_Invalid.xlsx");
            apistatuscode = body.FetchStatusCode("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().multiPart("file",file,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").header("userPrinciple",UPOHeader);
        }
    }

    @Given("Header is sent with details from datasheet by passing {string} & {string}")
    public void headerIsSentWithDetailsFromDatasheetByPassing(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        HashMap<String, String> DataFromExcel = new Reusable().readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        HashMap<String, Object> UPOMap = body.headerbuilder(DataFromExcel);
        //HashMap<String, Object> map= body.headerbuilder("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        apistatuscode = body.FetchStatusCode("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        requestspec = SerenityRest.given().spec(requestSpecification("accessmanagementbasepath.uri")).header("userPrinciple", UPOMap);
    }

    @Given("Valid search is done with details from datasheet by passing {string} & {string}")
    public void validSearchIsDoneWithDetailsFromDatasheetByPassing(String TDID, String SheetName) throws IOException, ParseException {

        TestdataId = TDID;
        DataSheetName = SheetName;
        DataFromExcel = new Reusable().readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        Requestdetails body = new Requestdetails();
        HashMap<String, Object> UPOMap = body.headerbuilder(DataFromExcel);
        HashMap<String, Object> map= body.queryparameterbuilder(DataFromExcel);
        //HashMap<String, Object> upoMap = body.headerbuilder("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        //HashMap<String, Object> map= body.queryparameterbuilder("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        //apistatuscode = body.FetchStatusCode("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
        String sc = (String) map.get("statuscode");
        apistatuscode= (int)Double.parseDouble(sc);
        map.remove("statuscode");
        requestspec = SerenityRest.given().spec(requestSpecification("accessmanagementbasepath.uri")).queryParams(map).header("userPrinciple", UPOMap);
    }

    @When("I call {string} API with {string} http request")
    public void ICallsEndpointAPIWithHttpRequest(String Endpoint, String httpRequestmethod) throws IOException {


        apiEndpoint = Endpoint;
        requestspecone = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
        responsespecificationone = new ResponseSpecBuilder().build();
        if ((Endpoint.equalsIgnoreCase("searchResults.endpoint"))||(Endpoint.equalsIgnoreCase("bulkupload.endpoint"))||(Endpoint.equalsIgnoreCase("addsinglesubsidyaward.endpoint"))) {
            apiresponse = requestspec.when().post(getGlobalValue(Endpoint)).
                    then().spec(requestspecone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("awards.endpoint")) {
            String endpointvalue = getGlobalValue(Endpoint);
            String endpointvalueupdated = endpointvalue + "/{awardnumber}";
            apiresponse = requestspec.when().get(endpointvalueupdated).
                    then().spec(requestspecone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("exportall.endpoint")) {
            apiresponse = requestspec.when().post(getGlobalValue(Endpoint)).
                    then().spec(responsespecificationone).extract().response();
        }
        else if ((Endpoint.equalsIgnoreCase("beisadmin.endpoint"))||(Endpoint.equalsIgnoreCase
                ("gaadmin.endpoint"))||(Endpoint.equalsIgnoreCase("gaapprover.endpoint"))||
                (Endpoint.equalsIgnoreCase("gaencoder.endpoint"))||
                (Endpoint.equalsIgnoreCase("accessmanagementsearchResults.endpoint"))){
            apiresponse = requestspec.when().get(getGlobalValue(Endpoint)).
                    then().spec(requestspecone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("approvalworkflow.endpoint")) {
            String endpointvalue = getGlobalValue(Endpoint);
            String endpointvalueupdated = endpointvalue + "/{awardnumber}";
            apiresponse = requestspec.when().put(endpointvalueupdated).
                    then().spec(responsespecificationone).extract().response();
        }
    }

    @Then("I will be getting the expected StatusCode")
    public void iWillBeGettingExpectedStatusCode() throws IOException, ParseException {

        responseString = apiresponse.asString();
        Integer apiStatuscodeActual = apiresponse.getStatusCode();
        assertEquals("Error in Validating Status Code:", apistatuscode, apiStatuscodeActual);
    }

    @Then("I will be validating response against values in datasheet")
    public void iWillBeValidatingResponseAgainstValuesInDatasheet() throws IOException, ParseException {

        exportallsheetname = "Public User Details";
        Responsedetails responseobject = new Responsedetails();
        if(apistatuscode.equals(200)) {
            if (apiEndpoint.equalsIgnoreCase("searchResults.endpoint")) {
                responseobject.Responsevalidations(responseString, DataSheetName, TestdataId);
            } else if (apiEndpoint.equalsIgnoreCase("awards.endpoint")) {
                responseobject.AwardsapiResponsevalidations(responseString, awardid);
            }
            else if (apiEndpoint.equalsIgnoreCase("bulkupload.endpoint")) {
                responseobject.BulkUploadResponsevalidations(responseString,DataSheetName, TestdataId);
            }
            else if (apiEndpoint.equalsIgnoreCase("exportall.endpoint")) {
                byte[] bytesup = apiresponse.getBody().asByteArray();
                File exportallfile = new File("./src/test/resources/data/apiexportall.xlsx");
                Files.write(exportallfile.toPath(),bytesup);
                String responseString = apiresponse.asString();
                responseobject.ExportAllResponsevalidation(exportallsheetname,DataSheetName,TestdataId);
            }
            else if (apiEndpoint.equalsIgnoreCase("addsinglesubsidyaward.endpoint")) {
                responseobject.AddSingleSubsidyAwardResponsevalidations(responseString,DataSheetName, TestdataId);
            }
            else if ((apiEndpoint.equalsIgnoreCase("beisadmin.endpoint"))||(apiEndpoint.equalsIgnoreCase("gaadmin.endpoint"))||(apiEndpoint.equalsIgnoreCase("gaapprover.endpoint"))||(apiEndpoint.equalsIgnoreCase("gaencoder.endpoint"))){
                responseobject.DashboardResponsevalidations(responseString, DataSheetName, TestdataId, apiEndpoint);
            }
            else if (apiEndpoint.equalsIgnoreCase("approvalworkflow.endpoint")) {
                responseobject.ApprovalWorkflowResponsevalidations(responseString, DataSheetName, TestdataId, apiEndpoint);
            }
            else if (apiEndpoint.equalsIgnoreCase("accessmanagementsearchResults.endpoint")) {
                responseobject.searchResultsResponsevalidations(responseString, DataSheetName, TestdataId);
                //responseobject.searchResultsResponsevalidations(responseString, DataFromExcel);
            }
        }

    }

    @Then("I will be validating response and write beneficiary names to datasheet")
    public void iWillBeValidatingResponseAndWriteBeneficiaryNamesToDatasheet() throws IOException, ParseException {
        Responsedetails responseobject = new Responsedetails();
        if(apistatuscode.equals(200)) {
            responseobject.ResponsevalidationsUIDatasheet(responseString,DataSheetName, TestdataId);
        }
    }
    @After
    public void afterscenario(){
        requestspec=null;
        DataFromExcel=null;

    }

}
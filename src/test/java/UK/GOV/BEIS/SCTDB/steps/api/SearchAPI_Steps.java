package UK.GOV.BEIS.SCTDB.steps.api;

import UK.GOV.BEIS.SCTDB.utilities.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;

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
    public String awardnumber;
    String TestdataId;
    String DataSheetName;
    String apiEndpoint;
    String awardid;
    Integer apistatuscode;
    Integer pagenumber = 1;
    Integer totalrecordsinapage;
    String Scenariocategory;
    String exportallsheetname;

    @Given("Payload is created with details from datasheet by passing {string} & {string}")
    public void payloadIsCreatedWithDetailsFromDatasheetByPassing(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        if (DataSheetName.equalsIgnoreCase("PublicSearch")){
            HashMap<String, Object> payload= body.Payloadbuilder("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().spec(requestSpecification("publicsearchbasepath.uri")).body(payload);
        }
        else if(DataSheetName.equalsIgnoreCase("AddSingleNewSubsidyAward")){
            HashMap<String, Object> payload= body.AddSingleSubsidyPayloadbuilder("./src/test/resources/data/PublishingSubsidies.xlsx",SheetName,TDID);
            requestspec = SerenityRest.given().spec(requestSpecification("publishingsubsidybasepath.uri")).body(payload);
        }
    }
    @Given("Payload is created with details from datasheet by passing {string} , {string}")
    public void payloadIsCreatedWithDetailsFromDatasheet(String TDID, String SheetName) throws IOException, ParseException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Requestdetails body = new Requestdetails();
        HashMap<String, Object> payload= body.createPayloadbuilderwithtotalrecordsperpage("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID,pagenumber);
        requestspec = SerenityRest.given().spec(requestSpecification("publicsearchbasepath.uri")).body(payload);
    }

    @Given("A award exists with award number of {string}")
    public void aAwardExistsWithAwardNumberOf(String award) throws IOException {
        awardid = award;
        awardnumber = fetchawardnumber(award);
        requestspec = SerenityRest.given().spec(requestSpecifications(awardnumber));
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
    public void bulkuploadSheetIsUpdatedWithSubsidyAwardDetails(String TDID, String SheetName, String Scenariotype) throws IOException {
        TestdataId = TDID;
        DataSheetName = SheetName;
        Scenariocategory = Scenariotype;
        if (Scenariocategory.equalsIgnoreCase("valid")) {
            File file = new File("./src/test/resources/data/BulkUpload_API_Valid.xlsx");
            requestspec = SerenityRest.given().multiPart("file",file,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
        else if (Scenariocategory.equalsIgnoreCase("invalid")) {
            File file = new File("./src/test/resources/data/BulkUpload_API_Invalid.xlsx");
            requestspec = SerenityRest.given().multiPart("file",file,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
    }

    @Given("Header is sent with following details")
    public void headerIsSentWithFollowingDetails(DataTable data) throws IOException {
        List<List<String>> headervalues = data.asLists();
        String username = headervalues.get(0).get(0);
        String password = headervalues.get(0).get(1);
        String role = headervalues.get(0).get(2);
        String grantingAuthorityGroupId = headervalues.get(0).get(3);
        String grantingAuthorityGroupName = headervalues.get(0).get(4);
        Requestdetails body = new Requestdetails();
       // HashMap<String, Object> payload= body.Payloadbuilderexportall("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
       // requestspec = SerenityRest.given().spec(requestSpecification("accessmanagementbasepath.uri")).header("userPrinciple", map);;
    }

    @When("I calls {string} API with {string} http request")
    public void ICallsEndpointAPIWithHttpRequest(String Endpoint, String httpRequestmethod) throws IOException {
        apiEndpoint = Endpoint;
        requestspecone = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
        responsespecificationone = new ResponseSpecBuilder().build();

        if (Endpoint.equalsIgnoreCase("searchResults.endpoint")) {
            apiresponse = requestspec.when().post(getGlobalValue(Endpoint)).
                    then().spec(requestspecone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("awards.endpoint")) {
            String endpointvalue = getGlobalValue(Endpoint);
            String endpointvalueupdated = endpointvalue + "/{awardnumber}";
            apiresponse = requestspec.when().get(endpointvalueupdated).
                    then().spec(requestspecone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("bulkupload.endpoint")) {
            apiresponse = requestspec.when().post(getGlobalValue(Endpoint)).
                    then().spec(requestspecone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("exportall.endpoint")) {
            apiresponse = requestspec.when().post(getGlobalValue(Endpoint)).
                    then().spec(responsespecificationone).extract().response();
        }
        else if (Endpoint.equalsIgnoreCase("addsinglesubsidyaward.endpoint")) {
            apiresponse = requestspec.when().post(getGlobalValue(Endpoint)).
                    then().spec(requestspecone).extract().response();
        }
    }

    @Then("I will be getting the expected {int}")
    public void iWillBeGettingExpectedStatusCode(int StatusCode) throws IOException, ParseException {
        apistatuscode = StatusCode;
        responseString = apiresponse.asString();
        assertEquals("Error in Validating Status Code:",StatusCode, apiresponse.getStatusCode());
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
                //String responseString = apiresponse.asString();
                //responseobject.ExportAllResponsevalidation(exportallsheetname,DataSheetName,TestdataId);
            }
            else if (apiEndpoint.equalsIgnoreCase("addsinglesubsidyaward.endpoint")) {
                responseobject.AddSingleSubsidyAwardResponsevalidations(responseString,DataSheetName, TestdataId);
            }
        }
    }
}
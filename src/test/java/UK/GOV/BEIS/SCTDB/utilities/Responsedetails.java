package UK.GOV.BEIS.SCTDB.utilities;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Collections;
import java.io.IOException;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.springframework.util.StringUtils;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Responsedetails extends ApiUtils {
        String responsestringglobal;
        public void Responsevalidations(String response, String SheetName, String TDID) throws IOException, ParseException {
                responsestringglobal = response;
                JsonPath js = new JsonPath(response);
                Integer totalSearchResults = js.getInt("totalSearchResults");
                Integer currentPagenum = js.getInt("currentPage");
                Integer totalPages = js.getInt("totalPages");

                //fetches award details in arraylist
                int count = js.getInt("awards.size()");
                System.out.println(count);
                List<String> awardslist[] = new ArrayList[count];
                for (int l = 0; l < count; l++) {
                        awardslist[l] = new ArrayList<>();
                        awardslist[l].add(String.valueOf(js.getInt("awards[" + l + "].awardNumber")));
                        if (js.getString("awards[" + l + "].subsidyInstrument").equalsIgnoreCase("Tax measures (tax credit, or tax/duty exemption)")) {
                                awardslist[l].add(js.getString("awards[" + l + "].subsidyFullAmountRange"));
                        }
                        awardslist[l].add(js.getString("awards[" + l + "].subsidyFullAmountExact"));
                        awardslist[l].add(js.getString("awards[" + l + "].subsidyObjective"));
                        awardslist[l].add(js.getString("awards[" + l + "].subsidyInstrument"));
                        awardslist[l].add(js.getString("awards[" + l + "].spendingSector"));
                        awardslist[l].add(js.getString("awards[" + l + "].legalGrantingDate"));
                        awardslist[l].add(js.getString("awards[" + l + "].beneficiary.beneficiaryName"));
                }
                List<String> awardsNumber[] = new ArrayList[count];
                for (int m = 0; m < awardslist.length; m++) {
                        awardsNumber[m] = new ArrayList<>();
                        awardsNumber[m].add(String.valueOf(js.getInt("awards[" + m + "].awardNumber")));
                        System.out.println(awardslist[m]);
                }

                //fetches beneficiary name details in arraylist
                List<String> beneficiaryName[] = new ArrayList[count];
                for (int n = 0; n < count; n++) {
                        beneficiaryName[n] = new ArrayList<>();
                        beneficiaryName[n].add(js.getString("awards[" + n + "].beneficiary.beneficiaryName"));
                }

                for (int o = 0; o < beneficiaryName.length; o++) {
                        System.out.println(beneficiaryName[o]);
                }

                //fetches subsidy measure details in arraylist
                List<String> subsidyMeasurelist[] = new ArrayList[count];
                for (int p = 0; p < count; p++) {
                        subsidyMeasurelist[p] = new ArrayList<>();
                        subsidyMeasurelist[p].add(js.getString("awards[" + p + "].subsidyMeasure.subsidyMeasureTitle"));
                        subsidyMeasurelist[p].add(js.getString("awards[" + p + "].subsidyMeasure.scNumber"));
                        subsidyMeasurelist[p].add(js.getString("awards[" + p + "].subsidyMeasure.adhoc"));
                        subsidyMeasurelist[p].add(js.getString("awards[" + p + "].subsidyMeasure.legalBasis.legalBasisText"));
                }

                List<String> ScNumber[] = new ArrayList[count];
                for (int q = 0; q < subsidyMeasurelist.length; q++) {
                        ScNumber[q] = new ArrayList<>();
                        ScNumber[q].add(js.getString("awards[" + q + "].subsidyMeasure.scNumber"));
                        System.out.println(subsidyMeasurelist[q]);
                }

                Reusable reUse = new Reusable();
                HashMap<String, String> verifydata = reUse.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx", SheetName, TDID);
                if (verifydata.isEmpty()) {
                        Assert.fail("There is no matching TDID in the datasheet");
                }
                String sortbyvalidation = verifydata.get("Sortby");
                if ((sortbyvalidation.equalsIgnoreCase("beneficiary.beneficiaryName,asc")) || (sortbyvalidation.equalsIgnoreCase("beneficiary.beneficiaryName,desc"))){
                        List<String> beneficiarynameslist = new ArrayList<String>();
                        for (int d = 0; d < count; d++) {
                                beneficiarynameslist.add(js.getString("awards[" + d + "].beneficiary.beneficiaryName"));
                        }
                        List<String> beneficiarynamescopylist = new ArrayList<>(List.copyOf(beneficiarynameslist));
                        beneficiarynamescopylist.sort(String.CASE_INSENSITIVE_ORDER);
                        if (sortbyvalidation.contains("desc")) {
                                Collections.reverse(beneficiarynamescopylist);
                        }
                        Assert.assertEquals(beneficiarynameslist,beneficiarynamescopylist);
                }
                if ((sortbyvalidation.equalsIgnoreCase("spendingSector,asc")) || (sortbyvalidation.equalsIgnoreCase("spendingSector,desc"))){
                        List<String> spendingSectorlist = new ArrayList<String>();
                        for (int e = 0; e < count; e++) {
                                spendingSectorlist.add(js.getString("awards[" + e + "].spendingSector"));
                        }
                        List<String> spendingSectorcopylist = new ArrayList<>(List.copyOf(spendingSectorlist));
                        spendingSectorcopylist.sort(String.CASE_INSENSITIVE_ORDER);
                        if (sortbyvalidation.contains("desc")) {
                                Collections.reverse(spendingSectorcopylist);
                        }
                        Assert.assertEquals(spendingSectorlist,spendingSectorcopylist);
                }
                /*
                if ((sortbyvalidation.equalsIgnoreCase("subsidyFullAmountExact,asc")) || (sortbyvalidation.equalsIgnoreCase("subsidyFullAmountExact,desc"))){
                        List<String> subsidyFullAmountExactlist = new ArrayList<String>();
                        for (int f = 0; f < count; f++) {
                                subsidyFullAmountExactlist.add(js.getString("awards[" + f + "].subsidyFullAmountExact"));
                        }
                        List<String> subsidyFullAmountExactcopylist = new ArrayList<>(List.copyOf(subsidyFullAmountExactlist));
                        Collections.sort(subsidyFullAmountExactcopylist);
                        if (sortbyvalidation.contains("desc")) {
                                Collections.reverse(subsidyFullAmountExactcopylist);
                        }
                        Assert.assertEquals(subsidyFullAmountExactlist,subsidyFullAmountExactcopylist);
                }
                */
                String validationData = verifydata.get("Validation");
                if (validationData.equalsIgnoreCase("SubsidyMeasureDetails")) {

                        //SubsidyMeasure validation
                        for (int r = 0; r < count; r++) {
                                String subsidyMeas = String.join(", ", ScNumber[r]);
                                ApiUtils respObj = new ApiUtils();
                                List<String> subsidyMeaList = respObj.SubsidyMeaValidate("SubsidyMeasureDetails", subsidyMeas);
                                Collections.sort(subsidyMeasurelist[r]);
                                Collections.sort(subsidyMeaList);
                                Assert.assertEquals(subsidyMeasurelist[r], subsidyMeaList);
                        }
                }
                if (validationData.equalsIgnoreCase("AwardsDetails")) {
                        //Awards validation
                        for (int s = 0; s < count; s++) {
                                String awardNumb = String.join(", ", awardsNumber[s]);
                                String awardSlno = "AW" + awardNumb;
                                ApiUtils awardObject = new ApiUtils();
                                List<String> awardLists = awardObject.AwardsValidate("AwardsDetails", awardSlno);
                                Collections.sort(awardslist[s]);
                                Collections.sort(awardLists);
                                Assert.assertEquals(awardslist[s].size(), awardLists.size());
                                for (int y = 0; y < awardslist[s].size(); y++) {
                                        assertEquals(awardslist[s].get(y).trim(), awardLists.get(y).trim());
                                }
                        }
                }
                if (validationData.equalsIgnoreCase("Pagination")) {
                        String testdatasheetname = SheetName;
                        String testcaseid = TDID;
                        int updatedcounttotal;
                        JsonPath jsonpathobject = new JsonPath(responsestringglobal);
                        int countpagination = jsonpathobject.getInt("awards.size()");
                        int updatedcount = 0;
                        int totalcount = 0;
                        int defaultpage = 1;
                        Requestdetails requestdeailsobject = new Requestdetails();
                        HashMap<String, Object> updatedpayload = requestdeailsobject.createPayloadbuilderwithtotalrecordsperpage("./src/test/resources/data/SearchAPIDatasheet.xlsx", testdatasheetname, TDID, defaultpage);
                        RequestSpecification requestspec = given().spec(requestSpecification("publicsearchbasepath.uri")).body(updatedpayload);
                        ResponseSpecification requestspecone = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
                        Response apiresponse = requestspec.when().post(getGlobalValue("searchResults.endpoint")).
                                then().spec(requestspecone).extract().response();
                        String updatedresponseString = apiresponse.asString();
                        JsonPath updatedjsonpathobject = new JsonPath(updatedresponseString);
                        count = updatedjsonpathobject.getInt("awards.size()");
                        Integer totalSearchResultspagination = updatedjsonpathobject.getInt("totalSearchResults");
                        Integer currentPagenumpagination = updatedjsonpathobject.getInt("currentPage");
                        Integer totalPagespagination = updatedjsonpathobject.getInt("totalPages");
                        ArrayList<Integer> arraylisttotalcount = new ArrayList<Integer>();
                        for (int currentpage = 2; currentpage <= totalPagespagination; currentpage++) {
                                Requestdetails requestdeailsobjectupdated = new Requestdetails();
                                HashMap<String, Object> updatedpayloadupdated = requestdeailsobjectupdated.createPayloadbuilderwithtotalrecordsperpage("./src/test/resources/data/SearchAPIDatasheet.xlsx", testdatasheetname, TDID, currentpage);
                                RequestSpecification requestspecupdated = given().spec(requestSpecification("publicsearchbasepath.uri")).body(updatedpayloadupdated);
                                ResponseSpecification requestspeconeupdated = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
                                Response apiresponseupdated = requestspecupdated.when().post(getGlobalValue("searchResults.endpoint")).
                                        then().spec(requestspeconeupdated).extract().response();
                                String updatedresponseStringvalue = apiresponseupdated.asString();
                                JsonPath updatedjsonpathobjectvalue = new JsonPath(updatedresponseStringvalue);
                                updatedcount = updatedjsonpathobjectvalue.getInt("awards.size()");
                                arraylisttotalcount.add(updatedcount);
                        }
                        int sum = 0;
                        for(int i=0;i<arraylisttotalcount.size();i++){
                                sum+=   arraylisttotalcount.get(i);
                        }
                        int totalcountvalues = sum + count;
                        Assert.assertSame(totalcountvalues,totalSearchResults);
                }
        }
        public void AwardsapiResponsevalidations(String response, String TDID) throws IOException, ParseException {
                JsonPath jsonpathobject = new JsonPath(response);
                //fetches award details in arraylist
                List<String> awardsapiawardslist = new ArrayList<String>();
                awardsapiawardslist.add(String.valueOf(jsonpathobject.getInt("awardNumber")));
                if (jsonpathobject.getString("subsidyInstrument").equalsIgnoreCase("Tax measures (tax credit, or tax/duty exemption)")) {
                        awardsapiawardslist.add(jsonpathobject.getString("subsidyFullAmountRange"));
                }
                awardsapiawardslist.add(jsonpathobject.getString("subsidyFullAmountExact"));
                awardsapiawardslist.add(jsonpathobject.getString("subsidyObjective"));
                awardsapiawardslist.add(jsonpathobject.getString("subsidyInstrument"));
                awardsapiawardslist.add(jsonpathobject.getString("spendingSector"));
                awardsapiawardslist.add(jsonpathobject.getString("goodsServicesFilter"));
                awardsapiawardslist.add(jsonpathobject.getString("legalGrantingDate"));
                awardsapiawardslist.add(jsonpathobject.getString("createdBy"));
                awardsapiawardslist.add(jsonpathobject.getString("approvedBy"));
                awardsapiawardslist.add(jsonpathobject.getString("status"));
                awardsapiawardslist.add(jsonpathobject.getString("createdTimestamp"));
                awardsapiawardslist.add(jsonpathobject.getString("lastModifiedTimestamp"));
                awardsapiawardslist.add(jsonpathobject.getString("publishedAwardDate"));
                awardsapiawardslist.add(jsonpathobject.getString("grantingAuthorityResponse.grantingAuthorityName"));
                String awardsNumber = String.valueOf(jsonpathobject.getInt("awardNumber"));

                //fetches beneficiary name details in arraylist
                List<String> awardsapibeneficiarylist = new ArrayList<String>();
                awardsapibeneficiarylist.add(jsonpathobject.getString("beneficiary.beneficiaryName"));
                awardsapibeneficiarylist.add(jsonpathobject.getString("beneficiary.nationalId"));
                awardsapibeneficiarylist.add(jsonpathobject.getString("beneficiary.nationalIdType"));
                awardsapibeneficiarylist.add(jsonpathobject.getString("beneficiary.orgSize"));
                //awardsapibeneficiarylist.add(jsonpathobject.getString("beneficiary.region"));
                //awardsapibeneficiarylist.add(jsonpathobject.getString("grantingAuthorityResponse.grantingAuthorityName"));
                String beneficiaryName = jsonpathobject.getString("beneficiary.beneficiaryName");

                //fetches subsidy measure details in arraylist
                List<String> awardsapisubsidylist = new ArrayList<String>();
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.subsidyMeasureTitle"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.scNumber"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.startDate"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.endDate"));
                awardsapisubsidylist.add(String.valueOf(jsonpathobject.getInt("subsidyMeasure.duration")));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.budget"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.adhoc"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.gaSubsidyWebLink"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.legalBasis.legalBasisText"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.publishedMeasureDate"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.createdBy"));
                awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.approvedBy"));
                //awardsapisubsidylist.add(jsonpathobject.getString("subsidyMeasure.status"));
                String ScNumber = jsonpathobject.getString("subsidyMeasure.scNumber");

                //Beneficiary details validation
                String awardsapibenificiaryname = String.join(", ", beneficiaryName);
                ApiUtils beneficiaryvalidations = new ApiUtils();
                List<String> awardsapibenList = beneficiaryvalidations.AwardsapiBeneficiarydetails("BeneficiaryDetails", awardsapibenificiaryname);
                Collections.sort(awardsapibenList);
                Collections.sort(awardsapibeneficiarylist);
                Assert.assertEquals(awardsapibeneficiarylist, awardsapibenList);

                //SubsidyMeasure validation
                String subsidyMeas = String.join(", ", ScNumber);
                ApiUtils respObj = new ApiUtils();
                List<String> subsidyMeaList = respObj.AwardsapiSubsidyMeaValidate("SubsidyMeasureDetails", subsidyMeas);
                Collections.sort(awardsapisubsidylist);
                Collections.sort(subsidyMeaList);
                Assert.assertEquals(awardsapisubsidylist, subsidyMeaList);

                //Awards validation
                String awardNumb = String.join(", ", awardsNumber);
                String awardSlno = "AW" + awardNumb;
                ApiUtils awardObject = new ApiUtils();
                List<String> awardLists = awardObject.AwardsapiAwardsValidate("AwardsDetails", awardSlno);
                Collections.sort(awardsapiawardslist);
                Collections.sort(awardLists);
                Assert.assertEquals(awardsapiawardslist, awardLists);
                //if(awardslist[s].equals(subsrt)){
                //  System.out.println("True");
                //}
                //Assert.assertSame(awardslist[s],subsrt);
                //Assert.assertEquals(awardslist[s].size(), awardLists.size());
        }
        public void BulkUploadResponsevalidations(String response, String SheetName, String TDID) throws IOException, ParseException {
                JsonPath js = new JsonPath(response);
                String totalRows = js.getString("totalRows");
                String errorRows = js.getString("errorRows");
                String message = js.getString("message");

                //fetches row error details in arraylist
                int count = js.getInt("validationErrorResult.size()");
                //System.out.println(count);

                List<String> rowlist = new ArrayList<String>();
                for (int l = 0; l < count; l++) {
                        rowlist.add(String.valueOf(js.getInt("validationErrorResult[" + l + "].row")));
                }

                //fetches columns error details in arraylist
                List<String> coulumnslist = new ArrayList<String>();
                for (int n = 0; n < count; n++) {
                        coulumnslist.add(js.getString("validationErrorResult[" + n + "].columns"));
                }

                //fetches errorMessage details in arraylist
                List<String> errorMessagelist = new ArrayList<String>();
                for (int p = 0; p < count; p++) {
                        errorMessagelist.add(js.getString("validationErrorResult[" + p + "].errorMessages"));
                }

                Reusable reUse = new Reusable();
                HashMap<String, String> verifydata = reUse.readExcelDataNew("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx", SheetName, TDID);
                if (verifydata.isEmpty()) {
                        Assert.fail("There is no matching TDID in the datasheet");
                }

                double totalrowsdouble = Double.parseDouble(verifydata.get("TotalRows"));
                int totalrowsint = (int) totalrowsdouble;
                String totalrowsdatasheet = String.valueOf(totalrowsint);
                double errorrowsdouble = Double.parseDouble(verifydata.get("ErrorRows"));
                int errorrowsint = (int) errorrowsdouble;
                String errorRowsdatasheet = String.valueOf(errorrowsint);
                String messagesdatasheet = verifydata.get("Messages");

                List<String> rowerrorlist = new ArrayList<String>();
                String rowerrorids = verifydata.get("Row");
                if(!(rowerrorids.equals("_BLANK"))) {
                        String[] rowidsplit = rowerrorids.split("\\|");
                        for (int i = 0; i < rowidsplit.length; i++) {
                                rowerrorlist.add(rowidsplit[i]);
                        }
                }

                List<String> columnerrorlist = new ArrayList<String>();
                String columnerrorids = verifydata.get("Columns");
                if(!(columnerrorids.equals("_BLANK"))) {
                        String[] columnidsplit = columnerrorids.split("\\|");
                        for (int a = 0; a < columnidsplit.length; a++) {
                                columnerrorlist.add(columnidsplit[a]);
                        }
                }

                List<String> errormessageslist = new ArrayList<String>();
                String errormessages = verifydata.get("ErrorMessages");
                if(!(errormessages.equals("_BLANK"))) {
                        String[] errormessagessplit = errormessages.split("\\|");
                        for (int b = 0; b < errormessagessplit.length; b++) {
                                errormessageslist.add(errormessagessplit[b]);
                        }
                }
                Collections.sort(rowlist);
                Collections.sort(rowerrorlist);
                coulumnslist.sort(String.CASE_INSENSITIVE_ORDER);
                columnerrorlist.sort(String.CASE_INSENSITIVE_ORDER);
                errorMessagelist.sort(String.CASE_INSENSITIVE_ORDER);
                errormessageslist.sort(String.CASE_INSENSITIVE_ORDER);
                Assert.assertEquals(rowlist, rowerrorlist);
                Assert.assertEquals(coulumnslist, columnerrorlist);
                Assert.assertEquals(errorMessagelist, errormessageslist);
                Assert.assertEquals(totalRows, totalrowsdatasheet);
                Assert.assertEquals(errorRows, errorRowsdatasheet);
                Assert.assertEquals(message, messagesdatasheet);
        }
        public void ExportAllResponsevalidation(String exportallsheetname, String testdatasheetname, String TDID) throws ParseException {
                String exportallvalidationsheet = "AwardsDataSheet";
                Reusable fetchexportallvalues = new Reusable();
                HashMap<String, String> fetawardnumbers = fetchexportallvalues.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx",testdatasheetname,TDID);
                if(fetawardnumbers.isEmpty()){
                        Assert.fail("There is no matching TDID in the datasheet");
                }
                String value = fetawardnumbers.get("ExpectedResult");
                String[] arrsplit = value.split("\\|");
                Integer count = arrsplit.length;
                List<String> awardsnumberlist[] = new ArrayList[count];
                for (int i = 0; i < count; i++) {
                        awardsnumberlist[i] = new ArrayList<>();
                        awardsnumberlist[i].add(arrsplit[i]);
                }

                for (int j = 0; j < count; j++) {
                        String awardnumbervalue = String.join(", ", awardsnumberlist[j]);
                        String awardSlno = "AW" + awardnumbervalue;
                        HashMap<String, String> Exportallawardsdata = fetchexportallvalues.readExcelDataNew("./src/test/resources/data/apiexportall.xlsx", exportallsheetname, awardnumbervalue);
                        if (Exportallawardsdata.isEmpty()) {
                                Assert.fail("There is no matching TDID in the datasheet");
                        }

                        List<String> exportalllist = new ArrayList<String>();
                        exportalllist.add(Exportallawardsdata.get("Subsidy Control (SC) Number"));
                        exportalllist.add(Exportallawardsdata.get("Subsidy Measure Title"));
                        exportalllist.add(awardnumbervalue);
                        exportalllist.add(Exportallawardsdata.get("Subsidy Objective"));
                        exportalllist.add(Exportallawardsdata.get("Subsidy Objective - Other"));
                        exportalllist.add(Exportallawardsdata.get("Subsidy Instrument"));
                        exportalllist.add(Exportallawardsdata.get("Subsidy Instrument - Other"));
                        exportalllist.add(Exportallawardsdata.get("Subsidy Element Full Amount Range"));
                        exportalllist.add(Exportallawardsdata.get("Subsidy Element Full Amount"));
                        exportalllist.add(Exportallawardsdata.get("National ID Type"));
                        if (Exportallawardsdata.get("National ID").contains("SC")){
                                String Nationalidvalue = Exportallawardsdata.get("National ID");
                                exportalllist.add(Nationalidvalue);
                        }
                        else if (Exportallawardsdata.get("National ID").contains("SB")){
                                String Nationalidvalue = Exportallawardsdata.get("National ID");
                                exportalllist.add(Nationalidvalue);
                        }
                        else if (Exportallawardsdata.get("National ID").matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
                                String Nationalidvalue = Exportallawardsdata.get("National ID");
                                exportalllist.add(Nationalidvalue);
                        }
                        else if (Exportallawardsdata.get("National ID").equalsIgnoreCase("_BLANK")){
                                String Nationalidvalue = Exportallawardsdata.get("National ID");
                                String Nationalvalue = Nationalidvalue.replace("_BLANK","");
                                exportalllist.add(Nationalvalue);
                        }
                        else {
                                double nationaliddouble = Double.parseDouble(Exportallawardsdata.get("National ID"));
                                long nationalidlong = (long) nationaliddouble;
                                String Nationalidvalue = String.valueOf(nationalidlong);
                                exportalllist.add(Nationalidvalue);
                        }
                        exportalllist.add(Exportallawardsdata.get("Beneficiary Name"));
                        exportalllist.add(Exportallawardsdata.get("Size of Organisation"));
                        exportalllist.add(Exportallawardsdata.get("Granting Authority Name"));
                        exportalllist.add(Exportallawardsdata.get("Legal Granting Date"));
                        exportalllist.add(Exportallawardsdata.get("Goods/Services Filter"));
                        exportalllist.add(Exportallawardsdata.get("Spending Region"));
                        exportalllist.add(Exportallawardsdata.get("Spending Sector"));
                        exportalllist.add(Exportallawardsdata.get("Published Date"));
                        System.out.println(exportalllist);

                        HashMap<String, String> Exportallawardsvalidation = fetchexportallvalues.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx", exportallvalidationsheet, awardSlno);
                        if (Exportallawardsvalidation.isEmpty()) {
                                Assert.fail("There is no matching TDID in the datasheet");
                        }

                        List<String> exportallvalidationlist = new ArrayList<String>();
                        //exportallvalidationlist.add(Exportallawardsvalidation.get("Subsidy Control (SC) Number"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("AwardNumber"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SubsidyMeasureTitle"));
                        exportallvalidationlist.add(awardnumbervalue);
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SubsidyObjective"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SubsidyObjectiveother"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SpendingSector"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SubsidyInstrumentother"));
                        String SubsidyFullAmountRangevalue = Exportallawardsvalidation.get("SubsidyFullAmountRange");
                        if (SubsidyFullAmountRangevalue.contains("NA")){
                                String SubsidyFullrange = SubsidyFullAmountRangevalue.replace("£","");
                                exportallvalidationlist.add(SubsidyFullrange);
                        }
                        else{
                                exportallvalidationlist.add(Exportallawardsvalidation.get("SubsidyFullAmountRange"));
                        }
                        double exactamountdouble = Double.parseDouble(Exportallawardsvalidation.get("SubsidyFullAmountExact"));
                        int exactamountdoubletoint = (int) exactamountdouble;
                        NumberFormat exactamountformat = NumberFormat.getInstance(new Locale("en","US"));
                        String subsidyexactamount = exactamountformat.format(exactamountdoubletoint);
                        String subsidyexactamountvalue = "£" + subsidyexactamount;
                        exportallvalidationlist.add(subsidyexactamountvalue);
                        exportallvalidationlist.add(Exportallawardsvalidation.get("NationalIdType"));

                        if (Exportallawardsvalidation.get("NationalId").contains("SC")){
                                String Nationalidvalue = Exportallawardsvalidation.get("NationalId");
                                exportallvalidationlist.add(Nationalidvalue);
                        }
                        else if (Exportallawardsvalidation.get("NationalId").contains("SB")){
                                String Nationalidvalue = Exportallawardsvalidation.get("NationalId");
                                exportallvalidationlist.add(Nationalidvalue);
                        }
                        else if (Exportallawardsvalidation.get("NationalId").matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
                                String Nationalidvalue = Exportallawardsvalidation.get("NationalId");
                                exportallvalidationlist.add(Nationalidvalue);
                        }
                        else if (Exportallawardsvalidation.get("NationalId").equalsIgnoreCase("_BLANK")){
                                String Nationalidvalue = Exportallawardsvalidation.get("NationalId");
                                String Nationalvalue = Nationalidvalue.replace("_BLANK","");
                                exportallvalidationlist.add(Nationalvalue);
                        }
                        else {
                                double nationaliddouble = Double.parseDouble(Exportallawardsvalidation.get("NationalId"));
                                long nationalidlong = (long) nationaliddouble;
                                String Nationalidvalue = String.valueOf(nationalidlong);
                                exportallvalidationlist.add(Nationalidvalue);
                        }
                        exportallvalidationlist.add(Exportallawardsvalidation.get("BeneficiaryName"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("OrgSize"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("GrantingAuthorityName"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("LegalGrantingDate"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("GoodsServicesFilter"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SpendingRegion"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("SpendingSector"));
                        exportallvalidationlist.add(Exportallawardsvalidation.get("PublishedAwardDate"));
                        Assert.assertEquals(exportalllist, exportallvalidationlist);
                        System.out.println(exportallvalidationlist);
                }
        }
        public void AddSingleSubsidyAwardResponsevalidations(String response, String SheetName, String TDID) throws IOException, ParseException {
                JsonPath js = new JsonPath(response);
                String totalErrors = js.getString("totalErrors");
                String message = js.getString("message");

                //fetches column error details in arraylist
                int count = js.getInt("validationErrorResult.size()");
                //System.out.println(count);

                List<String> columnlist = new ArrayList<String>();
                for (int l = 0; l < count; l++) {
                        columnlist.add(js.getString("validationErrorResult[" + l + "].column"));
                }

                //fetches  error message details in arraylist
                List<String> errorMessagelist = new ArrayList<String>();
                for (int n = 0; n < count; n++) {
                        errorMessagelist.add(js.getString("validationErrorResult[" + n + "].message"));
                }

                Reusable reUse = new Reusable();
                HashMap<String, String> verifydata = reUse.readExcelDataNew("./src/test/resources/data/PublishingSubsidiesAPIDatasheet.xlsx", SheetName, TDID);
                if (verifydata.isEmpty()) {
                        Assert.fail("There is no matching TDID in the datasheet");
                }

                double totalerrorsdouble = Double.parseDouble(verifydata.get("TotalErrors"));
                int totalerrorsint = (int) totalerrorsdouble;
                String totalerrorsdatasheet = String.valueOf(totalerrorsint);
                String messagesdatasheet = verifydata.get("Messages");
                String messagesdata;
                if (messagesdatasheet.contains("null")){
                        messagesdata = messagesdatasheet.replaceAll("^\"+|\"+$", "");
                }
                //String messagesdatasheetvalue = String.join(", ", messagesdatasheet);
                //String messagesdatasheetvalues = messagesdatasheet.replace("\"","");
                List<String> columnerrorlist = new ArrayList<String>();
                String columnerrorids = verifydata.get("Columns");
                if(!(columnerrorids.equals("_BLANK"))) {
                        String[] columnerroridsplit = columnerrorids.split("\\|");
                        for (int i = 0; i < columnerroridsplit.length; i++) {
                                columnerrorlist.add(columnerroridsplit[i]);
                        }
                }

                List<String> errormessageslist = new ArrayList<String>();
                String errormessages = verifydata.get("ErrorMessages");
                if(!(errormessages.equals("_BLANK"))) {
                        String[] errormessagessplit = errormessages.split("\\|");
                        for (int b = 0; b < errormessagessplit.length; b++) {
                                errormessageslist.add(errormessagessplit[b]);
                        }
                }
                columnlist.sort(String.CASE_INSENSITIVE_ORDER);
                columnlist.sort(String.CASE_INSENSITIVE_ORDER);
                columnlist.sort(String.CASE_INSENSITIVE_ORDER);
                columnlist.sort(String.CASE_INSENSITIVE_ORDER);
                Assert.assertEquals(columnlist, columnerrorlist);
                Assert.assertEquals(errorMessagelist, errormessageslist);
                Assert.assertEquals(totalErrors, totalerrorsdatasheet);
                //Assert.assertEquals(message, messagesdatasheet);
        }
        public void DashboardResponsevalidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
                ApiUtils apiutilities = new ApiUtils();
                if (apiEndpoint.equalsIgnoreCase("beisadmin.endpoint")){
                        apiutilities.DashboardGrantingAuthorityCountvalidations(response, SheetName, TDID, apiEndpoint);
                        apiutilities.DashboardBeisSubsidySchemeCountvalidations(response, SheetName, TDID, apiEndpoint);
                }
                else if ((apiEndpoint.equalsIgnoreCase("gaadmin.endpoint"))||(apiEndpoint.equalsIgnoreCase("gaapprover.endpoint"))||(apiEndpoint.equalsIgnoreCase("gaencoder.endpoint"))){
                        apiutilities.DashboardGrantingAuthorityCountNullvalidations(response);
                        apiutilities.DashboardSubsidySchemeCountvalidations(response, SheetName, TDID, apiEndpoint);
                }
        }
        public void ApprovalWorkflowResponsevalidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
                ApiUtils apiutilities = new ApiUtils();
                Requestdetails body = new Requestdetails();
                Reusable d = new Reusable();
                HashMap<String, String> data = d.readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx", SheetName, TDID);
                if (data.isEmpty()) {
                        Assert.fail("There is no matching TDID in the datasheet");
                }
                //expected status
                String expectedStatus = data.get("ExpectedStatus");
                //actual status
                String awardnumber = body.Fetchawardnumber("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx",SheetName,TDID);
                RequestSpecification requestspec = given().spec(requestSpecifications(awardnumber,"publicsearchbasepath.uri"));
                ResponseSpecification requestspecone = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
                String endpointvalue = getGlobalValue("awards.endpoint");
                String endpointvalueupdated = endpointvalue + "/{awardnumber}";
                Response apiresponse = requestspec.when().get(endpointvalueupdated).
                        then().spec(requestspecone).extract().response();
                String responseString = apiresponse.asString();
                JsonPath updatedjsonpathobject = new JsonPath(responseString);
                String actualStatus = updatedjsonpathobject.getString("status");
                Assert.assertEquals(expectedStatus, actualStatus);
        }
        public void ResponsevalidationsUIDatasheet(String response, String SheetName, String TDID) throws IOException, ParseException {
                JsonPath jsonpathobject = new JsonPath(response);
                int count = jsonpathobject.getInt("awards.size()");
                ArrayList<String> beneficiarylist = new ArrayList<String>();
                for(int i=0;i<count;i++){
                        beneficiarylist.add(jsonpathobject.getString("awards[" + i + "].beneficiary.beneficiaryName"));
                }
                String beneficiarynames = StringUtils.collectionToDelimitedString(beneficiarylist, "|");
                System.out.println(beneficiarynames);
                ApiUtils writetoexcelobject = new ApiUtils();
                writetoexcelobject.writeBeneficiaryNameToExcel("./src/test/resources/data/sample.xlsx",SheetName,TDID,"Expected Recipient",beneficiarynames);
        }
        public void searchResultsResponsevalidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
                ApiUtils apiutilities = new ApiUtils();
                Requestdetails body = new Requestdetails();
                Reusable d = new Reusable();
                HashMap<String, String> data = d.readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx", SheetName, TDID);
                if (data.isEmpty()) {
                        Assert.fail("There is no matching TDID in the datasheet");
                }
                String statusSheet = null;
                String validationTypeSheet = null;
                String searchNameSheet = null;

                //expected details
                if ((data.get("Status").trim().equalsIgnoreCase("_BLANK"))) {
                        statusSheet = "";
                } else {
                        statusSheet =  data.get("Status");
                }
                if ((data.get("Validationtype").trim().equalsIgnoreCase("_BLANK"))) {
                        validationTypeSheet = "";
                } else {
                        validationTypeSheet =  data.get("Validationtype");
                }
                if ((data.get("SearchName").trim().equalsIgnoreCase("_BLANK"))) {
                        searchNameSheet = "";
                } else {
                        searchNameSheet =  data.get("SearchName");
                }
                //actual details
                JsonPath jsonpathobject = new JsonPath(response);
                Integer totalSearchResults = jsonpathobject.getInt("totalSearchResults");
                Integer currentPagenumpagination = jsonpathobject.getInt("currentPage");
                Integer totalPages = jsonpathobject.getInt("totalPages");
                int count = jsonpathobject.getInt("awards.size()");
                //fetches status details in arraylist
                ArrayList<String> statuslist = new ArrayList<String>();
                for(int i=0;i<count;i++){
                        statuslist.add(jsonpathobject.getString("awards[" + i + "].status"));
                }
                //fetches subsidyMeasureTitle details in arraylist
                ArrayList<String> subsidyMeasureTitlelist = new ArrayList<String>();
                for(int j=0;j<count;j++) {
                        subsidyMeasureTitlelist.add(jsonpathobject.getString("awards[" + j + "].subsidyMeasureTitle"));
                }
                //fetches gaName details in arraylist
                ArrayList<String> gaNamelist = new ArrayList<String>();
                for(int k=0;k<count;k++) {
                        gaNamelist.add(jsonpathobject.getString("awards[" + k + "].gaName"));
                }
                //fetches scNumber details in arraylist
                ArrayList<String> scNumberlist = new ArrayList<String>();
                for(int l=0;l<count;l++) {
                        scNumberlist.add(jsonpathobject.getString("awards[" + l + "].scNumber"));
                }
                //Comparison between expected & actual results
                if (!(statusSheet.equalsIgnoreCase(""))) {
                        for (int m = 0; m < count; m++) {
                                String statusactual = statuslist.get(m);
                                Assert.assertEquals(statusSheet, statusactual);
                        }
                }
                if (!(searchNameSheet.equalsIgnoreCase(""))) {
                        if (validationTypeSheet.equalsIgnoreCase("SubsidyMeasureTitle")) {
                                for (int n = 0; n < count; n++) {
                                        String subsidyMeasureTitleactual = subsidyMeasureTitlelist.get(n);
                                        Assert.assertEquals(searchNameSheet, subsidyMeasureTitleactual);
                                }
                        }
                        if (validationTypeSheet.equalsIgnoreCase("GrantingAuthority")) {
                                for (int o = 0; o < count; o++) {
                                        String gaNameactual = gaNamelist.get(o);
                                        Assert.assertEquals(searchNameSheet, gaNameactual);
                                }
                        }
                        if (validationTypeSheet.equalsIgnoreCase("SubsidyControlNumber")) {
                                for (int p = 0; p < count; p++) {
                                        String scNumberactual = scNumberlist.get(p);
                                        Assert.assertEquals(searchNameSheet, scNumberactual);
                                }
                        }
                }
                if (validationTypeSheet.equalsIgnoreCase("Pagination")) {
                        String testdatasheetname = SheetName;
                        String testcaseid = TDID;
                        int updatedcounttotal;
                        int updatedcount = 0;
                        int totalcount = 0;
                        ArrayList<Integer> arraylisttotalcount = new ArrayList<Integer>();
                        for (int currentpage = 2; currentpage <= totalPages; currentpage++) {
                                Requestdetails requestdeailsobject = new Requestdetails();
                                HashMap<String, Object> updatedqueryparameters = requestdeailsobject.queryparameterbuilderpagination("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx", testdatasheetname, TDID, currentpage);
                                RequestSpecification requestspec = given().spec(requestSpecification("accessmanagementbasepath.uri")).queryParams(updatedqueryparameters);
                                ResponseSpecification requestspecone = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
                                Response apiresponse = requestspec.when().get(getGlobalValue("accessmanagementsearchResults.endpoint")).
                                        then().spec(requestspecone).extract().response();
                                String updatedresponseString = apiresponse.asString();
                                JsonPath updatedjsonpathobject = new JsonPath(updatedresponseString);
                                updatedcount = updatedjsonpathobject.getInt("awards.size()");
                                arraylisttotalcount.add(updatedcount);
                        }
                        int sum = 0;
                        for(int i=0;i<arraylisttotalcount.size();i++){
                                sum+=   arraylisttotalcount.get(i);
                        }
                        int totalcountvalues = sum + count;
                        Assert.assertSame(totalcountvalues,totalSearchResults);
                }
        }
}
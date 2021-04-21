package UK.GOV.BEIS.SCTDB.utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiUtils {
    public static RequestSpecification req=null;
    String awardvalue;

    public String fetchawardnumber(String awardnumber) {
        awardvalue = awardnumber.replace("AW", "");
        return awardvalue;
    }

    public RequestSpecification requestSpecification(String basepathuri) throws IOException {

//        if (req==null)
//        {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        req = new RequestSpecBuilder().setBaseUri(getGlobalValue(basepathuri))
                .setUrlEncodingEnabled(false)
                //.addFilter(RequestLoggingFilter.logRequestTo(log))
                //.addFilter(ResponseLoggingFilter.logResponseTo(log))
                        .setContentType(ContentType.JSON).build();
        return req;
//        }
//        return req;
    }

    public RequestSpecification requestSpecifications(String awardnumber,String basepathuri) throws IOException {
        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        req = new RequestSpecBuilder().setBaseUri(getGlobalValue(basepathuri))
                .setUrlEncodingEnabled(false)
                .addPathParam("awardnumber",awardnumber)
                //.addFilter(RequestLoggingFilter.logRequestTo(log))
                //.addFilter(ResponseLoggingFilter.logResponseTo(log))
                .setContentType(ContentType.JSON).build();
        return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("./serenity.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String datelongformatter(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(date1);
        return strDate;
    }

    public String dateformataddaward(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        String strDate = formatter.format(date1);
        return strDate;
    }

    public String dateformataddawardupdated(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = formatter.format(date1);
        return strDate;
    }

    public String floattostring(String floatvalue)  {
        double doublevalue = Double.parseDouble(floatvalue);
        long longvalue = (long) doublevalue;
        String formattedvalue = String.valueOf(longvalue);
        return formattedvalue;
    }

    public int floattoint(String floatvalue)  {
        double doublevalue = Double.parseDouble(floatvalue);
        int longvalue = (int) doublevalue;
        return longvalue;
    }

    public List<String> SubsidyMeaValidate(String SheetName, String TDID) {
        Reusable ds = new Reusable();
        HashMap<String, String> Responsedata = ds.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
        if(Responsedata.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }
        List<String> SubsidyMeaLis = new ArrayList<String>();
        SubsidyMeaLis.add(Responsedata.get("SubsidyMeasureTitle"));
        SubsidyMeaLis.add(TDID);
        SubsidyMeaLis.add(Responsedata.get("Adhoc"));
        SubsidyMeaLis.add(Responsedata.get("LegalBasis"));
        return SubsidyMeaLis;
    }

    public List<String> AwardsValidate(String SheetName, String TDID) throws ParseException {
        Reusable dsb = new Reusable();
        HashMap<String, String> Responsedat = dsb.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx", SheetName,TDID);
        if(Responsedat.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }
        String TDnew = TDID.replace("AW","");
        ApiUtils dateformat = new ApiUtils();
        String date = Responsedat.get("LegalGrantingDate");
        String strDate = dateformat.datelongformatter(date);
        double d = Double.parseDouble(Responsedat.get("SubsidyFullAmountExact"));
        int dtosi = (int) d;
        NumberFormat dtointse = NumberFormat.getInstance(new Locale("en","US"));
        String dtoint = dtointse.format(dtosi);
        List<String> AwardLis = new ArrayList<String>();
        AwardLis.add(String.valueOf(TDnew));
        AwardLis.add(Responsedat.get("SubsidyFullAmountRange"));
        AwardLis.add(dtoint);
        if (Responsedat.get("SubsidyObjective").trim().equalsIgnoreCase("Other")) {
            String purpose = Responsedat.get("SubsidyObjectiveother");
            String purposeupdated = "Other" + " " +"-" + " " + purpose;
            AwardLis.add(purposeupdated);
        }
        else{
            AwardLis.add(Responsedat.get("SubsidyObjective"));
        }
        if (Responsedat.get("SubsidyInstrument").trim().equalsIgnoreCase("Other")) {
            String type = Responsedat.get("SubsidyInstrumentother");
            String typeupdated = "Other" + " " +"-" + " " + type;
            AwardLis.add(typeupdated);
        }
        else{
            AwardLis.add(Responsedat.get("SubsidyInstrument"));
        }
        AwardLis.add(Responsedat.get("SpendingSector"));
        AwardLis.add(strDate);
        AwardLis.add(Responsedat.get("BeneficiaryName"));
        return AwardLis;
    }

    public List<String> AwardsapiSubsidyMeaValidate(String SheetName, String TDID) throws ParseException {
        Reusable fetchsubsidyvalues = new Reusable();
        HashMap<String, String> AwardsapiSubsidydata = fetchsubsidyvalues.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
        if(AwardsapiSubsidydata.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }
        ApiUtils dateformat = new ApiUtils();
        String date = AwardsapiSubsidydata.get("StartDate");
        String startDate = dateformat.datelongformatter(date);
        String finisheddate = AwardsapiSubsidydata.get("EndDate");
        String endDate = dateformat.datelongformatter(finisheddate);
        double durationdouble = Double.parseDouble(AwardsapiSubsidydata.get("Duration"));
        int durationint = (int) durationdouble;
        String durationvalue = String.valueOf(durationint);
        List<String> awardsApiList = new ArrayList<String>();
        awardsApiList.add(AwardsapiSubsidydata.get("SubsidyMeasureTitle"));
        awardsApiList.add(TDID);
        awardsApiList.add(startDate);
        awardsApiList.add(endDate);
        awardsApiList.add(durationvalue);
        if (AwardsapiSubsidydata.get("Budget").contains("-")){
            String budgetvalue = AwardsapiSubsidydata.get("Budget");
            awardsApiList.add(budgetvalue);
        }
        else {
            double budgetdouble = Double.parseDouble(AwardsapiSubsidydata.get("Budget"));
            String floatdecimalvalue = String.valueOf(budgetdouble).split("\\.")[1];
            if (floatdecimalvalue.equals("0")) {
                int budgetint = (int) budgetdouble;
                String budgetvalue = String.valueOf(budgetint);
                awardsApiList.add(budgetvalue);
            } else if (!(floatdecimalvalue.equals("0"))) {
                if (floatdecimalvalue.matches("^[0-9]*$")){
                awardsApiList.add(AwardsapiSubsidydata.get("Budget"));
                }
                else if (floatdecimalvalue.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
                    long budgetintupdated = (long) budgetdouble;
                    String budgetvalueupdated = String.valueOf(budgetintupdated);
                    awardsApiList.add(budgetvalueupdated);
                }
            }
        }
        awardsApiList.add(AwardsapiSubsidydata.get("Adhoc"));
        awardsApiList.add(AwardsapiSubsidydata.get("GaSubsidyWebLink"));
        awardsApiList.add(AwardsapiSubsidydata.get("LegalBasis"));
        return awardsApiList;
    }

    public List<String> AwardsapiAwardsValidate(String SheetName, String TDID) throws ParseException {
        Reusable aaAwards = new Reusable();
        HashMap<String, String> AaAwardsdata = aaAwards.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx", SheetName,TDID);
        if(AaAwardsdata.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }
        String TDIDnew = TDID.replace("AW","");
        ApiUtils dateformat = new ApiUtils();
        String date = AaAwardsdata.get("LegalGrantingDate");
        String legalDate = dateformat.datelongformatter(date);
        double exactamountdouble = Double.parseDouble(AaAwardsdata.get("SubsidyFullAmountExact"));
        int exactamountdoubletoint = (int) exactamountdouble;
        NumberFormat exactamountformat = NumberFormat.getInstance(new Locale("en","US"));
        String subsidyexactamount = exactamountformat.format(exactamountdoubletoint);
        List<String> AwardsapiAwardsvalidationList = new ArrayList<String>();
        AwardsapiAwardsvalidationList.add(String.valueOf(TDIDnew));
        if (AaAwardsdata.get("SubsidyInstrument").equalsIgnoreCase("Tax measures (tax credit, or tax/duty exemption)")) {
            AwardsapiAwardsvalidationList.add(AaAwardsdata.get("SubsidyFullAmountRange"));
        }
        AwardsapiAwardsvalidationList.add(subsidyexactamount);
        if (AaAwardsdata.get("SubsidyObjective").trim().equalsIgnoreCase("Other")) {
            String purpose = AaAwardsdata.get("SubsidyObjectiveother");
            String purposeupdated = "Other" + " " +"-" + " " + purpose;
            AwardsapiAwardsvalidationList.add(purposeupdated);
        }
        else{
            AwardsapiAwardsvalidationList.add(AaAwardsdata.get("SubsidyObjective"));
        }
        if (AaAwardsdata.get("SubsidyInstrument").trim().equalsIgnoreCase("Other")) {
            String type = AaAwardsdata.get("SubsidyInstrumentother");
            String typeupdated = "Other" + " " +"-" + " " + type;
            AwardsapiAwardsvalidationList.add(typeupdated);
        }
        else{
            AwardsapiAwardsvalidationList.add(AaAwardsdata.get("SubsidyInstrument"));
        }
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("SpendingSector"));
        if (AaAwardsdata.get("GoodsServicesFilter").equalsIgnoreCase("_BLANK")){
            String GoodsServices = AaAwardsdata.get("GoodsServicesFilter");
            String GoodsServicesFilter = GoodsServices.replace("_BLANK","");
            AwardsapiAwardsvalidationList.add(GoodsServicesFilter);
        }
        else {
            AwardsapiAwardsvalidationList.add(AaAwardsdata.get("GoodsServicesFilter"));
        }
        AwardsapiAwardsvalidationList.add(legalDate);
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("GrantingAuthorityName"));
        return AwardsapiAwardsvalidationList;
    }

    public List<String> AwardsapiBeneficiarydetails(String SheetName, String TDID) {
        Reusable awardsapibeneficiaryobject = new Reusable();
        HashMap<String, String> awardsapiBeneficiary = awardsapibeneficiaryobject.readExcelDataNew("./src/test/resources/data/SearchAPIDatasheet.xlsx",SheetName,TDID);
        if(awardsapiBeneficiary.isEmpty()){
            Assert.fail("There is no matching TDID in the datasheet");
        }
        List<String> AwardsapiBeneficiarylist = new ArrayList<String>();
        AwardsapiBeneficiarylist.add(TDID);
        if (awardsapiBeneficiary.get("NationalId").trim().equalsIgnoreCase("_BLANK")) {
            String Nationalidvalue = awardsapiBeneficiary.get("NationalId");
            String Nationalvalue = Nationalidvalue.replace("_BLANK","");
            AwardsapiBeneficiarylist.add(Nationalvalue);
        }
        else if(((awardsapiBeneficiary.get("NationalId").trim().matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"))) || ((awardsapiBeneficiary.get("NationalId").trim().matches("[a-zA-Z]+"))) || ((awardsapiBeneficiary.get("NationalId").trim().matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")))) {
            String Nationalidvalue = awardsapiBeneficiary.get("NationalId");
            AwardsapiBeneficiarylist.add(Nationalidvalue);
        }
        else{
            double nationaliddouble = Double.parseDouble(awardsapiBeneficiary.get("NationalId"));
            long nationalidlong = (long) nationaliddouble;
            String Nationalidvalue = String.valueOf(nationalidlong);
            AwardsapiBeneficiarylist.add(Nationalidvalue);
        }
        AwardsapiBeneficiarylist.add(awardsapiBeneficiary.get("NationalIdType"));
        AwardsapiBeneficiarylist.add(awardsapiBeneficiary.get("OrgSize"));
        return AwardsapiBeneficiarylist;
    }
    public void DashboardGrantingAuthorityCountvalidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
        JsonPath js = new JsonPath(response);
        String totalGrantingAuthority = js.getString("grantingAuthorityUserActionCount.totalGrantingAuthority");
        String totalActiveGA = js.getString("grantingAuthorityUserActionCount.totalActiveGA");
        String totalInactiveGA = js.getString("grantingAuthorityUserActionCount.totalDeactiveGA");
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx", SheetName,TDID);
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        String totalgafloat = data.get("TotalGrantingAuthority");
        String totalactivegafloat = data.get("TotalActiveGA");
        String totalinactivegafloat = data.get("TotalDeactiveGA");
        String totalgasheet = apiutilities.floattostring(totalgafloat);
        String totalactivegasheet = apiutilities.floattostring(totalactivegafloat);
        String totalinactivegasheet = apiutilities.floattostring(totalinactivegafloat);
        assertEquals("Error in Validating Total GA Count:",totalGrantingAuthority, totalgasheet);
        assertEquals("Error in Validating Total Active GA Count:",totalActiveGA, totalactivegasheet);
        assertEquals("Error in Validating Total Inactive GA Count:",totalInactiveGA, totalinactivegasheet);
    }
    public void DashboardGrantingAuthorityCountNullvalidations(String response) throws IOException, ParseException {
        JsonPath js = new JsonPath(response);
        String grantingAuthorityUserActionCount = js.getString("grantingAuthorityUserActionCount");
        String gacountnull =  grantingAuthorityUserActionCount + "value";
        assertEquals("Error in Validating Total GA Count:",gacountnull, "nullvalue");
    }
    public void DashboardBeisSubsidySchemeCountvalidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
        JsonPath js = new JsonPath(response);
        String totalPublishedSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalPublishedSubsidyMeasures");
        String totalDraftSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalDraftSubsidyMeasures");
        String totalSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalSubsidyMeasures");
        String totalAwaitingSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalAwaitingSubsidyMeasures");
        String totalDeletedSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalDeletedSubsidyMeasures");
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx", SheetName,TDID);
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        String beisTotalPublishedSubsidyMeasuresfloat = data.get("BeisTotalPublishedSubsidyMeasures");
        String beisTotalDraftSubsidyMeasuresfloat = data.get("BeisTotalDraftSubsidyMeasures");
        String beisTotalSubsidyMeasuresfloat = data.get("BeisTotalSubsidyMeasures");
        String beisTotalAwaitingSubsidyMeasuresfloat = data.get("BeisTotalAwaitingSubsidyMeasures");
        String beisTotalDeletedSubsidyMeasuresfloat = data.get("BeisTotalDeletedSubsidyMeasures");
        String beisTotalPublishedSubsidyMeasuressheet = apiutilities.floattostring(beisTotalPublishedSubsidyMeasuresfloat);
        String beisTotalDraftSubsidyMeasuressheet = apiutilities.floattostring(beisTotalDraftSubsidyMeasuresfloat);
        String beisTotalSubsidyMeasuressheet = apiutilities.floattostring(beisTotalSubsidyMeasuresfloat);
        String beisTotalAwaitingSubsidyMeasuressheet = apiutilities.floattostring(beisTotalAwaitingSubsidyMeasuresfloat);
        String beisTotalDeletedSubsidyMeasuressheet = apiutilities.floattostring(beisTotalDeletedSubsidyMeasuresfloat);
        assertEquals("Error in Validating Total Published Subsidy Measures Count:",totalPublishedSubsidyMeasures, beisTotalPublishedSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Draft Subsidy Measures Count:",totalDraftSubsidyMeasures, beisTotalDraftSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Subsidy Measures Count:",totalSubsidyMeasures, beisTotalSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Awaiting Subsidy Measures Count:",totalAwaitingSubsidyMeasures, beisTotalAwaitingSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Deleted Subsidy Measures Count:",totalDeletedSubsidyMeasures, beisTotalDeletedSubsidyMeasuressheet);
    }
    public void DashboardSubsidySchemeCountvalidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
        JsonPath js = new JsonPath(response);
        String totalPublishedSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalPublishedSubsidyMeasures");
        String totalDraftSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalDraftSubsidyMeasures");
        String totalSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalSubsidyMeasures");
        String totalAwaitingSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalAwaitingSubsidyMeasures");
        String totalDeletedSubsidyMeasures = js.getString("subsidyMeasureUserActionCount.totalDeletedSubsidyMeasures");
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew("./src/test/resources/data/AccessManagementAPIDatasheet.xlsx", SheetName,TDID);
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        String TotalPublishedSubsidyMeasuresfloat = data.get("TotalPublishedSubsidyMeasures");
        String TotalDraftSubsidyMeasuresfloat = data.get("TotalDraftSubsidyMeasures");
        String TotalSubsidyMeasuresfloat = data.get("TotalSubsidyMeasures");
        String TotalAwaitingSubsidyMeasuresfloat = data.get("TotalAwaitingSubsidyMeasures");
        String TotalDeletedSubsidyMeasuresfloat = data.get("TotalDeletedSubsidyMeasures");
        String TotalPublishedSubsidyMeasuressheet = apiutilities.floattostring(TotalPublishedSubsidyMeasuresfloat);
        String TotalDraftSubsidyMeasuressheet = apiutilities.floattostring(TotalDraftSubsidyMeasuresfloat);
        String TotalSubsidyMeasuressheet = apiutilities.floattostring(TotalSubsidyMeasuresfloat);
        String TotalAwaitingSubsidyMeasuressheet = apiutilities.floattostring(TotalAwaitingSubsidyMeasuresfloat);
        String TotalDeletedSubsidyMeasuressheet = apiutilities.floattostring(TotalDeletedSubsidyMeasuresfloat);
        assertEquals("Error in Validating Total Published Subsidy Measures Count:",totalPublishedSubsidyMeasures, TotalPublishedSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Draft Subsidy Measures Count:",totalDraftSubsidyMeasures, TotalDraftSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Subsidy Measures Count:",totalSubsidyMeasures, TotalSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Awaiting Subsidy Measures Count:",totalAwaitingSubsidyMeasures, TotalAwaitingSubsidyMeasuressheet);
        assertEquals("Error in Validating Total Deleted Subsidy Measures Count:",totalDeletedSubsidyMeasures, TotalDeletedSubsidyMeasuressheet);
    }
    public void DashboardBeisSubsidyAwardCountValidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
        Responsedetails responseDetailsObject = new Responsedetails();
        JsonPath js = new JsonPath(response);
        String totalPublishedAward = js.getString("awardUserActionCount.totalPublishedAward");
        String totalDraftAward = js.getString("awardUserActionCount.totalDraftAward");
        String totalSubsidyAward = js.getString("awardUserActionCount.totalSubsidyAward");
        String totalAwaitingAward = js.getString("awardUserActionCount.totalAwaitingAward");
        String totalDeletedAward = js.getString("awardUserActionCount.totalDeletedAward");
        ArrayList<String> tdlist = new ArrayList<String>();
        tdlist.add("TD_016");
        tdlist.add("TD_001");
        tdlist.add("TD_002");
        tdlist.add("TD_003");
        tdlist.add("TD_004");
        for (int i=0;i<tdlist.size();i++){
            String TDIDvalue = tdlist.get(i);
            int awardCount = responseDetailsObject.fetchAwardCountDetailsFromSearchResultsResponse("SearchResults", TDIDvalue);
            String awardCountValue = String.valueOf(awardCount);
            if (TDIDvalue.equalsIgnoreCase("TD_016")){
                assertEquals("Error in Validating Total Subsidy Award Count:",totalSubsidyAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_001")){
                assertEquals("Error in Validating Total Awaiting Award Count:",totalAwaitingAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_002")){
                assertEquals("Error in Validating Total Published Award Count:",totalPublishedAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_003")){
                assertEquals("Error in Validating Total Deleted Award Count:",totalDeletedAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_004")){
                assertEquals("Error in Validating Total Draft Award Count:",totalDraftAward, awardCountValue);
            }
        }
    }
    public void DashboardSubsidyAwardCountValidations(String response, String SheetName, String TDID, String apiEndpoint) throws IOException, ParseException {
        Responsedetails responseDetailsObject = new Responsedetails();
        JsonPath js = new JsonPath(response);
        String totalPublishedAward = js.getString("awardUserActionCount.totalPublishedAward");
        String totalDraftAward = js.getString("awardUserActionCount.totalDraftAward");
        String totalSubsidyAward = js.getString("awardUserActionCount.totalSubsidyAward");
        String totalAwaitingAward = js.getString("awardUserActionCount.totalAwaitingAward");
        String totalDeletedAward = js.getString("awardUserActionCount.totalDeletedAward");
        ArrayList<String> tdlist = new ArrayList<String>();
        tdlist.add("TD_007");
        tdlist.add("TD_017");
        tdlist.add("TD_018");
        tdlist.add("TD_019");
        tdlist.add("TD_020");
        for (int i=0;i<tdlist.size();i++){
            String TDIDvalue = tdlist.get(i);
            int awardCount = responseDetailsObject.fetchAwardCountDetailsFromSearchResultsResponse("SearchResults", TDIDvalue);
            String awardCountValue = String.valueOf(awardCount);
            if (TDIDvalue.equalsIgnoreCase("TD_007")){
                assertEquals("Error in Validating Total Subsidy Award Count:",totalSubsidyAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_017")){
                assertEquals("Error in Validating Total Awaiting Award Count:",totalAwaitingAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_018")){
                assertEquals("Error in Validating Total Published Award Count:",totalPublishedAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_019")){
                assertEquals("Error in Validating Total Deleted Award Count:",totalDeletedAward, awardCountValue);
            }
            else if (TDIDvalue.equalsIgnoreCase("TD_020")){
                assertEquals("Error in Validating Total Draft Award Count:",totalDraftAward, awardCountValue);
            }
        }
    }
    public void writeBeneficiaryNameToExcel(String filePath, String SheetName, String TDID,String ColumnTitletobewritten, String Columnvalues ) {
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheet(SheetName);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                if (currentRow.getCell(0).getStringCellValue().equalsIgnoreCase(TDID)) {
                    for (int cellNum = 1; cellNum < currentRow.getLastCellNum(); cellNum++) {
                        String ColumnTitle = sheet.getRow(0).getCell(cellNum).getStringCellValue();
                        if(ColumnTitle.equalsIgnoreCase(ColumnTitletobewritten)) {
                            currentRow.createCell(cellNum).setCellValue(Columnvalues);
                        }
                    }
                }
            }
            FileOutputStream writeexcelFile = new FileOutputStream(filePath);
            workbook.write(writeexcelFile);
            workbook.close();
        } catch (IOException e) {
            Assert.fail("Exception in reading Test Data from Excel file");
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }
}
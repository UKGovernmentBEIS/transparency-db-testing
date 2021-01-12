package UK.GOV.BEIS.SCTDB.utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApiUtils {
    public static RequestSpecification req;
    String awardvalue;

    public String fetchawardnumber(String awardnumber) {
        awardvalue = awardnumber.replace("AW", "");
        return awardvalue;
    }

    public RequestSpecification requestSpecification(String basepathuri) throws IOException {

        if (req==null)
        {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        req = new RequestSpecBuilder().setBaseUri(getGlobalValue(basepathuri))
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                        .setContentType(ContentType.JSON).build();
        return req;
        }
        return req;
    }

    public RequestSpecification requestSpecifications(String awardnumber) throws IOException {
        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        req = new RequestSpecBuilder().setBaseUri(getGlobalValue("publicsearchbasepath.uri"))
                .addPathParam("awardnumber",awardnumber)
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM YYYY");
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
        //System.out.println(SubsidyMeaLis);
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
        if (Responsedat.get("SubsidyInstrument").equalsIgnoreCase("Tax measures (tax credit, or tax/duty exemption)")) {
            AwardLis.add(Responsedat.get("SubsidyFullAmountRange"));
        }
        AwardLis.add(dtoint);
        AwardLis.add(Responsedat.get("SubsidyObjective"));
        AwardLis.add(Responsedat.get("SubsidyInstrument"));
        AwardLis.add(Responsedat.get("SpendingSector"));
        AwardLis.add(strDate);
        AwardLis.add(Responsedat.get("BeneficiaryName"));
        //System.out.println(AwardLis);
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
        awardsApiList.add(AwardsapiSubsidydata.get("PublishedMeasureDate"));
        awardsApiList.add(AwardsapiSubsidydata.get("CreatedBy"));
        awardsApiList.add(AwardsapiSubsidydata.get("ApprovedBy"));
        awardsApiList.add(AwardsapiSubsidydata.get("Status"));
        //System.out.println(awardsApiList);
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
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("SubsidyObjective"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("SubsidyInstrument"));
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
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("CreatedBy"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("ApprovedBy"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("Status"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("CreatedTimestamp"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("LastModifiedTimestamp"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("PublishedAwardDate"));
        AwardsapiAwardsvalidationList.add(AaAwardsdata.get("GrantingAuthorityName"));
        //System.out.println(AwardsapiAwardsvalidationList);
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
        if (awardsapiBeneficiary.get("NationalId").contains("SC")){
            String Nationalidvalue = awardsapiBeneficiary.get("NationalId");
            AwardsapiBeneficiarylist.add(Nationalidvalue);
        }
        else if (awardsapiBeneficiary.get("NationalId").contains("SB")){
            String Nationalidvalue = awardsapiBeneficiary.get("NationalId");
            AwardsapiBeneficiarylist.add(Nationalidvalue);
        }
        else if (awardsapiBeneficiary.get("NationalId").matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            String Nationalidvalue = awardsapiBeneficiary.get("NationalId");
            AwardsapiBeneficiarylist.add(Nationalidvalue);
        }
        else if (awardsapiBeneficiary.get("NationalId").equalsIgnoreCase("_BLANK")){
            String Nationalidvalue = awardsapiBeneficiary.get("NationalId");
            String Nationalvalue = Nationalidvalue.replace("_BLANK","");
            AwardsapiBeneficiarylist.add(Nationalvalue);
        }
        else {
            double nationaliddouble = Double.parseDouble(awardsapiBeneficiary.get("NationalId"));
            long nationalidlong = (long) nationaliddouble;
            String Nationalidvalue = String.valueOf(nationalidlong);
            AwardsapiBeneficiarylist.add(Nationalidvalue);
        }
        AwardsapiBeneficiarylist.add(awardsapiBeneficiary.get("NationalIdType"));
        AwardsapiBeneficiarylist.add(awardsapiBeneficiary.get("OrgSize"));
        //AwardsapiBeneficiarylist.add(awardsapiBeneficiary.get("Region"));
        //AwardsapiBeneficiarylist.add(awardsapiBeneficiary.get("GrantingAuthorityName"));
        //System.out.println(AwardsapiBeneficiarylist);
        return AwardsapiBeneficiarylist;
    }
}
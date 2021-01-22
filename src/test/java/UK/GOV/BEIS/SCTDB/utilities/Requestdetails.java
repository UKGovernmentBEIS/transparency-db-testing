package UK.GOV.BEIS.SCTDB.utilities;
import java.text.ParseException;
import java.util.*;

import java.io.IOException;

import org.junit.Assert;

import java.util.HashMap;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class Requestdetails {

    public HashMap<String, Object> Payloadbuilder(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        Reusable d = new Reusable();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName,TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //"beneficiaryName"
        if ((data.get("Recipient").trim().equalsIgnoreCase("_BLANK")) || (data.get("Recipient").trim().equalsIgnoreCase("No"))) {
            map.put("beneficiaryName", "");
        } else {
            map.put("beneficiaryName", data.get("Recipient"));
        }

        //"subsidyMeasureTitle"
        map.put("subsidyMeasureTitle", "");

        //"subsidyObjective"
        if (data.get("Purpose").trim().equalsIgnoreCase("_BLANK")) {
            map.put("subsidyObjective", Arrays.asList());
        }
        else if (data.get("Purpose").trim().equalsIgnoreCase("other")) {
            List<String> purposelist = new ArrayList<String>();
            String purpose = data.get("Other Purpose");
            String purposeupdated = "Other" + "-" + purpose;
            String[] arrsplit = purposeupdated.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                purposelist.add(arrsplit[i]);
            map.put("subsidyObjective", purposelist);
        }
        else {
            List<String> purposelist = new ArrayList<String>();
            String purpose = data.get("Purpose");
            String[] arrsplit = purpose.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                purposelist.add(arrsplit[i]);
            map.put("subsidyObjective", purposelist);
        }

        //"spendingRegion"
        map.put("spendingRegion", Arrays.asList());

        //"subsidyInstrument"
        if (data.get("Type").trim().equalsIgnoreCase("_BLANK")) {
            map.put("subsidyInstrument", Arrays.asList());
        }
        else if (data.get("Type").trim().equalsIgnoreCase("other")) {
            List<String> typelist = new ArrayList<String>();
            String type = data.get("Other Type");
            String typeupdated = "Other" + "-" + type;
            String[] arrsplit = typeupdated.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                typelist.add(arrsplit[i]);
            map.put("subsidyInstrument", typelist);
        }
        else {
            List<String> typelist = new ArrayList<String>();
            String type = data.get("Type");
            String[] arrsplits = type.split("\\|");
            for (int j = 0; j < arrsplits.length; j++)
                typelist.add(arrsplits[j]);
            map.put("subsidyInstrument", typelist);
        }

        //"spendingSector"
        if (data.get("Sector").trim().equalsIgnoreCase("_BLANK")) {
            map.put("spendingSector", Arrays.asList());
        } else {
            List<String> sectorlist = new ArrayList<String>();
            String valued = data.get("Sector");
            String[] arrspliti = valued.split("\\|");
            for (int k = 0; k < arrspliti.length; k++)
                sectorlist.add(arrspliti[k]);
            map.put("spendingSector", sectorlist);
        }

        //"legalGrantingFromDate"
        String From = data.get("From").toString();
        if ((From.trim().equalsIgnoreCase("_BLANK")) || (From.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingFromDate", "");
        } else {
            map.put("legalGrantingFromDate", From);
        }

        //"legalGrantingToDate"
        String To = data.get("To").toString();
        if ((To.trim().equalsIgnoreCase("_BLANK")) || (To.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingToDate", "");
        } else {
            map.put("legalGrantingToDate", To);
        }

        //"pageNumber"
        map.put("pageNumber", "1");

        //"totalRecordsPerPage"
        map.put("totalRecordsPerPage", "10");

        //"sortBy"
        if (data.get("Sortby").trim().equalsIgnoreCase("_BLANK")) {
            map.put("sortBy", Arrays.asList());
        } else {
            List<String> Sortbylis = new ArrayList<String>();
            String sortval = data.get("Sortby");
            String[] arrsort = sortval.split("\\|");
            for (int x = 0; x < arrsort.length; x++)
                Sortbylis.add(arrsort[x]);
            map.put("sortBy", Sortbylis);
        }
        return map;
    }
    public HashMap<String, Object> createPayloadbuilderwithtotalrecordsperpage(String filePath, String SheetName, String TDID,Integer pagenumber) throws IOException, ParseException {
        Reusable d = new Reusable();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName,TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //"beneficiaryName"
        if ((data.get("Recipient").trim().equalsIgnoreCase("_BLANK")) || (data.get("Recipient").trim().equalsIgnoreCase("No"))) {
            map.put("beneficiaryName", "");
        } else {
            map.put("beneficiaryName", data.get("Recipient"));
        }

        //"subsidyMeasureTitle"
        map.put("subsidyMeasureTitle", "");

        //"subsidyObjective"
        if (data.get("Purpose").trim().equalsIgnoreCase("_BLANK")) {
            map.put("subsidyObjective", Arrays.asList());
        } else {
            List<String> mylists = new ArrayList<String>();
            String value = data.get("Purpose");
            String[] arrsplit = value.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                mylists.add(arrsplit[i]);
            map.put("subsidyObjective", mylists);
        }

        //"spendingRegion"
        map.put("spendingRegion", Arrays.asList());

        //"subsidyInstrument"
        if (data.get("Type").trim().equalsIgnoreCase("_BLANK")) {
            map.put("subsidyInstrument", Arrays.asList());
        } else {
            List<String> si = new ArrayList<String>();
            String values = data.get("Type");
            String[] arrsplits = values.split("\\|");
            for (int j = 0; j < arrsplits.length; j++)
                si.add(arrsplits[j]);
            map.put("subsidyInstrument", si);
        }

        //"spendingSector"
        if (data.get("Sector").trim().equalsIgnoreCase("_BLANK")) {
            map.put("spendingSector", Arrays.asList());
        } else {
            List<String> ss = new ArrayList<String>();
            String valued = data.get("Sector");
            String[] arrspliti = valued.split("\\|");
            for (int k = 0; k < arrspliti.length; k++)
                ss.add(arrspliti[k]);
            map.put("spendingSector", ss);
        }

        //"legalGrantingFromDate"
        String From = data.get("From").toString();
        if ((From.trim().equalsIgnoreCase("_BLANK")) || (From.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingFromDate", "");
        } else {
            map.put("legalGrantingFromDate", From);
        }

        //"legalGrantingToDate"
        String To = data.get("To").toString();
        if ((To.trim().equalsIgnoreCase("_BLANK")) || (To.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingToDate", "");
        } else {
            map.put("legalGrantingToDate", To);
        }

        //"pageNumber"
        map.put("pageNumber", pagenumber);

        //"totalRecordsPerPage"
        double TotalRecordsperpagedouble = Double.parseDouble(data.get("TotalRecordsperpage"));
        int TotalRecordsperpageint = (int) TotalRecordsperpagedouble;
        String totalRecordsPerPage = String.valueOf(TotalRecordsperpageint);
        map.put("totalRecordsPerPage", totalRecordsPerPage);

        //"sortBy"
        if (data.get("Sortby").trim().equalsIgnoreCase("_BLANK")) {
            map.put("sortBy", Arrays.asList());
        } else {
            List<String> Sortbylis = new ArrayList<String>();
            String sortval = data.get("Sortby");
            String[] arrsort = sortval.split("\\|");
            for (int x = 0; x < arrsort.length; x++)
                Sortbylis.add(arrsort[x]);
            map.put("sortBy", Sortbylis);
        }
        return map;
    }
    public HashMap<String, Object> Payloadbuilderexportall(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        Reusable d = new Reusable();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName,TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //"beneficiaryName"
        if ((data.get("Recipient").trim().equalsIgnoreCase("_BLANK")) || (data.get("Recipient").trim().equalsIgnoreCase("No"))) {
            map.put("beneficiaryName", "");
        } else {
            map.put("beneficiaryName", data.get("Recipient"));
        }

        //"subsidyMeasureTitle"
        map.put("subsidyMeasureTitle", "");

        //"subsidyObjective"
        if (data.get("Purpose").trim().equalsIgnoreCase("_BLANK")) {
            map.put("subsidyObjective", Arrays.asList());
        } else {
            List<String> mylists = new ArrayList<String>();
            String value = data.get("Purpose");
            String[] arrsplit = value.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                mylists.add(arrsplit[i]);
            map.put("subsidyObjective", mylists);
        }

        //"spendingRegion"
        map.put("spendingRegion", Arrays.asList());

        //"subsidyInstrument"
        if (data.get("Type").trim().equalsIgnoreCase("_BLANK")) {
            map.put("subsidyInstrument", Arrays.asList());
        } else {
            List<String> si = new ArrayList<String>();
            String values = data.get("Type");
            String[] arrsplits = values.split("\\|");
            for (int j = 0; j < arrsplits.length; j++)
                si.add(arrsplits[j]);
            map.put("subsidyInstrument", si);
        }

        //"spendingSector"
        if (data.get("Sector").trim().equalsIgnoreCase("_BLANK")) {
            map.put("spendingSector", Arrays.asList());
        } else {
            List<String> ss = new ArrayList<String>();
            String valued = data.get("Sector");
            String[] arrspliti = valued.split("\\|");
            for (int k = 0; k < arrspliti.length; k++)
                ss.add(arrspliti[k]);
            map.put("spendingSector", ss);
        }

        //"legalGrantingFromDate"
        String From = data.get("From").toString();
        if ((From.trim().equalsIgnoreCase("_BLANK")) || (From.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingFromDate", "");
        } else {
            map.put("legalGrantingFromDate", From);
        }

        //"legalGrantingToDate"
        String To = data.get("To").toString();
        if ((To.trim().equalsIgnoreCase("_BLANK")) || (To.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingToDate", "");
        } else {
            map.put("legalGrantingToDate", To);
        }

        //"pageNumber"
        map.put("pageNumber", "1");

        //"totalRecordsPerPage"
        map.put("totalRecordsPerPage", "10");

        //"sortBy"
        if (data.get("Sortby").trim().equalsIgnoreCase("_BLANK")) {
            map.put("sortBy", Arrays.asList());
        } else {
            map.put("sortBy", Arrays.asList());
        }
        return map;
    }
    public HashMap<String, Object> AddSingleSubsidyPayloadbuilder(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        Reusable d = new Reusable();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName,TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //"subsidyControlTitle"
        if ((data.get("SubsidyControlTitle").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyControlTitle", "");
        } else {
            map.put("subsidyControlTitle", data.get("SubsidyControlTitle"));
        }

        //"subsidyControlNumber"
        if ((data.get("SubsidyControlNumber").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyControlNumber", "");
        } else {
            map.put("subsidyControlNumber", data.get("SubsidyControlNumber"));
        }

        //"nationalIdType"
        if ((data.get("NationalIdType").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("nationalIdType", "");
        } else {
            map.put("nationalIdType", data.get("NationalIdType"));
        }

        //"nationalId"
        if ((data.get("NationalId").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("nationalId", "");
        }
        else if(((data.get("NationalId").trim().matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"))) || ((data.get("NationalId").trim().matches("[a-zA-Z]+"))) || ((data.get("NationalId").trim().matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")))) {
            map.put("nationalId", data.get("NationalId"));
        }
        else{
            double nationalIddouble = Double.parseDouble(data.get("NationalId"));
            long nationalIdlong = (long) nationalIddouble;
            String nationalIdvalue = String.valueOf(nationalIdlong);
            map.put("nationalId",nationalIdvalue);
        }

        //"beneficiaryName"
        if ((data.get("BeneficiaryName").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("beneficiaryName", "");
        } else {
            map.put("beneficiaryName", data.get("BeneficiaryName"));
        }

        //"orgSize"
        if ((data.get("OrgSize").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("orgSize", "");
        } else {
            map.put("orgSize", data.get("OrgSize"));
        }

        //"subsidyInstrument"
        if ((data.get("SubsidyInstrument").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyInstrument", "");
        } else {
            map.put("subsidyInstrument", data.get("SubsidyInstrument"));
        }

        //"subsidyObjective"
        if ((data.get("SubsidyObjective").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyObjective", "");
        } else {
            map.put("subsidyObjective", data.get("SubsidyObjective"));
        }

        //"subsidyAmountRange"
        if ((data.get("SubsidyAmountRange").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyAmountRange", "");
        } else {
            map.put("subsidyAmountRange", data.get("SubsidyAmountRange"));
        }

        //"subsidyAmountExact"
        if ((data.get("SubsidyAmountExact").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyAmountExact", "");
        }
        else if(((data.get("SubsidyAmountExact").trim().matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"))) || ((data.get("SubsidyAmountExact").trim().matches("[a-zA-Z]+"))) || ((data.get("SubsidyAmountExact").trim().matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")))) {
            map.put("subsidyAmountExact", data.get("SubsidyAmountExact"));
        }
        else{
            double subsidyAmountExactdouble = Double.parseDouble(data.get("SubsidyAmountExact"));
            long subsidyAmountExactlong = (long) subsidyAmountExactdouble;
            String subsidyAmountExactvalue = String.valueOf(subsidyAmountExactlong);
            map.put("subsidyAmountExact",subsidyAmountExactvalue);
        }

        //"legalGrantingDate"
        if ((data.get("LegalGrantingDate").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("legalGrantingDate", "");
        }
        else if (TDID.equalsIgnoreCase("TD_066") || (TDID.equalsIgnoreCase("TD_067"))){
            map.put("legalGrantingDate", data.get("LegalGrantingDate"));
        }
        else {
            ApiUtils dateformat = new ApiUtils();
            String date = data.get("LegalGrantingDate");
            String legalDate = dateformat.dateformataddawardupdated(date);
            map.put("legalGrantingDate", legalDate);
        }

        //"grantingAuthorityName"
        if ((data.get("GrantingAuthorityName").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("grantingAuthorityName", "");
        } else {
            map.put("grantingAuthorityName", data.get("GrantingAuthorityName"));
        }

        //"goodsOrServices"
        if ((data.get("GoodsorServices").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("goodsOrServices", "");
        } else {
            map.put("goodsOrServices", data.get("GoodsorServices"));
        }

        //"spendingRegion"
        if ((data.get("SpendingRegion").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("spendingRegion", "");
        } else {
            map.put("spendingRegion", data.get("SpendingRegion"));
        }

        //"spendingSector"
        if ((data.get("SpendingSector").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("spendingSector", "");
        } else {
            map.put("spendingSector", data.get("SpendingSector"));
        }

        //"subsidyObjectiveOther"
        if ((data.get("SubsidyObjectiveOther").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyObjectiveOther", "");
        } else {
            map.put("subsidyObjectiveOther", data.get("SubsidyObjectiveOther"));
        }

        //"subsidyInstrumentOther"
        if ((data.get("SubsidyInstrumentOther").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("subsidyInstrumentOther", "");
        } else {
            map.put("subsidyInstrumentOther", data.get("SubsidyInstrumentOther"));
        }

        return map;
    }

    public HashMap<String, Object> headerbuilder(String filePath, String SheetName, String TDID) {
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName,TDID);
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        //userName
        if ((data.get("UserName").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("userName", "");
        } else {
            map.put("userName", data.get("UserName"));
        }

        //password
        if ((data.get("Password").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("password", "");
        } else {
            map.put("password", data.get("Password"));
        }

        //role
        if ((data.get("Role").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("role", "");
        } else {
            map.put("role", data.get("Role"));
        }

        //grantingAuthorityGroupId
        if ((data.get("GrantingAuthorityGroupId").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("grantingAuthorityGroupId", "");
        } else {
            String floatvalue = data.get("GrantingAuthorityGroupId");
            String gagroupid = apiutilities.floattostring(floatvalue);
            map.put("grantingAuthorityGroupId", gagroupid);
        }

        //grantingAuthorityGroupName
        if ((data.get("GrantingAuthorityGroupName").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("grantingAuthorityGroupName", "");
        } else {
            map.put("grantingAuthorityGroupName", data.get("GrantingAuthorityGroupName"));
        }
        return map;
    }
    public HashMap<String, Object> ApprovalworkflowPayloadbuilder(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        Reusable d = new Reusable();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName, TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //"expected status
        if ((data.get("ExpectedStatus").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("status", "");
        } else {
            map.put("status", data.get("ExpectedStatus"));
        }

        if ((data.get("ExpectedStatus").trim().equalsIgnoreCase("Reject"))) {
            map.put("reason", data.get("Rejection Reason"));
        }
        return map;
    }
    public String Fetchawardnumber(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        String awardnumber;
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName, TDID);
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //awardnumber
        String awardnumberfloat = data.get("AwardNumber");
        if (awardnumberfloat.contains("AW")){
            awardnumber = awardnumberfloat;
        }
        else {
            awardnumber = apiutilities.floattostring(awardnumberfloat);
        }
        return awardnumber;
    }
    public HashMap<String, Object> PayloadbuilderTestData(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        Reusable d = new Reusable();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName,TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //"beneficiaryName"
        if ((data.get("Recipient").trim().equalsIgnoreCase("_BLANK")) || (data.get("Recipient").trim().equalsIgnoreCase("No"))) {
            map.put("beneficiaryName", "");
        } else {
            map.put("beneficiaryName", data.get("Recipient"));
        }

        //"subsidyMeasureTitle"
        map.put("subsidyMeasureTitle", "");

        //"subsidyObjective"
        String purpose = "notblank";
        ArrayList<String> purposelist = new ArrayList<String>();
        if (data.get("Purpose").trim().equalsIgnoreCase("_BLANK")) {
            purpose = data.get("Purpose");
        }
        else if (data.get("Purpose").trim().equalsIgnoreCase("other")) {
            purpose = data.get("Other Purpose");
            String[] arrsplit = purpose.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                purposelist.add(arrsplit[i]);
        }
        else {
            purpose = data.get("Purpose");
            String[] arrsplit = purpose.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                purposelist.add(arrsplit[i]);
        }

        //"Purpose Filter"
        String purposefilter = "notblank";
        ArrayList<String> purposefilterlist = new ArrayList<String>();
        if (data.get("Purpose Filter").trim().equalsIgnoreCase("_BLANK")) {
            purposefilter = data.get("Purpose Filter");
        }
        else if (data.get("Purpose Filter").trim().equalsIgnoreCase("other")) {
            purposefilter = data.get("Other Purpose Filter");
            String[] arrsplit = purposefilter.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                purposefilterlist.add(arrsplit[i]);
        }
        else {
            purposefilter = data.get("Purpose Filter");
            String[] arrsplit = purposefilter.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                purposefilterlist.add(arrsplit[i]);
        }

        if (!(purposefilter.equalsIgnoreCase("_BLANK"))){
            int purposefiltercount = purposefilterlist.size();
            for (int j = 0; j < purposefiltercount; j++) {
                String purposefiltervalue = String.join(", ", purposefilterlist.get(j));
                if (purposelist.contains(purposefiltervalue)) {
                    purposelist.remove(new String(purposefiltervalue));
                } else {
                    purposelist.add(purposefiltervalue);
                }
            }
            map.put("subsidyObjective", purposelist);
        }
        else {
            map.put("subsidyObjective", purposelist);
        }

        //"spendingRegion"
        map.put("spendingRegion", Arrays.asList());

        //"subsidyInstrument"
        String type = "notblank";
        ArrayList<String> typelist = new ArrayList<String>();
        if (data.get("Type").trim().equalsIgnoreCase("_BLANK")) {
            type = data.get("Type");
        }
        else if (data.get("Type").trim().equalsIgnoreCase("other")) {
            type = data.get("Other Type");
            String[] arrsplit = type.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                typelist.add(arrsplit[i]);
        }
        else {
            type = data.get("Type");
            String[] arrsplit = type.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                typelist.add(arrsplit[i]);
        }

        //"Type Filter"
        String typefilter = "notblank";
        ArrayList<String> typefilterlist = new ArrayList<String>();
        if (data.get("Type Filter").trim().equalsIgnoreCase("_BLANK")) {
            typefilter = data.get("Type Filter");
        }
        else if (data.get("Type Filter").trim().equalsIgnoreCase("other")) {
            typefilter = data.get("Other Type Filter");
            String[] arrsplit = typefilter.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                typefilterlist.add(arrsplit[i]);
        }
        else {
            String value = data.get("Type Filter");
            String[] arrsplit = value.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                typefilterlist.add(arrsplit[i]);
        }

        if (!(typefilter.equalsIgnoreCase("_BLANK"))){
            int typefiltercount = typefilterlist.size();
            for (int j = 0; j < typefiltercount; j++) {
                String typefiltervalue = String.join(", ", typefilterlist.get(j));
                if (typelist.contains(typefiltervalue)) {
                    typelist.remove(new String(typefiltervalue));
                } else {
                    typelist.add(typefiltervalue);
                }
            }
            map.put("subsidyInstrument", typelist);
        }
        else {
            map.put("subsidyInstrument", typelist);
        }

        //"spendingSector"
        String sector = "notblank";
        ArrayList<String> sectorlist = new ArrayList<String>();
        if (data.get("Sector").trim().equalsIgnoreCase("_BLANK")) {
            sector = data.get("Sector");
        }
        else {
            sector = data.get("Sector");
            String[] arrsplit = sector.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                sectorlist.add(arrsplit[i]);
        }

        //"Sector Filter"
        String sectorfilter = "notblank";
        ArrayList<String> sectorfilterlist = new ArrayList<String>();
        if (data.get("Sector Filter").trim().equalsIgnoreCase("_BLANK")) {
            sectorfilter = data.get("Sector Filter");
        }
        else {
            sectorfilter = data.get("Sector Filter");
            String[] arrsplit = sectorfilter.split("\\|");
            for (int i = 0; i < arrsplit.length; i++)
                sectorfilterlist.add(arrsplit[i]);
        }

        if (!(sectorfilter.equalsIgnoreCase("_BLANK"))){
            int sectorfiltercount = sectorfilterlist.size();
            for (int j = 0; j < sectorfiltercount; j++) {
                String sectorfiltervalue = String.join(", ", sectorfilterlist.get(j));
                if (sectorlist.contains(sectorfiltervalue)) {
                    sectorlist.remove(new String(sectorfiltervalue));
                } else {
                    sectorlist.add(sectorfiltervalue);
                }
            }
            map.put("spendingSector", sectorlist);
        }
        else {
            map.put("spendingSector", sectorlist);
        }

        //"legalGrantingFromDate"
        String From = data.get("From").toString();
        if ((From.trim().equalsIgnoreCase("_BLANK")) || (From.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingFromDate", "");
        } else {
            map.put("legalGrantingFromDate", From);
        }

        //"legalGrantingToDate"
        String To = data.get("To").toString();
        if ((To.trim().equalsIgnoreCase("_BLANK")) || (To.trim().equalsIgnoreCase("No"))) {
            map.put("legalGrantingToDate", "");
        } else {
            map.put("legalGrantingToDate", To);
        }

        //"pageNumber"
        map.put("pageNumber", "1");

        //"totalRecordsPerPage"
        map.put("totalRecordsPerPage", "10");

        //sortby
        map.put("sortBy", Arrays.asList());

        return map;
    }
    public HashMap<String, Object> queryparameterbuilder(String filePath, String SheetName, String TDID) throws IOException, ParseException {
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName, TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //Page
        if ((data.get("Page").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("page", "");
        } else {
            String pagefloat = data.get("Page");
            String page = apiutilities.floattostring(pagefloat);
            map.put("page", page);
        }
        //RecordsPerPage
        if ((data.get("RecordsPerPage").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("recordsPerPage", "");
        } else {
            String recordsPerPagefloat = data.get("RecordsPerPage");
            String recordsPerPage = apiutilities.floattostring(recordsPerPagefloat);
            map.put("recordsPerPage", recordsPerPage);
        }
        //Status
        if ((data.get("Status").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("status", "");
        } else {
            map.put("status", data.get("Status"));
        }
        //SearchName
        if ((data.get("SearchName").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("searchName", "");
        } else {
            map.put("searchName", data.get("SearchName"));
        }
        return map;
    }
    public HashMap<String, Object> queryparameterbuilderpagination(String filePath, String SheetName, String TDID, Integer pagenumber) throws IOException, ParseException {
        Reusable d = new Reusable();
        ApiUtils apiutilities = new ApiUtils();
        HashMap<String, String> data = d.readExcelDataNew(filePath, SheetName, TDID);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (data.isEmpty()) {
            Assert.fail("There is no matching TDID in the datasheet");
        }
        //Page
        if ((data.get("Page").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("page", "");
        } else {
            map.put("page", pagenumber);
        }
        //RecordsPerPage
        if ((data.get("RecordsPerPage").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("recordsPerPage", "");
        } else {
            String recordsPerPagefloat = data.get("RecordsPerPage");
            String recordsPerPage = apiutilities.floattostring(recordsPerPagefloat);
            map.put("recordsPerPage", recordsPerPage);
        }
        //Status
        if ((data.get("Status").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("status", "");
        } else {
            map.put("status", data.get("Status"));
        }
        //SearchName
        if ((data.get("SearchName").trim().equalsIgnoreCase("_BLANK"))) {
            map.put("searchName", "");
        } else {
            map.put("searchName", data.get("SearchName"));
        }
        return map;
    }
}
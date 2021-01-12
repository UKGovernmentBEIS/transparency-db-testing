package UK.GOV.BEIS.SCTDB.utilities;
import java.text.ParseException;
import java.util.*;

import java.io.IOException;

import org.junit.Assert;

import java.util.HashMap;
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
        //String totalRecordsPerPage = data.get("TotalRecordsperpage");
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
}
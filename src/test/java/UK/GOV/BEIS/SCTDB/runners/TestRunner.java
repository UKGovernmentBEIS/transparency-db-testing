package UK.GOV.BEIS.SCTDB.runners;

import UK.GOV.BEIS.SCTDB.utilities.Reusable;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.DataValidationEvaluator;
import org.apache.poi.ss.formula.WorkbookEvaluatorProvider;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestRunner {

//   @Test
    public void test(){
        //System.out.println(new Reusable().readExcelToJSON("./src/test/resources/data/sample.xlsx"));

//        for(Object object: new Reusable().readExcelData("./src/test/resources/data/sample.xlsx","AddSubsidy","ASTD_003")) {
//        System.out.println(object);}

       //HashMap<String,String> test = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx","AwardDetails","Absolem Productions Limited");
       HashMap<String,String> test = new Reusable().readExcelDataNew("./src/test/resources/data/sample.xlsx","PublicSearch","USTD_036");
       test.forEach((key, value) -> System.out.println(key + " | " + value));

    }


    //@Test
    public void test2(){
        String BU_FILE = "./src/test/resources/DBInsert/BU.xlsx";
        XSSFWorkbook wb = null;
        try {
            wb = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(new File(BU_FILE).getCanonicalPath()));
        } catch (EncryptedDocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        XSSFSheet sheet = wb.getSheetAt(0);

        List<XSSFDataValidation> dataValidations = sheet.getDataValidations();
        Iterator<XSSFDataValidation> iterator = dataValidations.iterator();
        System.out.println("Size: "+dataValidations.size());
        String[] explicitListValues={""};
        while(iterator.hasNext()){
            XSSFDataValidation dataValidation = iterator.next();
            explicitListValues = dataValidation.getValidationConstraint().getExplicitListValues();
        }
        System.out.println("Values: "+Arrays.toString(explicitListValues));

    }


    @Test
    public void test3()
        {
            //String filePath = "ExcelWorkbook.xls";
            String  BU_FILE = "./src/test/resources/DBInsert/BU.xlsx";

            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(new FileInputStream(new File(BU_FILE).getCanonicalPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    //System.out.println(cell.getAddress());
                    DataValidation dataValidation = getDataValidationFromDataValidationEvaluator(cell);
                    if (dataValidation != null) {
                        DataValidationConstraint constraint = dataValidation.getValidationConstraint();
                        System.out.println("Data Validation: "+dataValidation);
                        System.out.println("constraint: "+constraint);
                        if (constraint != null) {
                            System.out.println("DataValidationConstraint.ValidationType: " + constraint.getValidationType());
                            //https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/DataValidationConstraint.ValidationType.html
                            System.out.println("Formula1: " + constraint.getFormula1());
                            System.out.println("DataValidationConstraint.OperatorType: " + constraint.getOperator());
                            //https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/DataValidationConstraint.OperatorType.html
                            System.out.println("Formula2: " + constraint.getFormula2());
                            String[] listValues = constraint.getExplicitListValues();
                            if (listValues != null) System.out.println("List values: " + Arrays.asList(listValues));
                        }
                    }
                    System.out.println();
                }
            }

            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


     public DataValidation getDataValidationFromDataValidationEvaluator(Cell cell) {
            Sheet sheet = cell.getSheet();
            Workbook workbook = sheet.getWorkbook();
            WorkbookEvaluatorProvider workbookEvaluatorProvider =
                    (WorkbookEvaluatorProvider)workbook.getCreationHelper().createFormulaEvaluator();
            DataValidationEvaluator dataValidationEvaluator = new DataValidationEvaluator(workbook, workbookEvaluatorProvider);
         return dataValidationEvaluator.getValidationForCell(new CellReference(cell));
        }

    }


package UK.GOV.BEIS.SCTDB.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.Map;

public class Reusable {


    public String readPayload(final String path) {

        String fileContents = "";
        try {
            fileContents = IOUtils.toString(new FileInputStream(path), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("java.io.FileNotFoundException: Incorrect payload file path");
        }
        return fileContents;
    }

    public String getProperty(String Key) {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String value = (String) environmentVariables.getProperty(Key);
        return value;
    }

    public String readExcelToJSON(String filePath) {
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheet("LoginData");
            Iterator<Row> rows = sheet.iterator();

            Map<String, Object> Exceldata = new HashMap<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    if (cellIndex == 0) { // ID
                        Exceldata.put("ID", String.valueOf(currentCell.getNumericCellValue()));
                    } else if (cellIndex == 1) { // Name
                        Exceldata.put("Name", currentCell.getStringCellValue());
                    } else if (cellIndex == 2) { // Address
                        Exceldata.put("Address", currentCell.getStringCellValue());
                    } else if (cellIndex == 3) { // Age
                        Exceldata.put("Age", (int) currentCell.getNumericCellValue());
                    }

                    cellIndex++;
                }

            }

            // Close WorkBook
            workbook.close();

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";

            try {
                jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Exceldata);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return jsonString;


        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    public ArrayList<Object> readExcelData(String filePath, String SheetName, String TDID) {

        ArrayList<Object> CellData = new ArrayList<>();
        //HashMap<String,String> TestData = new HashMap<>();

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
                    for (int cellNum =1; cellNum < currentRow.getLastCellNum(); cellNum++) {
                        //String ColumnTitle=sheet.getRow(0).getCell(cellNum).getStringCellValue();

                        switch(currentRow.getCell(cellNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()) {
                            case STRING:
                                CellData.add(currentRow.getCell(cellNum).getStringCellValue());
                                //TestData.put(ColumnTitle,currentRow.getCell(cellNum).getStringCellValue());
                                break;
                            case NUMERIC:
                                if(DateUtil.isCellDateFormatted(currentRow.getCell(cellNum))){
                                    CellData.add(currentRow.getCell(cellNum).getLocalDateTimeCellValue().toLocalDate());
                                    //TestData.put(ColumnTitle,currentRow.getCell(cellNum).getLocalDateTimeCellValue().toLocalDate().toString());
                                    //To format -> .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                }
                                else {
                                    CellData.add(currentRow.getCell(cellNum).getNumericCellValue());
                                    //TestData.put(ColumnTitle,Double.toString(currentRow.getCell(cellNum).getNumericCellValue()));
                                }
                                break;
                            case BOOLEAN:
                                CellData.add(currentRow.getCell(cellNum).getBooleanCellValue());
                                //TestData.put(ColumnTitle,String.valueOf(currentRow.getCell(cellNum).getBooleanCellValue()));
                                break;
                            case BLANK:
                                CellData.add("_BLANK");
                                //TestData.put(ColumnTitle,"_BLANK");
                                break;
                            default:
                                CellData.add("NO DATA");
                                //TestData.put(ColumnTitle,"NO DATA");

                        }

                    }
                }

            }

            workbook.close();

        } catch (IOException e) {
            Assert.fail("Exception in reading Test Data from Excel file");
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }

        return CellData;

    }

    public HashMap<String,String> readExcelDataNew(String filePath, String SheetName, String TDID) {

        //ArrayList<Object> CellData = new ArrayList<>();
        HashMap<String,String> TestData = new HashMap<>();

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
                    for (int cellNum =1; cellNum < currentRow.getLastCellNum(); cellNum++) {
                        String ColumnTitle=sheet.getRow(0).getCell(cellNum).getStringCellValue();

                        switch(currentRow.getCell(cellNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType()) {
                            case STRING:
                                //CellData.add(currentRow.getCell(cellNum).getStringCellValue());
                                TestData.put(ColumnTitle,currentRow.getCell(cellNum).getStringCellValue());
                                break;
                            case NUMERIC:
                                if(DateUtil.isCellDateFormatted(currentRow.getCell(cellNum))){
                                    //CellData.add(currentRow.getCell(cellNum).getLocalDateTimeCellValue().toLocalDate());
                                    TestData.put(ColumnTitle,currentRow.getCell(cellNum).getLocalDateTimeCellValue().toLocalDate().toString());
                                    //To format -> .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                }
                                else {
                                    //CellData.add(currentRow.getCell(cellNum).getNumericCellValue());
                                    TestData.put(ColumnTitle,Double.toString(currentRow.getCell(cellNum).getNumericCellValue()));
                                }
                                break;
                            case BOOLEAN:
                                //CellData.add(currentRow.getCell(cellNum).getBooleanCellValue());
                                TestData.put(ColumnTitle,String.valueOf(currentRow.getCell(cellNum).getBooleanCellValue()));
                                break;
                            case BLANK:
                                //CellData.add("_BLANK");
                                TestData.put(ColumnTitle,"_BLANK");
                                break;
                            default:
                                //CellData.add("NO DATA");
                                TestData.put(ColumnTitle,"NO DATA");

                        }

                    }
                }

            }

            workbook.close();

        } catch (IOException e) {
            Assert.fail("Exception in reading Test Data from Excel file");
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }

        return TestData;

    }

}

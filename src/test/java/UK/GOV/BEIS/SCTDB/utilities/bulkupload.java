package UK.GOV.BEIS.SCTDB.utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static io.restassured.RestAssured.given;

public class bulkupload {

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
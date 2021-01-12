package UK.GOV.BEIS.SCTDB.utilities;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CSVReaderDemo {

    private static final String MEASURE_CSV_FILE = "./src/test/resources/DBInsert/Measure.csv";
    private static final String GA_CSV_FILE = "./src/test/resources/DBInsert/GA.csv";
    private static final String LEGAL_CSV_FILE = "./src/test/resources/DBInsert/GA.csv";
    public static void main(String[] args) {

        try {
            //readAllSubsidyMeasure(new File(MEASURE_CSV_FILE).getCanonicalPath());
            readAllGAData(new File(GA_CSV_FILE).getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readAllSubsidyMeasure(String file) {
        try {
            LinkedList insertQueries = new LinkedList();
            // Create an object of filereader class
            // with CSV file as a parameter.

            FileReader filereader = new FileReader(file);
            int i =1;
            String query1= null;
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData =  csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                StringBuilder query = new StringBuilder("INSERT INTO SUBSIDY_MEASURE_READ (SC_NUMBER, GA_ID,  SUBSIDY_MEASURE_TITLE, START_DATE, END_DATE, DURATION, BUDGET,Adhoc, GA_SUBSIDY_WEBLINK, PUBLISHED_MEASURE_DATE) VALUES('SC'||nextval('subsidy_control_read_seq')");
                query1 = query.append(',').append(row[1]).append(',').append("'").append(row[2]).append("'")
                        .append(',').append("'").append(dateToFullMonthNameInDate(row[3])).append("'").append(',').append("'").append(dateToFullMonthNameInDate(row[4])).append("'")
                        .append(',').append(row[5]).append(',').append("'").append(row[6].replaceAll(",","").trim()).append("'")
                        .append(',').append(false).append(',').append("'").append(row[8]).append("'")
                        .append(',').append("'").append(dateToFullMonthNameInDate(row[9])).append("'").append(')').append(";").toString();
                System.out.println(query1);
                insertQueries.add(query1);
                //i++;
                query1 = null;
                query = null;
                //break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readAllGAData(String csvFilePath6) {
      try {
            LinkedList insertQueries = new LinkedList();
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(csvFilePath6);
            String query1= null;
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData =  csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                StringBuilder query = new StringBuilder("INSERT INTO GRANTING_AUTHORITY_READ (GA_ID, GA_NAME) VALUES(nextval('granting_authority_read_seq'),");
                query1 = query.append("'").append(row[1]).append("'")
                        .append(')').append(";").toString();
                System.out.println(query1);
                //insertQueries.add(query1);
                query1 = null;
                query = null;
                //break;
            }
            System.out.println("query::" + insertQueries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readAllAwardsData(String csvFilePath5) {

        try {
            LinkedList insertQueries = new LinkedList();
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(csvFilePath5);
            String query1= null;
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData =  csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                StringBuilder query = new StringBuilder("INSERT INTO AWARD_READ\n" +
                        "(AWARD_NUMBER, SC_NUMBER, GA_ID, BENEFICIARY_ID,\n" +
                        "SUBSIDY_ELEMENT_FULL_AMOUNT_RANGE, SUBSIDY_ELEMENT_FULL_AMOUNT_EXACT, SUBSIDY_OBJECTIVE, GOOD_SERVICES_FILTER,\n" +
                        "LEGAL_GRANTING_DATE, PUBLISHED_AWARD_DATE, SPENDING_REGION, SUBSIDY_INSTRUMENT, SPENDING_SECTOR)\n" +
                        "VALUES(");
                query1 = query.append(row[0]).append(',').append("'").append(row[1]).append("'").append(',').append(row[2]).append(',').append(row[3])
                        .append(',').append("'").append(row[4]).append("'").append(',').append(row[5])
                        .append(',').append("'").append(row[6]).append("'")
                        .append(',').append("'").append(row[7]).append("'")
                        .append(',').append("'").append(dateToFullMonthNameInDate(row[8])).append("'")
                        .append(',').append("'").append(dateToFullMonthNameInDate(row[9])).append("'")
                        .append(',').append("'").append(row[10]).append("'").append(',')
                        .append("'").append(row[11]).append("'").append(',')
                        .append("'").append(row[12]).append("'")
                        .append(')').append(";").toString();
                System.out.println(query1);
                //insertQueries.add(query1);
                query1 = null;
                query = null;
                //break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readAllLegalBasisData(String csvFilePath4) {
        try {
            LinkedList insertQueries = new LinkedList();
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(csvFilePath4);
            String query1= null;
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData =  csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                StringBuilder query = new StringBuilder("INSERT INTO LEGAL_BASIS (LEGAL_BASIS_ID, SC_NUMBER, LEGAL_BASIS_TEXT) VALUES(nextval('legal_basis_seq'),");
                query1 = query.append("'").append(row[1]).append("'").append(',').append("'").append(row[2].trim()).append("'")
                        .append(");").toString();
                System.out.println(query1);
                insertQueries.add(query1);
                query1 = null;
                query = null;
                //break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readAllOptionData(String csvFilePath3) {

        try {
            LinkedList insertQueries = new LinkedList();
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(csvFilePath3);
            String query1= null;
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData =  csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                StringBuilder query = new StringBuilder("INSERT INTO OPTION (OPTION_ID, OPTION_NAME, OPTION_VALUE) VALUES(nextval('option_seq'),");
                query1 = query.append("'").append(row[1]).append("'").append(',').append("'").append(row[2]).append("'")
                         .append(");").toString();
                System.out.println(query1);
                insertQueries.add(query1);
                query1 = null;
                query = null;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readAllBeneficiaryData(String file) {
        try {
            LinkedList insertQueries = new LinkedList();
            // Create an object of filereader class
            // with CSV file as a parameter.
            FileReader filereader = new FileReader(file);
            int i =1;
            String query1= null;
            // create csvReader object
            // and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData =  csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                StringBuilder query = new StringBuilder("INSERT INTO BENEFICIARY_READ(BENEFICIARY_ID, BENEFICIARY_NAME, BENEFICIARY_TYPE, NATIONAL_ID, NATIONAL_ID_TYPE,SIC_CODE, SIZE_OF_ORG) VALUES (nextval('beneficiary_read_seq')");
                query1 = query.append(',').append("'").append(row[1].toLowerCase()).append("'").append(',').append("'").append(row[2]).append("'")
                        .append(',').append("'").append(row[3]).append("'").append(',').append("'").append(row[4]).append("'")
                        .append(',').append("'").append(row[5]).append("'").append(',').append("'").append(row[6].replaceAll(",","").trim()).append("'")
                        .append(");").toString();
                System.out.println(query1);
               // insertQueries.add(query1);
                i++;
                query1 = null;
                query = null;
                //break;
            }

           // System.out.println("query::" + insertQueries);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String dateToFullMonthNameInDate(String inputStringDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(new Date(inputStringDate));
        return strDate;
    }
}

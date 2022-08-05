package excelvalidation;



import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import excelvalidation.driver.DriverManager;
import excelvalidation.util.automationReporter;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Iterator;
import excelvalidation.module.*;

import static excelvalidation.util.amtUtilities.moveFiles;

public class readexcel {
    public static ExtentReports extent;
    public static ExtentTest test;


    public static final String dirPath = new File("").getAbsolutePath();
    public static final String dataPath = dirPath+"\\"+"Data";
    //public static final String EXCEL_FILE = dirPath +"\\Data\\Open_Invoice_List.xlsx";


    public static String getFile()
    {
         String dataFile = "";
        //Creating a File object for directory
        File directoryPath = new File(dataPath);
        //Creating filter for jpg files
        FilenameFilter xlsxFilefilter = new FilenameFilter(){
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".xlsx")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        String excelFilesList[] = directoryPath.list(xlsxFilefilter);
        //System.out.println("List of the Excel files in the specified directory:");
        for(String fileName : excelFilesList) {
            dataFile = fileName;
            break;
        }
        return "\\"+dataFile;
    }


    private static int getLastFilledColumn(Sheet sheet) {
        int result = 0;
        for (Row row : sheet) {
            //System.out.println(row);
            if (row.getLastCellNum() > result) result = row.getLastCellNum();
        }
        return result;
    }

    public static void readXLSFile(String fileName) {
        try {
            InputStream ExcelFileToRead = new FileInputStream(dataPath+fileName);
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
            XSSFWorkbook workbook = null;


            for(int k=0; k<wb.getNumberOfSheets();k++) {
                XSSFSheet sheet = wb.getSheetAt(k);
                Boolean isRowEmpty=false;


                for(int i = 0; i <= sheet.getLastRowNum(); i++){
                    //     System.out.println(i + "   ---- " +sheet.getRow(i).getLastCellNum());

                    if(sheet.getRow(i)==null){
                        sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                        i--;
                        continue;
                    }

                    for(int j=0; j<sheet.getRow(i).getLastCellNum();j++){

                        try {
                            if(sheet.getRow(i).getCell(j).toString().trim().equals(""))
                            {
                                isRowEmpty=true;
                            }else {
                                isRowEmpty=false;
                                break;
                            }
                        } catch(Exception e) {

                        }

                    }
                    if(isRowEmpty==true){
                        sheet.shiftRows(i+ 1, sheet.getLastRowNum(), -1);
                        i--;
                    }
                }
//                Iterator<Row> iter = sheet.rowIterator();
//                int firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
//                int endColumn = 0;
//                while (iter.hasNext()) {
//                    Row row = iter.next();
//                    short firstCell = row.getFirstCellNum();
//                    if (firstCell >= 0) {
//                        firstColumn = Math.min(firstColumn, firstCell);
//                        endColumn = Math.max(endColumn, row.getLastCellNum());
//                    }
//                }
//
//                // main logic block
//                for (int i = 0; i< sheet.getLastRowNum(); i++) {
//                    if (sheet.getRow(i) != null) {
//                        isRowEmpty = true;
//                        Row row = sheet.getRow(i);
//                        for (int j = firstColumn; j < endColumn; j++) {
//                            if (j >= row.getFirstCellNum() && j < row.getLastCellNum()) {
//                                Cell cell = row.getCell(j);
//                                if (cell != null) {
//                                    if (!cell.getStringCellValue().equals("")) {
//                                        isRowEmpty = false;
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                        //if empty
//                        if (isRowEmpty) {
//                           System.out.println("Found empty row on: " + row.getRowNum());
//                            sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum(), -1);
//                            i--;
//                        }
//                    }
//                    // if row is null
//                    else{
//                        sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
//                        i--;
//                    }
//                }


                //System.out.println(sheet.getLastRowNum());
                XSSFRow row;
                XSSFCell cell;
                int lastFilledColumn = getLastFilledColumn(sheet);
                //System.out.println(lastFilledColumn);

                FileOutputStream fileOut = new FileOutputStream(dataPath+fileName);
                wb.write(fileOut);
                fileOut.close();
//                Iterator rows = sheet.rowIterator();
//                System.out.println("Data of sheet:"+k);
//                while (rows.hasNext()) {
//                    row = (XSSFRow) rows.next();
//
//                    for (int i = 0; i < row.getLastCellNum(); i++) {
//                        cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                        System.out.print(cell.toString() + "\t\t");
//                    }
//                    System.out.println("");
//                }
//                System.out.println("end of a sheet");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        //Start Reporter
        automationReporter.initializeReporter();
        //Driver Instance
        WebDriver driver = DriverManager.getDriver(false); //flag is for switch between headless and headed
        //Flow should be created for AMTDirect @ Parvez
       automationReporter.startReporter("Login Test");
           loginModule.execute(driver,test);
        automationReporter.endReporter();
        automationReporter.startReporter("AP Module");
          apManagerModule.execute(driver,test);
        automationReporter.endReporter();
        automationReporter.closeReporter();
        System.out.println("Finished Testing");
        driver.close();
        readXLSFile(getFile());
        // Moving previous file to vault
        moveFiles(dirPath);
    }
}
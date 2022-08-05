package excelvalidation;



import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import excelvalidation.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Iterator;
import excelvalidation.driver.DriverManager;
import excelvalidation.module.*;
public class readexcel {
    public static ExtentReports extent;
    public static ExtentTest test;


    public static final String dirPath = new File("").getAbsolutePath();
    public static final String EXCEL_FILE = dirPath +"\\Data\\Open_Invoice_List.xlsx";

    private static int getLastFilledColumn(Sheet sheet) {
        int result = 0;
        for (Row row : sheet) {
            //System.out.println(row);
            if (row.getLastCellNum() > result) result = row.getLastCellNum();
        }
        return result;
    }

    public static void readXLSFile() {

        try {
            InputStream ExcelFileToRead = new FileInputStream(EXCEL_FILE);
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
            XSSFWorkbook workbook = null;

            // sheet Iterator
            for(int sheetNumber=0; sheetNumber<wb.getNumberOfSheets();sheetNumber++) {
                XSSFSheet sheet = wb.getSheetAt(sheetNumber);
                Boolean isRowEmpty=false;

//
//                for(int i = 0; i <= sheet.getLastRowNum(); i++){
//                    System.out.println(i + "   ---- " +sheet.getRow(i).getLastCellNum());
//
//                    if(sheet.getRow(i)==null){
//                        sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
//                        i--;
//                        continue;
//                    }
//
//                    for(int j=0; j<sheet.getRow(i).getLastCellNum();j++){
//
//                        if(sheet.getRow(i).getCell(j).toString().trim().equals(""))
//                        {
//                            isRowEmpty=true;
//                        }else {
//                            isRowEmpty=false;
//                            break;
//                        }
//                    }
//                    if(isRowEmpty==true){
//                        sheet.shiftRows(i+ 1, sheet.getLastRowNum(), -1);
//                        i--;
//                    }
//                }
                Iterator<Row> iter = sheet.rowIterator();
                int firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
                int endColumn = 0;
                while (iter.hasNext()) {
                    Row row = iter.next();
                    short firstCell = row.getFirstCellNum();
                    if (firstCell >= 0) {
                        firstColumn = Math.min(firstColumn, firstCell);
                        endColumn = Math.max(endColumn, row.getLastCellNum());
                    }
                }

                // main logic block
                //row iterator
                for (int rowNumber = 0; rowNumber< sheet.getLastRowNum(); rowNumber++) {
                    if (sheet.getRow(rowNumber) != null) {
                        isRowEmpty = true;
                        Row row = sheet.getRow(rowNumber);
                        // column iterator
                        for (int columnNumber = firstColumn; columnNumber < endColumn; columnNumber++) {
                            if (columnNumber >= row.getFirstCellNum() && columnNumber < row.getLastCellNum()) {
                                Cell cell = row.getCell(columnNumber);
                                if (cell != null) {
                                    if (!cell.getStringCellValue().equals("")) {
                                        isRowEmpty = false;
                                        break;
                                    }
                                }
                            }
                        }
                        //if empty
                        if (isRowEmpty) {
                            System.out.println("Found empty row on: " + row.getRowNum());
                            sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum(), -1);
                            rowNumber--;
                        }
                    }
                    // if row is null
                    else{
                        sheet.shiftRows(rowNumber + 1, sheet.getLastRowNum(), -1);
                        rowNumber--;
                    }
                }


                //System.out.println(sheet.getLastRowNum());
                XSSFRow row;
                XSSFCell cell;
                int lastFilledColumn = getLastFilledColumn(sheet);
                //System.out.println(lastFilledColumn);

                FileOutputStream fileOut = new FileOutputStream(dirPath +"\\Data\\Open_Invoice_List.xlsx");
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
    public static void createDir(String path, String dir){
        String directoryName = dirPath+dir;

        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
    }
    public static void moveFiles(String path){
        File src = new File(path+"//Data");
        createDir(dirPath,"DataVault");
        File dest = new File(path+"//DataVault");

        try {
            FileUtils.copyDirectory(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.cleanDirectory(src);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
//        File file = new File("C:\\Users\\DSi\\Downloads\\Data Bleanding File Two.xlsx");
//        file.delete();
        //Reports

        automationReporter.initializeReporter();

        //create a new download directory if it doesn't exist already, skip if there is already one
        //        createDir(dirPath,"Data"); // we can also clean up file before running the test each time
        //Switch driver to project driver @parvez \\resolved
        WebDriver driver = DriverManager.getDriver(false); //flag is for switch between headless and headed
        //Flow should be created for AMTDirect @ Parvez
        automationReporter.startReporter("Login Test");
           loginModule.execute(driver,test);
        automationReporter.endReporter();
        automationReporter.startReporter("AP Module");
          apManagerModule.execute(driver,test);
        automationReporter.endReporter();
        moveFiles(dirPath);
        automationReporter.closeReporter();
        System.out.println("Finished Testing");
        driver.close();
    }
}
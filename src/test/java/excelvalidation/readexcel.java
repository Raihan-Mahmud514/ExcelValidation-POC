package excelvalidation;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;

public class readexcel {


    public static final String absolutePath = new File("").getAbsolutePath();
    public static final String EXCEL_FILE = absolutePath +"\\Data\\Open_Invoice_List.xlsx";

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

                FileOutputStream fileOut = new FileOutputStream(absolutePath +"\\Data\\Open_Invoice_List.xlsx");
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

    public static void main(String[] args) throws InterruptedException {
//        File file = new File("C:\\Users\\DSi\\Downloads\\Data Bleanding File Two.xlsx");
//        file.delete();
        //Switch driver to project driver @parvez \\resolved
        System.setProperty("webdriver.chrome.driver", absolutePath+"\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //Flow should be created for AMTDirect @ Parvez
        driver.get("https://www.wisdomaxis.com/technology/software/data/for-reports/");
        System.out.println("Navigated to the URL");
        Thread.sleep(5000);
        driver.manage().window().maximize();
        System.out.println("Window maximized");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"home\"]/div[6]/a[1]")).click();
        //String a = String.valueOf(file.toPath());
        //System.out.println(a);
        Thread.sleep(5000);
        System.out.println("Download completed.");
        driver.close();
        String pathName = new File("").getAbsolutePath();
//        System.out.println(pathName);
        readXLSFile();
    }
}
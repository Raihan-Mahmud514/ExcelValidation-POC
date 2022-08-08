package AmtAutomation.util;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static AmtAutomation.MAIN.dataPath;
import static AmtAutomation.MAIN.dirPath;
public class AmtUtilities {
    public static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            /// throw new Exception("Pause between steps was interrupted", e);
            e.printStackTrace();
        }
    }
    public static void takeSnapShot(WebDriver webdriver , String filename) throws Exception{
//Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
//Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
//Move image file to new destination
        createDir(dirPath,"\\Screenshot");

        File DestFile=new File(dirPath+"\\Screenshot\\"+filename);
//Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }
    public static void moveFiles(String path, String dir) throws Exception{
        File src = new File(path+"\\"+dir);
        String VaultDirectoryName = dirPath+"\\"+dir;
        File directory = new File(VaultDirectoryName);
        if (! directory.exists()){
            createDir(dirPath,dir+"Vault");
        }
        File dest = new File(path+"\\"+dir+"Vault");

        try {
            FileUtils.copyDirectory(src, dest);
            System.out.println("Moving previous datafiles to vault");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.cleanDirectory(src);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createDir(String path, String dir) throws Exception{
        System.out.println(path + dir + " Debugger");
        String directoryName = dirPath+"\\"+dir;
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            System.out.println("Created New Directory- "+dir);
        }
        else{
            File src = new File(path+"\\"+dir);
            String VaultDirectoryName = dirPath+"\\"+dir;
            directory = new File(VaultDirectoryName);
            if (! directory.exists()){
                createDir(dirPath,dir+"Vault");
            }
            System.out.println(dir+"Vault");
            File dest = new File(path+"\\"+dir+"Vault");

            try {
                FileUtils.copyDirectory(src, dest);
                System.out.println("Moving previous datafiles to vault");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileUtils.cleanDirectory(src);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String getFile(WebDriver driver) throws InterruptedException {
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
        // navigate to chrome downloads
//        driver.get("chrome://downloads");
//
//        JavascriptExecutor js1 = (JavascriptExecutor)driver;
//        // wait until the file is downloaded
//        Long percentage = (long) 0;
//        while ( percentage!= 100) {
//            try {
//                percentage = (Long) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#progress').value");
//                //System.out.println(percentage);
//            }catch (Exception e) {
//                // Nothing to do just wait
//            }
//            Thread.sleep(1000);
//        }
//        // get the latest downloaded file name
//        String fileName = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
//        return fileName;

   }

//    public static void readXLSFile(String fileName) {
//        try {
//            InputStream ExcelFileToRead = new FileInputStream(fileName);
//            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
//            XSSFWorkbook workbook = null;
//
//
//            for(int sheetIterator=0; sheetIterator<wb.getNumberOfSheets();sheetIterator++) {
//                XSSFSheet sheet = wb.getSheetAt(sheetIterator);
//                Boolean isRowEmpty=false;
//
//
//                for(int rowIterator = 0; rowIterator <= sheet.getLastRowNum(); rowIterator++){
//                    //     System.out.println(i + "   ---- " +sheet.getRow(i).getLastCellNum());
//
//
//                    if(sheet.getRow(rowIterator)==null){
//                        sheet.shiftRows(rowIterator + 1, sheet.getLastRowNum(), -1);
//                        rowIterator--;
//                        continue;
//                    }
//                    for(int columnIterator=0; columnIterator<sheet.getRow(rowIterator).getLastCellNum();columnIterator++){
//
//                        try {
//                            if(sheet.getRow(rowIterator).getCell(columnIterator).toString().trim().equals(""))
//                            {
//                                isRowEmpty=true;
//                            }else {
//                                isRowEmpty=false;
//                                break;
//                            }
//                        } catch(Exception e) {
//
//                        }
//                    }
//                    if(isRowEmpty==true){
//                        sheet.shiftRows(rowIterator+ 1, sheet.getLastRowNum(), -1);
//                        rowIterator--;
//                    }
//                }
//                FileOutputStream fileOut = new FileOutputStream(fileName);
//                wb.write(fileOut);
//                fileOut.close();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public static boolean ValidateDoubleData(String dataFileWithPath,  int sheetNumber, int vRow, int vColumn, double expectedValue) {
        String value = ""; //variable for storing the cell value
        Workbook wbook = null; //initialize Workbook null
        try {
            //reading data from a file in the form of bytes

            FileInputStream fis = new FileInputStream(dataFileWithPath);

            //creates an XSSFWorkbook object by buffering the whole stream into the memory
            wbook = new XSSFWorkbook(fis);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
        Sheet sheet = wbook.getSheetAt((int) sheetNumber);
        //getting the XSSFSheet object at given index
        Row row = sheet.getRow((int) vRow);
        //returns the logical row
        Cell cell = row.getCell((int) vColumn);
        CellType type = cell.getCellTypeEnum();
        if (type == CellType.STRING) {
            value = cell.getStringCellValue();
        } else if (type == CellType.NUMERIC) {
            //System.out.println(((Object)cell.getNumericCellValue()).getClass().getSimpleName());
            value = String.valueOf(cell.getNumericCellValue());
        } else if (type == CellType.BOOLEAN) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else if (type == CellType.BLANK) {
            value = "";
        }

        if (cell.getNumericCellValue()==expectedValue){
            return true;
        }
        else {
            return false;
        }
    }

    public static String ValidateDateData(String dataFileWithPath,  int sheetNumber, int vRow, int vColumn) {
        String value = ""; //variable for storing the cell value
        Workbook wbook = null; //initialize Workbook null
        try {
            //reading data from a file in the form of bytes

            FileInputStream fis = new FileInputStream(dataFileWithPath);

            //creates an XSSFWorkbook object by buffering the whole stream into the memory
            wbook = new XSSFWorkbook(fis);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
        Sheet sheet = wbook.getSheetAt(sheetNumber);
        //getting the XSSFSheet object at given index
        Row row = sheet.getRow(vRow);
        //returns the logical row
        Cell cell = row.getCell(vColumn);
        CellType type = cell.getCellTypeEnum();

        if(cell.getCellTypeEnum() == CellType.NUMERIC||cell.getCellTypeEnum() == CellType.FORMULA)
        {
            if(HSSFDateUtil.isCellDateFormatted(cell))
            {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = cell.getDateCellValue();
                value = df.format(date);
            }
        }
        return value;
    }

}

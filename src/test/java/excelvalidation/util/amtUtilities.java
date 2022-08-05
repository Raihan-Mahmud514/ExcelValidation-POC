package excelvalidation.util;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static excelvalidation.readexcel.dataPath;
import static excelvalidation.readexcel.dirPath;

public class amtUtilities {
    public static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            /// throw new Exception("Pause between steps was interrupted", e);
            e.printStackTrace();
        }
    }
    public static void moveFiles(String path){
        File src = new File(path+"\\Data");
        createDir(dirPath,"DataVault");
        File dest = new File(path+"\\DataVault");

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
    public static void createDir(String path, String dir){
        String directoryName = dirPath+"\\"+dir;

        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            System.out.println("Created New Directory- "+dir);
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
    }

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
            InputStream ExcelFileToRead = new FileInputStream(fileName);
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

                FileOutputStream fileOut = new FileOutputStream(fileName);
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

    public static boolean ValidateDoubleData(String dataFileWithPath, int vRow, int vColumn, double expectedValue) {
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
        Sheet sheet = wbook.getSheetAt(0);
        //getting the XSSFSheet object at given index
        Row row = sheet.getRow(vRow);
        //returns the logical row
        Cell cell = row.getCell(vColumn);
        //getting the cell representing the given column

//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_STRING:
//                //field that represents string cell type
//                System.out.print(cell.getStringCellValue() + "\t\t\t");
//                break;
//            case Cell.CELL_TYPE_NUMERIC:
//                //field that represents number cell type
//                System.out.print(cell.getNumericCellValue() + "\t\t\t");
//                break;
//            default:
//        }

        CellType type = cell.getCellTypeEnum();
//        if(cell.getStringCellValue().length()>0) {
//            value = cell.getStringCellValue();
//        }
//        else{
//            System.out.println("Null");
//        }

        if (type == CellType.STRING) {
            value = cell.getStringCellValue();
        } else if (type == CellType.NUMERIC) {
            System.out.println(((Object)cell.getNumericCellValue()).getClass().getSimpleName());
            value = String.valueOf(cell.getNumericCellValue());
        } else if (type == CellType.BOOLEAN) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else if (type == CellType.BLANK) {
            value = "";
        }

        //System.out.println(value.length());
        //getting cell value
        System.out.println(expectedValue+"-- expectedValue");
        System.out.println(cell.getNumericCellValue()+" -- Actual Value");


        if (cell.getNumericCellValue()==expectedValue){
            System.out.println("I am here");
            return true;
        }
        else {
            System.out.println("Opps");
            return false;
        }

        //returns the cell value
    }


}

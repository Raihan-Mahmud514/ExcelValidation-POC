package excelvalidation;



import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import excelvalidation.driver.DriverManager;
import excelvalidation.util.*;
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


import static excelvalidation.util.amtUtilities.*;

public class readexcel {
    public static ExtentReports extent;
    public static ExtentTest test;


    public static final String dirPath = new File("").getAbsolutePath();
    public static final String dataPath = dirPath+"\\"+"Data";

    public static void main(String[] args) throws Exception {
        //Start Reporter
        automationReporter.initializeReporter();
        //Driver Instance
        WebDriver driver = DriverManager.getDriver(false);//flag is for switch between headless and headed
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


    }
}
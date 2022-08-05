package excelvalidation;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import excelvalidation.driver.DriverManager;
import excelvalidation.module.apManagerModule;
import excelvalidation.module.loginModule;
import excelvalidation.util.automationReporter;
import org.openqa.selenium.WebDriver;

import java.io.File;

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
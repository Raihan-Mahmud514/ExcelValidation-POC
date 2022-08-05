package AmtAutomation;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import AmtAutomation.driver.DriverManager;
import AmtAutomation.module.APManagerModule;
import AmtAutomation.module.LoginModule;
import AmtAutomation.util.AutomationReporter;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class MAIN {
    public static ExtentReports extent;
    public static ExtentTest test;


    public static final String dirPath = new File("").getAbsolutePath();
    public static final String dataPath = dirPath+"\\"+"Data";

    public static void main(String[] args) throws Exception {
        //Start Reporter
        AutomationReporter.initializeReporter();
        //Driver Instance
        WebDriver driver = DriverManager.getDriver(false);//flag is for switch between headless and headed
        //Flow should be created for AMTDirect @ Parvez
       AutomationReporter.startReporter("Login Test");
           LoginModule.execute(driver,test);
        AutomationReporter.endReporter();
        AutomationReporter.startReporter("AP Module");
          APManagerModule.execute(driver,test);
        AutomationReporter.endReporter();
        AutomationReporter.closeReporter();
        System.out.println("Finished Testing");
        driver.close();
    }
}
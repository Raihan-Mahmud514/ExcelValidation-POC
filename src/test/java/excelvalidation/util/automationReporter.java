package excelvalidation.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

import static excelvalidation.readexcel.*;
import static excelvalidation.util.amtUtilities.*;

public class automationReporter {

    public static ExtentTest initializeReporter() {
        createDir(dirPath,"Reports");
        extent = new ExtentReports(dirPath + "\\Reports\\AMT_Automation_Results.html", true);
        extent.loadConfig(new File(dirPath + "\\extent-config.xml"));
        return test;
    }
    public static ExtentTest startReporter(String testName) {
        test = extent.startTest(testName);
        return test;
    }
    public static void endReporter() {
        extent.endTest(test);

    }
    public static void closeReporter() {
        extent.flush();
    }

}
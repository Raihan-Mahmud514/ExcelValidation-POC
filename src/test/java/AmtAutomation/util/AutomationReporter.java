package AmtAutomation.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

import static AmtAutomation.MAIN.*;
import static AmtAutomation.util.AmtUtilities.*;

public class AutomationReporter {

    public static ExtentTest initializeReporter() throws Exception {
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
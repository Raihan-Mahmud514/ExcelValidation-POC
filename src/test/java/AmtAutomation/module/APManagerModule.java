package AmtAutomation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import static AmtAutomation.MAIN.dataPath;
import static AmtAutomation.MAIN.dirPath;
import static AmtAutomation.util.AmtUtilities.*;

public class APManagerModule {
    public static void execute(WebDriver driver, ExtentTest test, Assertion hardAssert, SoftAssert softAssert) throws Exception{
        moveFiles(dirPath,"Data");
        System.out.println("Navigating AP Manager");
        sleep(5000);
        driver.findElement(By.xpath("//a[@title='Accounting']")).click();
        sleep(3000);
        driver.findElement(By.cssSelector("a[title='AccountingAPManager']")).click();
        sleep(15000);
        driver.findElement(By.cssSelector(".a-container #tabsTop #anc-inProcessInvoice")).click();
        test.log(LogStatus.PASS,"Navigation to APManager Successful");
        System.out.println("Inside AP Manager");
        sleep(15000);
        driver.findElement(By.xpath("//div[@class='divReportIcons divReportIcons_rfp']//span[@class='k-sprite a-icon file-xl']")).click();
        test.log(LogStatus.PASS,"Able to download the data");
        sleep(80000);
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[0]);
        String fileName = getFile(driver);
        readXLSFile(dataPath+fileName);
//        if(ValidateDoubleData(dataPath+fileName, 2, 8, 100.0)){
//            System.out.println("Assertion Successful");
//            test.log(LogStatus.PASS,"Able to validate the invoice amount is equal amount is equal to -100");
//        }
//        else {
//            System.out.println("Assertion Unsuccessful");
//            test.log(LogStatus.FAIL,"Unable to validate the invoice amount is equal amount is equal to -100");
//        }
        try{
            //Assertion to be placed here
            hardAssert.assertTrue(ValidateDoubleData(dataPath+fileName, 2, 8, 100.0));
            test.log(LogStatus.PASS,"Invoice Amount Matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Invoice Amount Did not Matched");
        }
        // Moving previous file to vault
        moveFiles(dirPath,"Data");
    }
}

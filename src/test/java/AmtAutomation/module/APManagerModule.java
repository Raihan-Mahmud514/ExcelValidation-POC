package AmtAutomation.module;
import AmtAutomation.util.AmtAssertions;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import static AmtAutomation.MAIN.dataPath;
import static AmtAutomation.MAIN.dirPath;
import static AmtAutomation.util.AmtUtilities.*;
public class APManagerModule {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        Assertion hardAssert = AmtAssertions.initializeHardAsserts();
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
        //readXLSFile(dataPath+fileName);
        try{
            hardAssert.assertTrue(ValidateDoubleData(dataPath+fileName, 0,  4, 8, 100.0));
            test.log(LogStatus.PASS,"Invoice Amount Matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Invoice Amount Did not Matched");
        }
        //Validating Date data
        try{
            //Assertion to be placed here
            hardAssert.assertEquals(ValidateDateData(dataPath+fileName, 0, 4, 7), "01/01/2017");
            test.log(LogStatus.PASS,"Invoice Date Matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Invoice Date Did not Matched");
        }
        // Moving previous file to vault
        moveFiles(dirPath,"Data");
    }
}

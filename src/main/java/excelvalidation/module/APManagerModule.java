package excelvalidation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import excelvalidation.util.AmtUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static excelvalidation.MAIN.dataPath;
import static excelvalidation.MAIN.dirPath;
import static excelvalidation.util.AmtUtilities.*;

public class APManagerModule {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        moveFiles(dirPath,"Data");
        System.out.println("Navigating AP Manager");
        AmtUtilities.sleep(5000);
        driver.findElement(By.xpath("//a[@title='Accounting']")).click();
        AmtUtilities.sleep(3000);
        driver.findElement(By.cssSelector("a[title='AccountingAPManager']")).click();
        AmtUtilities.sleep(15000);
        driver.findElement(By.cssSelector(".a-container #tabsTop #anc-inProcessInvoice")).click();
        test.log(LogStatus.PASS,"Navigation to APManager Successful");
        System.out.println("Inside AP Manager");
        AmtUtilities.sleep(15000);
        driver.findElement(By.xpath("//div[@class='divReportIcons divReportIcons_rfp']//span[@class='k-sprite a-icon file-xl']")).click();
        test.log(LogStatus.PASS,"Able to download the data");
        AmtUtilities.sleep(80000);
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[0]);
        String fileName = getFile(driver);
        readXLSFile(dataPath+fileName);
        if(AmtUtilities.ValidateDoubleData(dataPath+fileName, 2, 8, 100.0)){
            System.out.println("Assertion Successful");
            test.log(LogStatus.PASS,"Able to validate the invoice amount is equal amount is equal to -100");
        }
        else {
            System.out.println("Assertion Unsuccessful");
            test.log(LogStatus.FAIL,"Unable to validate the invoice amount is equal amount is equal to -100");
        }
        // Moving previous file to vault
        moveFiles(dirPath,"Data");
    }
}

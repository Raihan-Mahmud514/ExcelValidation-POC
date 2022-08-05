package excelvalidation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import excelvalidation.util.amtUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static excelvalidation.readexcel.dataPath;
import static excelvalidation.readexcel.dirPath;
import static excelvalidation.util.amtUtilities.*;

public class apManagerModule {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        moveFiles(dirPath,"Data");
        System.out.println("Navigating AP Manager");
        amtUtilities.sleep(5000);
        driver.findElement(By.xpath("//a[@title='Accounting']")).click();
        amtUtilities.sleep(3000);
        driver.findElement(By.cssSelector("a[title='AccountingAPManager']")).click();
        amtUtilities.sleep(15000);
        driver.findElement(By.cssSelector(".a-container #tabsTop #anc-inProcessInvoice")).click();
        test.log(LogStatus.PASS,"Navigation to APManager Successful");
        System.out.println("Inside AP Manager");
        amtUtilities.sleep(15000);
        driver.findElement(By.xpath("//div[@class='divReportIcons divReportIcons_rfp']//span[@class='k-sprite a-icon file-xl']")).click();
        test.log(LogStatus.PASS,"Able to download the data");
        amtUtilities.sleep(35000);
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[0]);
        String fileName = getFile(driver);
        readXLSFile(dataPath+fileName);
        if(amtUtilities.ValidateDoubleData(dataPath+fileName, 2, 8, 100.0)){
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

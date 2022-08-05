package excelvalidation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import excelvalidation.util.amtUtilities;
import excelvalidation.util.jsonReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

import static excelvalidation.readexcel.dirPath;
import static excelvalidation.util.amtUtilities.*;

public class apManagerModule {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
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
        amtUtilities.sleep(60000);
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[1]);
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().toArray(new String[0])[0]);
        readXLSFile(getFile());
        // Moving previous file to vault
        moveFiles(dirPath);
    }
}

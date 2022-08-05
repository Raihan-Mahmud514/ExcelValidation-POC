package excelvalidation.module;

import excelvalidation.util.amtUtilities;
import excelvalidation.util.jsonReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class apManagerModule {
    public static void execute(WebDriver driver) throws Exception{
        System.out.println("Inside AP Manager");
        amtUtilities.sleep(5000);

        driver.findElement(By.xpath("//a[@title='Accounting']")).click();
        amtUtilities.sleep(3000);
        driver.findElement(By.cssSelector("a[title='AccountingAPManager']")).click();
        amtUtilities.sleep(15000);
        driver.findElement(By.cssSelector(".a-container #tabsTop #anc-inProcessInvoice")).click();
        amtUtilities.sleep(15000);

        driver.findElement(By.xpath("//div[@class='divReportIcons divReportIcons_rfp']//span[@class='k-sprite a-icon file-xl']")).click();
        amtUtilities.sleep(60000);
    }
}

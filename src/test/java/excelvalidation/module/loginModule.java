package excelvalidation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import excelvalidation.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;


public class loginModule {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{

        System.out.println("Inside login function");
        HashMap<String,String> loginData = jsonReader.configReader("credential.json");
        driver.navigate().to(loginData.get("environment"));
        test.log(LogStatus.PASS,"Able to navigate to AMT");
        amtUtilities.sleep(3000);
        driver.findElement(By.cssSelector("#txtUSER_NAME")).sendKeys(loginData.get("userName"));
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys(loginData.get("password"));
        driver.findElement(By.cssSelector("#txtClient_Number")).sendKeys(loginData.get("clientId"));
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        amtUtilities.sleep(2000);
//        automationReporter.endReporter();
    }
}

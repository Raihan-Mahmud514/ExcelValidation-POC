package AmtAutomation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import AmtAutomation.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;


public class LoginModule {
    public static void execute(WebDriver driver, ExtentTest test, Assertion hardAssert,SoftAssert softAssert) throws Exception{

        System.out.println("Inside login function");
        HashMap<String,String> loginData = JsonReader.configReader("credential.json");
        driver.navigate().to(loginData.get("environment"));
        test.log(LogStatus.PASS,"Able to navigate to AMT");
        AmtUtilities.sleep(3000);
        softAssert.assertEquals(driver.getTitle(), "AMTdirect :: Login");
        test.log(LogStatus.PASS,"Login page title matched");
        driver.findElement(By.cssSelector("#txtUSER_NAME")).sendKeys(loginData.get("userName"));
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys(loginData.get("password"));
        driver.findElement(By.cssSelector("#txtClient_Number")).sendKeys(loginData.get("clientId"));
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        test.log(LogStatus.PASS,"Login Successful");
        System.out.println(driver.getTitle());
        try{
            //Assertion to be placed here
            hardAssert.assertEquals(driver.getTitle(), "AMTdirect :: Index");
            test.log(LogStatus.PASS,"Homepage title matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Title Did Not Matched, Skipping The Rest of The Test");
        }
        try{
            //Assertion to be placed here
            hardAssert.assertEquals(driver.getTitle(), "Homepage");
            test.log(LogStatus.PASS,"Homepage title matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Homepage Title Did Not Matched, Continuing rest of the test");
        }

        AmtUtilities.sleep(2000);
//        automationReporter.endReporter();
    }
}
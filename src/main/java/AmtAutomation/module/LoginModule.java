package AmtAutomation.module;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import AmtAutomation.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import java.util.HashMap;

import static AmtAutomation.util.AmtUtilities.takeSnapShot;

public class LoginModule {
    public static void execute(WebDriver driver, ExtentTest test) throws Exception{
        Assertion hardAssert = AmtAssertions.initializeHardAsserts();
        SoftAssert softAssert = AmtAssertions.initializeSoftAsserts();
        System.out.println("Inside login function");
        HashMap<String,String> loginData = JsonReader.configReader("Credentials.json");
        driver.navigate().to(loginData.get("environment"));
        test.log(LogStatus.PASS,"Able to navigate to AMT");
        AmtUtilities.sleep(3000);
        softAssert.assertEquals(driver.getTitle(), "AMTdirect :: Login");
        test.log(LogStatus.PASS,"Landed on login page");
        driver.findElement(By.cssSelector("#txtUSER_NAME")).sendKeys(loginData.get("userName"));
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys(loginData.get("password"));
        driver.findElement(By.cssSelector("#txtClient_Number")).sendKeys(loginData.get("clientId"));
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        test.log(LogStatus.PASS,"Login Successful");
        try{
            hardAssert.assertEquals(driver.getTitle(), "AMTdirect :: Index");
            test.log(LogStatus.PASS,"Homepage title matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Title did not matched");
        }
        try{
            hardAssert.assertEquals(driver.getTitle(), "Homepage");
            test.log(LogStatus.PASS,"Homepage title matched");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL,"Homepage Title did not matched, Continuing rest of the test");
            takeSnapShot(driver,"Homepage.png");
        }
        AmtUtilities.sleep(2000);
    }
}

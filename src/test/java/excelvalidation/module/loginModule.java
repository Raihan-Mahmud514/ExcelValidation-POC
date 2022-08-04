package excelvalidation.module;

import excelvalidation.util.amtUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class loginModule {
    public static void execute(WebDriver driver) throws Exception{
        System.out.println("Inside login function");

        amtUtilities.sleep(5000);
        driver.findElement(By.cssSelector("#txtUSER_NAME")).sendKeys("Parvez01");
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys("amtDirect01!");
        driver.findElement(By.cssSelector("#txtClient_Number")).sendKeys("201533");
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        amtUtilities.sleep(2000);

    }

}

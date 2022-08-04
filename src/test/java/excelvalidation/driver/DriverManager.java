package excelvalidation.driver;

import io.testproject.sdk.DriverBuilder;
import io.testproject.sdk.drivers.ReportType;
import io.testproject.sdk.drivers.web.EdgeDriver;
import io.testproject.sdk.drivers.web.FirefoxDriver;
import io.testproject.sdk.drivers.web.SafariDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import excelvalidation.readexcel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.lang.System;

public class DriverManager {
    public static synchronized WebDriver getDriver() {

        WebDriver driver = new ChromeDriver();
        return driver;
    }



}

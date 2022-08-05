package excelvalidation.driver;

import excelvalidation.readexcel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;


import static excelvalidation.readexcel.dirPath;
import static excelvalidation.util.amtUtilities.createDir;

public class DriverManager {
    public static   WebDriver getDriver(boolean flag) throws Exception {
        createDir(dirPath,"Data");
        System.setProperty("webdriver.chrome.driver", dirPath +"\\drivers\\chromedriver.exe");
        WebDriver driver = chromeDriver(flag);
        return driver;
    }
    private static WebDriver chromeDriver(boolean flag) throws Exception {
        ChromeOptions options = defaultChromeOptions(flag);

        return new ChromeDriver(options);
    }

    private static ChromeOptions defaultChromeOptions(boolean flag) throws Exception {
        String downloadFilepath = dirPath+"\\Data";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        if (flag){
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
            System.out.println("Running Headless");
        }

        return options;
    }



}

package MyRunner;

import java.net.URL;
import java.util.HashMap;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;


@CucumberOptions(
        features = "src/main/java/Features/LTApplication.feature",
        glue = {"stepDefinitions"},
        plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class TestRunner extends AbstractTestNGCucumberTests {
	
    private TestNGCucumberRunner testNGCucumberRunner;
  
    public static RemoteWebDriver connection;
    public static String platformname;
    
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
    	 testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "version", "platform" })
    public void setUpClass(String browser, String version, String platform) throws Exception {
    		Configuration config = new PropertiesConfiguration("\\\\MS.DS.UHC.COM\\userdata\\033\\ps49\\Documents\\Lambdatest\\cucumber-testng-sample-master.zip_expanded\\cucumber-testng-sample-master\\src\\main\\java\\Properties\\application.properties");
    		
    		String username = config.getString("LT_USERNAME");
    		String accesskey = config.getString("LT_ACCESS_KEY");

    		DesiredCapabilities capability = new DesiredCapabilities(); 
    		capability.setCapability(CapabilityType.BROWSER_NAME, browser);
    		capability.setCapability(CapabilityType.BROWSER_VERSION,version);
    		capability.setCapability(CapabilityType.PLATFORM_NAME, platform);
    		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
    		ltOptions.put("selenium_version", "4.0.0");
    		ltOptions.put("build", "Cucumber - Automation Testing - Assignment");
    		
    		ltOptions.put("network", true);
    		ltOptions.put("video", true);
    		ltOptions.put("console", true);
    		ltOptions.put("visual", true);
    		capability.setCapability("LT:Options", ltOptions);
    		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
    		System.out.println(gridURL);
    		connection = new RemoteWebDriver(new URL(gridURL), capability);
    		System.out.println(capability);
    		System.out.println(connection.getSessionId());
    		System.out.println("Before method:"+Thread.currentThread().getId());
    		platformname = platform;
}


    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideScenarios();
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
}
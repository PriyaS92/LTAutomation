package stepDefinitions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.*;
import MyRunner.TestRunner;


public class ToDoStepDefinition extends TestRunner {

	public RemoteWebDriver driver = this.connection;

	@Given("user is navigated to lambda test application with {string}")
	public void user_is_navigated_to_lambda_test_application_with(String url) {
		System.out.println(driver.getCapabilities());
		driver.get(url);
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	@When("clicked on the see all integrations link")
	public void clicked_on_the_see_all_integrations_link() {
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("img[title='Jira']")));
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	    WebElement integration_link = driver.findElement(By.xpath("//a[text()='See All Integrations']"));
	    Actions action = new Actions(driver);
	    
	    if(platformname.equals("macOS Sierra")) {
	    	action.moveToElement(integration_link).keyDown(Keys.COMMAND).click().perform();
	    }
	    else {
	    	action.moveToElement(integration_link).keyDown(Keys.CONTROL).click().perform();
	    }
	}

	@Then("user should be navigated to new tab")
	public void user_should_be_navigated_to_new_tab() {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles()); 
		driver.switchTo().window(tabs.get(1));
	}

	@Then("verify URL is same as the expected url with {string}")
	public void verify_url_is_same_as_the_expected_url_with(String expected_url) {
	    Assert.assertTrue(driver.getCurrentUrl().equals(expected_url));
	    driver.close();
	}


}

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.asserts.SoftAssert;
//import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.annotations.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TestNG {
// create new object of ChromeDriver  
	// WebDriver driver;

	// Executes before the Tests
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("---> beforeMethod: Starting Test On Chrome Browser <---");
	}

	@Test(priority = 0, description = "Validate the title on the main Google webpage")
	public void verifyGoogleTitle() {

		// set Chromedriver and define web URL
		System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
		String baseUrl = "https://www.google.ca/";
		WebDriver driver = new ChromeDriver();

		System.out.println("-----Launching Google Chrome browser-----");
		driver.get(baseUrl);

		// Page Title Validation
		String expectedTitle = "Perhaps Google"; // ------- Updated to FAIL -------
		String extractedTitle = driver.getTitle();
		Assert.assertEquals(extractedTitle, expectedTitle);

	}

	@Test(priority = 1, description = "Enter into the Google search field, click Google Search and validate results")
	public void performGoogleSearch() throws InterruptedException {

		// set Chromedriver and define web URL
		System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
		String baseUrl = "https://www.google.ca/";
		WebDriver driver = new ChromeDriver();

		/*
		 * The SoftAssert lib allows all tests to run regardless of failure, regular
		 * Assert will stop tests at the first failure
		 */
		SoftAssert softAssert = new SoftAssert();

		System.out.println("-----Launching Google Chrome browser-----");
		driver.get(baseUrl);

		// 1) Enter search criteria
		// Define search field XPath location
		String googleSearchBar = "//input[@title='Search']";
		// Define search string
		String googleSearchValue = "Today's Date";
		// Enter value into the search bar
		driver.findElement(By.xpath(googleSearchBar)).sendKeys(googleSearchValue + Keys.TAB);

		// 2) Validate search button and click it
		// Define search button XPath location
		String googleSearchButton = "//body/div/div/form[contains(@role,'search')]/div[contains(@jsmodel,'vWNDde')]/div[contains(@jscontroller,'cnjECf')]/div/center/input[1]";
		// Define expected google search button label and validate
		String expectedGoogleSearchButtonLabel = "Google Search";
		String googleSearchButtonLabel = driver.findElement(By.xpath(googleSearchButton)).getAttribute("value");
		softAssert.assertEquals(googleSearchButtonLabel, expectedGoogleSearchButtonLabel);
		// Click search button
		driver.findElement(By.xpath(googleSearchButton)).click();
		Thread.sleep(1000); // Search to fully load page for next test

		// 3) Validate top search results
		// Define XPath for first search bar results
		String googleSearchResultsBar = "//div[@class='vk_bk dDoNo FzvWSb']";
		// Get text of top search result
		String googleResultsTodaysDate = driver.findElement(By.xpath(googleSearchResultsBar)).getText();
		// Format today's date
		Date currentDate = new Date();
		String expectedDateFormatString = "EEEE, MMMM d, yyyy";
		SimpleDateFormat formatTodaysDate = new SimpleDateFormat(expectedDateFormatString);
		String expectedFormattedTodaysDate = formatTodaysDate.format(currentDate) + "Some Text"; // ------- Updated to FAIL -------
		// Validate google date matches today's date
		softAssert.assertEquals(googleResultsTodaysDate, expectedFormattedTodaysDate);
		softAssert.assertAll(); // if this is not used at the end of the test block, all tests will default to
								// pass
	}

	@AfterMethod
	public void afterMethod() {
		// driver.close();
		System.out.println("--> AfterMethod: This runs after each test method is ran <--");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("--> AfterTest: This runs after all tests have been run <--");
	}
}
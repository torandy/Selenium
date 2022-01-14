import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.asserts.SoftAssert;
//import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.annotations.*;

public class TestNG {
// create new object of ChromeDriver  
	// WebDriver driver;

	// Executes before the Tests
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("---> beforeMethod: Starting Test On Chrome Browser <---");
	}

	@Test(priority = 0, description = "Validate the title on the main Sunlife webpage")
	public void verifySunlifeTitle() {

		// set Chromedriver and define web URL
		System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
		String baseUrl = "https://www.sunlife.ca/en/";
		WebDriver driver = new ChromeDriver();

		System.out.println("-----Launching Google Chrome browser-----");
		driver.get(baseUrl);

		// Page Title Validation
		String expectedTitle = "Not Sunlife";
		String extractedTitle = driver.getTitle();
		System.out.println("Expected page title is: " + expectedTitle);
		System.out.println("Web page title is: " + extractedTitle); //
		Assert.assertEquals(extractedTitle, expectedTitle);

	}

	@Test(priority = 1, description = "Validate the sign in button, click it, and input sign in text on the Sunlife webpage")
	public void performActionOnSignIn() throws InterruptedException {

		// set Chromedriver and define web URL
		System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
		String baseUrl = "https://www.sunlife.ca/en/";
		WebDriver driver = new ChromeDriver();

		/*
		 * The SoftAssert lib allows all tests to run regardless of failure, regular
		 * Assert will stop tests at the first failure
		 */
		SoftAssert softAssert = new SoftAssert();

		System.out.println("-----Launching Google Chrome browser-----");
		driver.get(baseUrl);

		// 1) Validate Sign in button label
		// Define location
		String signInXpath = "//span[@class='button-class']";

		// Define expected results
		String expectedSignInButtonLabel = "Sign in";

		// Validate results
		String signInButtonLabel = driver.findElement(By.xpath(signInXpath)).getText();
		softAssert.assertEquals(expectedSignInButtonLabel, signInButtonLabel);

		// 2) Click the Sign in button
		driver.findElement(By.xpath(signInXpath)).click();
		Thread.sleep(1000); // Required to allow sign-in to fully load for next test

		// 3) Validate Labels on the Sign in page
		// Define location
		String signInTitleXpath = "//h4[@id='customerSignInHeader']";
		String signInEmailOrAccessIDXpath = "//input[@id='USER']";
		String signInRememberMeXpath = "//label[@id='remembermelabel']";
		String signInPasswordXpath = "//input[@id='PASSWORD']";

		// Define expected results
		String expectedSignInTitle = "Client sign in";
		String expectedSignInOrAccessFieldLabel = "Email/Access ID";
		String expectedRememberMeCheckbox = "Remember me";
		String expectedPasswordFieldLabel = "Password";

		// Validate Labels
		String signInTitle = driver.findElement(By.xpath(signInTitleXpath)).getText();
		softAssert.assertEquals(expectedSignInTitle, signInTitle);
		String signInEmailOrAccessID = driver.findElement(By.xpath(signInEmailOrAccessIDXpath))
				.getAttribute("placeholder");
		softAssert.assertEquals(expectedSignInOrAccessFieldLabel, signInEmailOrAccessID);
		String signInRememberMe = driver.findElement(By.xpath(signInRememberMeXpath)).getText();
		softAssert.assertEquals(expectedRememberMeCheckbox, signInRememberMe);
		String signInPassword = driver.findElement(By.xpath(signInPasswordXpath)).getAttribute("placeholder");
		softAssert.assertEquals(expectedPasswordFieldLabel, signInPassword);

		// 4) Input something into the Sign in page
		// Define location
		String signInRememberMeCheckBoxXpath = "//input[@id='rememberIDModal']";

		// Define values to input into fields
		String emailAccessInput = "Automation is cool";
		String passwordInput = "Somepassword";

		driver.findElement(By.xpath(signInEmailOrAccessIDXpath)).sendKeys(emailAccessInput);
		driver.findElement(By.xpath(signInRememberMeCheckBoxXpath)).click();
		driver.findElement(By.xpath(signInPasswordXpath)).sendKeys(passwordInput);
		softAssert.assertAll(); // if this is not used at the end of the test block, all tests will default
								// to pass
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
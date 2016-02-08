package MainPackage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NascarTest {

	// TO SIGN UP USING FAKE EMAIL
	// www.fakemailgenerator.com and use a new email

	// USER NAVIGATES TO WWW.NASCAR.COM
	// CLICKS ON REGISTER LINK
	// ENTERS VALID DATA AND REGISTERS FOR NEW ACCOUNT
	// USER CLICKS ON MY PROFILE AND VERIFIES URL

	public static WebDriver driver;
	public static final String webpage = "http://www.nascar.com";
	public static final int DEFAULT_WAIT_4_PAGE = 20;
	public static final int DEFAUL_WAIT_TIME = 20;

	public static boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {

		boolean jQcondition = false;

		try {

			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			new WebDriverWait(driver, timeOutInSeconds) {

			}.until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver driverObject) {

					return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");

				}

			});

			jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");

			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS);

			return jQcondition;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return jQcondition;

	}

	public static WebElement elementVisible(final By locator, int timeout, WebDriver driver) throws Exception {

		WebElement element;

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			WebDriverWait wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			driver.manage().timeouts().implicitlyWait(DEFAUL_WAIT_TIME, TimeUnit.SECONDS);

			return element;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		// load a new instance of firefox driver
		driver = new FirefoxDriver();

		// user maximizes window
		driver.manage().window().maximize();

		// add initial implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// user navigates to nascar webpage
		driver.get(webpage);

		// explicit wait for jquery completion
		waitForJQueryProcessing(driver, DEFAULT_WAIT_4_PAGE);

		// explicit wait for register link to appear
		elementVisible(By.xpath(".//*[@id='registerOrLogin']/a[2]"), 20, driver);

		// user clicks register button
		driver.findElement(By.xpath(".//*[@id='registerOrLogin']/a[2]")).click();

		// explicit wait for email field
		elementVisible(By.xpath(".//*[@id='gigya-register-screen']/form/div[3]/div[1]/div[1]/div/input"), 20, driver);

		// user enters email info
		driver.findElement(By.xpath(".//*[@id='gigya-register-screen']/form/div[3]/div[1]/div[1]/div/input"))
				.sendKeys("Nascar116@gustr.com");

		// user enters password info
		driver.findElement(By.xpath(".//*[@id='gigya-register-screen']/form/div[3]/div[1]/div[2]/div/input"))
				.sendKeys("nascarpassword");

		// user enters zip code info
		driver.findElement(By.xpath(".//*[@id='gigya-register-screen']/form/div[3]/div[1]/div[3]/div/input"))
				.sendKeys("91801");

		// save read policy checkbox web element
		WebElement readPolicy = driver
				.findElement(By.xpath(".//*[@id='gigya-register-screen']/form/div[3]/div[1]/div[4]/div[2]/input"));

		// save submit button web element
		WebElement submitBttn = driver
				.findElement(By.xpath(".//*[@id='gigya-register-screen']/form/div[3]/div[1]/div[5]/div[1]/div/input"));

		// if read policy is not selected , select it , then click submit button
		if (!readPolicy.isSelected()) {
			readPolicy.click();
			submitBttn.submit();
		} else {
			// else if read policy checkbox is selected , only click submit
			// button
			submitBttn.submit();
		}

		// wait for ajax to stop processing
		waitForJQueryProcessing(driver, 20);

		// explicit wait for my profile link
		elementVisible(By.xpath("//a[@id='myProfileLink']"), 20, driver);

		// user clicks my profile
		driver.findElement(By.xpath("//a[@id='myProfileLink']")).click();

		// verify my profile page url
		String expectedUrl = "http://www.nascar.com/en_us/ajax/dynamic/myNASCAR.html";
		String actualUrl = driver.getCurrentUrl();
		if (actualUrl.equals(expectedUrl)) {
			System.out.println("Page Title Verification = [ PASS ] ");
		} else {
			System.out.println("Page Title Verification = [ FAILED ] ");
		}

		// wait for ajax to stop processing
		waitForJQueryProcessing(driver, 20);

		// select nascar fan since drop down
		Select fanSince = new Select(driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[1]/div[3]/div[3]/div[1]/select")));

		// user picks 4-10 years option
		fanSince.selectByValue("4");

		// select races watched per season
		Select racesWatched = new Select(driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[1]/div[3]/div[3]/div[2]/select")));

		// user picks 6-12 option
		racesWatched.selectByValue("6-12");

		// select races attended per season
		Select racesAttended = new Select(driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[1]/div[3]/div[3]/div[3]/select")));

		// user picks 3-4 option
		racesAttended.selectByValue("3-4");

		// user selects nascar newsletter subscriptions
		driver.findElement(By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[8]/div[2]/div[1]/div/input"))
				.click();

		// user selects nascar events subscriptions
		driver.findElement(By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[8]/div[2]/div[2]/div/input"))
				.click();

		// user selects offers from nascar partners subscriptions
		driver.findElement(By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[8]/div[2]/div[3]/div/input"))
				.click();

		// user fills out first name
		driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[1]/div[1]/input"))
				.sendKeys("Ali");

		// user fills out last name
		driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[1]/input"))
				.sendKeys("Ibrahim");

		// user fills out address
		driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[1]/div[2]/input"))
				.sendKeys("915 N Garfield Ave");

		// user selects STATE dropdown
		Select state = new Select(driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[2]/select")));

		// user picks California from drop down
		state.selectByValue("California");

		// user selects COUNTRY dropdown
		Select country = new Select(driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[1]/div[3]/select")));

		// user picks USA from drop down
		country.selectByValue("USA");

		// user enters zip code [ clear text field first ]
		driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[3]/input"))
				.clear();
		driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[3]/input"))
				.sendKeys("91801");

		// user enters phone number
		driver.findElement(
				By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[1]/div[4]/input"))
				.sendKeys("458-423-6989");

		// user selects date of birth from drop down
		Select month = new Select(driver.findElement(By
				.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[4]/div/select[1]")));
		
		month.selectByValue("12");

		Select day = new Select(driver.findElement(By
				.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[4]/div/select[2]")));
		
		day.selectByValue("29");

		Select year = new Select(driver.findElement(By
				.xpath(".//*[@id='gigya-update-profile-screen']/form/div[16]/div[1]/div/div[2]/div[4]/div/select[3]")));
		
		year.selectByValue("1989");

		// user Selects Drivers From MY DRIVERS LIST and ADDS THEM
		Select addDrivers = new Select(driver.findElement(By.xpath(".//*[@id='favouriteDriverName']")));
		
		addDrivers.selectByValue("Bryan Silas");
		
		// user clicks on ADD Button
		driver.findElement(By.xpath(".//*[@id='addDriver']")).click();
		
		addDrivers.selectByValue("Justin Allgaier");
	
		// user clicks on ADD Button
		driver.findElement(By.xpath(".//*[@id='addDriver']")).click();
		
		addDrivers.selectByValue("Tony Stewart");
		
		// user clicks on ADD Button
		driver.findElement(By.xpath(".//*[@id='addDriver']")).click();

		// user clicks on SAVE ALL CHANGES
		driver.findElement(By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[19]/div/input")).click();

		// explicit wait for log out button to load
		elementVisible(By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[1]/div[1]/input"), 20, driver);

		// user LOGS OUT OF ACCOUNT
		driver.findElement(By.xpath(".//*[@id='gigya-update-profile-screen']/form/div[1]/div[1]/input")).click();

		// wait for ajax to stop processing
		waitForJQueryProcessing(driver, 20);

	}

}

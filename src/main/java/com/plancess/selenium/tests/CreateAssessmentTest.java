package com.plancess.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.pages.CreateAccessment;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.HomePage;
import com.plancess.selenium.pages.LandingPage;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.LoginPage;
import com.plancess.selenium.pages.ProfilePage;
import com.plancess.selenium.pages.SecurityPage;
import com.plancess.selenium.utils.DataProviderClass;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;

public class CreateAssessmentTest extends BaseTest {

	private WebDriver driver;
	private LoginDialogPage loginDialogPage;
	private LandingPage landingPage;
	private String pageTitle = "Plancess Dashboard";
	private WebDriverWait wait;
	private Executioner executor;
	private CreateAccessment createAssessment;

	@Parameters({ "host_ip", "port", "os", "browser", "browserVersion" })
	@BeforeMethod
	public void setUp(@Optional("localhost") String host, @Optional("4444") String port, @Optional("LINUX") String os,
			@Optional("firefox") String browser, @Optional("40.0") String browserVersion) {

		try {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			// capabilities.setCapability("version", browserVersion);
			capabilities.setCapability("platform", Platform.valueOf(os));

			this.driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);

			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
			executor = new Executioner(driver, wait);
			landingPage = new LandingPage(driver, wait);

			
			util = Util.getInstance();
			verifications = Verifications.getInstance();

		} catch (MalformedURLException e) {
			Assert.fail("Unable to start selenium session make sure Grid hub is running on url :" + "http://" + host
					+ ":" + port + "/wd/hub");

		}
	}

	@AfterMethod
	public void tearDown(ITestResult testResult) {

		if (testResult.getStatus() == ITestResult.FAILURE) {
			verifications.verifyEquals(testResult.getStatus(), ITestResult.SUCCESS,
					util.takeScreenshot(driver, "verify test status for :" + testResult.getTestName()));
		}
		driver.quit();
	}

	@Test(dataProvider = "createAssessmentDataProvider", groups = { "smoke", "regression" })
	public void CreateAssessmentWithValidDataTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();
		Dashboard dashboard = loginDialogPage.doLogin(user);
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getCreateAssessmentButton()));

		Assert.assertTrue(dashboard.getCreateAssessmentButton().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and start assessment section displayed"));
		//executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getToggleDropDown()));
		createAssessment = dashboard.navigateToCreateAccessment();
		executor.softWaitForWebElement(createAssessment.getCustomTestLink());
		selectCustomTest(user);
		
		/*userSecurity.updateUserSecurity(user);
		executor.softWaitForWebElement(userSecurity.getToastTitle());

		Assert.assertEquals(userSecurity.getToastTitle().getText(), "SUCCESS!",
				util.takeScreenshot(driver, "verify toast title displayed"));
		Assert.assertEquals(userSecurity.getToastMessage().getText(), "Your password has been changed successfully",
				util.takeScreenshot(driver, "verify toast message displayed"));

		String currentPassword = user.get("currentPassword");
		user.put("currentPassword", user.get("newPassword"));
		user.put("newPassword", currentPassword);
		user.put("confirmPassword", currentPassword);
		userSecurity.updateUserSecurity(user);
		executor.softWaitForWebElement(userSecurity.getToastTitle());

		Assert.assertEquals(userSecurity.getToastTitle().getText(), "SUCCESS!",
				util.takeScreenshot(driver, "verify toast title displayed"));
		Assert.assertEquals(userSecurity.getToastMessage().getText(), "Your password has been changed successfully",
				util.takeScreenshot(driver, "verify toast message displayed"));
*/
	}
	
	public CreateAccessment  selectCustomTest(Map<String, String> user) {
		
		String subjectTopic=user.get("subject");
		String[] Topic = subjectTopic.split(",");
		
		int len=Topic.length;
		
		for(int i=0;i<len;i++){
		String[] TopicList = Topic[i].split(">");
		String subject = TopicList[0];
		String Module = TopicList[1];
		String Sub_Module = TopicList[2];

		switch (subject.toLowerCase()) {

		case "physics":
			wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectPhysicsLink()));
			createAssessment.getsubjectPhysicsLink().click();
			
			
			//executor.getElement(By.xpath("//span[.='" +Sub_Module+ "']")).click();
			break;
		case "chemistry":
			wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectChemistryLink()));
			createAssessment.getsubjectChemistryLink().click();
			break;
		case "math":
		case "maths":
		case "mathematics":
			wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectMathematicsLink()));
			createAssessment.getsubjectMathematicsLink().click();
			break;
		default:
			Assert.fail("Subject :" + user.get("subject") + "not found");

		}
		
		if(Sub_Module!=""){
			executor.getElement(By.xpath("//span[.='" +Module+ "']")).click();
			executor.getElement(By.xpath("//span[.='" +Module+ "']")).click();
			executor.getElement(By.xpath("//span[.='" +Sub_Module+ "']")).click();
			}
		else
		{
			executor.getElement(By.xpath("//span[.='" +Module+ "']")).click();
		}
		
		
		
		/*
		 * email.sendKeys(user.get("email")); password.clear();
		 * password.sendKeys(user.get("password"));
		 * actions.click(loginButton).build().perform();
		 */
		// //label[normalize-space(.)='Advanced']/input
		
	}
		executor.getElement(By.xpath("//label[normalize-space(.)='" +user.get("ExamType")+ "']/input")).click();
		
		//executor.getElement(By.xpath("//label[normalize-space(.)='" +user.get("ExamType")+ "']/input")).click();
		
		
		return new CreateAccessment(driver, wait);
	}
	
		
	@DataProvider(name = "createAssessmentDataProvider")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "createAssessment");

	}

	/*@DataProvider(name = "dashboardNewUserDataProvider")

	public static Object[][] signUpDataProviderMandatoryData() {
		Map<String, String> user = new HashMap<String, String>();

		Object[][] dataSet = new Object[1][1];
		long timestamp = System.currentTimeMillis();

		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;

	}*/

}

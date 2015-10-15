package com.plancess.selenium.tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class DashboardTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private Dashboard dashboard;
	private SignUpDialogPage signUpDialogPage;

	@Test(dataProvider = "dashboardNewUserDataProvider", groups = { "smoke", "regression" })
	public void dashboardNewUserContentTest(Map<String, String> user) {
		// signUpDialogPage = landingPage.openSignUpDialogPage();
		// signUpDialogPage.signUp(user);
		landingPage.openLoginDialogPage().doLogin(user);

		executor.softWaitForWebElement(dashboard.getWelcomeMessage());

		executor.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				"assert user login succefull and welcome message displayed");

		// dashboard = loginDialogPage.doLogin(user);

		executor.softWaitForWebElement(dashboard.getWelcomeMessage());
		executor.click(dashboard.getDashBoardButton(), "Dashboard button");

		executor.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				"assert user login succefull and welcome message displayed");

		executor.verifyTrue(dashboard.getNotificationsButton().isDisplayed(), "verify notifications button displayed");

		executor.verifyTrue(dashboard.getToggleDropDown().isDisplayed(), "verify toggle dropdown displayed");

		executor.verifyTrue(dashboard.getPlancessHeaderLogo().isDisplayed(),
				"verify toggle dropdown displayed");
		verifications.verifyTrue(dashboard.getAcceptChallenges().size() >= 2,
				util.takeScreenshot(driver, "verify accept challenges displayed"));
		executor.softWaitForWebElement(dashboard.getPhysicsTakeTest());

		verifications.verifyTrue(dashboard.getPhysicsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify physics take test button displayed"));

		verifications.verifyTrue(dashboard.getChemistryTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify chemistry take test button displayed"));
		verifications.verifyTrue(dashboard.getMathsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify maths take test button  displayed"));

		verifications.verifyTrue(dashboard.getCreateCustomTestButton().isDisplayed(),
				util.takeScreenshot(driver, "verify create your own test link  displayed"));

		verifications.verifyTrue(!executor.isElementExist(By.xpath(dashboard.getPerformanceSummarySectionXpath())),
				util.takeScreenshot(driver, "verify performance summary section not displayed"));
		// more verifications to be added

	}

	@Test(dataProvider = "dashboardDataProvider", groups = { "smoke", "regression" })
	public void dashboardWithPerformanceSectionContentTest(Map<String, String> user) {
		loginDialogPage = landingPage.openLoginDialogPage();
		dashboard = loginDialogPage.doLogin(user);

		executor.softWaitForWebElement(dashboard.getWelcomeMessage());
		dashboard.getDashBoardButton().click();

		Assert.assertTrue(dashboard.getWelcomeMessage().isDisplayed(),
				util.takeScreenshot(driver, "assert user login succefull and welcome message displayed"));

		verifications.verifyTrue(dashboard.getNotificationsButton().isDisplayed(),
				util.takeScreenshot(driver, "verify notifications button displayed"));

		verifications.verifyTrue(dashboard.getToggleDropDown().isDisplayed(),
				util.takeScreenshot(driver, "verify toggle dropdown displayed"));

		verifications.verifyTrue(dashboard.getPlancessHeaderLogo().isDisplayed(),
				util.takeScreenshot(driver, "verify toggle dropdown displayed"));
		verifications.verifyTrue(dashboard.getAcceptChallenges().size() >= 2,
				util.takeScreenshot(driver, "verify accept challenges displayed"));
		executor.softWaitForWebElement(dashboard.getPhysicsTakeTest());

		verifications.verifyTrue(dashboard.getPhysicsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify physics take test button displayed"));

		verifications.verifyTrue(dashboard.getChemistryTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify chemistry take test button displayed"));
		verifications.verifyTrue(dashboard.getMathsTakeTest().isDisplayed(),
				util.takeScreenshot(driver, "verify maths take test button  displayed"));

		verifications.verifyTrue(dashboard.getCreateCustomTestButton().isDisplayed(),
				util.takeScreenshot(driver, "verify create your own test link  displayed"));

		verifications.verifyTrue(executor.isElementExist(By.xpath(dashboard.getPerformanceSummarySectionXpath())),
				util.takeScreenshot(driver, "verify performance summary section displayed"));

		// more verifications to be added

	}

	// data providers section

	@DataProvider(name = "dashboardDataProvider")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "dashboard");

	}

	@DataProvider(name = "dashboardNewUserDataProvider")

	public static Object[][] signUpDataProviderMandatoryData() {
		Map<String, String> user = new HashMap<String, String>();

		Object[][] dataSet = new Object[1][1];
		long timestamp = System.currentTimeMillis();

		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;

	}

}

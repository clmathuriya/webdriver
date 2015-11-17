package com.plancess.selenium.website.tests;

import org.testng.annotations.Test;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.utils.ExcelReader;
import com.plancess.selenium.utils.Util;
import com.plancess.selenium.utils.Verifications;
import com.plancess.selenium.website.pages.LandingPage;
import com.plancess.selenium.website.pages.LoginDialogPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class LoginTest extends BaseTest{

	private LoginDialogPage loginDialogPage;
	

	@Test(alwaysRun = true, dataProvider = "PlancessWebsiteDataProvider")
	public void LoginContentTest(Map<String, String> user) {

		
		landingPage.openLoginDialogPage();
		loginDialogPage = new LoginDialogPage(driver, wait);
		
		
		loginDialogPage.doLogin(user);
			
		executor.softWaitForCondition(ExpectedConditions.titleContains("Plancess"));
		verifications.verifyTrue(driver.getTitle().contains("Plancess"),
				util.takeScreenshot(driver, "Verify Login"));
		;
		verifications.verifyTrue(executor.getElement(By.xpath("//*[contains(text(),'" + user.get("name") + "')]")).isDisplayed(),
				util.takeScreenshot(driver, "verify name field displayed"));

	}

	@DataProvider(name = "PlancessWebsiteDataProvider")
	public Object[][] PlancessLoginDataProvider() {

		ExcelReader excelReader = new ExcelReader();
		return excelReader.getUserDataFromExcel("testData.xlsx", "PlancessWebsiteUserData");

	}

}

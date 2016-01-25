package com.plancess.selenium.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.AssessmentPage;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.PaymentPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class PaymentTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private SignUpDialogPage signUpDialogPage;
	private Dashboard dashboard;
		
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;
	
	private PaymentPage paymentPage;



	@Test(dataProvider = "makePaymentLandingPageValidData", groups = { "smoke","regression" })
	public void makePaymentFromLandingPageWithValidData(Map<String, String> user) {
		long timestamp = System.currentTimeMillis();

		user.put("email", "webuser" + timestamp + "@mailinator.com");
		paymentPage = landingPage.openPaymentPage();
		executor.softWaitForWebElement(paymentPage.getPackages());
		executor.assertTrue(paymentPage.getPackages().isDisplayed(), "Packages are displayed");
		
		executor.verifyTrue(executor.isElementExist(By.xpath(".//*[.='"+user.get("packageName")+"']")),
				"Verify if Package Exists: "+user.get("packageName"));
		
		executor.verifyTrue(executor.isElementExist(By.xpath(".//*[.='"+user.get("price")+"']")),
				"Verify if Package Exists: "+user.get("price"));
				
		paymentPage.selectPackage(user.get("packageSelected"));
		executor.softWaitForWebElement(paymentPage.getPaymentPreModal());
		paymentPage.enterUserDetails(user);
				
		paymentPage.makePayment(user);
				
		paymentPage.testPayment("sucess");
		paymentPage.sucessMessage(paymentPage.getPaymentSucessModal());
		paymentPage.verifyUpgradeEmail(user);
		paymentPage.verifyEmail(user);		
		
	}
	
	@Test(dataProvider = "makePaymentLandingPageValidData", groups = { "smoke", "regression" })
	public void makePaymentAfterLoginValidData(Map<String, String> user) {

		long timestamp = System.currentTimeMillis();

		user.put("email", "webuser" + timestamp + "@mailinator.com");
		user.put("subject", "physics");

		signUpDialogPage = landingPage.openSignUpDialogPage();
		signUpDialogPage.signUp(user);
		
		dashboard = signUpDialogPage.verifyEmail(user).doLogin(user);

		executor.softWaitForWebElement(dashboard.getDashBoardButton());	
		
				
		paymentPage = dashboard.openPaymentPage();
		
		executor.softWaitForWebElement(paymentPage.getUpgradePackages());
		executor.assertTrue(paymentPage.getUpgradePackages().isDisplayed(), "Packages are displayed");
		
		executor.verifyTrue(executor.isElementExist(By.xpath(".//*[.='"+user.get("packageName")+"']")),
				"Verify if Package Exists: "+user.get("packageName"));
		List<WebElement> elements=driver.findElements(By.xpath(".//*[@id='content']//*[@class='package-cost']//span"));
		String packageText="";
		for(WebElement e:elements){			
			if(e.getText().contains(user.get("price"))){
				packageText=e.getText();
				break;
			}
			System.out.println(e.getText());
		}
		executor.verifyTrue(packageText.contains(user.get("price")),
				"Verify if Package Exists: "+user.get("price"));
				
		paymentPage.selectPackageAfterLogin(user.get("packageSelected"));
		paymentPage.makePayment(user);
		paymentPage.testPayment("sucess");
		paymentPage.sucessMessage(paymentPage.getPaymentSucessModalAfterLogin());
		paymentPage.verifyUpgradeEmail(user);
		
		//dashboard.logoutUser();

	}
	
	/*@DataProvider(name = "makePaymentLandingPageValidData")
	public static Object[][] signUpDataProviderMandatoryData() {
		Map<String, String> user = new HashMap<String, String>();

		Object[][] dataSet = new Object[1][1];
		long timestamp = System.currentTimeMillis();

		user.put("password", "P@ssw0rd");
		//user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@mailinator.com");
		dataSet[0][0] = user;
		user.put("price", "1599");
		user.put("packageName", "JEE Main & Advanced Assessment 2016 Online");
		user.put("packageSelected", "1");
		user.put("contact", "9167360969");
		user.put("bank", "Axis Bank");
		user.put("firstname", "test");
		user.put("lastname", "user");
		return dataSet;

	}
	*/
	@DataProvider(name = "makePaymentLandingPageValidData")
	public static Object[][] signUpWithGoogleDataProvider() {
		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "payment");
	}
}

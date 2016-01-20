package com.plancess.selenium.pages;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class PaymentPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;

	// private SetNewPasswordDialogPage setNewPasswordDialogPage;
	WebElement resetPassError;
	WebElement resetPassSuccess;
	
	@FindBy(xpath = ".//*[@name='email']")
	WebElement emailPayment;
	
	@FindBy(xpath = ".//*[@name='fname']")
	WebElement fnamePayment;
	
	@FindBy(xpath = ".//*[@name='lname']")
	WebElement lnamePayment;

	@FindBy(css = "#email")
	WebElement email;
	@FindBy(css = "#contact")
	WebElement contact;

	@FindBy(xpath = "//*[.='Packages']")
	WebElement packages;

	@FindBy(xpath = "//*[.='Upgrade your account!']")
	WebElement upgradePackages;

	@FindBy(xpath = ".//*[@id='package-free']")
	WebElement packagefree;

	@FindBy(xpath = ".//*[@id='package-1']")
	WebElement package1;

	@FindBy(xpath = ".//*[@id='package-2']")
	WebElement package2;

	@FindBy(css = "#modal-content")
	WebElement paymentModal;

	@FindBy(css = "#header")
	WebElement paymentHeader;

	@FindBy(css = "#submitbtn")
	WebElement submitbtn;

	@FindBy(css = "#method-netbanking-tab")
	WebElement netBankingTab;

	@FindBy(xpath = ".//*[@id='netb-banks']//*[@for='bank-radio-SBIN']")
	WebElement sbibank;

	@FindBy(xpath = "//*[@class='success']")
	WebElement sucess;

	@FindBy(xpath = "//*[@class='danger']")
	WebElement failure;

	/*@FindBy(xpath = ".//*[@id='payment-success']//*[@class='modal-content']")
	WebElement paymentSucessModal;*/
	
	//@FindBy(xpath = "(.//*[@class='modal-content'])[1]")
	@FindBy(xpath = ".//*[@id='payment-success']//*[@class='modal-content']")
	WebElement paymentSucessModal;
	
	@FindBy(xpath = ".//*[@class='modal-content']")
	WebElement paymentSucessModalAfterLogin;

	@FindBy(xpath = ".//*[.='Payment Successful']")
	WebElement paymentSucess;

	/*@FindBy(xpath = ".//*[@id='payment-success']//*[@class='btn']")
	WebElement paymentOk;*/
	
	@FindBy(xpath = ".//*[@class='btn' and contains(text(),'OK')]")
	WebElement paymentOk;

	@FindBy(id = "inboxfield")
	WebElement inboxField;

	@FindBy(xpath = "//*[@onclick='changeInbox(); return false;']")
	WebElement checkInbox;

	@FindBy(partialLinkText = "Welcome to PrepLane")
	WebElement plancessWelcomeMail;

	@FindBy(partialLinkText = "Verify your email for PrepLane")
	WebElement plancessMail;

	@FindBy(partialLinkText = "PrepLane: You've been upgraded")
	WebElement plancessUpgradeMail;

	@FindBy(xpath = "//*[@name='rendermail']")
	WebElement renderemail;

	@FindBy(partialLinkText = "INVOICE LINK")
	WebElement invoiceLink;

	@FindBy(xpath = "//*[@title='VERIFY MY EMAIL ID']")
	WebElement activationLink;

	@FindBy(xpath = "//*[@id='myModalLabel' and contains(text(),'activated')]")
	WebElement activationMessage;

	@FindBy(xpath = "//*[@id='setBtn']")
	WebElement setBtn;

	@FindBy(xpath = "(//*[@name='password' and @placeholder='New Password'])[1]")
	WebElement password;

	@FindBy(xpath = "(//*[@name='cpassword'])[1]")
	WebElement cpassword;

	@FindBy(xpath = "//*[@class='mainContainer']")
	WebElement mailbox;
	
	@FindBy(xpath = ".//*[@id='pre-buy']//*[@class='modal-content']")
	WebElement paymentPreModal;
	
	@FindBy(xpath = ".//*[@id='proceedPayBtn']")
	WebElement proceedPayBtn;
	

	public PaymentPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);
		PageFactory.initElements(driver, this);

	}

	public WebElement getEmailPayment() {
		return emailPayment;
	}
	
	public WebElement getPaymentPreModal() {
		return paymentPreModal;
	}
	
	public WebElement getPaymentModal() {
		return paymentModal;
	}

	public WebElement getPaymentHeader() {
		return paymentHeader;
	}

	public WebElement getSubmitbtn() {
		return submitbtn;
	}

	public WebElement getPackages() {
		return packages;
	}

	public WebElement getUpgradePackages() {
		return upgradePackages;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getContact() {
		return contact;
	}

	public WebElement getSBIBank() {
		return sbibank;
	}

	public WebElement getPaymentSucessModal() {
		return paymentSucessModal;
	}
	
	public WebElement getPaymentSucessModalAfterLogin() {
		return paymentSucessModalAfterLogin;
	}

	public WebElement getPaymentOk() {
		return paymentOk;
	}

	public void selectPackage(String string) {
		switch (string) {
		case "free":
			executor.click(packagefree.findElement(By.tagName("a")), "Package free");
			break;
		case "1":
			executor.click(package1.findElement(By.tagName("a")), "Package 1");
			break;
		case "2":
			executor.click(package2.findElement(By.tagName("a")), "Package 2");
			break;
		default:
			break;
		}

	}

	public void selectPackageAfterLogin(String packageId) {
		WebElement element = executor.getElement(By.xpath(".//*[@packageid='" + packageId + "']"));
		executor.softWaitForWebElement(element);
		final int windowsBefore=driver.getWindowHandles().size();
		executor.click(element, "Upgrade Package");
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {

				return driver.getWindowHandles().size() > windowsBefore;
			}
		});
	}

	public void makePayment(Map<String, String> user) {
		int winBefore = 0;
		// String winHandleBefore = driver.getWindowHandle();

		executor.switchToDefaultContent();

		WebElement iframe = driver.findElement(By.tagName("iframe"));

		executor.switchToFrame(iframe);

		Set<String> winHandles = driver.getWindowHandles();
		winBefore = winHandles.size();

		executor.verifyTrue(executor.isElementExist(paymentModal),
				"Verify if Package Exists: " + user.get("packageName"));
		executor.softWaitForWebElement(getPaymentModal());

		executor.assertEquals(
				getPaymentHeader().getText().contains(user.get("packageName"))
						&& getPaymentHeader().getText().contains("Preplane"),
				true, "Verify if Payment modal has correct product name");
		executor.assertEquals(getSubmitbtn().getText().contains(user.get("price")), true,
				"Verify if Payment modal has correct product name");
		
			executor.clear(getEmail(), "Email Clear");
			executor.sendKeys(getEmail(), user.get("email"), "Email");
		
		executor.clear(getContact(), "Contact Clear");
		executor.sendKeys(getContact(), user.get("contact"), "Contact");

		executor.click(netBankingTab, "NetBanking-Tab");
		executor.click(getSBIBank(), "Bank");

		executor.click(getSubmitbtn(), "Submit", true);

		winHandles = driver.getWindowHandles();
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {

				return driver.getWindowHandles().size() > 1;
			}
		});

		executor.verifyTrue(winHandles.size() > winBefore, "Verfiy Pop Up:RazorPay");

		// testPayment("sucess");
		executor.switchToDefaultContent();

	}

	public void testPayment(String string) {
		executor.switchToDefaultContent();
		String currentWindowHandle = driver.getWindowHandle();
		Set<String> winHandles = driver.getWindowHandles();
		int winBefore = winHandles.size();
		final int temp=winBefore;
		int winAfter = 0;

		for (String winHandle : winHandles) {
			driver.switchTo().window(winHandle);
			if (executor.isElementExist(sucess)) {
				if (string.equalsIgnoreCase("failure")) {
					executor.click(failure, "Test Faliure");
					break;
				} else {
					executor.click(sucess, "Test sucess");
					executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

						@Override
						public Boolean apply(WebDriver driver) {

							return driver.getWindowHandles().size() <temp;
						}
					});

					break;
				}

			}

		}

		winHandles = driver.getWindowHandles();
		winAfter = winHandles.size();
		executor.verifyTrue(winBefore > winAfter, "After Payment Submission Windows should be less");

		driver.switchTo().window(currentWindowHandle);
		
		/*
		executor.softWaitForWebElement(getPaymentSucessModal());
		executor.assertTrue(executor.isElementExist(getPaymentSucessModal()), "Payment Sucess Modal");
		//executor.softWaitForWebElement(getPaymentSucessModal()||getPaymentSucessModalAfterLogin);
		

		String paymentText = getPaymentSucessModal().getText();
		System.out.println(getPaymentSucessModal().getText());

		executor.verifyTrue((paymentText.contains("Payment Successful")||paymentText.contains("Upgrade Successful") )&& paymentText.contains("OK"),
				"Payment Sucessful Text");
		executor.click(getPaymentOk(), "Sucess-Ok");
		*/

		// System.out.println();

	}
	
	public void sucessMessage(WebElement successModal){

		executor.softWaitForWebElement(successModal);
		executor.assertTrue(executor.isElementExist(successModal), "Payment Sucess Modal");
		//executor.softWaitForWebElement(getPaymentSucessModal()||getPaymentSucessModalAfterLogin);
		

		String paymentText = successModal.getText();
		System.out.println(successModal.getText());

		executor.verifyTrue((paymentText.contains("Payment Successful")||paymentText.contains("Upgrade Successful") )&& paymentText.contains("OK"),
				"Payment Sucessful Text");
		executor.click(getPaymentOk(), "Sucess-Ok");
	}

	public WebElement getSetBtn() {
		return setBtn;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getCpassword() {
		return cpassword;
	}

	public WebElement getResetPassMessage() {
		return resetPassError;
	}

	public WebElement getResetPassSuccess() {
		return resetPassSuccess;
	}

	public void openInbox(Map<String, String> user) {
		executor.navigateToURL("http://mailinator.com/inbox.jsp?to=" + user.get("email"));
		executor.softWaitForWebElement(inboxField);
		if (executor.isElementExist(inboxField)) {
			executor.sendKeys(inboxField, user.get("email"), "email inbox");
			executor.click(checkInbox, "Check inbox button");
		}
	}

	public void checkInboxPlancessMail() {
		int count = 0;

		while (!executor.isElementExist(plancessMail) && count++ <= 10) {
			driver.navigate().refresh();
		}
		executor.click(plancessMail, "plancess verification mail");

		executor.switchToFrame(renderemail);
		executor.softWaitForWebElement(activationLink);
		String windowHandle = driver.getWindowHandle();
		executor.click(activationLink, "activation link");
		driver.switchTo().defaultContent();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(windowHandle)) {
				driver.switchTo().window(handle);
			}
		}
		executor.softWaitForWebElement(getSetBtn());
	}

	public void setPassword(Map<String, String> user) {
		executor.softWaitForWebElement(getPassword(), "Enter New Password");
		executor.click(getPassword(), "New Password");
		executor.sendKeys(getPassword(), user.get("password"), "password");
		//executor.softWaitForWebElement(getCpassword(), "Enter confirm Password");
		executor.click(getCpassword(), "Confirm Password");
		executor.sendKeys(getCpassword(), user.get("password"), "confirm password");
		executor.click(getSetBtn(), "Reset Password Button");
		executor.softWaitForWebElement(activationMessage);
		executor.verifyTrue(activationMessage.isDisplayed(), "verify activation success message displayed");

	}

	public void verifyEmail(Map<String, String> user) {
		openInbox(user);
		checkInboxPlancessMail();
		setPassword(user);
	}

	public void verifyUpgradeEmail(Map<String, String> user) {
		openInbox(user);
		verfiyInboxUpgradeMail();
	}

	public void verifyRegisterEmail(Map<String, String> user) {
		openInbox(user);
		checkInboxPlancessMail();
		setPassword(user);
	}

	public void verfiyInboxUpgradeMail() {
		int count = 0;

		while (!executor.isElementExist(plancessUpgradeMail) && count++ <= 10) {
			driver.navigate().refresh();
		}
		executor.click(plancessUpgradeMail, "plancess upgrade mail");

		executor.switchToFrame(renderemail);
		executor.softWaitForWebElement(invoiceLink);
		String mailText = mailbox.getText();
		executor.assertTrue(mailText.contains("Your account has been upgraded to a premium account"),
				"Verify mail content");
		String windowHandle = driver.getWindowHandle();
		executor.click(invoiceLink, "invoice link");
	}

	public void verifyWelcomeEmail(Map<String, String> user) {
		// to verify email address
		executor.navigateToURL("http://mailinator.com/inbox.jsp?to=" + user.get("email"));
		executor.softWaitForWebElement(inboxField);
		if (executor.isElementExist(inboxField)) {
			executor.sendKeys(inboxField, user.get("email"), "email inbox");
			executor.click(checkInbox, "Check inbox button");
		}
		int count = 0;
		while (!executor.isElementExist(plancessWelcomeMail) && count++ <= 10) {
			driver.navigate().refresh();
		}

		executor.verifyTrue(plancessWelcomeMail.isDisplayed(), "verify welcome mail displayed");

	}
	
	public void enterUserDetails(Map<String, String> user){
		executor.softWaitForWebElement(emailPayment);
		executor.clear(fnamePayment, "firstname Clear");
		executor.sendKeys(fnamePayment, user.get("firstname"), "firstname");
		executor.clear(lnamePayment, "lastname Clear");
		executor.sendKeys(lnamePayment, user.get("lastname"), "lastname");
		executor.clear(emailPayment, "Payment Clear");
		executor.sendKeys(emailPayment,  user.get("email"), "Payment Email");
		executor.click(proceedPayBtn, "Proceed To Pay");
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@id='proceedPayBtn']")));
		//WebElement iframe = driver.findElement(By.tagName("iframe"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
		
	}

}

package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class ForgotPasswordDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	@FindBy(xpath = ".//*[@id='forgotPassForm']//*[@name='email']")
	WebElement email;

	@FindBy(xpath = "//*[@id='forgotPassBtn']")
	WebElement sendInstructionsButton;

	@FindBy(xpath = "//div[@class='success-message' and @id='forgotPassError']")
	WebElement successMessage;

	@FindBy(partialLinkText = "Password reset on Plancess")
	WebElement plancessResetMail;

	@FindBy(xpath = "//a[contains(@href,'reset-password')]")
	WebElement resetPasswordLink;

	@FindBy(xpath = "//*[@id='myModalLabel' and contains(text(),'activated')]")
	WebElement activationMessage;

	@FindBy(id = "inboxfield")
	WebElement inboxField;

	@FindBy(xpath = "//*[@onclick='changeInbox(); return false;']")
	WebElement checkInbox;

	@FindBy(xpath = "//*[@name='rendermail']")
	WebElement renderemail;
	private SetNewPasswordDialogPage setNewPasswordDialogPage;

	public ForgotPasswordDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);

		if (!"Preplane".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Forgot Password Dialog page");
		}
		PageFactory.initElements(driver, this);

	}

	public Actions getActions() {
		return actions;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getSendInstructionsButton() {
		return sendInstructionsButton;
	}

	public WebElement getSuccessMessage() {
		return successMessage;
	}

	public String getTitle() {
		return executor.getTitle();
	}

	public void resetPassword(Map<String, String> user) {
		// to verify email address
		executor.navigateToURL("http://mailinator.com/inbox.jsp?to=" + user.get("email"));
		executor.softWaitForWebElement(inboxField);
		if (executor.isElementExist(inboxField)) {
			executor.sendKeys(inboxField, user.get("email"), "email inbox");
			executor.click(checkInbox, "Check inbox button");
		}
		int count = 0;
		while (!executor.isElementExist(plancessResetMail) && count++ <= 10) {
			driver.navigate().refresh();
		}
		executor.click(plancessResetMail, "plancess reset password mail");

		executor.switchToFrame(renderemail);
		executor.softWaitForWebElement(resetPasswordLink);
		String windowHandle = driver.getWindowHandle();
		executor.click(resetPasswordLink, "reset password link");
		driver.switchTo().defaultContent();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(windowHandle)) {
				driver.switchTo().window(handle);
			}
		}
		setNewPasswordDialogPage = new SetNewPasswordDialogPage(driver, wait);
		executor.softWaitForWebElement(setNewPasswordDialogPage.getResetBtn());
		executor.sendKeys(setNewPasswordDialogPage.getPassword(), user.get("password"), "password");
		executor.sendKeys(setNewPasswordDialogPage.getCpassword(), user.get("password"), "confirm password");
		executor.click(setNewPasswordDialogPage.getResetBtn(), "Reset Password Button");
		executor.softWaitForCondition(
				ExpectedConditions.textToBePresentInElement(setNewPasswordDialogPage.getResetPassSuccess(),
						"Password reset successfully. Login with new password"));
		executor.verifyEquals(setNewPasswordDialogPage.getResetPassSuccess().getText().trim(),
				"Password reset successfully. Login with new password", "verify reset password message");

	}

}

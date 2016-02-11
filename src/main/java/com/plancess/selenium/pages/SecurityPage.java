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
import com.plancess.selenium.utils.Config;

public class SecurityPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private Executioner executor;

	WebElement currentPassword;

	@FindBy(name = "seurityNewPassword")
	WebElement newPassword;

	@FindBy(name = "seurityConfirmPassword")
	WebElement confirmPassword;

	@FindBy(css = "input[name='tel']")
	WebElement mobile;

	@FindBy(xpath = "//input[@ng-model='dt']")
	WebElement dob;

	@FindBy(xpath = "//*[@ng-model='profile.exam_target_year']")
	WebElement exam_target_year;

	@FindBy(xpath = "//input[@ng-model='profile.notify_exam_prep_info']")
	WebElement notify_exam_prep_checkbox;

	@FindBy(xpath = "//input[@ng-model='profile.notify_by_email']")
	WebElement notify_by_email_switch;

	@FindBy(xpath = "//button[text()='SUBMIT']")
	WebElement submit_button;

	@FindBy(css = "div.toast-title")
	WebElement toastTitle;

	@FindBy(css = "div.toast-message")
	WebElement toastMessage;

	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(css = "a[ng-click='logoutUser()']")
	WebElement logoutLink;

	@FindBy(css = "a[ui-sref='profile.main']")
	WebElement profileLink;

	@FindBy(css = "a[ui-sref='profile.security']")
	WebElement securityLink;

	@FindBy(css = "a[ui-sref='profile.purchases']")
	WebElement purchasesLink;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;
	@FindBy(xpath = "//*[@ng-show='alertShow']")
	WebElement alertMessage;

	public SecurityPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);

		if (!Config.PROFILE_TITLE.equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the user security page");
		}
		PageFactory.initElements(driver, this);

	}

	// getter and setters

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getPlancessHeaderLogo() {
		return plancessHeaderLogo;
	}

	public WebElement getPlancessFooterLogo() {
		return plancessFooterLogo;
	}

	public WebElement getToggleDropDown() {
		return toggleDropDown;
	}

	public WebElement getLogoutLink() {
		return logoutLink;
	}

	public WebElement getProfileLink() {
		return profileLink;
	}

	public WebElement getPurchasesLink() {
		return purchasesLink;
	}

	public WebElement getSecurityLink() {
		return securityLink;
	}

	public WebElement getCurrentPassword() {
		return currentPassword;
	}

	public WebElement getNewPassword() {
		return newPassword;
	}

	public WebElement getConfirmPassword() {
		return confirmPassword;
	}

	public WebElement getSubmit_button() {
		return submit_button;
	}

	public WebElement getToastMessage() {
		return toastMessage;
	}

	public WebElement getToastTitle() {
		return toastTitle;
	}

	public WebElement getAlertMessage() {
		return alertMessage;
	}

	// user operations

	public LoginPage logoutUser() {
		executor.click(toggleDropDown, "toggle drop down");
		executor.click(logoutLink, "logout link");

		return new LoginPage(driver, wait);

	}

	public SecurityPage updateUserSecurity(Map<String, String> user) {
		int timer = 1;
		while ((!executor.isElementExist(currentPassword) || !currentPassword.isDisplayed()) && timer++ < 10) {
			executor.softWaitForWebElement(currentPassword);
		}

		executor.click(currentPassword, "current password");
		executor.clear(currentPassword, "current password");
		executor.sendKeys(currentPassword, user.get("currentPassword"), "current password");

		executor.clear(newPassword, "new password");
		executor.sendKeys(newPassword, user.get("newPassword"), "new password");

		executor.clear(confirmPassword, "confirm password");
		executor.sendKeys(confirmPassword, user.get("confirmPassword"), "confirm password");

		executor.click(submit_button, "submit button");

		return this;

	}

	public String getTitle() {
		return driver.getTitle();
	}

	public boolean isHeaderLogoVisible() {
		return plancessHeaderLogo.isDisplayed();
	}

	public boolean isFooterLogoVisible() {
		return plancessFooterLogo.isDisplayed();
	}

	public boolean isToggleDropDownVisible() {
		return toggleDropDown.isDisplayed();
	}

	public void clickToggelDropDown() {
		executor.click(toggleDropDown, "toggle drop down");
	}
}

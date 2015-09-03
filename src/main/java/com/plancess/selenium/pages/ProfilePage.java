package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class ProfilePage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	WebElement firstName;
	WebElement lastName;
	WebElement email;

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

	@FindBy(xpath = "//button[text()='SAVE DETAILS']")
	WebElement save_details_button;

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

	public ProfilePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);

		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the user profile page");
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

	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getLastName() {
		return lastName;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getMobile() {
		return mobile;
	}

	public WebElement getDob() {
		return dob;
	}

	public WebElement getExam_target_year() {
		return exam_target_year;
	}

	public WebElement getNotify_exam_prep_checkbox() {
		return notify_exam_prep_checkbox;
	}

	public WebElement getNotify_by_email() {
		return notify_by_email_switch;
	}

	public WebElement getSave_details_button() {
		return save_details_button;
	}

	public WebElement getToastMessage() {
		return toastMessage;
	}

	public WebElement getToastTitle() {
		return toastTitle;
	}

	// user operations

	public LoginPage logoutUser() {
		toggleDropDown.click();
		logoutLink.click();

		return new LoginPage(driver, wait);

	}

	public ProfilePage updateUserProfile(Map<String, String> user) {
		firstName.clear();
		firstName.sendKeys(user.get("firstName"));
		lastName.clear();
		lastName.sendKeys(user.get("lastName"));
		mobile.clear();
		mobile.sendKeys(user.get("mobile"));
		//dob.clear();
		dob.sendKeys(user.get("dob"));
		new Select(exam_target_year).selectByVisibleText(user.get("target_year"));
		int trycount = 0;
		while (!notify_exam_prep_checkbox.isSelected() && trycount <= 5) {

			try {

				actions.click(notify_exam_prep_checkbox).build().perform();

				trycount++;
			} catch (Exception e) {
				trycount++;
			}
		}
		if (notify_by_email_switch.getAttribute("aria-checked").equals("false")) {

			notify_by_email_switch.click();
		}
		save_details_button.click();

		return new ProfilePage(driver, wait);

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
		toggleDropDown.click();
	}
}

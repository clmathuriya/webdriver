package com.plancess.selenium.pages;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.utils.Config;

public class ProfilePage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private Executioner executor;

	WebElement firstName;
	WebElement lastName;
	WebElement email;

	@FindBy(xpath = "//input[@type='file']")
	WebElement fileInput;

	@FindBy(xpath = ".//*[@class='selected-flag']")
	WebElement mobileCountryCode;

	@FindBy(css = "input[name='tel']")
	WebElement mobile;

	@FindBy(xpath = "//input[@ng-model='dt']")
	WebElement dob;

	@FindBy(xpath = "//*[@class='ui-datepicker-year']")
	WebElement dob_year;

	@FindBy(xpath = "//*[@class='ui-datepicker-month']")
	WebElement dob_month;

	@FindBy(xpath = "//*[@ng-model='profile.exam_target_year']")
	WebElement exam_target_year;

	@FindBy(xpath = "//input[@ng-model='profile.notify_exam_prep_info']")
	WebElement notify_exam_prep_checkbox;

	@FindBy(xpath = "//input[@ng-model='profile.notify_by_email']")
	WebElement notify_by_email_switch;

	@FindBy(xpath = "//*[@class='onoffswitch-label']")
	WebElement notify_by_email;

	@FindBy(xpath = "//button[text()='SAVE DETAILS']")
	WebElement save_details_button;

	@FindBy(css = "div.toast-title")
	WebElement toastTitle;

	@FindBy(css = "div.toast-message")
	WebElement toastMessage;

	@FindBy(xpath = "//*[@ng-show='alertShow']")
	WebElement alertMessage;

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

	@FindBy(xpath = "//*[@ng-click='croppedImg(myCroppedImage)']")
	WebElement profileCroppedImage;

	public ProfilePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);

		if (!Config.PROFILE_TITLE.equals(driver.getTitle())) {
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

	public WebElement getFileInput() {
		return fileInput;
	}

	public WebElement getNotify_by_email_switch() {
		return notify_by_email_switch;
	}

	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getAlertMessage() {
		return alertMessage;
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

	public WebElement getMobileCountryCode() {
		return mobileCountryCode;
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
		return notify_by_email;
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

	public WebElement getProfileCroppedImage() {
		return profileCroppedImage;
	}

	// user operations

	public LoginPage logoutUser() {
		toggleDropDown.click();
		logoutLink.click();

		return new LoginPage(driver, wait);

	}

	public ProfilePage updateUserProfile(Map<String, String> user) {
		int timer = 1;
		while (!executor.isElementExist(firstName) || !firstName.isDisplayed() && timer++ < 10) {
			executor.softWaitForWebElement(firstName);
		}
		executor.clear(firstName, "firstName");

		executor.sendKeys(firstName, user.get("firstName"), "firstName");

		executor.clear(lastName, "lastName");
		executor.sendKeys(lastName, user.get("lastName"), "lastName");

		executor.clear(mobile, "mobile");
		executor.click(mobileCountryCode, "Mobile Country Code");

		if (!user.get("mobile_Country_Code").equals("")) {
			WebElement mobileCountryCodeElement = executor
					.getElement(By.xpath("//span[.='" + user.get("mobile_Country_Code") + "']"));
			executor.click(mobileCountryCodeElement, "mobileCountryCode");
		} else if (!user.get("mobile_Country").equals("")) {
			WebElement mobileCountryElement = executor
					.getElement(By.xpath("//span[.='" + user.get("mobile_Country") + "']"));
			executor.click(mobileCountryElement, "mobileCountry");
		}

		executor.click(mobile, "mobile");
		executor.sendKeys(mobile, user.get("mobile"), "mobile");

		if (!user.get("dob").equals("")) {
			executor.mouseClick(dob);

			String a[] = user.get("dob").split("/");
			String date = a[0];
			String month = a[1];
			String year = a[2];

			int actualMonth = (Integer.parseInt(month) - 1);
			executor.softWaitForWebElement(dob_year);

			executor.selectFromDropDown(dob_year, "text", year);

			executor.selectFromDropDown(dob_month, "value", "" + actualMonth);

			WebElement dateDOBElement = executor.getElement(By.xpath("//a[.='" + Integer.parseInt(date) + "']"));
			executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(dateDOBElement));
			executor.mouseClick(dateDOBElement);

		}

		// Date Month Year
		if (!user.get("target_year").equals("")) {
			executor.selectFromDropDown(exam_target_year, "text", user.get("target_year"));

		}
		int trycount = 0;
		while (!notify_exam_prep_checkbox.isSelected() && trycount <= 5) {

			try {

				executor.mouseClick(notify_exam_prep_checkbox);
				trycount++;

			} catch (Exception e) {
				trycount++;
			}
		}
		if (user.get("profilePic").equalsIgnoreCase("Yes")) {

			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(user.get("filePath")).getFile());
			String js = "arguments[0].style.visibility = 'visible';arguments[0].style.display = 'block'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1";

			((JavascriptExecutor) driver).executeScript(js, getFileInput());

			getFileInput().sendKeys(file.getAbsolutePath());
			executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(getProfileCroppedImage()));

			if (executor.isElementExist(getProfileCroppedImage()))
				executor.click(getProfileCroppedImage(), "Profile Cropped Image");
		}

		if (getNotify_by_email_switch().getAttribute("aria-checked").equals("false")
				^ (user.get("emailNotification").equalsIgnoreCase("No"))) {

			executor.click(getNotify_by_email(), "Email Notification");
		}

		// notify_by_email_switch.click();
		executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(save_details_button));

		executor.mouseClick(save_details_button);// , "save user details
													// button");

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

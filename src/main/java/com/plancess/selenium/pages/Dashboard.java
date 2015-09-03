package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Dashboard {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(css = "a[ng-click='logoutUser()']")
	WebElement logoutLink;

	@FindBy(xpath = "//a[contains(.,'Profile')]")
	WebElement profileLink;

	@FindBy(css = "a[ui-sref='profile.security']")
	WebElement securityLink;

	@FindBy(css = "a[ui-sref='profile.purchases']")
	WebElement purchasesLink;

	@FindBy(css = ".start-assessment-section")
	WebElement startAssessmentSection;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	public Dashboard(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		// new Executioner(driver).navigateToURL(url);
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Dashboard page");
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

	public WebElement getStartAssessmentSection() {

		return startAssessmentSection;
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

	public LoginPage logoutUser() {

		toggleDropDown.click();
		logoutLink.click();

		return new LoginPage(driver, wait);

	}

	public ProfilePage navigateToUserProfile() {

		toggleDropDown.click();
		profileLink.click();

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

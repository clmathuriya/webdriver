package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.plancess.selenium.executor.Executioner;

public class HomePage {
	private final WebDriver driver;
	private String url = "http://dev.plancess.com/ui/#/";

	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(css = "a[ng-click='openLogin()']")
	WebElement loginLink;

	@FindBy(css = "a[ng-click='openSignUp()']")
	WebElement signupLink;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		new Executioner(driver).navigateToURL(url);
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Home page");
		}
		PageFactory.initElements(driver, this);

	}

	// getter and setters

	public WebDriver getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public WebElement getPlancessHeaderLogo() {
		return plancessHeaderLogo;
	}

	public WebElement getPlancessFooterLogo() {
		return plancessFooterLogo;
	}

	public WebElement getLoginLink() {
		return loginLink;
	}

	public WebElement getSignupLink() {
		return signupLink;
	}

	public WebElement getToggleDropDown() {
		return toggleDropDown;
	}

	// user operations
	public SignUpPage openSignUpPage() {
		toggleDropDown.click();
		signupLink.click();

		return new SignUpPage(driver);

	}

	public LoginPage openLoginPage() {
		toggleDropDown.click();
		loginLink.click();

		return new LoginPage(driver);

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

	public boolean isLoginLinkDisplayed() {

		return loginLink.isDisplayed();
	}

	public boolean isSignUpLinkDisplayed() {

		return signupLink.isDisplayed();
	}

	public void clickToggelDropDown() {
		toggleDropDown.click();
	}
}

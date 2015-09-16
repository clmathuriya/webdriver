package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class HomePage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private String url = "http://dev.plancess.com/ui/app/#/";

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

	@FindBy(css = "a[ng-click='logoutUser()']")
	WebElement logoutLink;

	// getter and setters

	public HomePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		new Executioner(driver, wait).navigateToURL(url);
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Home page");
		}
		PageFactory.initElements(driver, this);

	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public WebElement getPlancessHeaderLogo() {
		wait.until(ExpectedConditions.visibilityOf(plancessHeaderLogo));
		return plancessHeaderLogo;
	}

	public WebElement getPlancessFooterLogo() {
		wait.until(ExpectedConditions.visibilityOf(plancessFooterLogo));
		return plancessFooterLogo;
	}

	public WebElement getLoginLink() {
		wait.until(ExpectedConditions.visibilityOf(loginLink));
		return loginLink;
	}

	public WebElement getSignupLink() {
		wait.until(ExpectedConditions.visibilityOf(signupLink));
		return signupLink;
	}

	public WebElement getToggleDropDown() {
		wait.until(ExpectedConditions.visibilityOf(toggleDropDown));
		return toggleDropDown;
	}

	// user operations
	public SignUpPage openSignUpPage() {
		toggleDropDown.click();
		signupLink.click();

		return new SignUpPage(driver, wait);

	}

	public LoginPage openLoginPage() {
		toggleDropDown.click();
		tryLogout();
		loginLink.click();

		return new LoginPage(driver, wait);

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

	public void tryLogout() {

		try {
			logoutLink.click();
			toggleDropDown.click();
		} catch (Exception e) {
			System.out.println("user not logged in opening login page ...");
		}

	}
}

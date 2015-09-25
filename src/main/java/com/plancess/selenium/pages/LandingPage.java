package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class LandingPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private String url = "http://dev.preplane.com/";

	@FindBy(css = "header img[title='Preplane Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(xpath = "//a[.='Login']")
	WebElement loginLink;

	@FindBy(xpath = "//a[.='Sign Up']")
	WebElement signupLink;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	@FindBy(css = "a[ng-click='logoutUser()']")
	WebElement logoutLink;

	// getter and setters

	public LandingPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		new Executioner(driver, wait).navigateToURL(url);
		if (!"Plancess".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Landing page");
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
	public SignUpDialogPage openSignUpDialogPage() {
		// toggleDropDown.click();
		signupLink.click();

		return new SignUpDialogPage(driver, wait);

	}

	public LoginDialogPage openLoginDialogPage() {
		// toggleDropDown.click();
		// tryLogout();
		wait.until(ExpectedConditions.visibilityOf(loginLink));
		loginLink.click();

		return new LoginDialogPage(driver, wait);

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

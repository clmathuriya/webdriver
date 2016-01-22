package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.utils.Config;

public class LoginDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;

	@FindBy(xpath = ".//*[@id='loginForm']//*[@name='logindataEmail']")
	WebElement email;

	@FindBy(xpath = ".//*[@id='loginForm']//*[@name='logindataPass']")
	WebElement password;
	WebElement remember;

	@FindBy(css = "header img[title='Preplane Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(xpath = "//*[@ng-click='userLogin(logindata)']")
	WebElement loginButton;

	@FindBy(xpath = "//*[@ng-click='signUpForFree()']")
	WebElement signupButton;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	@FindBy(linkText = "Reset")
	WebElement resetPasswordLink;

	@FindBy(xpath = ".//*[@id='loginError']")
	WebElement failureMessage;

	@FindBy(xpath = "//*[@id='FbBtn']")
	WebElement FbBtn;

	// @FindBy(xpath = "(//*[@id='GBtn'])[2]")
	WebElement GBtn2;

	public LoginDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);
		executor.softWaitForCondition(ExpectedConditions.titleIs(Config.LANDING_PAGE_TITLE));
		PageFactory.initElements(driver, this);

		if (!loginButton.isDisplayed()) {
			throw new IllegalStateException("This is not  the Plancess login dialog page");
		}

	}

	public Actions getActions() {
		return actions;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getRemember() {
		return remember;
	}

	public WebElement getPlancessHeaderLogo() {
		return plancessHeaderLogo;
	}

	public WebElement getPlancessFooterLogo() {
		return plancessFooterLogo;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public WebElement getSignupButton() {
		return signupButton;
	}

	public WebElement getToggleDropDown() {
		return toggleDropDown;
	}

	public WebElement getResetPasswordLink() {
		return resetPasswordLink;
	}

	public WebElement getFailureMessage() {
		return failureMessage;
	}

	public WebElement getFbBtn() {
		return FbBtn;
	}

	public WebElement getGBtn() {
		return GBtn2;
	}

	public Dashboard doLogin(Map<String, String> user) {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(email), "wait for email field to be displayed");
		executor.clear(email, "email");
		executor.sendKeys(email, user.get("email"), "email");
		executor.clear(password, "Password");
		executor.sendKeys(password, user.get("password"), "Password");
		executor.click(loginButton, "login button");
		
		
		return new Dashboard(driver, wait);
	}

	public SignUpPage navigateToSignUpPage() {
		executor.click(signupButton, "signup button");
		return new SignUpPage(driver, wait);
	}

	public String getTitle() {
		return executor.getTitle();
	}

	public FacebookLoginDialogPage navigateToFacebookLoginDialog() {
		String currentWindowHandle = driver.getWindowHandle();
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(getFbBtn()), "wait for Facebook button");
		executor.click(getFbBtn(), "Facebook button");
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {

				return driver.getWindowHandles().size() >= 2;
			}
		});
		for (String handle : driver.getWindowHandles()) {

			if (!handle.equals(currentWindowHandle))
				driver.switchTo().window(handle);
		}
		return new FacebookLoginDialogPage(driver, wait);
	}

	public GoogleSignInDialogPage navigateToGoogleLoginDialog() {
		String currentWindowHandle = driver.getWindowHandle();
		executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(getGBtn()),
				"wait for google plus button");
		executor.mouseClick(getGBtn());
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {

				return driver.getWindowHandles().size() >= 2;
			}
		});
		for (String handle : driver.getWindowHandles()) {

			if (!handle.equals(currentWindowHandle))
				driver.switchTo().window(handle);
		}
		return new GoogleSignInDialogPage(driver, wait);
	}

}

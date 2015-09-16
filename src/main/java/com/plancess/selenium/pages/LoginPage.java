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

public class LoginPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	WebElement email;

	WebElement password;
	WebElement remember;

	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement loginButton;

	@FindBy(xpath = "//*[@ng-click='signUpForFree()'']")
	WebElement signupButton;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	@FindBy(linkText = "Forgot Password?")
	WebElement forgotPasswordLink;

	@FindBy(xpath = "//form[@name='form']/div[3]")
	WebElement failureMessage;

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);

		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Home page");
		}
		PageFactory.initElements(driver, this);

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

	public WebElement getForgotPasswordLink() {
		return forgotPasswordLink;
	}

	public WebElement getFailureMessage() {
		return failureMessage;
	}

	public Dashboard doLogin(Map<String, String> user) {
		wait.until(ExpectedConditions.visibilityOf(email));
		email.clear();
		email.sendKeys(user.get("email"));
		password.clear();
		password.sendKeys(user.get("password"));
		actions.click(loginButton).build().perform();

		return new Dashboard(driver, wait);
	}

	public SignUpPage navigateToSignUpPage() {
		actions.click(signupButton).build().perform();
		return new SignUpPage(driver, wait);
	}

	public String getTitle() {
		return driver.getTitle();
	}

}

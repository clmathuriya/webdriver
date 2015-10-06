package com.plancess.selenium.pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class LoginDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	@FindBy(xpath = ".//*[@id='loginForm']//*[@name='email']")
	WebElement email;

	@FindBy(xpath = ".//*[@id='loginForm']//*[@name='password']")
	WebElement password;
	WebElement remember;

	@FindBy(css = "header img[title='Preplane Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(xpath = "//*[@id='loginBtn']")
	WebElement loginButton;

	@FindBy(xpath = "//*[@ng-click='signUpForFree()']")
	WebElement signupButton;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	@FindBy(linkText = "Reset")
	WebElement forgotPasswordLink;

	@FindBy(xpath = "//div[@class='error-message' and @id='loginError']")
	WebElement failureMessage;

	@FindBys(value = { @FindBy(id = "FbBtn") })
	List<WebElement> FbBtn;

	@FindBys(value = { @FindBy(id = "GBtn") })
	List<WebElement> GBtn;

	public LoginDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);

		if (!"Preplane".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Home page");
		}
		PageFactory.initElements(driver, this);

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

	public WebElement getForgotPasswordLink() {
		return forgotPasswordLink;
	}

	public WebElement getFailureMessage() {
		return failureMessage;
	}

	public WebElement getFbBtn() {
		for (WebElement e : FbBtn) {
			if (e.isDisplayed())
				return e;
		}
		return null;
	}

	public WebElement getGBtn() {
		for (WebElement e : GBtn) {
			if (e.isDisplayed())
				return e;
		}
		return null;
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

	public FacebookLoginDialogPage navigateToFacebookLoginDialog() {
		String currentWindowHandle = driver.getWindowHandle();
		wait.until(ExpectedConditions.visibilityOf(getFbBtn()));
		actions.click(getFbBtn()).build().perform();
		wait.until(new ExpectedCondition<Boolean>() {

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
		wait.until(ExpectedConditions.visibilityOf(getGBtn()));
		actions.click(getGBtn()).build().perform();
		wait.until(new ExpectedCondition<Boolean>() {

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

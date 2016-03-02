package com.newton.selenium.website.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newton.selenium.executor.Executioner;
import com.newton.selenium.utils.Config;

public class LoginDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	
	@FindBy(xpath = ".//*[@id='popemail']")
	WebElement email;
	
	@FindBy(xpath = ".//*[@id='poppassword']")
	WebElement password;
	
	@FindBy(xpath = "//*[@id='popupLoginFormBtn']")
	WebElement loginButton;
	
	
	public LoginDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);
		//System.out.println(getTitle());
		if (!Config.LANDING_PAGE_TITLE_WEBSITE.equals(driver.getTitle())) {
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
	
	public WebElement getLoginButton() {
		return loginButton;
	}
	
	public String getTitle() {
		return executor.getTitle();
	}
	
	public LandingPage doLogin(Map<String, String> user) {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(email), "wait for email field to be displayed");
		executor.clear(email, "email");
		executor.sendKeys(email, user.get("email"), "email");
		executor.clear(password, "Password");
		executor.sendKeys(password, user.get("password"), "Password");
		executor.click(loginButton, "login button");

		return new LandingPage(driver, wait);
	}
	/*public void doLogin(Map<String, String> user) {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(email), "wait for email field to be displayed");
		executor.clear(email, "email");
		executor.sendKeys(email, user.get("email"), "email");
		executor.clear(password, "Password");
		executor.sendKeys(password, user.get("password"), "Password");
		executor.click(loginButton, "login button");

		//return new LandingPage(driver, wait);
	}*/

}

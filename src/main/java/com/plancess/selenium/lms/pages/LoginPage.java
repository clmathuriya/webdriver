package com.plancess.selenium.lms.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	WebElement username;

	WebElement password;

	@FindBy(xpath = "//*[@id='btn-login']")
	WebElement loginButton;

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		// new Executioner(driver).navigateToURL(url);
		if (!driver.getCurrentUrl().contains("/auth")) {
			throw new IllegalStateException("This is not a login page");
		}
		PageFactory.initElements(driver, this);

	}

	// getter and setters

	public WebDriver getDriver() {
		return driver;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public Actions getActions() {
		return actions;
	}

	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void login(Map<String, String> user) {

		username.sendKeys(user.get("username"));
		password.sendKeys(user.get("password"));
		loginButton.click();

	}

}

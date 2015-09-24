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

public class GoogleSignInDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;

	@FindBy(xpath = "//*[@name='Email']")
	WebElement email;
	@FindBy(xpath = "//*[@name='Passwd']")
	WebElement password;

	WebElement signIn;
	WebElement next;
	WebElement cancel;

	@FindBy(xpath = "//button[@id='submit_approve_access']")
	WebElement allowButton;

	@FindBy(xpath = "//button[@id='submit_deny_access']")
	WebElement denyButton;

	public GoogleSignInDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);
		PageFactory.initElements(driver, this);
		if (!"Sign in - Google Accounts".equals(driver.getTitle().trim())) {
			throw new IllegalStateException("This is not  Google Sign In  page");
		}

	}

	public Actions getActions() {
		return actions;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPass() {
		return password;
	}

	public WebElement getLogin() {
		return signIn;
	}

	public WebElement getCancel() {
		return cancel;
	}

	public WebElement getNext() {
		return next;
	}

	public WebElement getDenyButton() {
		return denyButton;
	}

	public WebElement getAllowButton() {
		return allowButton;
	}

	public Dashboard doLogin(Map<String, String> user) {
		wait.until(ExpectedConditions.visibilityOf(email));
		email.clear();
		email.sendKeys(user.get("googleEmail"));
		actions.click(next).build().perform();
		wait.until(ExpectedConditions.visibilityOf(password));
		password.clear();
		password.sendKeys(user.get("googlePassword"));

		String currentWindowHandle = driver.getWindowHandle();
		actions.click(signIn).build().perform();
		if (executor.isElementExist(allowButton)) {
			allowButton.click();
		}
		wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {

				return driver.getWindowHandles().size() == 1;
			}
		});
		for (String handle : driver.getWindowHandles()) {

			if (!handle.equals(currentWindowHandle))
				driver.switchTo().window(handle);
		}

		return new Dashboard(driver, wait);
	}

	public LoginDialogPage cancelLogin() {
		wait.until(ExpectedConditions.visibilityOf(email));

		String currentWindowHandle = driver.getWindowHandle();
		// actions.click(cancel).build().perform();
		driver.close();
		wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {

				return driver.getWindowHandles().size() == 1;
			}
		});
		for (String handle : driver.getWindowHandles()) {

			if (!handle.equals(currentWindowHandle))
				driver.switchTo().window(handle);
		}

		return new LoginDialogPage(driver, wait);
	}

	public String getTitle() {
		return driver.getTitle();
	}

}

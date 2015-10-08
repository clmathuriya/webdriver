package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookLoginDialogPage extends BasePage {
	WebElement email;
	WebElement pass;
	WebElement login;
	WebElement cancel;

	public FacebookLoginDialogPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		PageFactory.initElements(driver, this);
		if (!"Facebook".equals(driver.getTitle().trim())) {
			throw new IllegalStateException("This is not  Facebook login page");
		}

	}

	public Actions getActions() {
		return actions;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPass() {
		return pass;
	}

	public WebElement getLogin() {
		return login;
	}

	public WebElement getCancel() {
		return cancel;
	}

	public Dashboard doLogin(Map<String, String> user) {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(email));
		executor.clear(email, "email ");
		executor.sendKeys(email, user.get("fbEmail"), "email");
		executor.clear(pass, "password");
		executor.sendKeys(pass, user.get("fbPassword"), "Password");

		String currentWindowHandle = driver.getWindowHandle();
		executor.click(login, "login button");
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

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
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(email));

		String currentWindowHandle = driver.getWindowHandle();
		// click without taking screenshot as window will be closed.
		executor.click(cancel, "cancel button", false);
		executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

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
		return executor.getTitle();
	}

}

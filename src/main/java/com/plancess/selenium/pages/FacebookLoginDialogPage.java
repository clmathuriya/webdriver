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

public class FacebookLoginDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;

	WebElement email;
	WebElement pass;
	WebElement login;
	WebElement cancel;

	public FacebookLoginDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
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
		wait.until(ExpectedConditions.visibilityOf(email));
		email.clear();
		email.sendKeys(user.get("fbEmail"));
		pass.clear();
		pass.sendKeys(user.get("fbPassword"));

		String currentWindowHandle = driver.getWindowHandle();
		actions.click(login).build().perform();
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
		actions.click(cancel).build().perform();
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

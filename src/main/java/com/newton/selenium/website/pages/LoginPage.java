package com.newton.selenium.website.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newton.selenium.executor.Executioner;

public class LoginPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	WebElement email;

	WebElement password;
	WebElement remember;
	
	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);

		if (!"Plancess".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Home page");
		}
		PageFactory.initElements(driver, this);

	}
	public String getTitle() {
		return driver.getTitle();
	}

}

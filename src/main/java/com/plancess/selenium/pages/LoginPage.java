package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private final WebDriver driver;
	WebElement q;

	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(css = "a[ng-click='openLogin()']")
	WebElement loginLink;

	@FindBy(css = "a[ng-click='openSignUp()']")
	WebElement signupLink;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Home page");
		}
		PageFactory.initElements(driver, this);

	}

}

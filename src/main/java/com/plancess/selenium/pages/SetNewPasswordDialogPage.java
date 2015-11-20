package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.utils.Config;

public class SetNewPasswordDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	@FindBy(xpath = "(//*[@name='password' and @placeholder='New Password'])[2]")
	WebElement password;
	@FindBy(xpath = "(//*[@name='cpassword'])[2]")
	WebElement cpassword;
	WebElement resetBtn;

	WebElement resetPassError;
	WebElement resetPassSuccess;

	public SetNewPasswordDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);

		if (!Config.LANDING_PAGE_TITLE.equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Forgot Password Dialog page");
		}
		PageFactory.initElements(driver, this);

	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getCpassword() {
		return cpassword;
	}

	public WebElement getResetBtn() {
		return resetBtn;
	}

	public WebElement getResetPassMessage() {
		return resetPassError;
	}

	public WebElement getResetPassSuccess() {
		return resetPassSuccess;
	}

}

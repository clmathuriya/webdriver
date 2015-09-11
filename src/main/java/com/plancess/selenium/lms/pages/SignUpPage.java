package com.plancess.selenium.lms.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;;

	@FindBy(id = "exampleInputName")
	WebElement name;
	@FindBy(id = "exampleInputEmail")
	WebElement email;
	@FindBy(id = "exampleInputMobile")
	WebElement mobile;

	WebElement city;

	WebElement year;

	@FindBy(css = "button.form-btn")
	WebElement submitButton;

	public WebDriver getDriver() {
		return driver;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public Actions getActions() {
		return actions;
	}

	public WebElement getName() {
		return name;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getMobile() {
		return mobile;
	}

	public WebElement getCity() {
		return city;
	}

	public WebElement getYear() {
		return year;
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public SignUpPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		if (!driver.getCurrentUrl().contains("pages.plancessjee.com")) {
			throw new IllegalStateException("This is not  the Plancess Campaign SignUp page");
		}
		PageFactory.initElements(driver, this);

	}

	// getter setters

	// user operations
	public String getTitle() {

		return driver.getTitle();
	}

	public ThankYouPage signUpWithMandatoryFiels(Map<String, String> user) {
		wait.until(ExpectedConditions.visibilityOf(email));

		return new ThankYouPage(driver, wait);

	}

	public void fillSignUpForm(Map<String, String> user) {
		wait.until(ExpectedConditions.visibilityOf(name));
		name.sendKeys(user.get("name"));

		email.sendKeys(user.get("email"));

		mobile.sendKeys(user.get("mobile"));
		city.sendKeys(user.get("city"));
		new Select(year).selectByVisibleText(user.get("target_year"));

	}

	public ThankYouPage signUp(Map<String, String> user) {
		fillSignUpForm(user);

		actions.click(submitButton).build().perform();
		wait.until(ExpectedConditions.urlContains("thankyou"));
		return new ThankYouPage(driver, wait);
	}

}

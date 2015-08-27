package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;;
	private final String agreeCheckBoxXpath = "//input[@type='checkbox']";
	WebElement firstName;
	@FindBy(xpath = "//input[@name='firstName']/../div")
	WebElement firstNameErrorMessage;
	WebElement lastName;
	@FindBy(xpath = "//input[@name='lastName']/../div")
	WebElement lastNameErrorMessage;

	WebElement email;

	@FindBy(xpath = "//input[@name='email']/../div")
	WebElement emailErrorMessage;

	@FindBy(css = "input[name='tel']")
	WebElement mobile;

	@FindBy(xpath = "//input[@name='tel']/../div")
	WebElement mobileErrorMessage;

	WebElement password;
	@FindBy(xpath = "//input[@name='password']/../div")
	WebElement passwordErrorMessage;
	WebElement confirmPassword;
	@FindBy(xpath = "//input[@name='confirmPassword']/../../div[3]")
	// in case of password mismatch error check for contains not equals
	WebElement confirmPasswordErrorMessage;

	@FindBy(xpath = agreeCheckBoxXpath)
	WebElement agreeCheckbox;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement submit;

	@FindBy(xpath = "//a[.='Back']")
	WebElement back;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	@FindBy(xpath = "//div[.='Account created successfully!']")
	WebElement successMessage;

	@FindBy(xpath = "//div[@class='error-message']")
	WebElement failureMessage;

	public SignUpPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess SignUp page");
		}
		PageFactory.initElements(driver, this);

	}

	// getter setters

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getLastName() {
		return lastName;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getMobile() {
		return mobile;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getConfirmPassword() {
		return confirmPassword;
	}

	public WebElement getAgreeCheckbox() {

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(agreeCheckBoxXpath)));
		return agreeCheckbox;
	}

	public WebElement getSubmit() {
		wait.until(ExpectedConditions.visibilityOf(submit));
		return submit;
	}

	public WebElement getBack() {
		return back;
	}

	public WebElement getToggleDropDown() {
		return toggleDropDown;
	}

	public WebElement getSuccessMessage() {
		return successMessage;
	}

	public WebElement getConfirmPasswordErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(confirmPasswordErrorMessage));
		return confirmPasswordErrorMessage;
	}

	public WebElement getEmailErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(emailErrorMessage));
		return emailErrorMessage;
	}

	public WebElement getFailureMessage() {
		return failureMessage;
	}

	public WebElement getFirstNameErrorMessage() {
		return firstNameErrorMessage;
	}

	public WebElement getLastNameErrorMessage() {
		return lastNameErrorMessage;
	}

	public WebElement getMobileErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(mobileErrorMessage));
		return mobileErrorMessage;
	}

	public WebElement getPasswordErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(passwordErrorMessage));
		return passwordErrorMessage;
	}

	// user operations
	public String getTitle() {

		return driver.getTitle();
	}

	public Dashboard signUpWithMandatoryFiels(Map<String, String> user) {
		wait.until(ExpectedConditions.visibilityOf(email));
		email.sendKeys(user.get("email"));
		password.sendKeys(user.get("password"));
		confirmPassword.sendKeys(user.get("confirm_password"));
		int trycount = 0;
		while (!agreeCheckbox.isSelected() && trycount <= 5) {
			actions.click(agreeCheckbox).build().perform();

			trycount++;
		}
		actions.click(submit).build().perform();

		return new Dashboard(driver, wait);

	}

	public void fillSignUpForm(Map<String, String> user) {
		wait.until(ExpectedConditions.visibilityOf(firstName));
		firstName.sendKeys(user.get("firstName"));

		email.sendKeys(user.get("email"));
		password.sendKeys(user.get("password"));
		lastName.sendKeys(user.get("lastName"));
		mobile.sendKeys(user.get("mobile"));
		confirmPassword.sendKeys(user.get("confirm_password"));

	}

	public Dashboard signUp(Map<String, String> user) {
		fillSignUpForm(user);

		int trycount = 0;
		while (!agreeCheckbox.isSelected() && trycount <= 5) {

			try {
				// wait.until(ExpectedConditions.elementToBeClickable(agreeCheckbox));
				actions.click(agreeCheckbox).build().perform();

				trycount++;
			} catch (Exception e) {
				trycount++;
			}
		}
		actions.click(submit).build().perform();
		return new Dashboard(driver, wait);
	}

}

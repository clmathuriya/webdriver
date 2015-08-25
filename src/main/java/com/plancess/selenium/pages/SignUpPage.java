package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
	private final WebDriver driver;
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

	@FindBy(css = "input[type='Checkbox']")
	WebElement agreeCheckbox;

	@FindBy(xpath = "//button[.='Submit']")
	WebElement submit;

	@FindBy(xpath = "//a[.='Back']")
	WebElement back;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

	@FindBy(xpath = "//div[.='Account created successfully!']")
	WebElement successMessage;

	@FindBy(xpath = "//div[@class='error-message']")
	WebElement failureMessage;

	public SignUpPage(WebDriver driver) {
		this.driver = driver;
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
		return agreeCheckbox;
	}

	public WebElement getSubmit() {
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
		return confirmPasswordErrorMessage;
	}

	public WebElement getEmailErrorMessage() {
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
		return mobileErrorMessage;
	}

	public WebElement getPasswordErrorMessage() {
		return passwordErrorMessage;
	}

	// user operations
	public String getTitle() {

		return driver.getTitle();
	}

	public Dashboard signUpWithMandatoryFiels(Map<String, String> user) {
		email.sendKeys(user.get("email"));
		password.sendKeys(user.get("password"));
		confirmPassword.sendKeys(user.get("confirm_password"));
		agreeCheckbox.click();
		submit.click();
		return new Dashboard(driver);

	}

	public void fillSignUpForm(Map<String, String> user) {
		firstName.sendKeys(user.get("firstName"));

		email.sendKeys(user.get("email"));
		password.sendKeys(user.get("password"));
		lastName.sendKeys(user.get("lastName"));
		mobile.sendKeys(user.get("mobile"));
		confirmPassword.sendKeys(user.get("confirm_password"));
		if (agreeCheckbox.isEnabled())
			agreeCheckbox.click();
	}

	public void signUp(Map<String, String> user) {
		fillSignUpForm(user);
		submit.click();
	}

}

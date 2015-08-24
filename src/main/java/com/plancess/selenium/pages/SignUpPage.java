package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
	private final WebDriver driver;
	WebElement firstName;
	@FindBy(xpath = "//*[.='Please enter atleast 3 characters']") //to be updated
	WebElement firstNameErrorMessage;
	WebElement lastName;
	@FindBy(xpath = "//*[.='Please enter correct email address']")//to be updated
	WebElement lastNameErrorMessage;

	WebElement email;

	@FindBy(xpath = "//*[.='Please enter correct email address']")
	WebElement emailErrorMessage;

	@FindBy(css = "input[name='tel']")
	WebElement mobile;

	@FindBy(xpath = "//*[.='Valid phone number starting with 7,8 or 9 is required']")
	WebElement mobileErrorMessage;

	WebElement password;
	@FindBy(xpath = "//*[.='Please enter correct email address']")//to be updated
	WebElement passwordErrorMessage;
	WebElement confirmPassword;
	@FindBy(xpath = "//*[.='Please enter correct email address']")//to be updated
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

	@FindBy(xpath = "//div[.='Account created successfully!']")// to be updated
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

	// user operations
	public String getTitle() {

		return driver.getTitle();
	}

	public WebElement getSuccessMessage() {
		return successMessage;
	}

	public void signUpWithMandatoryFiels(Map<String, String> user) {
		email.sendKeys(user.get("email"));
		password.sendKeys(user.get("password"));
		confirmPassword.sendKeys(user.get("confirm_password"));
		agreeCheckbox.click();
		submit.click();

	}

	public void signUp(Map<String, String> user) {
		firstName.sendKeys(user.get("firstName"));
		lastName.sendKeys(user.get("lastName"));
		mobile.sendKeys(user.get("mobile"));
		email.sendKeys(user.get("email"));
		password.sendKeys(user.get("password"));
		confirmPassword.sendKeys(user.get("confirm_password"));
		agreeCheckbox.click();
		submit.click();
	}
}

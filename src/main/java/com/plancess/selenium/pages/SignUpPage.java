package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
	private final WebDriver driver;
	WebElement firstName;
	WebElement lastName;

	WebElement email;
	@FindBy(css = "input[name='tel']")
	WebElement mobile;

	WebElement password;
	WebElement confirmPassword;

	@FindBy(css = "input[type='Checkbox']")
	WebElement agreeCheckbox;

	@FindBy(xpath = "//button[.='Submit']")
	WebElement submit;

	@FindBy(xpath = "//a[.='Back']")
	WebElement back;

	@FindBy(css = "a[data-toggle='dropdown'] img")
	WebElement toggleDropDown;

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
}

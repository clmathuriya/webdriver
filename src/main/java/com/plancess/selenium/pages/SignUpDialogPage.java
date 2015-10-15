package com.plancess.selenium.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class SignUpDialogPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private Executioner executor;
	private final String agreeCheckBoxXpath = "//input[@type='checkbox']";
	WebElement fname;
	@FindBy(xpath = "//*[@id='regFnameError']")
	WebElement firstNameErrorMessage;
	WebElement lname;
	@FindBy(xpath = "//*[@id='regLnameError']")
	WebElement lastNameErrorMessage;

	WebElement email;

	@FindBy(xpath = ".//*[@id='regEmailError']")
	WebElement emailErrorMessage;

	@FindBy(css = "input[name='tel']")
	WebElement mobile;

	@FindBy(xpath = "//input[@name='tel']/../div")
	WebElement mobileErrorMessage;

	WebElement password;

	@FindBy(xpath = ".//*[@id='regPasswordError']")
	WebElement passwordErrorMessage;
	// WebElement confirmPassword;
	// @FindBy(xpath = "//input[@name='confirmPassword']/../../div[3]")
	// // in case of password mismatch error check for contains not equals
	// WebElement confirmPasswordErrorMessage;

	// @FindBy(xpath = agreeCheckBoxXpath)
	// WebElement agreeCheckbox;

	@FindBy(xpath = "//input[@id='regBtn']")
	WebElement submit;

	// @FindBy(xpath = "//a[.='Back']")
	// WebElement back;

	// @FindBy(css = "a[data-toggle='dropdown'] img")
	// WebElement toggleDropDown;

	@FindBy(xpath = "//*[@id='myModalLabel' and contains(text(),'Thank You')]")
	WebElement successMessage;

	@FindBy(xpath = "//*[@id='regEmailError']")
	WebElement failureMessage;

	@FindBy(partialLinkText = "Verify your email for PrepLane")
	WebElement plancessMail;

	@FindBy(partialLinkText = "VERIFY MY EMAIL")
	WebElement activationLink;

	@FindBy(xpath = "//*[@id='myModalLabel' and contains(text(),'activated')]")
	WebElement activationMessage;

	@FindBy(id = "inboxfield")
	WebElement inboxField;

	@FindBy(xpath = "//*[@onclick='changeInbox();']")
	WebElement checkInbox;

	@FindBy(xpath = "//*[@name='rendermail']")
	WebElement renderemail;

	public SignUpDialogPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);
		if (!"Preplane".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess SignUp page");
		}
		PageFactory.initElements(driver, this);

	}

	// getter setters

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getFirstName() {

		return fname;
	}

	public WebElement getLastName() {
		return lname;
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

	public WebElement getSubmit() {
		wait.until(ExpectedConditions.visibilityOf(submit));
		return submit;
	}

	public WebElement getSuccessMessage() {
		wait.until(ExpectedConditions.visibilityOf(successMessage));
		return successMessage;
	}

	public WebElement getEmailErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(emailErrorMessage));
		return emailErrorMessage;
	}

	public WebElement getFailureMessage() {
		wait.until(ExpectedConditions.visibilityOf(failureMessage));
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

	public void signUpWithMandatoryFiels(Map<String, String> user) {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(fname));
		executor.clear(email, "email");
		executor.sendKeys(email, user.get("email"), "email");

		executor.clear(password, "password");

		executor.sendKeys(password, user.get("password"), "password");
		executor.click(submit, "Submit button");

		// return new Dashboard(driver, wait);

	}

	public void fillSignUpForm(Map<String, String> user) {
		executor.softWaitForWebElement(ExpectedConditions.visibilityOf(fname));
		executor.sendKeys(fname, user.get("firstName"), "first name");
		executor.sendKeys(lname, user.get("lastName"), "last name");
		executor.sendKeys(email, user.get("email"), "email");
		executor.sendKeys(password, user.get("password"), "password");
	}

	public void signUp(Map<String, String> user) {
		fillSignUpForm(user);
		executor.click(submit, "Submit button");
		// return new Dashboard(driver, wait);
	}

	public void verifyEmail(Map<String, String> user) {
		// to verify email address
		executor.navigateToURL("http://mailinator.com/inbox.jsp?to=" + user.get("email"));
		executor.softWaitForWebElement(inboxField);
		if (executor.isElementExist(inboxField)) {
			executor.sendKeys(inboxField, user.get("email"), "email inbox");
			executor.click(checkInbox, "Check inbox button");
		}
		int count = 0;
		while (!executor.isElementExist(plancessMail) && count++ <= 10) {
			driver.navigate().refresh();
		}
		executor.click(plancessMail, "plancess verification mail");

		executor.switchToFrame(renderemail);
		executor.softWaitForWebElement(activationLink);
		String windowHandle = driver.getWindowHandle();
		executor.click(activationLink, "activation link");
		driver.switchTo().defaultContent();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(windowHandle)) {
				driver.switchTo().window(handle);
			}
		}
		executor.softWaitForWebElement(activationMessage);
		executor.verifyTrue(activationMessage.isDisplayed(), "verify activation success message displayed");

	}

}

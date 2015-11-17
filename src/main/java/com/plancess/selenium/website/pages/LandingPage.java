package com.plancess.selenium.website.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;
//import com.plancess.selenium.pages.LoginDialogPage;
//import com.plancess.selenium.pages.LoginPage;

public class LandingPage {
	
	private final WebDriver driver;
	private WebDriverWait wait;
	
	private Executioner executor;
	private String url = "http://dev.plancess.com/";
	
	@FindBy(xpath = "//.[@class='login-btn']")
	WebElement loginLink;
	
	@FindBy(xpath = "//.[@class='login-btn']")
	WebElement Link;
	
	public LandingPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		executor = new Executioner(driver, wait);
		/*executor.navigateToURL(url);
		//System.out.println(getTitle());
		if (!"Plancess JEE Main & Advanced Preparation | IIT JEE Online Coaching | AIPMT Preparation | Test Series".equals(driver.getTitle().trim())) {
			throw new IllegalStateException("This is not  the Plancess Landing page");
		}*/
		PageFactory.initElements(driver, this);

	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public WebElement getLoginLink() {
		wait.until(ExpectedConditions.visibilityOf(loginLink));
		return loginLink;
	}
	
	public LoginDialogPage openLoginDialogPage() {

		executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(loginLink));
		executor.mouseClick(loginLink);
		return new LoginDialogPage(driver, wait);
		//return new LoginDialogPage(driver, wait);

	}
}

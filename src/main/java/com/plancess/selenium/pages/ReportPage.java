package com.plancess.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement plancessHeaderLogo;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement plancessFooterLogo;

	@FindBy(css = "a[ng-click='logoutUser()']")
	WebElement logoutLink;

	@FindBy(xpath = "//a[contains(.,'Profile')]")
	WebElement profileLink;

	@FindBy(css = "a[ui-sref='profile.security']")
	WebElement securityLink;

	@FindBy(css = "a[ui-sref='profile.purchases']")
	WebElement purchasesLink;

	@FindBy(xpath = ".//img[@title='Profile']")
	WebElement toggleDropDown;

	WebElement dashboard;

	@FindBy(xpath = "//div[@class='topic-title']")
	WebElement topicTitle;

	@FindBy(css = ".recomendations")
	WebElement recomendationsSection;

	@FindBy(xpath = "//div[@class='question-wise-performance']")
	WebElement questionsWisePerformance;

	public ReportPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
		// new Executioner(driver).navigateToURL(url);
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Dashboard page");
		}

	}

	// getter and setters

	public LoginPage logoutUser() {

		toggleDropDown.click();
		logoutLink.click();

		return new LoginPage(driver, wait);

	}

	public ProfilePage navigateToUserProfile() {

		toggleDropDown.click();
		profileLink.click();

		return new ProfilePage(driver, wait);

	}

	public SecurityPage navigateToUserSecurity() {
		// actions.click(toggleDropDown).build().perform();
		toggleDropDown.click();
		securityLink.click();

		return new SecurityPage(driver, wait);

	}

	public WebElement getPlancessHeaderLogo() {
		return plancessHeaderLogo;
	}

	public WebElement getPlancessFooterLogo() {
		return plancessFooterLogo;
	}

	public WebElement getLogoutLink() {
		return logoutLink;
	}

	public WebElement getProfileLink() {
		return profileLink;
	}

	public WebElement getSecurityLink() {
		return securityLink;
	}

	public WebElement getPurchasesLink() {
		return purchasesLink;
	}

	public WebElement getToggleDropDown() {
		return toggleDropDown;
	}

	public WebElement getDashboard() {
		return dashboard;
	}

	public WebElement getTopicTitle() {
		return topicTitle;
	}

	public WebElement getRecomendationsSection() {
		return recomendationsSection;
	}

	public WebElement getQuestionsWisePerformance() {
		return questionsWisePerformance;
	}

}

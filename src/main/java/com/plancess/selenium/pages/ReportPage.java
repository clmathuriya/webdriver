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
import com.plancess.selenium.utils.Config;

public class ReportPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private Executioner executor;
	
	public static boolean takeTour;
	private TourPage tourPage;
	
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
	@FindBy(css = "header img[title='Plancess Logo']")
	WebElement dashBoardButton;
	@FindBy(xpath = "//*[@ng-if='isNewUser']")
	WebElement performanceSection;
	@FindBy(xpath = "//div[@class='topic-title']")
	WebElement topicTitle;

	@FindBy(css = ".recomendations")
	WebElement recomendationsSection;
	@FindBy(xpath = "//ul[@class='list-group']/li")
	WebElement notificationItem;

	@FindBy(xpath = "//div[@class='question-wise-performance']")
	WebElement questionsWisePerformance;

	@FindBy(xpath = "//*[@ng-show='bell']")
	WebElement notificationsButton;
	@FindBy(xpath = "//*[.=\"No I'm not interested\"]")
	WebElement notInterestedButton;
	
	

	public ReportPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.executor = new Executioner(driver, wait);
		PageFactory.initElements(driver, this);
		// new Executioner(driver).navigateToURL(url);
		if (!Config.REPORT_TITLE.equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Report page");
		}
		tourPage=new TourPage(driver, wait);
		
		executor.softWaitForWebElement(tourPage.getNotInterestedButton());
		executor.softWaitForWebElement(tourPage.getNotInterestedButton());
		
		if (executor.isElementExist(tourPage.getBeginTour())&&tourPage.getBeginTour().isDisplayed() && takeTour == true) {
			tourPage.completeReportTour();
		}
		if (executor.isElementExist(tourPage.getNotInterestedButton()) && tourPage.getNotInterestedButton().isDisplayed()) {
			executor.click(tourPage.getNotInterestedButton(), "not interested button");
		}

	}

	// getter and setters

	public WebElement getDashBoardButton() {
		return dashBoardButton;
	}

	public WebElement getPerformanceSection() {
		return performanceSection;
	}

	public WebElement getNotificationItem() {
		return notificationItem;
	}

	public WebElement getNotificationsButton() {
		return notificationsButton;
	}

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

	public void verifyReport(Map<String, String> user) {

		executor.softWaitForWebElement(getTopicTitle());
		executor.verifyTrue(getTopicTitle().isDisplayed(), "verify topic title displayed");
		// executor.softWaitForWebElement(getRecomendationsSection());
		// int count = 0;
		// while (!executor.isElementExist(getRecomendationsSection()) &&
		// count++ <= 10) {
		// driver.navigate().refresh();
		// }
		// executor.verifyTrue(getRecomendationsSection().isDisplayed(), "verify
		// recommendations section displayed");
		executor.verifyTrue(getQuestionsWisePerformance().isDisplayed(), "verify questions wise performance displayed");
		// executor.mouseClick(getDashBoardButton());

		executor.refresh();

		executor.softWaitForWebElement(getNotificationsButton());

		// to verify notification displayed for test completion
		executor.click(getNotificationsButton(), "Notifications button ");
		String notificationItemText = getNotificationItem().getAttribute("innerHTML").toLowerCase();
		
		executor.verifyTrue(
				(notificationItemText.contains(user.get("subject")) || notificationItemText.contains("custom test"))
						&& (notificationItemText.contains("completed") || notificationItemText.contains("available")),

		"verify notification item contains test completed for subject" + user.get("subject"));
	}

}

package com.plancess.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;
import com.plancess.selenium.utils.Config;

public class Dashboard {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private String performanceSummarySectionXpath = "//*[@ng-if='isNewUser']";
	private Executioner executor;

	@FindBy(css = "header img[title='Preplane Logo']")
	WebElement preplaneHeaderLogo;

	@FindBy(css = "header img[src='images/logo-icon.png']")
	WebElement preplaneHeaderLogoIcon;

	@FindBy(css = "footer img[title='Plancess Logo']")
	WebElement preplaneFooterLogo;

	@FindBy(css = "a[ng-click='logoutUser()']")
	WebElement logoutLink;

	// @FindBy(xpath = "//a[contains(.,'Profile')]")
	@FindBy(css = "a[ui-sref='profile.main']")
	WebElement profileLink;

	@FindBy(css = "a[ui-sref='profile.security']")
	WebElement securityLink;

	@FindBy(css = "a[ui-sref='profile.purchases']")
	WebElement purchasesLink;

	@FindBy(css = ".start-assessment-section")
	WebElement startAssessmentSection;

	@FindBy(xpath = "//img[@title='Profile']")
	WebElement toggleDropDown;

	WebElement dashboard;

	@FindBy(xpath = "//div[@id='PHYSICS']/a")
	WebElement physicsSubjectSection;

	@FindBy(xpath = "//div[@id='CHEMISTRY']/a")
	WebElement chemistrySubjectSection;

	@FindBy(xpath = "//div[@id='MATHEMATICS']/a")
	WebElement mathsSubjectSection;

	@FindBy(xpath = "//a[normalize-space(.)='Take Subject Test']")
	WebElement takeSubjectTest;

	@FindBy(xpath = ".//button[.='Start Test']")
	WebElement startTest;

	@FindBy(xpath = "//*[@ng-click='cancel()']")
	WebElement cancelButton;

	@FindBy(xpath = "//*[contains(text(),'TIME REQUIRED:')]/span")
	WebElement timeRequired;

	@FindBy(xpath = "//*[contains(text(),'REMAINING TIME:')]/span")
	WebElement remainingTime;

	@FindBy(xpath = "//h3[.='Total Questions']/following-sibling::p")
	WebElement totalQuestions;

	@FindBy(xpath = ".//button[.='Resume Test']")
	WebElement resumeTest;

	// @FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='Option A']")
	// })
	@FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='A']") })
	List<WebElement> answerChoicesA;

	@FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='B']") })
	// @FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='Option B']")
	// })
	List<WebElement> answerChoicesB;

	@FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='C']") })
	// @FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='Option C']")
	// })
	List<WebElement> answerChoicesC;

	@FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='D']") })
	// @FindBys(value = { @FindBy(xpath = "//*[normalize-space(.)='Option D']")
	// })
	List<WebElement> answerChoicesD;

	@FindBy(xpath = "//*[@ng-click='nextQues(true)']")
	WebElement nextButton;

	@FindBy(xpath = "//*[@ng-click='nextQues(false)']")
	WebElement previousButton;

	@FindBy(xpath = "//button[@ng-click='pauseTest(true)']")
	WebElement pauseTestButton;

	@FindBy(xpath = "//button[@ng-click='pauseTest(false)']")
	WebElement submitTestButton;

	@FindBy(xpath = "//button[@ng-click='markForReview()']")
	WebElement markForReview;

	@FindBy(xpath = "//button[@ng-click='submit()']")
	WebElement confirmSubmitTestButton;

	@FindBy(css = "div.toast-title")
	WebElement toastTitle;

	@FindBy(xpath = "//*[@ng-if='noTopic']")
	WebElement noTopicMsg;

	@FindBy(xpath = "//*[@ng-click='closeModal()']")
	WebElement closeModel;

	@FindBy(css = "div.toast-message")
	WebElement toastMessage;

	@FindBy(xpath = "//*[@name='lightbulb-o']")
	WebElement hintButton;

	@FindBy(xpath = "//*[@ng-if='showHint']")
	WebElement hintText;

	@FindBy(css = "header img[title='Preplane Logo']")
	WebElement dashBoardButton;

	@FindBy(xpath = "(//*[@ui-sref='usertest.customize-test'])[2]")
	WebElement createAccessmentButton;

	@FindBy(xpath = "//*[@ng-if='!isNewUser']")
	WebElement performanceSection;

	@FindBy(xpath = "//*[@ng-show='bell']")
	WebElement notificationsButton;

	@FindBy(xpath = "//ul[@class='list-group']/li")
	WebElement notificationItem;

	@FindBy(xpath = ".//*[@data-toggle='dropdown']/span[contains(.,'Hi')]")
	WebElement welcomeMessage;

	@FindBys(value = { @FindBy(xpath = "//button[@ng-click='takeTest(recommentation)']") })
	List<WebElement> acceptChallenges;

	@FindBy(xpath = ".//*[.='Create Your Own Test']")
	WebElement createCustomTestButton;

	@FindBy(xpath = "//*[.='UPCOMING MOCK TESTS']")
	WebElement upcomingTests;

	@FindBy(xpath = "//*[.=\"No I'm not interested\"]")
	WebElement notInterestedButton;

	@FindBys(value = @FindBy(xpath = "//a[.='Begin Tour']") )
	List<WebElement> beginTour;

	@FindBys(value = @FindBy(xpath = "//a[.='Got it!']") )
	List<WebElement> gotIt;

	@FindBys(value = @FindBy(xpath = "//a[.='Start Test' and @class='shepherd-button ']") )
	List<WebElement> startTourTest;

	@FindBys(value = @FindBy(xpath = "//a[.='Take Test' and @class='shepherd-button ']") )
	List<WebElement> tourTestBtn;

	public Dashboard(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);
		PageFactory.initElements(driver, this);
		executor.softWaitForCondition(ExpectedConditions.titleIs(Config.DASHBOARD_TITLE));
		if (!Config.DASHBOARD_TITLE.equals(driver.getTitle().trim())) {
			throw new IllegalStateException("This is not  the Plancess Dashboard page");
		}
		driver.manage().window().maximize();
		executor.softWaitForWebElement(notInterestedButton);

		for (WebElement tourBtn : beginTour) {
			if (tourBtn.isDisplayed()) {
				completeTour();
			}
		}
		if (executor.isElementExist(notInterestedButton) && notInterestedButton.isDisplayed()) {
			executor.click(notInterestedButton, "Not interested button");
		}

	}

	// getter and setters

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getPlancessHeaderLogo() {
		return preplaneHeaderLogo;
	}

	public WebElement getPlancessFooterLogo() {
		return preplaneFooterLogo;
	}

	public WebElement getToggleDropDown() {
		return toggleDropDown;
	}

	public WebElement getLogoutLink() {
		return logoutLink;
	}

	public WebElement getStartAssessmentSection() {

		return startAssessmentSection;
	}

	public WebElement getProfileLink() {
		return profileLink;
	}

	public WebElement getPurchasesLink() {
		return purchasesLink;
	}

	public WebElement getSecurityLink() {
		return securityLink;
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public boolean isHeaderLogoVisible() {
		return preplaneHeaderLogo.isDisplayed();
	}

	public boolean isFooterLogoVisible() {
		return preplaneFooterLogo.isDisplayed();
	}

	public boolean isToggleDropDownVisible() {
		return toggleDropDown.isDisplayed();
	}

	public void clickToggelDropDown() {
		toggleDropDown.click();
	}

	public WebElement getDashboard() {
		return dashboard;
	}

	public WebElement getDashBoardButton() {
		return dashBoardButton;
	}

	public WebElement getCreateAssessmentButton() {
		return createAccessmentButton;
	}

	public List<WebElement> getAcceptChallenges() {
		return acceptChallenges;
	}

	public WebElement getPerformanceSection() {
		return performanceSection;
	}

	public WebElement getPhysicsSubjectSection() {
		return physicsSubjectSection;
	}

	public WebElement getChemistrySubjectSection() {
		return chemistrySubjectSection;
	}

	public WebElement getMathsSubjectSection() {
		return mathsSubjectSection;
	}

	public WebElement getTakeSubjectTest() {
		return takeSubjectTest;
	}

	public WebElement getStartTest() {
		return startTest;
	}

	public WebElement getUpcomingTests() {
		return upcomingTests;
	}

	public WebElement getCancelButton() {
		return cancelButton;
	}

	public WebElement getTimeRequired() {
		return timeRequired;
	}

	public WebElement getTotalQuestions() {
		return totalQuestions;
	}

	public WebElement getResumeTest() {
		return resumeTest;
	}

	public WebElement getRemainingTime() {
		return remainingTime;
	}

	public List<WebElement> getAnswerChoicesA() {
		return answerChoicesA;
	}

	public List<WebElement> getAnswerChoicesB() {
		return answerChoicesB;
	}

	public List<WebElement> getAnswerChoicesC() {
		return answerChoicesC;
	}

	public List<WebElement> getAnswerChoicesD() {
		return answerChoicesD;
	}

	public WebElement getNextButton() {
		return nextButton;
	}

	public WebElement getPreviousButton() {
		return previousButton;
	}

	public WebElement getPauseTestButton() {
		return pauseTestButton;
	}

	public WebElement getSubmitTestButton() {
		return submitTestButton;
	}

	public WebElement getMarkForReview() {
		return markForReview;
	}

	public WebElement getConfirmSubmitTestButton() {
		return confirmSubmitTestButton;
	}

	public WebElement getToastTitle() {
		return toastTitle;
	}

	public WebElement getToastMessage() {
		return toastMessage;
	}

	public WebElement getHintButton() {
		return hintButton;
	}

	public WebElement getHintText() {
		return hintText;
	}

	public WebElement getNotificationsButton() {
		return notificationsButton;
	}

	public WebElement getNotificationItem() {
		return notificationItem;
	}

	public WebElement getWelcomeMessage() {
		return welcomeMessage;
	}

	public WebElement getCreateCustomTestButton() {
		return createCustomTestButton;
	}

	public String getPerformanceSummarySectionXpath() {
		return performanceSummarySectionXpath;
	}

	public WebElement getNoTopicMsg() {
		return noTopicMsg;
	}

	public WebElement getPreplaneHeaderLogoIcon() {
		return preplaneHeaderLogoIcon;
	}

	public WebElement getNotInterestedButton() {
		return notInterestedButton;
	}

	public WebElement getCloseModel() {
		return closeModel;
	}

	public LandingPage logoutUser() {

		executor.softWaitForWebElement(toggleDropDown);
		executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(toggleDropDown));
		executor.mouseClick(toggleDropDown);
		executor.softWaitForWebElement(logoutLink);
		executor.click(logoutLink, "logout link");

		return new LandingPage(driver, wait);

	}

	public ProfilePage navigateToUserProfile() {
		executor.softWaitForWebElement(toggleDropDown);
		executor.click(toggleDropDown, "toggleDropDown");

		executor.softWaitForWebElement(profileLink);
		executor.click(profileLink, "profileLink");

		return new ProfilePage(driver, wait);

	}

	public SecurityPage navigateToUserSecurity() {
		// actions.click(toggleDropDown).build().perform();
		executor.click(toggleDropDown, "toggle drop down");

		executor.click(securityLink, "security tab link");

		return new SecurityPage(driver, wait);

	}

	public CreateAccessment navigateToCreateAccessment() {
		executor.softWaitForWebElement(ExpectedConditions.elementToBeClickable(createAccessmentButton));

		if (!driver.getTitle().trim().equals(Config.CUSTOM_TEST_TITLE)) {
			executor.click(createAccessmentButton, "Create assessment button");
			executor.softWaitForCondition(ExpectedConditions.titleIs(Config.CUSTOM_TEST_TITLE),
					"wait for custom test page loading");
		}

		return new CreateAccessment(driver, wait);
	}

	private Dashboard completeTour() {

		for (WebElement tourBtn : beginTour) {
			if (tourBtn.isDisplayed()) {
				executor.click(tourBtn, "Begin Tour Button");

			}
		}
		for (int i = 0; i <= 5; i++) {
			for (WebElement gotItBtn : gotIt) {
				if (gotItBtn.isDisplayed()) {
					executor.click(gotItBtn, "Got It Tour Button");
				}
			}
		}

		for (WebElement testBtn : tourTestBtn) {
			if (testBtn.isDisplayed()) {
				executor.click(testBtn, "Take test Tour Button");
			}
		}

		for (WebElement startTestBtn : startTourTest) {
			if (startTestBtn.isDisplayed()) {
				executor.click(startTestBtn, "start test Tour Button");
			}
		}

		return this;
	}

}

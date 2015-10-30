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

public class CreateAccessment {

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private Executioner executor;

	WebElement CreateAccessment;

	@FindBy(xpath = "(//a[@ui-sref='app.dashboard'])[4]")
	WebElement createAccessmentButton;

	@FindBy(xpath = "(//a[@ui-sref='usertest.customize-test'])[2]")
	WebElement customTestLink;

	@FindBy(xpath = "(//a[@ui-sref='usertest.schedule-test.upcoming-test'])[3]")
	WebElement mockTestLink;

	@FindBy(partialLinkText = "Physics")
	WebElement subjectPhysicsLink;

	@FindBy(linkText = "Chemistry")
	WebElement subjectChemistryLink;

	@FindBy(linkText = "Mathematics")
	WebElement subjectMathematicsLink;

	@FindBy(xpath = "(//a[@ui-sref='usertest.schedule-test.upcoming-test'])[3]")
	WebElement moduleLink;

	@FindBy(css = "div.toast-title")
	WebElement toastTitle;

	@FindBy(xpath = "//*[normalize-space(.)='Mechanics']/../a")
	WebElement moduleToggel;

	@FindBy(xpath = "//*[@ng-model='examOptions.time']")
	WebElement examDurationDropDown;

	@FindBy(xpath = "//*[@ng-click='createTest()']")
	WebElement createTest;

	@FindBys(value = { @FindBy(xpath = ".//*[@ng-if='subject.selected' and @class='angular-ui-tree-node']") })
	List<WebElement> topicsSelected;
	@FindBy(xpath = "//a[.=\"I'm not interested\"]")
	WebElement notInterestedButton;

	public CreateAccessment(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);

		PageFactory.initElements(driver, this);
		executor.softWaitForCondition(ExpectedConditions.titleIs(Config.CUSTOM_TEST_TITLE));
		if (!Config.CUSTOM_TEST_TITLE.equals(driver.getTitle().trim())) {
			throw new IllegalStateException("This is not  the Custom Test page");
		}
		executor.softWaitForWebElement(notInterestedButton);
		if (executor.isElementExist(notInterestedButton) && notInterestedButton.isDisplayed()) {
			executor.click(notInterestedButton, "not interested button");
		}

	}

	public WebElement getCustomTestLink() {
		return customTestLink;
	}

	public WebElement getMockTestLink() {
		return mockTestLink;
	}

	public WebElement getToastTitle() {
		return toastTitle;
	}

	public WebElement getsubjectPhysicsLink() {
		return subjectPhysicsLink;
	}

	public WebElement getsubjectChemistryLink() {
		return subjectChemistryLink;
	}

	public WebElement getsubjectMathematicsLink() {
		return subjectMathematicsLink;
	}

	public WebElement getCreateAccessment() {
		return CreateAccessment;
	}

	public WebElement getExamDurationDropDown() {
		return examDurationDropDown;
	}

	public WebElement getCreateTest() {
		return createTest;
	}

	public List<WebElement> getTopicsSelected() {
		return topicsSelected;
	}

	public Actions getActions() {
		return actions;
	}

}

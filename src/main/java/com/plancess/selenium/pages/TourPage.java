package com.plancess.selenium.pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.plancess.selenium.executor.Executioner;

public class TourPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;

	@FindBy(xpath = "//*[.=\"No I'm not interested\"]")
	WebElement notInterestedButton;

	@FindBy(xpath = "//*[.='Begin Tour']")
	WebElement beginTour;

	@FindBy(xpath = "//*[.='Got it!']")
	WebElement gotIt;

	@FindBy(xpath = "//*[@class='popover-navigation']//button[.='Next']")
	WebElement next;

	@FindBy(xpath = "//*[.='Back']")
	WebElement back;

	@FindBy(xpath = "//*[.='Prev']")
	WebElement prev;

	@FindBy(xpath = "//*[.='End Tour']")
	WebElement endTour;

	@FindBys(value = @FindBy(xpath = "//a[.='Start Test' and @class='shepherd-button ']") )
	List<WebElement> startTourTest;

	@FindBys(value = @FindBy(xpath = "//a[.='Take Test' and @class='shepherd-button ']") )
	List<WebElement> tourTestBtn;

	@FindBy(xpath = "//*[.='Skip Tour']")
	WebElement skipTour;

	@FindBy(xpath = "(.//span[@ng-click='getChild(module)'])[1]")
	WebElement selectModule;

	@FindBy(xpath = "(.//*[@class='button-reset answer-button'])[1]")
	WebElement assessmentAnswer;

	@FindBy(xpath = ".//button[@ng-click='markreview(question)']")
	WebElement markForReview;

	public TourPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);
		PageFactory.initElements(driver, this);

	}

	public WebElement getBack() {
		return back;
	}

	public WebElement getBeginTour() {
		return beginTour;
	}

	public WebElement getEndTour() {
		return endTour;
	}

	public WebElement getGotIt() {
		return gotIt;
	}

	public WebElement getNotInterestedButton() {
		return notInterestedButton;
	}

	public WebElement getSkipTour() {
		return skipTour;
	}

	public List<WebElement> getStartTourTest() {
		return startTourTest;
	}

	public WebElement getModule() {
		return selectModule;
	}

	public List<WebElement> getTourTestBtn() {
		return tourTestBtn;
	}

	public WebElement getAssessmentAnswer() {
		return assessmentAnswer;
	}

	public WebElement getNext() {
		return next;
	}

	public WebElement getPrev() {
		return prev;
	}

	public WebElement getMarkForReview() {
		return markForReview;
	}

	public void completeDashboardTour() {

		if (getBeginTour().isDisplayed()) {
			executor.click(getBeginTour(), "Begin Tour Button");

		}
		executor.softWaitForWebElement(getGotIt());
		String[] tourMessage = { "Recommendations (Step 1 out of 5 )", "Upcoming Mock Test (Step 2 out of 5 )",
				"Modules (Step 3 out of 5 )", "Chapters (Step 4 out of 5 )", "End Tour (Step 5 out of 5 )" };

		for (int i = 1; i <= 4; i++) {
			WebElement stepElement = executor.softWaitForWebElement(
					ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='step-" + i + "']")));

			executor.verifyTrue(stepElement.isDisplayed(), "Verify Step " + i + " of Dashboard Tour");
			// System.out.println(stepElement.getText());
			// System.out.println(tourMessage[i - 1]);

			executor.verifyEquals(stepElement.getText().contains(tourMessage[i - 1]), true, "Verify Tour Steps");
			
			if (executor.isElementExist(getGotIt()) && getGotIt().isDisplayed()) {
			
				executor.click(getGotIt(), "Got It Tour Button");

				executor.softWaitForWebElement(getBack());
			} else {
				executor.softWaitForWebElement(getModule());
				executor.click(getModule(), "Take test Tour Button");

				executor.softWaitForWebElement(getEndTour());
				stepElement = executor.softWaitForWebElement(
						ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='step-" + (i+1) + "']")));
				executor.verifyEquals(stepElement.getText().contains(tourMessage[i]), true, "Verify Tour Steps");

				if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
					executor.click(getEndTour(), "End Tour Button");

				}
			}
		}
		if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
			executor.click(getEndTour(), "End Tour Button");

		}
		

	}

	public void completeAssessmentTour() {

		if (getBeginTour().isDisplayed()) {
			executor.click(beginTour, "Begin Tour Button");

		}
		System.out.println();
		executor.softWaitForWebElement(getSkipTour());
		String[] tourMessage = { "Question & Answers (Step 1 out of 7 )", "Question State (Step 2 out of 7 )",
				"Review (Step 3 out of 7 )", "Filter Questions (Step 4 out of 7 )",
				"Remaining Questions & Timer (Step 5 out of 7 )", "Pause/Submit Test (Step 6 out of 7 )",
				"End Tour (Step 7 out of 7 )" };

		for (int i = 1; i <= 7; i++) {
			WebElement stepElement = executor.softWaitForWebElement(
					ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='step-" + i + "']")));
			executor.verifyTrue(stepElement.isDisplayed(), "Verify Step " + i + " of Dashboard Tour");

			executor.verifyEquals(stepElement.getText().contains(tourMessage[i - 1]), true, "Verify Tour Steps");

			if (executor.isElementExist(getNext()) && getNext().isDisplayed()) {

				// System.out.println(stepElement.getText());
				// System.out.println(tourMessage[i - 1]);

				executor.click(getNext(), "Next Tour Button");
				if(executor.isElementExist(getPrev())){
					executor.softWaitForWebElement(prev);
				}else if(executor.isElementExist(getBack())){
					executor.softWaitForWebElement(getBack());
				}
			} else if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
				executor.click(getEndTour(), "End Tour Button");

			}else if (executor.isElementExist(getAssessmentAnswer()) && getAssessmentAnswer().isDisplayed()&&(i==1)) {

				// executor.softWaitForWebElement(getAssessmentAnswer());
				executor.click(getAssessmentAnswer(), "Accessment Answer ");

				if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
					executor.click(getEndTour(), "End Tour Button");

				} else
					executor.softWaitForWebElement(getSkipTour());

			} else if (executor.isElementExist(getMarkForReview()) && getMarkForReview().isDisplayed()) {
				executor.click(getMarkForReview(), "Accessment Answer ");

				if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
					executor.click(getEndTour(), "End Tour Button");

				} else
					executor.softWaitForWebElement(getSkipTour());
			}
		}

		if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
			executor.click(getEndTour(), "End Tour Button");

		}

		
	}

	public void completeReportTour() {

		if (getBeginTour().isDisplayed()) {
			executor.click(beginTour, "Begin Tour Button");

		}
		executor.softWaitForWebElement(getSkipTour());
		String[] tourMessage = { "Score Card (Step 1 out of 7 )", "Recommendation (Step 2 out of 7 )",
				"Question-wise Analysis (Step 3 out of 7 )", "Level (Step 4 out of 7 )",
				"View Solution (Step 5 out of 7 )","Review (Step 6 out of 7 )","Mark Error (Step 7 out of 7 )" };

		for (int i = 1; i <= 7; i++) {
			WebElement stepElement = executor.softWaitForWebElement(
					ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='step-" + i + "']")));
			executor.verifyTrue(stepElement.isDisplayed(), "Verify Step " + i + " of Dashboard Tour");
			// System.out.println(stepElement.getText());
			// System.out.println(tourMessage[i - 1]);
			executor.verifyEquals(stepElement.getText().contains(tourMessage[i - 1]), true, "Verify Tour Steps");

			if (executor.isElementExist(getNext()) && getNext().isDisplayed()) {

				// System.out.println(stepElement.getText());
				// System.out.println(tourMessage[i - 1]);

				executor.click(getNext(), "Next Tour Button");

				if(executor.isElementExist(getPrev())){
					executor.softWaitForWebElement(getPrev());
				}else if(executor.isElementExist(getBack())){
					executor.softWaitForWebElement(getBack());
				}
			}  else if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
				executor.click(getEndTour(), "End Tour Button");

			}
		}

		if (executor.isElementExist(getEndTour()) && getEndTour().isDisplayed()) {
			executor.click(getEndTour(), "End Tour Button");

		}

		
	}

}

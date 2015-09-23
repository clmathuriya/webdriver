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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.plancess.selenium.executor.Executioner;

public class CreateAccessment {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

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

	public CreateAccessment(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		
		PageFactory.initElements(driver, this);
		wait.until(ExpectedConditions.titleIs("Plancess Dashboard"));
		if (!"Plancess Dashboard".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not  the Plancess Dashboard page");
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

	
}

package com.plancess.selenium.lms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

	private WebDriver driver;

	private WebDriverWait wait;

	private Actions actions;

	@FindBy(partialLinkText = "Campaigns")
	WebElement campaignsLink;

	@FindBy(partialLinkText = "Add Campaign")
	WebElement addCampaignLink;

	public DashboardPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		if (!driver.getTitle().contains("Plancess - CRM")) {
			throw new IllegalStateException("This is not  a Dashboard page");
		}
		PageFactory.initElements(driver, this);

	}

	public WebElement getCampaignsLink() {
		return campaignsLink;
	}

	public WebElement getAddCampaignLink() {
		return addCampaignLink;
	}

	public AddCampaignPage openAddCampaignPage() {

		campaignsLink.click();
		addCampaignLink.click();

		return new AddCampaignPage(driver, wait);
	}

}

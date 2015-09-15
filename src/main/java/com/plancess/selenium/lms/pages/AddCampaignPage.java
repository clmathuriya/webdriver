package com.plancess.selenium.lms.pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddCampaignPage {

	private WebDriver driver;

	private WebDriverWait wait;

	private Actions actions;

	@FindBy(partialLinkText = "Campaigns")
	WebElement campaignsLink;

	@FindBy(partialLinkText = "Add Campaign")
	WebElement addCampaignLink;

	WebElement cname;

	WebElement campaign_type;

	@FindBy(xpath = "//label[normalize-space(.)='Facebook']/input[@id='channel_id']")
	WebElement channelCheckBox;

	@FindBy(xpath = "//label[normalize-space(.)='Plancess']/input[@id='vendor_id']")
	WebElement vendorCheckBox;

	WebElement exp_leads;

	WebElement total_budget;
	@FindBy(xpath = "//input[@name='sd']")
	WebElement startDate;

	@FindBy(xpath = "//input[@name='ed']")
	WebElement endDate;

	WebElement welcome_email;

	WebElement welcome_sms;

	@FindBy(xpath = "//input[@id='target_audience']/following-sibling::div/input")
	WebElement target_audience;

	WebElement allocate_demo;

	WebElement cdescription;

	@FindBys(value = { @FindBy(xpath = ".//button[.='Save & Next']") })
	List<WebElement> saveButtons;

	@FindBy(xpath = "//label[normalize-space(.)='Test Html template']/input[@name='creative']")
	WebElement creativeTemplateRadio;

	@FindBy(xpath = "//iframe[@title='Rich Text Editor, mailerHTML']")
	WebElement mailerContentFrame;

	@FindBy(xpath = "//iframe[@title='Rich Text Editor, thankyouHTML']")
	WebElement thankYouContentFrame;

	WebElement landingPageURL;

	WebElement thankyouPageURL;

	WebElement saveandclose;

	public AddCampaignPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);

		if (!cname.isDisplayed()) {
			throw new IllegalStateException("This is not  a add campaign page");
		}

	}

	public WebElement getCampaignsLink() {
		return campaignsLink;
	}

	public WebElement getAddCampaignLink() {
		return addCampaignLink;
	}

	public WebElement getCname() {
		return cname;
	}

	public WebElement getCampaign_type() {
		return campaign_type;
	}

	public WebElement getChannelCheckBox() {
		return channelCheckBox;
	}

	public WebElement getVendorCheckBox() {
		return vendorCheckBox;
	}

	public WebElement getExp_leads() {
		return exp_leads;
	}

	public WebElement getTotal_budget() {
		return total_budget;
	}

	public WebElement getStartDate() {
		return startDate;
	}

	public WebElement getEndDate() {
		return endDate;
	}

	public WebElement getWelcome_email() {
		return welcome_email;
	}

	public WebElement getWelcome_sms() {
		return welcome_sms;
	}

	public WebElement getTarget_audience() {
		return target_audience;
	}

	public WebElement getAllocate_demo() {
		return allocate_demo;
	}

	public WebElement getCdescription() {
		return cdescription;
	}

	public List<WebElement> getSaveButton() {
		return saveButtons;
	}

	public WebElement getCreativeTemplateRadio() {
		return creativeTemplateRadio;
	}

	public WebElement getMailerContentFrame() {
		return mailerContentFrame;
	}

	public WebElement getThankYouContentFrame() {
		return thankYouContentFrame;
	}

	public WebElement getLandingPageURL() {
		return landingPageURL;
	}

	public WebElement getThankyouPageURL() {
		return thankyouPageURL;
	}

	public WebElement getSaveandclose() {
		return saveandclose;
	}

	public String addCampaign(Map<String, String> user) {

		cname.sendKeys(user.get("campaignName"));

		new Select(campaign_type).selectByVisibleText(user.get("campaignType"));

		// to select channel checkboxes
		for (String channel : user.get("channels").split(",")) {

			driver.findElement(By.xpath("//label[normalize-space(.)='" + channel + "']/input[@id='channel_id']"))
					.click();
		}

		// to select vendors

		for (String vendor : user.get("vendors").split(",")) {

			driver.findElement(By.xpath("//label[normalize-space(.)='" + vendor + "']/input[@id='vendor_id']")).click();
		}

		exp_leads.sendKeys(user.get("expectedLeads"));

		total_budget.sendKeys(user.get("totalBudget"));

		startDate.sendKeys(user.get("startDate"));

		endDate.sendKeys(user.get("endDate"));

		new Select(welcome_email).selectByVisibleText(user.get("welcomeEmail"));

		new Select(welcome_sms).selectByVisibleText(user.get("welcomeSMS"));

		target_audience.sendKeys(user.get("targetAudiences"));

		new Select(allocate_demo).selectByVisibleText(user.get("allocate_demo"));

		cdescription.sendKeys(user.get("campaignDescription"));
		for (WebElement e : saveButtons) {
			if (e.isDisplayed()) {
				e.click();
			}
		}

		creativeTemplateRadio.click();

		if (isAlertPresent()) {
			System.out.println(closeAlertAndGetItsText());
		}
		for (WebElement e : saveButtons) {
			if (e.isDisplayed()) {
				e.click();

			}
		}
		// driver.switchTo().frame(mailerContentFrame);
		//
		// driver.findElement(By.tagName("body")).click();
		// driver.findElement(By.tagName("body")).sendKeys(user.get("mailerContent"));
		//
		// driver.switchTo().defaultContent();
		for (WebElement e : saveButtons) {
			if (e.isDisplayed()) {
				e.click();

			}
		}

		// for thank you page content

		// driver.switchTo().frame(thankYouContentFrame);
		// driver.findElement(By.tagName("body")).click();
		//
		// driver.findElement(By.tagName("body")).sendKeys(user.get("mailerContent"));
		//
		// driver.switchTo().defaultContent();
		for (WebElement e : saveButtons) {
			if (e.isDisplayed()) {
				e.click();
			}
		}

		String campaignId = driver.getCurrentUrl().split("/")[3];

		if (saveandclose.isDisplayed()) {
			saveandclose.click();
		}

		return campaignId;

	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();

			alert.accept();

			return alertText;
		} finally {

		}
	}

}
package com.plancess.selenium.lms.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeadDetailsPage {

	private WebDriver driver;

	private WebDriverWait wait;

	private Actions actions;

	@FindBy(xpath = "//dt[.='Name:']/following-sibling::dd[1]")
	WebElement name;

	@FindBy(xpath = "//dt[.='Phone:']/following-sibling::dd[1]")
	WebElement phone;

	@FindBy(xpath = "//dt[.='Email:']/following-sibling::dd[1]")
	WebElement email;

	@FindBy(xpath = "//dt[.='City:']/following-sibling::dd[1]")
	WebElement city;

	@FindBy(xpath = "//dt[.='JEE Year:']/following-sibling::dd[1]")
	WebElement target_year;

	@FindBy(xpath = "//a[@title='view preview page']")
	WebElement campaignLink;

	@FindBy(xpath = "//a[@title='view preview page']/../following-sibling::td[contains(text(),'R Lead')]")
	WebElement leadType;

	public LeadDetailsPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		if (!driver.getCurrentUrl().contains("lead/view")) {
			throw new IllegalStateException("This is not  a lead view page");
		}
		PageFactory.initElements(driver, this);

	}

	public WebElement getName() {
		return name;
	}

	public WebElement getPhone() {
		return phone;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getCity() {
		return city;
	}

	public WebElement getTarget_year() {
		return target_year;
	}

	public WebElement getCampaignLink() {
		return campaignLink;
	}

	public WebElement getLeadType() {
		return leadType;
	}

	public void verifyLeadDetails(Map<String, String> user) {

	}

}

package com.plancess.selenium.pages;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class PaymentPage {
	private final WebDriver driver;
	private WebDriverWait wait;
	private Executioner executor;
	private Actions actions;
	
	@FindBy(css = "#email")
	WebElement email;
	@FindBy(css = "#contact")
	WebElement contact;
	
	@FindBy(xpath = "//*[.='Packages']")
	WebElement packages;

	@FindBy(xpath = ".//*[@id='package-free']")
	WebElement packagefree;

	@FindBy(xpath = ".//*[@id='package-1']")
	WebElement package1;

	@FindBy(xpath = ".//*[@id='package-2']")
	WebElement package2;

	@FindBy(css = "#modal-content")
	WebElement paymentModal;
	
	@FindBy(css = "#header")
	WebElement paymentHeader;
	
	@FindBy(css = "#submitbtn")
	WebElement submitbtn;
	
	@FindBy(css = "#method-netbanking-tab")
	WebElement netBankingTab;
	
	@FindBy(css = "#bank-select")
	WebElement bank;

	public PaymentPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);
		PageFactory.initElements(driver, this);

	}
	
	public WebElement getPaymentModal() {
		return paymentModal;
	}
	
	public WebElement getPaymentHeader() {
		return paymentHeader;
	}
	
	public WebElement getSubmitbtn() {
		return submitbtn;
	}

	public WebElement getPackages() {
		return packages;
	}
	
	public WebElement getEmail() {
		return email;
	}
	
	public WebElement getContact() {
		return contact;
	}
	
	public WebElement getBank() {
		return bank;
	}

	public void selectPackage(String string) {
		switch (string) {
		case "free":
			executor.click(packagefree.findElement(By.tagName("a")), "Package free");		
			break;
		case "1":
			executor.click(package1.findElement(By.tagName("a")), "Package 1");
			break;
		case "2":
			executor.click(package2.findElement(By.tagName("a")), "Package 2");			
			break;
		default:
			break;
		}
		
	}

	public void makePayment(Map<String, String> user){
		System.out.println();
		executor.softWaitForWebElement(getContact());
		List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
	    for (WebElement iframe : iframes) {
	    	executor.softWaitForWebElement(iframe.findElement(By.cssSelector("#modal-content")));
	    }
		WebElement t=driver.findElement(By.cssSelector("#modal-content"));
		executor.switchToFrame(paymentModal);
		executor.verifyTrue(executor.isElementExist(paymentModal),
		"Verify if Package Exists: "+user.get("packageName"));
		executor.softWaitForWebElement(getPaymentModal());
		//executor.softWaitForWebElement(driver.findElement(By.cssSelector(selector)id("modal-content")));
		/*List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
	    for (WebElement iframe : iframes) {
	    	executor.softWaitForWebElement(iframe.findElement(By.id("modal-content")));
	    	
	    	executor.assertEquals(iframe.findElement(By.xpath(".//*[@id='header']")).getText().contains(user.get("packageName"))
					&& iframe.findElement(By.xpath(".//*[@id='header']")).getText().contains("Preplane"), 
					true, "Verify if Payment modal has correct product name");
	    	
	    }*/
		//WebElement t=driver.findElement(By.tagName("iframe"));
		//	driver.findElement(By.cssSelector(""))
		
		executor.assertEquals(getPaymentHeader().getText().contains(user.get("packageName"))
				&&getPaymentHeader().getText().contains("Preplane"), 
				true, "Verify if Payment modal has correct product name");
		executor.assertEquals(getSubmitbtn().getText().contains(user.get("price")), 
				true, "Verify if Payment modal has correct product name");
		
		executor.clear(getEmail(), "Email Clear");
		executor.sendKeys(getEmail(), user.get("email"), "Email");
		executor.clear(getContact(), "Contact Clear");
		executor.sendKeys(getContact(), user.get("contact"), "Contact");
		
		executor.click(netBankingTab, "NetBanking-Tab");
		//executor.sendKeys(getBank(), user.get("bank"),"Bank");
		
		executor.click(getSubmitbtn(), "Submit", true);
		
	}


	
}

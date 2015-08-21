package com.plancess.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
	private final WebDriver driver;
	WebElement q;
	@FindBy(css = "button[name='btnG']")
	WebElement searchButton;

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		if (!"Google".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not a Search page");
		}
		PageFactory.initElements(driver, this);

	}

	public SearchResultPage searchText(String text) {
		q.sendKeys(text);
		q.submit();
		searchButton.click();

		return new SearchResultPage();

	}
}

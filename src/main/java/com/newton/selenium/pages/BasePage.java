package com.newton.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newton.selenium.executor.Executioner;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Executioner executor;
	protected Actions actions;

	public BasePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		executor = new Executioner(driver, wait);

	}
	public BasePage() {
		
	}
	

	public String getTitle() {
		return driver.getTitle();
	}

}

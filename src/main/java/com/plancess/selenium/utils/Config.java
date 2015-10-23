package com.plancess.selenium.utils;

public class Config {

	private Config instance = null;
	
	public String pageTitle = "Preplane Dashboard";

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}
	

}

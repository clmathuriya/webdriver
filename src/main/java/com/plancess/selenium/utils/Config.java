package com.plancess.selenium.utils;

public class Config {

	private Config instance = null;

	public static String DASHBOARD_TITLE = "Dashboard - Preplane";
	public static String CUSTOM_TEST_TITLE = "Custom Test - Preplane";

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}

}

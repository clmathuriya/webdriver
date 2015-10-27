package com.plancess.selenium.utils;

public class Config {

	private Config instance = null;

	public static String DASHBOARD_TITLE = "Dashboard - Preplane";
	public static String CUSTOM_TEST_TITLE = "Custom Test - Preplane";
	public static String ASSESSMENT_TITLE = "Assessment - Preplane";
	public static String PROFILE_TITLE = "Profile - Preplane";
	public static String REPORT_TITLE = "Report - Preplane";
	public static String LANDING_PAGE_TITLE = "Preplane";

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}

}

package com.plancess.selenium.utils;

public class Config {

	private Config instance = null;

	public static String URL = "http://dev.preplane.com/";
	
	public static String URL_WEBSITE = "http://dev.plancess.com/";

	public static String DASHBOARD_TITLE = "Dashboard - Preplane";
	public static String CUSTOM_TEST_TITLE = "Custom Test - Preplane";
	public static String ASSESSMENT_TITLE = "Assessment - Preplane";
	public static String PROFILE_TITLE = "Profile - Preplane";
	public static String REPORT_TITLE = "Report - Preplane";
	public static String LANDING_PAGE_TITLE = "Preplane";
	
	public static String LANDING_PAGE_TITLE_WEBSITE = "Plancess JEE Main & Advanced Preparation | IIT JEE Online Coaching | AIPMT Preparation | Test Series";
	

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}

}

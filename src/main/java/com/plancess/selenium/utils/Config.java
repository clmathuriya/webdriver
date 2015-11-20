package com.plancess.selenium.utils;

public class Config {

	private Config instance = null;

	public static String URL = "http://dev.preplane.com/";

	public static String URL_WEBSITE = "http://dev.plancess.com/";

	public static String DASHBOARD_TITLE = "Dashboard - PrepLane";
	public static String CUSTOM_TEST_TITLE = "Custom Test - PrepLane";
	public static String ASSESSMENT_TITLE = "Assessment - PrepLane";
	public static String PROFILE_TITLE = "Profile - PrepLane";
	public static String REPORT_TITLE = "Report - PrepLane";
	public static String LANDING_PAGE_TITLE = "Online JEE Main & Advanced assessment platform, Practice Questions";

	public static String LANDING_PAGE_TITLE_WEBSITE = "Plancess JEE Main & Advanced Preparation | IIT JEE Online Coaching | AIPMT Preparation | Test Series";

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}

}

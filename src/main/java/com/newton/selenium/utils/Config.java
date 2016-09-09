package com.newton.selenium.utils;

public class Config {

	private Config instance = null;

	public static String URL = "https://en.wikipedia.org/wiki/Main_Page";
	// public static String URL = "";

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}

}

package com.plancess.selenium.utils;

public class Config {

	private Config instance = null;

	public Config getInstance() {
		if (instance != null) {
			instance = new Config();
		}
		return instance;
	}
	

}

package com.plancess.selenium.utils;

import org.testng.Assert;

public class Verifications extends Assert {

	private static Verifications instance;

	private Verifications() {

	}

	public static Verifications getInstance() {
		if (instance == null) {
			instance = new Verifications();
		}
		return instance;
	}

	public void verifyEquals(Object actual, Object expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			System.out.println(e.getMessage());

		}
	}

	public void verifyEquals(Object actual, Object expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			System.out.println(e.getMessage());

		}
	}

	public void verifyTrue(boolean flag) {
		try {
			Assert.assertTrue(flag);
		} catch (AssertionError e) {
			System.out.println(e.getMessage());

		}
	}

	public void verifyTrue(boolean flag, String message) {
		try {
			Assert.assertTrue(flag, message);
		} catch (AssertionError e) {
			System.out.println(e.getMessage());

		}
	}

}

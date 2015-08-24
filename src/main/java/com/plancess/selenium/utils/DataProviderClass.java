package com.plancess.selenium.utils;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
	static Map<String, String> user = new HashMap<String, String>();
	static long timestamp = System.currentTimeMillis();

	@DataProvider(name = "mandatoryData")
	public static Object[][] signUpDataProviderMandatoryData() {

		Object[][] dataSet = new Object[1][1];

		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;

	}

	@DataProvider(name = "invalidEmails")
	public static Object[][] signUpDataProviderWithInvalidEmails() {
		String[] invalidEmails = { "resttest", "RESTTEST!@#$%^&*()_-+=", "@plancessresttest.com",
				"Rest Test <rest_api_test@plancess.com>", "restapi.user.plancess.com",
				"rest@test@email@plancess@@plancess.com", ".resttest@plancess.com", "username.@plancess.com",
				"username..username@plancess.com", "username@plancess.com (some name)", "username@plancess",
				"username@-plancess.com", "username" + timestamp + "@plancess.web",
				"username" + timestamp + "@192.168.123.1111", "username@plancess..com", "¢£¥®@plancess.com" };
		Object[][] dataSet = new Object[invalidEmails.length][1];

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		for (int i = 0; i < invalidEmails.length; i++) {
			user.put("email", invalidEmails[i]);
			dataSet[i][0] = user;
		}

		return dataSet;
	}

	@DataProvider(name = "validEmailPasswords")
	public static Object[][] signUpDataProviderWithValidEmailPasswords() {
		String[] validEmails = { "resttest1" + timestamp + "@plancess.com",
				"rest.test.firstname.lastname" + timestamp + "@plancess.com",
				"resttestuser" + timestamp + "@plancess.com", "resttestuser2" + timestamp + "@plancess-edu.com",
				"firstname+lastname+rest+test" + timestamp + "@plancess.com",
				"firstname.lastnameresttest" + timestamp + "@123.123.123.123",
				"firstname.lastnameresttest" + timestamp + "@[123.123.123.123]",
				"\"firstname.lastnameresttest" + timestamp + "\"@plancess.com",
				"1234567890resttest" + timestamp + "@plancess.com",
				"firstname.lastnameresttest3" + timestamp + "@plancess.name",
				"____@resttest" + timestamp + "plancess.com", "emailresttest" + timestamp + "@plancess.co.in",
				"firstname-lastnameresttest" + timestamp + "@plancess.com" };
		String[] validPasswords = { "123456A@", "1234567B!", "123456789C@", "123456789D$", "1234567890E%",
				"12345678901F&", "123456789012g^", "1234567890123h*", "12345678901234i?", "$@!%*?&^123456ab",
				"$@!%*?&^12345abc", "$@!%*?&^1234abcd", "$@!%*?&^123abcde" };
		Object[][] dataSet = new Object[validEmails.length][1];

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");

		for (int i = 0; i < validEmails.length; i++) {
			user.put("password", validPasswords[i]);
			user.put("confirm_password", validPasswords[i]);
			user.put("email", validEmails[i]);
			dataSet[i][0] = user;
		}

		return dataSet;
	}
}

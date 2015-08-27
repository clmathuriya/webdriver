package com.plancess.selenium.utils;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

	@DataProvider(name = "mandatoryData")
	public static Object[][] signUpDataProviderMandatoryData() {
		Map<String, String> user = new HashMap<String, String>();

		Object[][] dataSet = new Object[1][1];
		long timestamp = System.currentTimeMillis();

		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		user.put("email", "webuser" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;

	}

	@DataProvider(name = "invalidEmails")
	public static Object[][] signUpDataProviderWithInvalidEmails() {
		long timestamp = System.currentTimeMillis();
		String[] invalidEmails = { "webtest", "webTEST!@#$%^&*()_-+=", "@plancesswebtest.com",
				"web Test <web_api_test@plancess.com>", "webapi.user.plancess.com",
				"web@test@email@plancess@@plancess.com", ".webtest@plancess.com", "username.@plancess.com",
				"username..username@plancess.com", "username@plancess.com (some name)", "username@plancess",
				"username@-plancess.com", "username" + timestamp + "@192.168.123.1111", "username@plancess..com",
				"¢£¥®@plancess.com" };
		Object[][] dataSet = new Object[invalidEmails.length][1];
		Map<String, String> user = new HashMap<String, String>();

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

	@DataProvider(name = "invalidEmail")
	public static Object[][] signUpDataProviderWithInvalidEmail() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		user.put("email", "webtest");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "invalidPassword")
	public static Object[][] signUpDataProviderWithInvalidPassword() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");
		user.put("email", "webuser@plancess.com");

		user.put("password", "n@t8");
		user.put("confirm_password", "n@t8");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "invalidPasswords")
	public static Object[][] signUpDataProviderWithInvalidPasswords() {
		String[] invalidPasswords = { "n@t8", "0nly@07", "!@##$%^&*", "onlyalphabets", "12345678", "$@!%*?&^",
				"alphanumeric10", "alphaspcl@", "123!@$", "morethan16p@ssw0rd", "seventeen@1234567", "01234",
				"with space", "sql' or '1'='1", "_________", "¢£¥®asddf12234" };
		Object[][] dataSet = new Object[invalidPasswords.length][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");
		user.put("email", "webuser@plancess.com");

		for (int i = 0; i < invalidPasswords.length; i++) {
			user.put("password", invalidPasswords[i]);
			user.put("confirm_password", invalidPasswords[i]);
			dataSet[i][0] = user;
		}

		return dataSet;
	}

	@DataProvider(name = "misMatchPasswords")
	public static Object[][] signUpDataProviderWithMismatchPasswords() {
		long timestamp = System.currentTimeMillis();

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd1");
		user.put("email", "webuser" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "invalidMobile")
	public static Object[][] signUpDataProviderWithInvalidMobile() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");

		user.put("email", "webuser@plancess.com");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		user.put("mobile", "!@#$%^");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "invalidMobiles")
	public static Object[][] signUpDataProviderWithInvalidMobiles() {
		String[] invalidMobiles = { "a", "!@#$%^", "1234", "123456789", "0000000000", "12345678", "asdf123456",
				"asdfasdfasdf", "!@#$%^&*()_-+=", "12345678910", "000000000000000000", "asdf1234567" };
		Object[][] dataSet = new Object[invalidMobiles.length][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");

		user.put("email", "webuser@plancess.com");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		for (int i = 0; i < invalidMobiles.length; i++) {

			user.put("mobile", invalidMobiles[i]);
			dataSet[i][0] = user;
		}

		return dataSet;
	}

	@DataProvider(name = "invalidNames")
	public static Object[][] signUpDataProviderWithInvalidNames() {
		String[] invalidNames = { "!@#$%^&*()_-+=", "1234", "asdf111", "12345" };
		Object[][] dataSet = new Object[invalidNames.length][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("mobile", "9876543210");

		user.put("email", "webuser@plancess.com");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		for (int i = 0; i < invalidNames.length; i++) {

			user.put("firstName", invalidNames[i]);
			user.put("lastName", invalidNames[i]);
			dataSet[i][0] = user;
		}

		return dataSet;
	}

	@DataProvider(name = "validEmailPassword")
	public static Object[][] signUpDataProviderWithValidEmailPassword() {
		long timestamp = System.currentTimeMillis();

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");

		user.put("password", "$@!%*?&^123abcde");
		user.put("confirm_password", "$@!%*?&^123abcde");
		user.put("email", "webtest1" + timestamp + "@plancess.com");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "validEmailPasswords")
	public static Object[][] signUpDataProviderWithValidEmailPasswords() {
		long timestamp = System.currentTimeMillis();
		String[] validEmails = { "webtest1" + timestamp + "@plancess.com",
				"web.test.firstname.lastname" + timestamp + "@plancess.com",
				"webtestuser" + timestamp + "@plancess.com", "webtestuser2" + timestamp + "@plancess-edu.com",
				"firstname+lastname+web+test" + timestamp + "@plancess.com",
				"firstname.lastnamewebtest" + timestamp + "@123.123.123.123",
				"firstname.lastnamewebtest" + timestamp + "@[123.123.123.123]",
				"\"firstname.lastnamewebtest" + timestamp + "\"@plancess.com",
				"1234567890webtest" + timestamp + "@plancess.com",
				"firstname.lastnamewebtest3" + timestamp + "@plancess.name",
				"____@webtest" + timestamp + "plancess.com", "emailwebtest" + timestamp + "@plancess.co.in",
				"firstname-lastnamewebtest" + timestamp + "@plancess.com" };
		String[] validPasswords = { "123456A@", "1234567B!", "123456789C@", "123456789D$", "1234567890E%",
				"12345678901F&", "123456789012g^", "1234567890123h*", "12345678901234i?", "$@!%*?&^123456ab",
				"$@!%*?&^12345abc", "$@!%*?&^1234abcd", "$@!%*?&^123abcde" };
		Object[][] dataSet = new Object[validEmails.length][1];
		Map<String, String> user = new HashMap<String, String>();

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

	// login data providers

	@DataProvider(name = "loginWithNonExistingEmail")
	public static Object[][] loginDataProviderWithNonExistingEmail() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("email", "nonexistingemail@plancess.com");
		user.put("password", "P@ssw0rd");

		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "loginWithInvalidPassword")
	public static Object[][] loginDataProviderWithInvalidPassword() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("email", "testuser@gmail.com");
		user.put("password", "InvalidPassword");

		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "loginWithValidEmailPassword")
	public static Object[][] loginDataProviderWithValidEmailPassword() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("email", "testuser@gmail.com");
		user.put("password", "P@ssw0rd");

		dataSet[0][0] = user;

		return dataSet;
	}

}

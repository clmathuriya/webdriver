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
		user.put("email", "webuser" + timestamp + "@mailinator.com");
		dataSet[0][0] = user;

		return dataSet;

	}

	@DataProvider(name = "invalidEmails")
	public static Object[][] signUpDataProviderWithInvalidEmails() {
		long timestamp = System.currentTimeMillis();
		String[] invalidEmails = { "webtest", "webTEST!@#$%^&*()_-+=", "@plancesswebtest.com",
				"web Test <web_api_test@mailinator.com>", "webapi.user.plancess.com",
				"web@test@email@plancess@@mailinator.com", ".webtest@mailinator.com", "username.@mailinator.com",
				"username..username@mailinator.com", "username@mailinator.com (some name)", "username@plancess",
				"username@-plancess.com", "username@plancess..com", "¢£¥®@mailinator.com" };
		Object[][] dataSet = new Object[invalidEmails.length][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");
		user.put("mobile", "9876543210");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");
		for (int i = 0; i < invalidEmails.length; i++) {
			user.put("email", invalidEmails[i]);
			Map<String, String> temp = user;
			dataSet[i][0] = temp;
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
		user.put("email", "webuser@mailinator.com");

		user.put("password", "1234");
		user.put("confirm_password", "1234");
		dataSet[0][0] = user;

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
		user.put("email", "webuser" + timestamp + "@mailinator.com");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "invalidMobile")
	public static Object[][] signUpDataProviderWithInvalidMobile() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("firstName", "WebUser");
		user.put("lastName", "Test");

		user.put("email", "webuser@mailinator.com");
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

		user.put("email", "webuser@mailinator.com");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		for (int i = 0; i < invalidMobiles.length; i++) {

			user.put("mobile", invalidMobiles[i]);
			Map<String, String> temp = user;
			dataSet[i][0] = temp;

		}

		return dataSet;
	}

	@DataProvider(name = "invalidNamesLessThanThreeChars")
	public static Object[][] signUpDataProviderWithInvalidNamesLessThanThreeChars() {
		String[] invalidNames = { "a", "ab", "aa" };
		Object[][] dataSet = new Object[invalidNames.length][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("mobile", "9876543210");

		user.put("email", "webuser@mailinator.com");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		for (int i = 0; i < invalidNames.length; i++) {

			user.put("firstName", invalidNames[i]);
			user.put("lastName", invalidNames[i]);
			Map<String, String> temp = user;
			dataSet[i][0] = temp;

		}

		return dataSet;
	}

	@DataProvider(name = "invalidNamesMoreThanThreeChars")
	public static Object[][] signUpDataProviderWithInvalidNamesMoreThanThreeChars() {
		String[] invalidNames = { "!@#$%^&*()_-+=", "1234", "asdf111", "12345" };
		Object[][] dataSet = new Object[invalidNames.length][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("mobile", "9876543210");

		user.put("email", "webuser@mailinator.com");
		user.put("password", "P@ssw0rd");
		user.put("confirm_password", "P@ssw0rd");

		for (int i = 0; i < invalidNames.length; i++) {

			user.put("firstName", invalidNames[i]);
			user.put("lastName", invalidNames[i]);
			Map<String, String> temp = user;
			dataSet[i][0] = temp;
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
		user.put("email", "webtest1" + timestamp + "@mailinator.com");
		dataSet[0][0] = user;

		return dataSet;
	}

	@DataProvider(name = "validEmailPasswords")
	public static Object[][] signUpDataProviderWithValidEmailPasswords() {
		long timestamp = System.currentTimeMillis();
		String[] validEmails = { "web.test.firstname.lastname1" + timestamp + "@mailinator.com",
				"webtestuser2" + timestamp + "@mailinator.com", "webtestuser3" + timestamp + "@plancess-edu.com",
				"firstname+lastname+web+test4" + timestamp + "@mailinator.com",
				"firstname.lastnamewebtest5" + timestamp + "@123.123.123.123",
				"firstname.lastnamewebtest6" + timestamp + "@[123.123.123.123]",
				"\"firstname.lastnamewebtest7" + timestamp + "\"@mailinator.com",
				"1234567890webtest8" + timestamp + "@mailinator.com",
				"firstname.lastnamewebtest39" + timestamp + "@plancess.name",
				"____@webtest10" + timestamp + "plancess.com", "emailwebtest11" + timestamp + "@plancess.co.in",
				"firstname-lastnamewebtest12" + timestamp + "@mailinator.com" };
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
			Map<String, String> temp = user;
			dataSet[i][0] = temp;
			
		}

		return dataSet;
	}

	// login data providers

	// update user profile data providers

	@DataProvider(name = "userProfileValidData")
	public static Object[][] userProfileDataProviderWithValidDetails() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("email", "testuser@gmail.com");
		user.put("password", "P@ssw0rd");
		user.put("firstName", "Web");
		user.put("lastName", "User");
		user.put("mobile", "9876543210");
		user.put("dob", "28/08/1990");
		user.put("target_year", "2018");
		user.put("target_year", "2018");

		dataSet[0][0] = user;

		return dataSet;
	}

	// update user security data providers

	@DataProvider(name = "userSecurityValidData")
	public static Object[][] userSecurityDataProviderWithValidDetails() {

		Object[][] dataSet = new Object[1][1];
		Map<String, String> user = new HashMap<String, String>();

		user.put("email", "testuser1@mailinator.com");
		user.put("password", "P@ssw0rd");
		user.put("currentPassword", "P@ssw0rd");
		user.put("newPassword", "P@ssw0rd1");
		user.put("confirmPassword", "P@ssw0rd1");

		dataSet[0][0] = user;

		return dataSet;
	}

}

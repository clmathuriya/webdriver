package com.plancess.selenium.reporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.Reporter;

public class PlancessReporter implements IReporter {
	private String getURL = "https://plancess.atlassian.net/wiki/rest/api/content/2097245/?expand=body.storage";
	private String putURL = "https://plancess.atlassian.net/wiki/rest/api/content/2097245/";

	private String step = "";

	private String buildStatus = "Failed";

	private int buildNumber;

	private int passCount;

	private int failCount;

	private int skipCount;

	private Date date;
	private PrintWriter mOut;
	private static PlancessReporter instance;
	private StopWatch stopWatch;
	private String reportFileName = "";

	public PlancessReporter() {
		new File("./test-output/custom-reports").mkdirs();
		String reportPath = "./test-output/custom-reports/custom-report" + System.currentTimeMillis() + ".html";
		stopWatch = new StopWatch();
		stopWatch.start();
		try {
			File reportFile = new File(reportPath);
			mOut = new PrintWriter(new BufferedWriter(new FileWriter(reportFile)), true);
			Reporter.log("<h1><a href='" + reportFile.getAbsolutePath() + "'>Custom Report</a></h1>");
			instance = this;
			if (reportFile.getAbsolutePath().contains("jenkins"))
				reportFileName = reportFile.getName();
		} catch (IOException e) {
			System.out.println("Error in creating writer: " + e);
		}

	}

	public static PlancessReporter getInstance() {
		if (instance == null) {
			instance = new PlancessReporter();
		}
		return instance;
	}

	public StopWatch getStopWatch() {
		return stopWatch;
	}

	public void log(String s) {
		mOut.print(s);
	}

	@Override
	public void generateReport(List xmlSuites, List suites, String outputDirectory) {
		try {
			// Iterating over each suite included in the test
			for (Object suite : suites) {

				// Following code gets the suite name
				String suiteName = ((ISuite) suite).getName();
				// Getting the results for the said suite
				Map suiteResults = ((ISuite) suite).getResults();
				for (Object sr : suiteResults.values()) {
					ITestContext tc = ((ISuiteResult) sr).getTestContext();
					System.out.println("Test Date-Time: " + tc.getEndDate());
					passCount = tc.getPassedTests().getAllResults().size();
					failCount = tc.getFailedTests().getAllResults().size();
					skipCount = tc.getSkippedTests().getAllResults().size();
					date = tc.getEndDate();

					Reporter.log("Passed tests for suite '" + suiteName + "' is:" + passCount, true);
					Reporter.log("Failed tests for suite '" + suiteName + "' is:" + failCount, true);
					if (failCount == 0 && skipCount == 0)
						buildStatus = "Success";
					Reporter.log("Skipped tests for suite '" + suiteName + "' is:" + skipCount, true);
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(getURL);

					// add request header
					request.addHeader("Authorization", "Basic Y2hoYWdhbmxhbC5tOlBsYUAxMjM0NQ==");
					HttpResponse response = client.execute(request);
					Reporter.log("Response Code : " + response.getStatusLine().getStatusCode(), true);
					String json_string = EntityUtils.toString(response.getEntity());
					JSONObject temp1 = new JSONObject(json_string);
					JSONObject storage = new JSONObject(
							new JSONObject(temp1.get("body").toString()).get("storage").toString());
					// Reporter.log(storage.get("value").toString(), true);
					String currentValue = storage.get("value").toString();
					String replaceString = "<table style=\"width: 100.0%;\"><tbody><tr><th>Build#</th><th>Date-Time</th><th>Passed</th><th>Skipped</th><th>Failed</th><th>Build Status</th><th>Comments</th></tr>";

					buildNumber = currentValue.split("<tr>").length - 1;
					step = "<tr> <td>" + buildNumber + "</td> <td>" + date + "</td><td>" + passCount + "</td><td>"
							+ skipCount + "</td><td>" + failCount + "</td><td>" + buildStatus
							+ "</td><td> for suite name : <a href='http://phabricator.plancess.com:8080/job/webapp-selenium-tests/ws/test-output/custom-reports/"
							+ reportFileName + "'>" + suiteName + "</a></td></tr>";

					String replacewith = replaceString + step;
					String valuetoPost = currentValue.replace(replaceString, replacewith).replaceAll("\"", "'");
					// Reporter.log("updated content:" + valuetoPost, true);

					HttpPut requestPut = new HttpPut(putURL);

					// add request header
					requestPut.addHeader("Authorization", "Basic Y2hoYWdhbmxhbC5tOlBsYUAxMjM0NQ==");
					requestPut.addHeader("Content-Type", "application/json");
					requestPut.addHeader("Accept", "application/json");
					String putbody = "{\"id\":\"2097245\",\"type\":\"page\",\"title\":\"Testing Automation Status\",\"space\":{\"key\":\"WEBAPP\"},\"body\":{\"storage\":{\"value\":\""
							+ valuetoPost + "\",\"representation\":\"storage\"}},\"version\":{\"number\":\""
							+ (4 + buildNumber) + "\"}}";

					StringEntity params = new StringEntity(putbody);
					requestPut.setEntity(params);
					response = client.execute(requestPut);
					// Reporter.log("update response :" + response, true);
				}
			}

			// print("Suites run: " + suites.size() + "<br>");
			// for (Object suite : suites) {
			// print("Suite>" + ((ISuite) suite).getName() + "<br>");
			// Map<String, ISuiteResult> suiteResults = ((ISuite)
			// suite).getResults();
			// for (String testName : suiteResults.keySet()) {
			// print(" Test>" + testName + "<br>");
			// ISuiteResult suiteResult = suiteResults.get(testName);
			// ITestContext testContext = suiteResult.getTestContext();
			// print(" Failed>" + testContext.getFailedTests().size() + "<br>");
			// IResultMap failedResult = testContext.getFailedTests();
			// Set testsFailed = failedResult.getAllResults();
			// for (Object testResult : testsFailed) {
			// print(" " + ((ITestResult) testResult).getName() + "<br>");
			// print(" " + ((ITestResult) testResult).getThrowable() + "<br>");
			// }
			// IResultMap passResult = testContext.getPassedTests();
			// Set testsPassed = passResult.getAllResults();
			// print(" Passed>" + testsPassed.size() + "<br>");
			// for (Object testResult : testsPassed) {
			// print(" " + ((ITestResult) testResult).getName() + ">took "
			// + (((ITestResult) testResult).getEndMillis()
			// - ((ITestResult) testResult).getStartMillis())
			// + "ms" + "<br>");
			// }
			// IResultMap skippedResult = testContext.getSkippedTests();
			// Set testsSkipped = skippedResult.getAllResults();
			// print(" Skipped>" + testsSkipped.size() + "<br>");
			// for (Object testResult : testsSkipped) {
			// print(" " + ((ITestResult) testResult).getName() + "<br>");
			// }
			//
			// }
			// }

		} catch (Exception e) {
			Reporter.log("Exception in updating status on confluence", true);

		}

	}

	// private void print(String text) {
	// System.out.println(text);
	// mOut.println(text + "");
	// }

	public void killReporter() {

		mOut.flush();
		mOut.close();
	}

}
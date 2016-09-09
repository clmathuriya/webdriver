package com.newton.selenium.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Util {
	private static Util instance;
	String jira_attachment_baseURL = "";
	String jira_attachment_authentication = "";
	private String reportPath;

	private Util() {
		reportPath = getExtentReportPath();

	}

	public static Util getInstance() {
		if (instance == null) {
			instance = new Util();
		}
		return instance;
	}

	public String takeScreenshot(WebDriver driver, String step) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String filename = "./screenshots/screenshot" + System.currentTimeMillis() + ".png";
			File file = new File(filename);
			FileUtils.copyFile(scrFile, file);
			// Reporter.log("<br> Screenshot for " + step + " captured as <a
			// href='" + file.getAbsolutePath()
			// + "' target='_blank'> screenshot </a> <br>", true);
			return "Screenshot for " + step + " is :" + file.getName() + "\n";
		} catch (Exception e) {
			// Reporter.log("Exception in taking screenshot");
		}
		return null;
	}

	public String takeScreenshot(WebDriver driver) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy
			// somewhere
			String filename = "./screenshots/screenshot" + System.currentTimeMillis() + ".png";
			File file = new File(filename);
			FileUtils.copyFile(scrFile, file);
			// Reporter.log("Screenshot captured as <a href='" +
			// file.getAbsolutePath()
			// + "' target='_blank'> screenshot </a> <br>");
			if (file.getAbsolutePath().contains("/jenkins/")) {
				return "http://phabricator.plancess.com:8080/job/webapp-selenium-tests/ws/screenshots/"
						+ file.getName();
			}
			return file.getAbsolutePath();
		} catch (Exception e) {
			// Reporter.log("Exception in taking screenshot");
		}
		return "NA";
	}

	public boolean addAttachmentToIssue(String issueKey, String fullfilename) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httppost = new HttpPost(jira_attachment_baseURL + "/api/latest/issue/" + issueKey + "/attachments");
		httppost.setHeader("X-Atlassian-Token", "nocheck");
		httppost.setHeader("Authorization", "Basic " + jira_attachment_authentication);

		File fileToUpload = new File(fullfilename);
		FileBody fileBody = new FileBody(fileToUpload);

		HttpEntity entity = MultipartEntityBuilder.create().addPart("file", fileBody).build();

		httppost.setEntity(entity);
		String mess = "executing request " + httppost.getRequestLine();
		// logger.info(mess);

		CloseableHttpResponse response;

		try {
			response = httpclient.execute(httppost);
		} finally {
			httpclient.close();
		}

		if (response.getStatusLine().getStatusCode() == 200)
			return true;
		else
			return false;

	}

	public boolean getAttachmentFromIssue(String contentURI, String fullfilename) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpget = new HttpGet(contentURI);
			httpget.setHeader("Authorization", "Basic " + jira_attachment_authentication);

			System.out.println("executing request " + httpget.getURI());

			CloseableHttpResponse response = httpclient.execute(httpget);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				if (entity.isStreaming()) {
					byte data[] = EntityUtils.toByteArray(entity);
					FileOutputStream fout = new FileOutputStream(new File(fullfilename));
					fout.write(data);
					fout.close();
				}
			}
		} finally {
			httpclient.close();
		}

		return true;
	}

	public List<String> getBrokenLinks(WebDriver driver) {
		List<String> links = new ArrayList<String>();

		return links;

	}

	private String getExtentReportPath() {

		new File("./test-output/extent-reports").mkdirs();
		String reportPath = "./test-output/extent-reports/extent-report" + System.currentTimeMillis() + ".html";
		return reportPath;
	}

	public String getReportPath() {
		return reportPath;
	}
}
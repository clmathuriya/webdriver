package com.newton.selenium;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ReporterTest {
	public static void main(String arg[]) throws ClientProtocolException, IOException {
		String url = "https://plancess.atlassian.net/wiki/rest/api/content/2097245/?expand=body.storage";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("Authorization", "Basic Y2hoYWdhbmxhbC5tOlBsYUAxMjM0NQ==");
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String json_string = EntityUtils.toString(response.getEntity());
		JSONObject temp1 = new JSONObject(json_string);
		System.out.println(temp1);
		JSONObject storage = new JSONObject(new JSONObject(temp1.get("body").toString()).get("storage").toString());
		System.out.println(storage.get("value"));
		String currentValue = storage.get("value").toString();
		String replaceString = "<table style=\"width: 100.0%;\"><tbody><tr><th>Build#</th><th>Date-Time</th><th>Passed</th><th>Skipped</th><th>Failed</th><th>Build Status</th><th>Comments</th></tr>";

		int buildNumber = currentValue.split("<tr>").length - 1;

		String replacewith = replaceString + "<tr><td>" + buildNumber + "</td><td>" + new Date() + "</td><td>" + 5
				+ "</td><td>" + 3 + "</td><td>" + 1 + "</td><td>" + "Failed" + "</td><td>" + "No comments"
				+ "</td></tr>";
		String valuetoPost = currentValue.replace(replaceString, replacewith).replaceAll("\"", "'");
		System.out.println(valuetoPost);

		HttpPut requestPut = new HttpPut("https://plancess.atlassian.net/wiki/rest/api/content/2097245/");

		// add request header
		requestPut.addHeader("Authorization", "Basic Y2hoYWdhbmxhbC5tOlBsYUAxMjM0NQ==");
		requestPut.addHeader("Content-Type", "application/json");
		requestPut.addHeader("Accept", "application/json");
		String putbody = "{\"id\":\"2097245\",\"type\":\"page\",\"title\":\"Testing Automation Status\",\"space\":{\"key\":\"WEBAPP\"},\"body\":{\"storage\":{\"value\":\""
				+ valuetoPost + "\",\"representation\":\"storage\"}},\"version\":{\"number\":\"" + (4 + buildNumber)
				+ "\"}}";
		System.out.println(putbody);
		StringEntity params = new StringEntity(putbody);
		requestPut.setEntity(params);
		response = client.execute(requestPut);
		System.out.println(response);

	}

}

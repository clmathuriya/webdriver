package com.plancess.selenium.tests;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.plancess.selenium.pages.AssessmentPage;
import com.plancess.selenium.pages.CreateAccessment;
import com.plancess.selenium.pages.Dashboard;
import com.plancess.selenium.pages.LoginDialogPage;
import com.plancess.selenium.pages.ReportPage;
import com.plancess.selenium.pages.SignUpDialogPage;
import com.plancess.selenium.utils.ExcelReader;

public class CreateAssessmentTest extends BaseTest {

	private LoginDialogPage loginDialogPage;
	private CreateAccessment createAssessment;
	private ReportPage reportPage;
	private AssessmentPage assessmentPage;
	private SignUpDialogPage signUpDialogPage;
	private Dashboard dashboard;

	@Test(dataProvider = "createAssessmentDataProvider", groups = { "smoke", "regression" })
	public void CreateAssessmentWithValidDataTest(Map<String, String> user) {

		// long timestamp = System.currentTimeMillis();
		//
		// user.put("email", "webuser" + timestamp + "@mailinator.com");
		//
		// signUpDialogPage = landingPage.openSignUpDialogPage();
		// signUpDialogPage.signUp(user);
		// dashboard = signUpDialogPage.verifyEmail(user).doLogin(user);
		user.put("email", "clmathuriya@gmail.com");
		dashboard = landingPage.openLoginDialogPage().doLogin(user);
		executor.softWaitForWebElement(dashboard.getDashBoardButton());
		// executor.softWaitForWebElement(ExpectedConditions.visibilityOf(dashboard.getToggleDropDown()));

		createAssessment = dashboard.navigateToCreateAccessment();
		executor.softWaitForWebElement(createAssessment.getCustomTestLink());
		selectCustomTest(user);

		executor.click(createAssessment.getCreateTest(), "Begin Test");
		// createAssessment.getCreateTest().click();
		// -------------
		executor.softWaitForWebElement(dashboard.getStartTest());
		executor.assertTrue(!executor.isElementExist(By.xpath("//*[@ng-if='noTopic']")),
				"Verify if no more tests exist for this subject");

		executor.verifyEquals(dashboard.getTimeRequired().getText().trim(), user.get("timeRequired").trim(),
				"verify time required");
		executor.verifyEquals(dashboard.getTotalQuestions().getText().trim(), user.get("totalQuestions"),
				"verify number of total questions");

		executor.click(dashboard.getStartTest(), "Start The Test");
		// dashboard.getStartTest().click();
		executor.softWaitForWebElement(dashboard.getNextButton());

		assessmentPage = new AssessmentPage(driver, wait);
		assessmentPage.takeAssessment(user);

		reportPage = new ReportPage(driver, wait);
		reportPage.verifyReport(user);
		dashboard.logoutUser();

	}

	public CreateAccessment selectCustomTest(Map<String, String> user) {

		String subjectTopic = user.get("subject");
		String[] topics = subjectTopic.split(",");

		int len = topics.length;

		for (int i = 0; i < len; i++) {
			String[] topicList = topics[i].split(">");
			String subject = topicList[0];
			String module = topicList[1];
			String sub_Module = "";
			if (topicList.length > 2)
				sub_Module = topicList[2];

			switch (subject.toLowerCase()) {

			case "physics":
				executor.softWaitForWebElement(
						ExpectedConditions.elementToBeClickable(createAssessment.getsubjectPhysicsLink()));

				// wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectPhysicsLink()));
				if (createAssessment.getsubjectPhysicsLink().isEnabled())
					executor.mouseClick(createAssessment.getsubjectPhysicsLink());
				// createAssessment.getsubjectPhysicsLink().click();
				break;
			case "chemistry":
				executor.softWaitForWebElement(createAssessment.getsubjectChemistryLink(), "wait for chemistry link");
				// wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectChemistryLink()));
				if (createAssessment.getsubjectChemistryLink().isEnabled())
					executor.click(createAssessment.getsubjectChemistryLink(), "Chemistry Link");
				// createAssessment.getsubjectChemistryLink().click();
				break;
			case "math":
			case "maths":
			case "mathematics":
				executor.softWaitForWebElement(createAssessment.getsubjectMathematicsLink());
				// wait.until(ExpectedConditions.visibilityOf(createAssessment.getsubjectMathematicsLink()));
				if (createAssessment.getsubjectMathematicsLink().isEnabled())
					executor.click(createAssessment.getsubjectMathematicsLink(), "Subject Mathematics Link");
				// createAssessment.getsubjectMathematicsLink().click();
				break;
			default:
				Assert.fail("Subject :" + user.get("subject") + "not found");

			}
			WebElement toggleModule = executor.softWaitForWebElement(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//*[normalize-space(.)='" + module + "']/../a")));

			// WebElement toggleModule =
			// executor.getElement(By.xpath("//*[normalize-space(.)='" + module
			// + "']/../a"));

			if (executor.isElementExist(toggleModule)
					&& toggleModule.findElement(By.tagName("span")).getAttribute("class").contains("plus")
					&& sub_Module.length() > 0) {
				executor.click(toggleModule, module);
				// toggleModule.click();
			}

			if (!sub_Module.isEmpty()) {

				executor.getElement(By.xpath("//input[@name='" + sub_Module + "']")).click();
			} else {
				// executor.getElement(By.xpath("//span[normalize-space(.)='" +
				// module + "']")).click();
				executor.getElement(By.xpath("//input[@name='" + module + "']")).click();
			}

			final String MODULE = module;
			executor.softWaitForCondition(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver driver) {

					String selectedSubjects = "";
					for (WebElement e : createAssessment.getTopicsSelected()) {
						selectedSubjects += e.getText();
						selectedSubjects += e.getAttribute("innerHTML");
					}
					return selectedSubjects.contains(MODULE);
				}
			});

			String selectedTopics = "";
			for (WebElement e : createAssessment.getTopicsSelected()) {
				selectedTopics += e.getText();
				selectedTopics += e.getAttribute("innerHTML");
			}


			executor.verifyTrue(selectedTopics.contains(sub_Module) && selectedTopics.contains(module),
					"verify topic selected");

		}
		executor.getElement(By.xpath("//label[normalize-space(.)='" + user.get("ExamType") + "']/input")).click();

		// executor.getElement(By.xpath("//label[normalize-space(.)='"
		// +user.get("ExamType")+ "']/input")).click();
		executor.selectFromDropDown(createAssessment.getExamDurationDropDown(), "text", user.get("TestDuration"));

		// new
		// Select(createAssessment.getExamDurationDropDown()).selectByVisibleText(user.get("TestDuration"));

		WebElement difficultyLevel = executor
				.getElement(By.xpath("//span[.='" + user.get("DifficultyLevel") + "']/ins"));
		if (difficultyLevel != null && difficultyLevel.isEnabled())
			executor.mouseClick(difficultyLevel);

		return new CreateAccessment(driver, wait);
	}

	@DataProvider(name = "createAssessmentDataProvider")
	public static Object[][] dashboardDataProvider() {

		return new ExcelReader().getUserDataFromExcel("testData.xlsx", "createAssessment");

	}

}

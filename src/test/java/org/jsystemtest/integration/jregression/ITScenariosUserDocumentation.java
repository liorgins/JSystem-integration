package org.jsystemtest.integration.jregression;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestInfoTab;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.testng.annotations.Test;


public class ITScenariosUserDocumentation extends AbstractITJSystem {

	/**
	 * 1.Tests user documentation.<br>
	 * 2.Create sub scenario.<br>
	 * 3.Adds user doc to the sub scenario.<br>
	 * 4.Create root scenario.<br>
	 * 5.Add the sub scenario three times to the root scenario.<br>
	 * 6.Asserts the sub scenarios user documentation.<br>
	 * 7.Marks one of the sub scenarios as test.<br>
	 * 8.Sets different user doc to each one of the sub scenarios.<br>
	 * 9.Loads the sub scenario and asserts the original user documentation.<br>
	 * 10.Loads the root scenario and asserts the user documentation.<br>
	 */
	@Test
	public void scenarioUserDocSanity() throws Exception {

		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();

		Reporter.log("Creating sub scenario");
		final String subScenarioName = "docSanitySubScenario";
		app.createScenario(subScenarioName);
		testsTreeTab.addTest("reportSuccess", "Example", 3);

		final String sonOriginalExpectedDoc = "Son original expected documantation";
		scenarioTree.selectTestByRow(0);
		testInfoTab.setTestUserDocumentation(sonOriginalExpectedDoc);
		app.getToolBar().pushSaveScenarioButton();

		Reporter.log("Adding sub scenario to root scenario");
		final String rootScenarioName = "docSanityRootScenario";
		app.createScenario(rootScenarioName);
		scenarioTree.selectTestByRow(0);

		testsTreeTab.addTest(subScenarioName, "scenarios", 3);
		scenarioTree.markScenarioAsTest(1, true);

		Reporter.log("Changing the sub scenarios documentation");
		final String expectedSonUserDocPrefix = "Sub scenario user doc number ";

		scenarioTree.selectTestByRow(1);
		String actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(actualSubScenarioDoc, sonOriginalExpectedDoc);
		scenarioTree.selectTestByRow(1);
		testInfoTab.setTestUserDocumentation(expectedSonUserDocPrefix + "1");

		scenarioTree.selectTestByRow(2);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(actualSubScenarioDoc, sonOriginalExpectedDoc);
		scenarioTree.selectTestByRow(2);
		testInfoTab.setTestUserDocumentation(expectedSonUserDocPrefix + "2");

		scenarioTree.selectTestByRow(6);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(actualSubScenarioDoc, sonOriginalExpectedDoc);
		scenarioTree.selectTestByRow(6);
		testInfoTab.setTestUserDocumentation(expectedSonUserDocPrefix + "3");

		app.getToolBar().pushSaveScenarioButton();

		Reporter.log("Loading the sub scenario and asserting documentation");
		app.openScenario(subScenarioName);
		scenarioTree.selectTestByRow(0);
		final String actualUserDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(actualUserDoc, sonOriginalExpectedDoc);

		Reporter.log("Loading root scenario and asserting sub scenarios documentation");
		app.openScenario(rootScenarioName);
		scenarioTree.selectTestByRow(1);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals( actualSubScenarioDoc, expectedSonUserDocPrefix + "1");
		scenarioTree.selectTestByRow(2);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals( actualSubScenarioDoc, expectedSonUserDocPrefix + "2");
		scenarioTree.selectTestByRow(6);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(actualSubScenarioDoc, expectedSonUserDocPrefix + "3");
	}
}

package org.jsystemtest.integration.jregression;

import jsystem.framework.TestProperties;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestInfoTab;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.Test;

public class ITScenariosUserDocumentationTests extends AbstractJSystemIT {

	/**
	 * <b>Tests user documentation</b><br>
	 * <li>Create sub scenario</li><br>
	 * <li>Adds user doc to the sub scenario</li><br>
	 * <li>Create root scenario</li><br>
	 * <li>Add the sub scenario three times to the root scenario</li><br>
	 * <li>Asserts the sub scenarios user documentation</li><br>
	 * <li>Marks one of the sub scenarios as test</li><br>
	 * <li>Sets different user doc to each one of the sub scenarios</li><br>
	 * <li>Loads the sub scenario and asserts the original user documentation</li>
	 * <br>
	 * <li>Loads the root scenario and asserts the user documentation</li><br>
	 */
	@Test
	@TestProperties(paramsInclude = { "" })
	public void scenarioUserDocSanity() throws Exception {

		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();

		System.out.println("Creating sub scenario");
		final String subScenarioName = "docSanitySubScenario";
		app.createScenario(subScenarioName);
		testsTreeTab.addTest("reportSuccess", "Example", 3);

		final String sonOriginalExpectedDoc = "Son original expected documantation";
		scenarioTree.selectTestByRow(0);
		testInfoTab.setTestUserDocumentation(sonOriginalExpectedDoc);
		app.getToolBar().pushSaveScenarioButton();

		System.out.println("Adding sub scenario to root scenario");
		final String rootScenarioName = "docSanityRootScenario";
		app.createScenario(rootScenarioName);
		scenarioTree.selectTestByRow(0);

		testsTreeTab.addTest(subScenarioName, "scenarios", 3);
		scenarioTree.markScenarioAsTest(1, true);

		System.out.println("Changing the sub scenarios documentation");
		final String expectedSonUserDocPrefix = "Sub scenario user doc number ";

		scenarioTree.selectTestByRow(1);
		String actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(sonOriginalExpectedDoc, actualSubScenarioDoc);
		scenarioTree.selectTestByRow(1);
		testInfoTab.setTestUserDocumentation(expectedSonUserDocPrefix + "1");

		scenarioTree.selectTestByRow(2);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(sonOriginalExpectedDoc, actualSubScenarioDoc);
		scenarioTree.selectTestByRow(2);
		testInfoTab.setTestUserDocumentation(expectedSonUserDocPrefix + "2");

		scenarioTree.selectTestByRow(6);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(sonOriginalExpectedDoc, actualSubScenarioDoc);
		scenarioTree.selectTestByRow(6);
		testInfoTab.setTestUserDocumentation(expectedSonUserDocPrefix + "3");

		app.getToolBar().pushSaveScenarioButton();

		System.out.println("Loading the sub scenario and asserting documentation");
		app.openScenario(subScenarioName);
		scenarioTree.selectTestByRow(0);
		final String actualUserDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(sonOriginalExpectedDoc, actualUserDoc);

		System.out.println("Loading root scenario and asserting sub scenarios documentation");
		app.openScenario(rootScenarioName);
		scenarioTree.selectTestByRow(1);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(expectedSonUserDocPrefix + "1", actualSubScenarioDoc);
		scenarioTree.selectTestByRow(2);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(expectedSonUserDocPrefix + "2", actualSubScenarioDoc);
		scenarioTree.selectTestByRow(6);
		actualSubScenarioDoc = testInfoTab.getTestUserDocumentationForSelectedBlock();
		Assert.assertEquals(expectedSonUserDocPrefix + "3", actualSubScenarioDoc);
	}
}

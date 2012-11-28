package org.jsystemtest.integration;

import jsystem.framework.FrameworkOptions;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITTestTreeFunctionality {

	private static JSystemApplication app;

	@BeforeClass
	public static void prepareEnv() throws InterruptedException {
		app = new JSystemApplication();
		app.setJSystemStandartProperties(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.DEFAULT_SUT_FILE);
		app.setJSystemOptionalProperties(new PropertyPair(FrameworkOptions.AUTO_DELETE_NO_CONFIRMATION, JSystemApplication.TRUE),
				new PropertyPair(FrameworkOptions.AUTO_SAVE_NO_CONFIRMATION, JSystemApplication.TRUE));
		app.launch();

	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildingBlockInfoView() throws Exception {
		testTestDocumentation();
		testScenarioDocumentation();
	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	private void testTestDocumentation() throws Exception {

		final String expectedTestDocumentation = "test     with javadoc";

		System.out.println("Testing test documentation");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("testWithJavadoc", "Example");
		String bbInfo = testsTreeTab.getCurrentBuildingBlockInformation();
		bbInfo = bbInfo.replaceAll("\n", "");
		boolean contains = bbInfo.contains(expectedTestDocumentation);
		Assert.assertEquals(true, contains);
	}

	/**
	 * Tests scenario user documentation
	 */
	private void testScenarioDocumentation() throws Exception {
		System.out.println("Testing scenario documentation");
		final String subScenarioName = "scenarioWithUserDoc";
		app.createScenario(subScenarioName);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 3);
		final String userDoc = "scenario expected documentation";
		app.getTestTableController().getScenarioTree().selectTestByRow(0);
		app.getTestsTreeController().getTestInfoTab().setTestUserDocumentation(userDoc);
		app.getToolBar().pushSaveScenarioButton();

		final String expectedScenarioUserDoc = "scenario     expected documentation";

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode(subScenarioName, "scenarios");
		String bbInfo = testsTreeTab.getCurrentBuildingBlockInformation();

		bbInfo = bbInfo.replaceAll("\n", "");
		boolean contains = bbInfo.contains(expectedScenarioUserDoc);
		Assert.assertEquals(true, contains);
	}

	/**
	 * Tests that the tests tree search works and supports OR, AND operators.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchBox() throws Exception {
		System.out.println("Testing tests tree search box");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();

		int searchCount = testsTreeTab.search("report");
		Assert.assertEquals(5, searchCount);

		searchCount = testsTreeTab.search("with");
		Assert.assertEquals(3, searchCount);

		searchCount = testsTreeTab.search("with AND report");
		Assert.assertEquals(1, searchCount);

		searchCount = testsTreeTab.search("with OR report OR default");
		Assert.assertEquals(8, searchCount);

		testsTreeTab.search("");
	}

	@After
	public void cleanTest() {
		System.out.println("@After");

		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);

		System.out.println("Cleanning generated logs and properties files");
		JSystemTestUtils.cleanPropertiesAndLogs(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}

}

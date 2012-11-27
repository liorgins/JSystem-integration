package org.jsystemtest.integration;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.analyzer.AnalyzerException;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
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
	 * Tests that documentation of test and scenario is shown correctly in the
	 * information view. <br>
	 * <li>Select test in the test tree</li> <li>
	 * Assert that the information view shows the test documentation.</li>
	 * 
	 * <li>Create new scenario</li> <li>
	 * Add user documentation</li> <li>Select scenario in the tests tree</li>
	 * <li>Assert that the information view shows the scenario documentation.</li>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildingBlockInfoView() throws Exception {
		testTestDocumentation();
		testScenarioDocumentation();
	}

	/**
	 * Test user documentation
	 * 
	 * @throws Exception
	 * @throws AnalyzerException
	 */
	private void testTestDocumentation() throws Exception, AnalyzerException {
		System.out.println("Testing test documentation");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("", "");
		String bbInfo = testsTreeTab.getCurrentBuildingBlockInformation();

		// This is not exactly the text of the documentation. We are adding
		// spaces because the HTML edit component changes the string a little
		// bit.
		bbInfo = bbInfo.replaceAll("\n", "");
		final String expectedTestDocumentation = "This     is the documentation of test testWithDocumentations  ";
		Assert.assertEquals(expectedTestDocumentation, bbInfo);
	}
	
	/**
	 * Tests scenario user documentation
	 */
	private void testScenarioDocumentation() throws Exception {
		System.out.println("Testing scenario documentation");
		final String subScenarioName = "scenarioWithUserDoc";
		app.createScenario(subScenarioName);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "TestsExamples", 3);
		final String userDoc = "scenario expected documentation";
		app.getTestTableController().getScenarioTree().selectTestByRow(0);
		app.getTestsTreeController().getTestInfoTab().setTestUserDocumentation(userDoc);
		app.getToolBar().pushSaveScenarioButton();
		
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("", "");
		String bbInfo = testsTreeTab.getCurrentBuildingBlockInformation();

		// This is not exactly the text of the documentation. We are adding
		// spaces because the HTML edit component changes the string a little
		// bit.
		bbInfo = bbInfo.replaceAll("\n", "");
		final String expectedTestDocumentation = "scenario     expected documentation  ";
		Assert.assertEquals(expectedTestDocumentation, bbInfo);
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


}

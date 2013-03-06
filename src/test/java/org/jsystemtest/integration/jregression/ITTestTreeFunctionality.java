package org.jsystemtest.integration.jregression;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class ITTestTreeFunctionality extends AbstractITJSystem {

	final String expectedTestDocumentation = "Test     with javadoc";
	final String expectedScenarioUserDoc = "scenario     expected documentation";
	final String subScenarioName = "scenarioWithUserDoc";
	final String userDoc = "scenario expected documentation";

	/**
	 * Tests javadoc and user documentation
	 * 
	 * @see analyzeTestDocumentation() documentation.<br>
	 * @see analyzeScenarioDocumentation() documentation.<br>
	 * @throws Exception
	 */
	@Test
	public void buildingBlockInfoView() throws Exception {
		analyzeTestDocumentation();
		analyzeScenarioDocumentation();
	}

	/**
	 * 1. filter search "report" and assert count.<br>
	 * 2. filter search "with" and assert count.<br>
	 * 3. filter search "with AND report" and assert count.<br>
	 * 4. filter search "with OR report OR default" and assert count.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void searchBox() throws Exception {

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		int searchCount = testsTreeTab.search("report");
		Assert.assertEquals(searchCount, 5);

		searchCount = testsTreeTab.search("with");
		Assert.assertEquals(searchCount, 5);

		searchCount = testsTreeTab.search("with AND report");
		Assert.assertEquals(searchCount, 1);

		searchCount = testsTreeTab.search("with OR report OR default");
		Assert.assertEquals(searchCount, 9);
		testsTreeTab.search("");
	}

	/**
	 * 1. select test with javadoc.<br>
	 * 2. get building block information and adjust text .<br>
	 * 3. assert that the information contain the javadoc text.<br>
	 * 
	 * @throws Exception
	 */
	private void analyzeTestDocumentation() throws Exception {

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("testWithJavadoc", "Example");

		String bbInfo = testsTreeTab.getCurrentBuildingBlockInformation();
		bbInfo = bbInfo.replaceAll("\n", "");

		Assert.assertEquals(bbInfo.contains(expectedTestDocumentation), true);
	}

	/**
	 * 1. create subScenario and add 3 test that pass to it.<br>
	 * 2. select root scenario and set user documentation and save.<br>
	 * 3. select the the subScenario again and get building block information.<br>
	 * 4. assert information contains the user documentation.<br>
	 */
	private void analyzeScenarioDocumentation() throws Exception {

		app.createScenario(subScenarioName);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 3);

		app.getTestTableController().getScenarioTree().selectTestByRow(0);
		app.getTestsTreeController().getTestInfoTab().setTestUserDocumentation(userDoc);
		app.getToolBar().pushSaveScenarioButton();

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode(subScenarioName, "scenarios");
		String bbInfo = testsTreeTab.getCurrentBuildingBlockInformation();

		bbInfo = bbInfo.replaceAll("\n", "");
		boolean contains = bbInfo.contains(expectedScenarioUserDoc);
		Assert.assertEquals(contains, true);
	}

	@AfterMethod
	public void clearSearchBox() throws Exception {
		app.getTestsTreeController().getTestsTreeTab().search("");
	}
	
}

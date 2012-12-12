package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.TestType;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTableController;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITScenarioAs extends AbstractITJSystem {

	private static String sonScenario = "ScenarioAsTest_SON";
	private static String parentScenario = "ScenarioAsTest_PARENT";

	/**
	 * 1. create son scenario and add 3 test to it.<br/>
	 * 2. create parent scenario and add the son scenario to it.<br/>
	 * 
	 * @throws Exception
	 */
	@Before
	public void classFixture() throws Exception {
		System.out.println("**************** Running Class fixture for ITScenarioAsTest");
		app.createScenario(sonScenario);
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);

		app.createScenario(parentScenario);
		testsTreeTab.addTest(sonScenario, TestType.SCENARIO.getType(), 1);
	}

	/**
	 * 1.open sonScenario and add test that fail to it.<br>
	 * 2.open parentSenario mark it as test and as negative.<br>
	 * 3.push play and assert that the scenario is counted as one and passed.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkNegativeScenarioAs() throws Exception {

		app.openScenario(sonScenario);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportFailure", "Example", 1);
		app.getToolBar().pushSaveScenarioButton();

		app.openScenario(parentScenario);
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		scenarioTree.markScenarioAsTest(1, true);
		scenarioTree.markAsNegative(1, true);

		app.playAndWaitForRunEnd();
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsPass());
	}

	/**
	 * 1. analyze initial state.<br>
	 * 2. mark son scenario as test.<br>
	 * 3. analyze post state.<br>
	 * 
	 * @see addTestToSonAndAnalyze(boolean markedAsTest) for documentation
	 * 
	 * @throws Exception
	 * 
	 * 
	 */

	@Test
	public void checkAddingTestToScenarioAs() throws Exception {

		addTestToSonAndAnalyze(false);

		app.getTestTableController().getScenarioTree().markScenarioAsTest(1, true);
		app.getToolBar().pushSaveScenarioButton();

		addTestToSonAndAnalyze(true);
	}

	/**
	 * 1. count scenarios direct children.<br>
	 * 2. add test to the subScenario.<br>
	 * 3.count again and assert the result based on the scenario state.<br>
	 * 
	 * @param markedAsTest
	 *            - boolean flag represent the state of the scenario
	 * @throws Exception
	 */
	private void addTestToSonAndAnalyze(boolean markedAsTest) throws Exception {

		app.openScenario(parentScenario);
		TestsTableController testTableController = app.getTestTableController();
		ScenarioTree scenarioTree = testTableController.getScenarioTree();
		int initialParentCount = scenarioTree.getScenarioDirectChildrenCount(0);
		int initialSonCount = scenarioTree.getScenarioDirectChildrenCount(1);

		scenarioTree.selectTestByRow(1);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 1);
		app.getToolBar().pushSaveScenarioButton();

		int newParentCount = scenarioTree.getScenarioDirectChildrenCount(0);
		int newSonCount = scenarioTree.getScenarioDirectChildrenCount(1);

		if (markedAsTest) {
			Assert.assertEquals(initialParentCount + 1, newParentCount);
		} else {
			Assert.assertEquals(initialParentCount, newParentCount);
		}
		if (markedAsTest) {
			Assert.assertEquals(initialSonCount, newSonCount);
		} else {
			Assert.assertEquals(initialSonCount + 1, newSonCount);
		}
	}

	/**
	 * 1.open sonSenario, unMark the first test, run tests and analyze result.<br>
	 * 2.get both children count of son and parent scenario, run test and analyze result.<br>
	 * 3.mark sonScenario as test ,unmap it, and check parent child count as expected.<br>
	 * 4.map the sonScenario, run and analyze that children count as expected.<br>
	 * 5.unmark sonScenario as test, unmap it, and check parent count again.<br>
	 * 6.map the sonScenario and make sure count as expected by all scenarios children.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkMappingAndTestCount() throws Exception {
		app.openScenario(sonScenario);
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		int numOfSonTests = scenarioTree.getScenarioDirectChildrenCount(0);
		scenarioTree.mapTest(1, false, false);
		numOfSonTests--;
		playAnalyze(numOfSonTests);

		app.openScenario(parentScenario);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 1);
		app.getToolBar().pushSaveScenarioButton();
		int numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		numOfSonTests = scenarioTree.getScenarioDirectChildrenCount(1);
		Assert.assertEquals(4, numOfParentTests - 1 + numOfSonTests);

		scenarioTree.markScenarioAsTest(1, true);
		scenarioTree.mapTest(1, false, false);
		numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		playAnalyze(numOfParentTests - 1);

		scenarioTree.mapTest(1, true, false);
		numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		playAnalyze(numOfParentTests);

		scenarioTree.markScenarioAsTest(1, false);
		scenarioTree.mapTest(1, false, true);
		playAnalyze(numOfParentTests - 1);

		scenarioTree.mapTest(1, true, true);
		numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		numOfSonTests = scenarioTree.getScenarioDirectChildrenCount(1);
		int totalTests = numOfParentTests - 1 + numOfSonTests;
		playAnalyze(totalTests);
	}

	/**
	 * 1. save current, push play and wait for execution to end.<br>
	 * 2. assert the number of test ran is as expected.<br>
	 * 
	 * @param expectedTests - Number of tests expected to run.
	 * @throws Exception
	 */
	private void playAnalyze(int expectedTest) throws Exception {

		app.getToolBar().pushSaveScenarioButton();
		app.getToolBar().pushPlayButton();
		app.waitForRunEnd();

		Assert.assertEquals(expectedTest, XmlReportHandler.getInstance().getNumberOfTests());
	}

	@After
	public void clean() {
		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}

}

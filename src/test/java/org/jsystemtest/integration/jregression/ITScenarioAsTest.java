package org.jsystemtest.integration.jregression;


import jsystem.extensions.report.xml.XmlReportHandler;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.jsystemtest.integration.TestType;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTableController;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.Test;


public class ITScenarioAsTest extends AbstractJSystemIT{
	
	private static String sonScenario = "ScenarioAsTest_SON";
	private static String parentScenario = "ScenarioAsTest_PARENT";
	
	
	/**
	 * 1. create son scenario and add 3 test to it.<br/>
	 * 2. create parent scenario and add the son scenario to it.<br/>
	 * 
	 * @throws Exception
	 */
	private void classFixture() throws Exception {
		System.out.println("**************** Running Class fixture for ITScenarioAsTest");
		app.createScenario(sonScenario);
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);
		
		app.createScenario(parentScenario);
		testsTreeTab.addTest(sonScenario,  TestType.SCENARIO.getType() , 1);
	}
	
	/**
	 * Check that adding a failed test to a son scenario and then marking it as negative works good
	 * @throws Exception 
	 */
	@Test
	public void checkNegativeScenarioAsTest() throws Exception {
		classFixture();
		app.openScenario(sonScenario);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportFailure", "Example", 1);
		app.getToolBar().pushSaveScenarioButton();
		
		app.openScenario(parentScenario);
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		scenarioTree.markScenarioAsTest(1,true);
		scenarioTree.markAsNegative(1, true);
		
		app.getToolBar().pushPlayButton();
		
		app.waitForRunEnd();
		int testpass = XmlReportHandler.getInstance().getNumberOfTestsPass();
		Assert.assertEquals("TESTS RUN - PASS: ", 1, testpass);
	}
	
	/**
	 * 1. analyze initial state<br/>
	 * 2. mark son scenario as test.<br/>
	 * 3. analyze post state.<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkAddingTestToScenarioTest() throws Exception{
		
		classFixture();
		
		addTestToSonAndAnalyze(false);
		
		app.getTestTableController().getScenarioTree().markScenarioAsTest(1, true);
		app.getToolBar().pushSaveScenarioButton();
		
		addTestToSonAndAnalyze(true);
	}
	
	private void addTestToSonAndAnalyze(boolean markedAsTest) throws Exception{
		classFixture();
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
			Assert.assertEquals(initialParentCount +1, newParentCount);
		}else{
			Assert.assertEquals(initialParentCount, newParentCount);
		}
		if (markedAsTest){
			Assert.assertEquals(initialSonCount, newSonCount);
		}else{
			Assert.assertEquals(initialSonCount + 1, newSonCount);
		}
	}

	
	/**
	 * Test the test map and unmap and that a ScenarioTest is counted as one test
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkMappingAndTestCount() throws Exception{
		classFixture();
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
		Assert.assertEquals (4, numOfParentTests -1 + numOfSonTests);
	
		scenarioTree.markScenarioAsTest(1, true);
		scenarioTree.mapTest(1, false, false);
		numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		playAnalyze(numOfParentTests -1);

		scenarioTree.mapTest(1, true, false);
		numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		playAnalyze(numOfParentTests);

		scenarioTree.markScenarioAsTest(1, false);
		scenarioTree.mapTest(1, false, true);
		playAnalyze(numOfParentTests - 1);
		
		scenarioTree.mapTest(1, true, true);
		numOfParentTests = scenarioTree.getScenarioDirectChildrenCount(0);
		numOfSonTests = scenarioTree.getScenarioDirectChildrenCount(1);
		int totalTests = numOfParentTests - 1 +numOfSonTests;
		playAnalyze(totalTests);
	}
	
	private void playAnalyze(int expectedTest) throws Exception{
		app.getToolBar().pushSaveScenarioButton();
		app.getToolBar().pushPlayButton();
		app.waitForRunEnd();
		int testExecuted = XmlReportHandler.getInstance().getNumberOfTests();
		Assert.assertEquals("TESTS RUN - Executed: ", expectedTest, testExecuted);
	}

	@After
	public void cleanTest() {
		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}
	
}


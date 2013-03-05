package org.jsystemtest.integration.jregression;



import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.TestType;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTableController;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITNavigateToSubScenario extends AbstractITJSystem {


	private final String scenario1 = "Scenario1";
	private final String scenario2 = "Scenario2";
	private final String scenario3 = "Scenario3";
	private final String scenario4 = "Scenario4";
	private final String midScenario = "midSceanrio";

	@BeforeMethod
	public void classfixture() throws Exception {
		app.createScenario(scenario1);
		app.createScenario(scenario2);
		app.createScenario(scenario3);
		app.createScenario(scenario4);
	}

	/**
	 * 1. select the root scenario and check that it is scenario4 and then make
	 * sure the<br/>
	 * navigate left button is enable, and that navigate right button is
	 * disabled.<br/>
	 * 2. move to scenario2 make sure it's scenario2 and and check that both
	 * right and left<br/>
	 * navigate buttons are enabled.<br/>
	 * 3. push the right navigate button once and check that scenario is
	 * scenario3 and both buttons are enabled.<br/>
	 * 4. push the right navigate button once and check that scenario is
	 * scenario4 and right button is disabled.<br/>
	 * 5. push the left navigate button twice and check that scenario is
	 * scenario2 and both buttons are enabled.<br/>
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void moveBetweenCreatedScenarios() throws Exception {

		TestsTableController testsTableController = app.getTestTableController();
		testsTableController.getScenarioTree().selectTestByRow(0);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(), scenario4);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.BACKWARD), true);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.FORWARD), false);

		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(),scenario2);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.BACKWARD), true);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.FORWARD), true);

		testsTableController.pushNavigateToScenarioButton(TestsTableController.FORWARD);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(),scenario3);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.BACKWARD), true);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.FORWARD), true);

		testsTableController.pushNavigateToScenarioButton(TestsTableController.FORWARD);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(), scenario4);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.BACKWARD), true);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.FORWARD), false);

		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(), scenario2);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.BACKWARD), true);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.FORWARD), true);
	}

	/**
	 * 1. to two steps back to scenario 2 and check it's really 2 as expected.<br>
	 * 2. create a scenario midScenario, and check that scenario3 and scenario4
	 *    can't be navigated to anymore.<br>
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void goToScenario2AndCreateNewScenarioAndCheckThat3and4NoLongerExist() throws Exception {

		TestsTableController testsTableController = app.getTestTableController();

		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		
		Assert.assertEquals(testsTableController.getCurrentScenarioName(), scenario2);

		app.createScenario(midScenario);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.FORWARD), false);
		Assert.assertEquals(testsTableController.isNavigateButtonEnabled(TestsTableController.BACKWARD), true);

		testsTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(), scenario2);

		testsTableController.pushNavigateToScenarioButton(TestsTableController.FORWARD);
		Assert.assertEquals(testsTableController.getCurrentScenarioName(), midScenario);
	}

	/**
	 * 1. open scenario1 and add to it as sub scenario the scenario2.<br>
	 * 2. select scenario2 and choose, Navigate to sub scenario.<br>
	 * 3. check that current scenario opened is scenario2 as expected.<br>
	 * 4. navigate left once and check that current scenario is scenario1.<br> 
	 * 5. navigate right once and check that current scenario is scenario2.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void createScenarioInSceanrioSelectChildScenarioAndNavigateToSubScenario() throws Exception {

		app.openScenario(scenario1);
		TestsTableController testTableController = app.getTestTableController();
		ScenarioTree scenarioTree = testTableController.getScenarioTree();
		scenarioTree.selectTestByRow(0);
		app.getTestsTreeController().getTestsTreeTab().addTest(scenario2, TestType.SCENARIO.getType(), 1);
		app.getToolBar().pushSaveScenarioButton();

		scenarioTree.selectTestByRow(1);
		scenarioTree.navigateToSubScenario(1);

		Assert.assertEquals(testTableController.getCurrentScenarioName(), scenario2);

		testTableController.pushNavigateToScenarioButton(TestsTableController.BACKWARD);
		Assert.assertEquals(testTableController.getCurrentScenarioName(), scenario1);

		testTableController.pushNavigateToScenarioButton(TestsTableController.FORWARD);
		Assert.assertEquals(testTableController.getCurrentScenarioName(), scenario2);
	}
	
	@AfterMethod
	public void cleanGeneratedScenarios() {
		Reporter.log("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}

}

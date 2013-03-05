package org.jsystemtest.integration.reportserver;

import java.util.List;
import java.util.Map;
import java.util.Random;



import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestInfoTab;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITPublisherNotificationEvent extends AbstractITJSystem {

	private static String uniqeIdentifier = "";
	

	/**
	 * get unique identifier to use in each test
	 * 
	 * @throws Exception
	 */
	@BeforeMethod
	public void fixture() throws Exception {
		uniqeIdentifier = String.valueOf(System.currentTimeMillis());
		app.createScenario(uniqeIdentifier);
	}

	/**
	 * 1. create new scenario with unique identifier 1 create new sut with the unique name.<br/>
	 * 2. add notification event to scenario tree.<br/>
	 * 3. set notification event build and version to the unique string.<br/>
	 * 4. play scenario and wait for execution to end.<br/>
	 * 5. query the datebase and assert that version, build and sut were published.<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void publisherBuildVersionSut() throws Exception {

		app.addSutFile(uniqeIdentifier);

		app.getToolBar().selectSUTFile(uniqeIdentifier + ".xml");

		app.getTestTableController().pushAddNotificationEvent();

		app.getTestTableController().getScenarioTree().selectTestByRow(1);
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();
		testInfoTab.setTestParameter("general", "Build", String.valueOf(uniqeIdentifier), false);
		testInfoTab.setTestParameter("general", "Version", String.valueOf(uniqeIdentifier), false);

		app.playAndWaitForRunEnd();

		Assert.assertNotSame(db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqeIdentifier + "' and propertyKey='Build'").size(), 0);
		Assert.assertNotSame(db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqeIdentifier + "' and propertyKey='Version'").size(), 0);
		Assert.assertNotSame(db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqeIdentifier + ".xml' and propertyKey='setupName'").size(), 0);

	}

	/**
	 * 1. add 3 kind of tests to scenario, success, fail and warn.<br/>
	 * 2. add notification event. <br/>
	 * 3. play scenario and assert the database for the result.<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkPublishWithDifferentTypesOfTest() throws Exception {

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 1);
		testsTreeTab.addTest("reportFailure", "Example", 3);
		testsTreeTab.addTest("reportWarning", "Example", 3);
		

		app.getTestTableController().pushAddNotificationEvent();

		app.playAndWaitForRunEnd();

		String query = "SELECT * FROM jsystem.published_runs_01 WHERE scenarioName='" + uniqeIdentifier + "'";
		List<Map<String, Object>> resultList = db.getResultList(query);
		Assert.assertNotNull(resultList);
		Assert.assertEquals(1, db.getResultList(query).size());
		Assert.assertEquals((Integer) resultList.get(0).get("runTest"), new Integer(6));
		Assert.assertEquals((Integer) resultList.get(0).get("failTests"), new Integer(3));
		Assert.assertEquals((Integer) resultList.get(0).get("warningTests"), new Integer(2));
		Assert.assertEquals((Integer) resultList.get(0).get("successTests"),new Integer(1));

	}
	
	

	@Test
	public void simplePublish() throws Exception {

		String[] results = {"success", "failure", "warning"};
		
		app.createScenario("scenariofirst");
		
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("testDynamivResultByParam", "Example", 16);
	
		
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		Random rand = new Random();
		
		for(int i=1; i< 17; i++) {
			scenarioTree.selectTestByRow(i);
		
			scenarioTree.updateMeaningfulName(i, "testNumber " + i);
			
			
			testInfoTab.setTestParameter("general", "Result",results[rand.nextInt(3)], false);
		
		}
		app.getTestTableController().pushAddNotificationEvent();
		app.getTestTableController().pushAddNotificationEvent();

		
		app.getToolBar().pushSaveScenarioButton();
		
		app.playAndWaitForRunEnd();
		
		System.out.println("wait here in debug");
	}

	/**
	 * 1. add test that pass and pushing notification event to the scenario.<br/>
	 * 2. run the test and publishing the results (no verification).<br/>
	 * 3. waiting for the idle timeout to pass and publish again.<br/>
	 * 4. verifying that both published events actually published.<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkReportServerAliveAfterShortIdleTime() throws Exception {

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();

		testsTreeTab.addTest("reportSuccess", "Example", 1);

		app.getTestTableController().pushAddNotificationEvent();

		app.playAndWaitForRunEnd();
		Thread.sleep(70000);
		app.playAndWaitForRunEnd();

		String query = "SELECT * FROM jsystem.published_runs_01 WHERE scenarioName='" + uniqeIdentifier + "'";
		List<Map<String, Object>> resultList = db.getResultList(query);
		Assert.assertNotNull(resultList);
		Assert.assertEquals(db.getResultList(query).size(), 2);

	}

	/**
	 * 1. create new scenario with unique identifier 1 create new sut with the unique name.<br/>
	 * 2. add notification event to scenario tree.<br/>
	 * 3. set notification event build and version to the unique string.<br/>
	 * 4. play scenario and wait for execution to end.<br/> 
	 * 5. query the datebase and assert that version, build and sut were published.<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void publisherExecutionPropertiesAndDescription() throws Exception {

		app.getTestTableController().pushAddNotificationEvent();

		app.getTestTableController().getScenarioTree().selectTestByRow(1);
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();

		String key1 = uniqeIdentifier + "_k1";
		String key2 = uniqeIdentifier + "_k2";
		String val1 = uniqeIdentifier + "_v1";
		String val2 = uniqeIdentifier + "_v2";

		StringBuilder sb = new StringBuilder();
		sb.append(key1).append("=").append(val1).append(";").append(key2).append("=").append(val2);
		testInfoTab.setTestParameter("publish", "ExecutionPropertiesStr", sb.toString(), false);
		testInfoTab.setTestParameter("Publish", "Description", uniqeIdentifier, false);

		app.playAndWaitForRunEnd();

		Assert.assertEquals(db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyKey='" + key1 + "' and propertyValue='" + val1 + "'").size(), 1);
		Assert.assertEquals(db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyKey='" + key2 + "' and propertyValue='" + val2 + "'").size(), 1);
		Assert.assertEquals(db.getResultList("SELECT * FROM jsystem.published_runs_01 WHERE description='" + uniqeIdentifier + "'").size(), 1);
	}
	
	/**
	 * 1. add 3 kind of tests to scenario, success, fail and warn.<br/>
	 * 2. add notification event. <br/>
	 * 3. play scenario and assert the database for the result.<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkPublishWithDifferentTestsTwoRuns() throws Exception {

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 1);
	
		testsTreeTab.addTest("reportWarning", "Example", 2);
		

		app.getTestTableController().pushAddNotificationEvent();

		app.playAndWaitForRunEnd();

	}
	
	
}

package org.jsystemtest.integration.reportserver;

import java.util.List;
import java.util.Map;

import jsystem.extensions.report.html.Report;
import jsystem.runner.agent.publisher.Publisher;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.TestInfoTab;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.Before;
import org.junit.Test;



public class ITPublisherNotificationEvent extends AbstractITJSystem {
	
	private static String uniqeIdentifier = "";
	
	/**
	 *  get unique identifier to use in each test
	 * 
	 * @throws Exception
	 */
	@Before
	public void fixture() throws Exception {
		uniqeIdentifier = String.valueOf(System.currentTimeMillis());
		app.createScenario(uniqeIdentifier);
	}
	
	/**
	 * 1. create new scenario with unique identifier
	 * 1  create new sut with the unique name.	 
	 * 2. add notification event to scenario tree.
	 * 3. set notification event build and version to the unique string.
	 * 4. play scenario and wait for execution to end.
	 * 5. query the datebase and assert that version, build and sut were published.
	 * 
	 * @throws Exception
	 */
	@Test
	public void publisherBuildVersionSut() throws Exception {

		app.addSutFile(uniqeIdentifier);
		
		app.getToolBar().selectSUTFile(uniqeIdentifier+".xml");
		
		app.getTestTableController().pushAddNotificationEvent();
		
		app.getTestTableController().getScenarioTree().selectTestByRow(1);
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();
		testInfoTab.setTestParameter("general", "Build", String.valueOf(uniqeIdentifier), false);
		testInfoTab.setTestParameter("general", "Version", String.valueOf(uniqeIdentifier), false);
		
		app.playAndWaitForRunEnd();
		
		Assert.assertNotSame(0, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqeIdentifier + "' and propertyKey='Build'").size());
		Assert.assertNotSame(0, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqeIdentifier + "' and propertyKey='Version'").size());
		Assert.assertNotSame(0, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqeIdentifier + ".xml' and propertyKey='setupName'").size());
	}
	
	/**
	 * 1. add 3 kind of tests to scenario, success, fail and warn.
	 * 2. add notification event.
	 * 3. play scenario and assert the database for the result.
	 * 4. assert scenario name is published.
	 * 5. assert test result published correctly. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkPublishedRunIsCorrect() throws Exception {
		
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportFailure", "Example", 3);
		testsTreeTab.addTest("reportWarning", "Example", 2);
		testsTreeTab.addTest("reportSuccess", "Example", 1);
	
		app.getTestTableController().pushAddNotificationEvent();
		
		app.playAndWaitForRunEnd();
		String query = "SELECT * FROM jsystem.published_runs_01 WHERE scenarioName='" + uniqeIdentifier + "'";
		List<Map<String, Object>> resultList = db.getResultList(query);
		Assert.assertNotNull(resultList);
		Assert.assertEquals(1, db.getResultList(query).size());
		
		Assert.assertEquals(new Integer(6), (Integer)resultList.get(0).get("runTest"));
		Assert.assertEquals(new Integer(3), (Integer)resultList.get(0).get("failTests"));
		Assert.assertEquals(new Integer(2), (Integer)resultList.get(0).get("warningTests"));
		Assert.assertEquals(new Integer(1), (Integer)resultList.get(0).get("successTests"));
	}
	
	/**
	 * 1. create new scenario with unique identifier
	 * 1  create new sut with the unique name.	 
	 * 2. add notification event to scenario tree.
	 * 3. set notification event build and version to the unique string.
	 * 4. play scenario and wait for execution to end.
	 * 5. query the datebase and assert that version, build and sut were published.
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
		sb.append(key1);
		sb.append("=");
		sb.append(val1);
		sb.append(";");
		sb.append(key2);
		sb.append("=");
		sb.append(val2);
		testInfoTab.setTestParameter("publish", "ExecutionPropertiesStr",sb.toString(), false);
		testInfoTab.setTestParameter("Publish", "Description", uniqeIdentifier, false);
		
		app.playAndWaitForRunEnd();
		
		Assert.assertEquals(1, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyKey='" + key1 + "' and propertyValue='" + val1 + "'").size());
		Assert.assertEquals(1, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyKey='" + key2 + "' and propertyValue='" + val2 + "'").size());
		Assert.assertEquals(1, db.getResultList("SELECT * FROM jsystem.published_runs_01 WHERE description='" + uniqeIdentifier + "'").size());
	}
	
	
	
	
	
	

}

package org.jsystemtest.integration.reportserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.TestInfoTab;
import org.junit.Before;
import org.junit.Test;

public class ITPublisherNotificationEvent extends AbstractITJSystem {
	
	/**
	 * 1. open the default scenario.
	 * 2. clear it.
	 * 3. add 5 tests that pass to scenario.
	 * 
	 * @throws Exception
	 */
	@Before
	public void fixture() throws Exception {
		
		
		app.openScenario("default");
		
		app.clearCurrentRootScenario("default");
		
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 1);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportFailure", "Example", 1);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportWarning", "Example", 1);
	}
	
	/**
	 * 1. add notification event to scenario tree.
	 * 2. play scenario and wait for execution to end.
	 * 
	 * @throws Exception
	 */
	@Test
	public void addNotificationEventTry() throws Exception {
		
		app.getTestTableController().pushAddNotificationEvent();
		
		app.playAndWaitForRunEnd();
		
		int rowCount  = db.countRowsInTable("published_runs_01");
		System.out.println(rowCount);
		
	}
	/**
	 * /**
	 * 1. create unique string for sut name, build and version values.
	 * 2  create new sut with the unique name.	 
	 * 3. add notification event to scenario tree.
	 * 4. set notification event build and version to the unique string.
	 * 5. play scenario and wait for execution to end.
	 * 6. query the datebase and assert that version and build were published.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPublisherBuildAndVersion() throws Exception {

		String uniqueValue = String.valueOf(System.currentTimeMillis());
		
		app.getToolBar().createNewSUTFile(uniqueValue);
		
		app.getTestTableController().pushAddNotificationEvent();
		
		app.getTestTableController().getScenarioTree().selectTestByRow(4);
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();
		testInfoTab.setTestParameter("general", "Build", String.valueOf(uniqueValue), false);
		testInfoTab.setTestParameter("general", "Version", String.valueOf(uniqueValue), false);
		
		app.playAndWaitForRunEnd();
		
		Assert.assertNotSame(0, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqueValue + "' and propertyKey='Build'").size());
		Assert.assertNotSame(0, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqueValue + "' and propertyKey='Version'").size());
		Assert.assertNotSame(0, db.getResultList("SELECT * FROM jsystem.scenario_properties WHERE propertyValue='" + uniqueValue + ".xml' and propertyKey='setupName'").size());
	}
	
	

}

package org.jsystemtest.integration.reportserver;

import org.jsystemtest.integration.AbstractITJSystem;
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

}

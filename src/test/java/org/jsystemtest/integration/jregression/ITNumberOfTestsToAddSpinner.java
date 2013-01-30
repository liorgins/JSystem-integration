package org.jsystemtest.integration.jregression;

import jsystem.framework.TestProperties;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.junit.Before;
import org.junit.Test;

public class ITNumberOfTestsToAddSpinner extends AbstractITJSystem {

	/**
	 * 1. open the default scenario.
	 * 2. clear it.
	 * 
	 * @throws Exception
	 */
	@Before
	public void classFixture() throws Exception {
		
		app.openScenario("default");
		
		app.clearCurrentRootScenario("default");
		
	}
	
	/**
	 * Tests number of test to add JSpinner
	 * 
	 * 1. Add one test 20 times
	 * 2. Add one test 10 times
	 * 3. Assert that the test appears 30 times in the scenario
	 * @throws Exception
	 */
	@Test
	@TestProperties(name = "test num of tests to be added to the scenario tree")
	public void checkNumOfTestsToAddSpinner() throws Exception {
		
		System.out.println("Adding test 20 times");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 20);
		
		System.out.println("Adding test 10 times");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 10);
	
		Assert.assertEquals(30, app.getTestTableController().getScenarioTree().getScenarioDirectChildrenCount(0));
	}
}

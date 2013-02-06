package org.jsystemtest.integration.jregression;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITNumberOfTestsToAddSpinner extends AbstractITJSystem {

	/**
	 * 1. open the default scenario. 2. clear it.
	 * 
	 * @throws Exception
	 */
	@BeforeMethod
	public void classFixture() throws Exception {

		app.openScenario("default");

		app.clearCurrentRootScenario("default");

	}

	/**
	 * Tests number of test to add JSpinner
	 * 
	 * 1. Add one test 20 times 2. Add one test 10 times 3. Assert that the test
	 * appears 30 times in the scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkNumOfTestsToAddSpinner() throws Exception {
		Reporter.log("Adding test 20 times");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 20);

		Reporter.log("Adding test 10 times");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 10);

		Assert.assertEquals(app.getTestTableController().getScenarioTree().getScenarioDirectChildrenCount(0), 30);
	}
}

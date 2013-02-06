package org.jsystemtest.integration.jregression;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITPlayButtonFunctionality extends AbstractITJSystem {
	
	/**
	 * 1. open the default scenario.
	 * 2. clear it.
	 * 3. add 5 tests that pass to scenario.
	 * 
	 * @throws Exception
	 */
	@BeforeMethod
	public void fixture() throws Exception {
		
		app.openScenario("default");
		
		app.clearCurrentRootScenario("default");
		
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 5);
	}
	
	/**
	 * 1. check that play button is enabled
	 * 2. delete the tests 
	 * 3. check that play button is disabled
	 * 
	 * @throws Exception 
	 */
	@Test
	public void checkPlayButtonEnabledDisabled() throws Exception{
		
		Assert.assertEquals(app.getToolBar().isPlayButtonEnable(), true);

		app.getTestTableController().removeAllTests();

		Assert.assertEquals(app.getToolBar().isPlayButtonEnable(), false);
	
	}
	
}

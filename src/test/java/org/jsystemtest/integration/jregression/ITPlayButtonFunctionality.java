package org.jsystemtest.integration.jregression;

import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.junit.Before;
import org.junit.Test;


public class ITPlayButtonFunctionality extends AbstractJSystemIT {
	
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
		
		Assert.assertEquals(true, app.getToolBar().isPlayButtonEnable());

		app.getTestTableController().removeAllTests();

		Assert.assertEquals(false, app.getToolBar().isPlayButtonEnable());
	}
	
}

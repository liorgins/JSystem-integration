package org.jsystemtest.integration.jregression;

import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.junit.Test;


public class ITPlayButtonFunctionality extends AbstractJSystemIT {
	
	/**
	 * 1. add 5 test to scenario tree and check that 5 test are checked
	 * 2. check that play button is enabled
	 * 3. delete the tests 
	 * 4. check that play button is disabled
	 * 
	 * @throws Exception 
	 */
	@Test
	public void checkPlayButtonEnabledDisabled() throws Exception{
		
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 5);
		
		Assert.assertEquals(true, app.getToolBar().isPlayButtonEnable());

		app.getTestTableController().removeAllTests();

		Assert.assertEquals(false, app.getToolBar().isPlayButtonEnable());
	}
	
}

package org.jsystemtest.integration.jregression;



import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class ITFlowControl extends AbstractITJSystem {


	/**
	 * 1. get "Flow Control Toolbar" start state.<br>
	 * 2. change "Flow Control Toolbar" state.<br>
	 * 3. get "Flow Control Toolbar" end state after change.<br>
	 * 4. assert start and end states not equal.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void FlowControlToolBar() throws Exception {
		
		boolean startState = app.getMenuBar().getViewMenu().getToolbarsMenu().getToolbarState("Flow Control Toolbar");
	
		app.getMenuBar().getViewMenu().getToolbarsMenu().changeToolbarState("Flow Control Toolbar");
		
		boolean endState = app.getMenuBar().getViewMenu().getToolbarsMenu().getToolbarState("Flow Control Toolbar");
		
		Assert.assertEquals(startState == endState, false);
		
	}
	
	@AfterMethod
	public void clean() {
		Reporter.log("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}
	
}

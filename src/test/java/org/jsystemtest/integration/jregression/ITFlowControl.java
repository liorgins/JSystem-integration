package org.jsystemtest.integration.jregression;

import jsystem.framework.TestProperties;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.junit.Test;

public class ITFlowControl extends AbstractITJSystem {

	/**
	 * @throws Exception
	 */
	@Test
	@TestProperties(name = "Test Flow Control toolbar enable/disable")
	public void FlowControlToolBar() throws Exception {
		
	
		
		boolean startState = app.getMenuBar().getViewMenu().getToolbarsMenu().getToolbarState("Flow Control Toolbar");
		
		app.getMenuBar().getViewMenu().getToolbarsMenu().changeToolbarState("Flow Control Toolbar");
		
		boolean endState = app.getMenuBar().getViewMenu().getToolbarsMenu().getToolbarState("Flow Control Toolbar");
		
		
		Assert.assertEquals(false, startState == endState );
		System.out.println("");
		
		//app.getMenuBar().getViewMenu().getToolBars().changeToolbarState("Flow Control Toolbar");
		
		
//		report.step("Get the new state of the toolbar");
//		Boolean endState = (boolean)applicationClient.getToolbarViewState(JsystemMapping.getInstance().getFlowControlToolbar());
//		Boolean endSeenState = (boolean)applicationClient.getFlowControlToolbarState();
//		
//		report.step("Verify the toolbar change states");
//		// Verify the checkbox and the toolbar status are the same
//		analyzer.setTestAgainstObject(startState);
//		analyzer.analyze(new CompareValues(startSeenState));
//		
//		// Verify the state has changed
//		analyzer.analyze(new CompareValues(!endState));
//		
//		// Verify the checkbox and the toolbar status are the same
//		analyzer.setTestAgainstObject(endState);
//		analyzer.analyze(new CompareValues(endSeenState));
	}
	
}

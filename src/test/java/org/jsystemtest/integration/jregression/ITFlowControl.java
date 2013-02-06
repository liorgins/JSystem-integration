package org.jsystemtest.integration.jregression;

import jsystem.framework.TestProperties;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.junit.Test;

public class ITFlowControl extends AbstractITJSystem {


	/**
	 * 1. get "Flow Control Toolbar" start state.
	 * 2. change "Flow Control Toolbar" state.
	 * 3. get "Flow Control Toolbar" end state after change.
	 * 4. assert start and end states not equal.
	 * 
	 * @throws Exception
	 */
	@Test
	@TestProperties(name = "Test Flow Control toolbar enable/disable")
	public void FlowControlToolBar() throws Exception {
		
		boolean startState = app.getMenuBar().getViewMenu().getToolbarsMenu().getToolbarState("Flow Control Toolbar");
	
		app.getMenuBar().getViewMenu().getToolbarsMenu().changeToolbarState("Flow Control Toolbar");
		
		boolean endState = app.getMenuBar().getViewMenu().getToolbarsMenu().getToolbarState("Flow Control Toolbar");
		
		Assert.assertEquals(false, startState == endState );
		
	}
	
}

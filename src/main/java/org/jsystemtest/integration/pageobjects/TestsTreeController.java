package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;


public class TestsTreeController extends AbstractPageObject{
	
	private JTabbedPaneOperator jTabbedPaneOperator;

		
	public TestsTreeController(JFrameOperator app) {	
		jTabbedPaneOperator = new JTabbedPaneOperator(app, 0);
		
	}
	
	public TestsTreeTab getTestsTreeTab() {
		return new TestsTreeTab(jTabbedPaneOperator);
	}
	
	
	public TestsInfoTab getTestsInfoTab() {
		return new TestsInfoTab(jTabbedPaneOperator);
	}
	
	
}

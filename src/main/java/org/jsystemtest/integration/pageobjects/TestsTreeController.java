package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;


public class TestsTreeController extends AbstractPageObject{
	
	private static JTabbedPaneOperator jTabbedPaneOperator;

		
	public TestsTreeController(JFrameOperator app) {	
		jTabbedPaneOperator = new JTabbedPaneOperator(app, 0);
		
	}
	
	public TestsTreeTab getTestsTreeTab() {
		return new TestsTreeTab(jTabbedPaneOperator);
	}
	
	public ReporterTab getReporterTab() {
		return new ReporterTab(jTabbedPaneOperator);
	}
	
	public TestInfoTab getTestInfoTab() {
		return new TestInfoTab(jTabbedPaneOperator);
	}
		
}

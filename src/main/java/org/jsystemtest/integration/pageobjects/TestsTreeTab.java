package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JSplitPaneOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;


public class TestsTreeTab extends AbstractPageObject {
	
	private JSplitPaneOperator jSplitPaneOperator; 
	
	public TestsTreeTab(JTabbedPaneOperator jTabbedPaneOperator) {
		
		jSplitPaneOperator = new JSplitPaneOperator(jTabbedPaneOperator, 0);
	}
	
	public TestsTree getTestTree() {
		return new TestsTree(jSplitPaneOperator);
	}
	
	public void pushAddTestsButton() {
		new JButtonOperator(jSplitPaneOperator, new TooltipChooser(jmap.getAddTestsButton())).pushNoBlock();
	}
}

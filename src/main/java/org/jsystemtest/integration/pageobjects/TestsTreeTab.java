package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JSpinnerOperator;
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
		new EventTool().waitNoEvent(1000);
	}
	
	public void setTestsNumberSpinner(int num) {
		JSpinnerOperator jSpinnerOperator = new JSpinnerOperator(jSplitPaneOperator);
		if(jSpinnerOperator.isEnabled() && num > 0) {
			jSpinnerOperator.setValue(num);
			
			//			for(int i=0; i < num; i++) {
//				jSpinnerOperator.getIncreaseOperator().clickMouse();	
//			}
		}
	}
	
	public void search(final String textToSearch) throws Exception {
		JComboBoxOperator filter = new JComboBoxOperator(jSplitPaneOperator, new TooltipChooser(jmap.getFilterToolTip()));
		filter.addItem(textToSearch);
		filter.selectItem(textToSearch);
		new EventTool().waitNoEvent(1000);
	}
}
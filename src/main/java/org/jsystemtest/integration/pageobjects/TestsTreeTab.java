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
	
	public void pushAddTestsButton() throws InterruptedException {
		new JButtonOperator(jSplitPaneOperator, new TooltipChooser(jmap.getAddTestsButton())).clickMouse();
		Thread.sleep(1000);
	}
	
	public void setTestsNumberSpinner(int num) {
		JSpinnerOperator jSpinnerOperator = new JSpinnerOperator(jSplitPaneOperator);
		if(jSpinnerOperator.isEnabled() && num > 0) {
			jSpinnerOperator.setValue(num);
		}
	}
	
	public void search(final String textToSearch) throws Exception {
		JComboBoxOperator filter = new JComboBoxOperator(jSplitPaneOperator, new TooltipChooser(jmap.getFilterToolTip()));
		filter.addItem(textToSearch);
		filter.selectItem(textToSearch);
		Thread.sleep(1000);
	}
	
	public void addTest(String node, String parentNode, int amount) throws Exception {
		getTestTree().selectPathByNodeAndParentNode(node, parentNode);
		setTestsNumberSpinner(amount);
		pushAddTestsButton();
	}
}

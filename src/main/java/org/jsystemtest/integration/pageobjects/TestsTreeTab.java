package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JSpinnerOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;


public class TestsTreeTab extends AbstractPageObject {
	
	private JTabbedPaneOperator testsTreeTab; 
	
	public TestsTreeTab(JTabbedPaneOperator jTabbedPaneOperator) {
		testsTreeTab = jTabbedPaneOperator;
	}
	
	public TestsTree getTestTree() {
		testsTreeTab.selectPage(0);
		return new TestsTree(testsTreeTab);
	}
	
	public void pushAddTestsButton() throws InterruptedException {
		testsTreeTab.selectPage(0);
		new JButtonOperator(testsTreeTab, new TooltipChooser(jmap.getAddTestsButton())).clickMouse();
		Thread.sleep(1000);
	}
	
	public void setTestsNumberSpinner(int num) {
		testsTreeTab.selectPage(0);
		JSpinnerOperator jSpinnerOperator = new JSpinnerOperator(testsTreeTab);
		if(jSpinnerOperator.isEnabled() && num > 0) {
			jSpinnerOperator.setValue(num);
		}
	}
	
	public void search(final String textToSearch) throws Exception {
		testsTreeTab.selectPage(0);
		JComboBoxOperator filter = new JComboBoxOperator(testsTreeTab, new TooltipChooser(jmap.getFilterToolTip()));
		filter.addItem(textToSearch);
		filter.selectItem(textToSearch);
		Thread.sleep(1000);
	}
	
	public void addTest(String node, String parentNode, int amount) throws Exception {
		testsTreeTab.selectPage(0);
		getTestTree().selectPathByNodeAndParentNode(node, parentNode);
		setTestsNumberSpinner(amount);
		pushAddTestsButton();
	}
}

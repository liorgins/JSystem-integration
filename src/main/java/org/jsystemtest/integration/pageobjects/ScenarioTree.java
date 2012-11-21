package org.jsystemtest.integration.pageobjects;

import java.awt.AWTEvent;

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTreeOperator;

public class ScenarioTree extends AbstractPageObject {

	JTreeOperator scenarioTreeOperator;

	public ScenarioTree(JFrameOperator app) {
		this.scenarioTreeOperator = new JTreeOperator(app);
	}

	public int selectTestByRow(int row) {
		int treeRowCount = scenarioTreeOperator.getRowCount();
		if (row > treeRowCount || row < 0) {
			return -1;
		}
		TreePath path = scenarioTreeOperator.getPathForRow(row);
		scenarioTreeOperator.selectPath(path);
		return 0;
	}
	
	public int getRowCount() {
		return scenarioTreeOperator.getRowCount();
	}
	
	public void markAsKnownIssue(int testIndex, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getScenarioMarkAsKnownIssueMenuItem() : jmap.getScenarioUnMarkAsKnownIssueMenuItem();
		pushMenuItemForTest(testIndex, menuItem);
	}
	
	/**
	 * 1) locate a test by given index\String.<br>
	 * 2) open pop-up menu<br>
	 * 3) select given menuItem<br>
	 * 
	 * @param identifier
	 *            Integer index \ String name
	 * @param menuItem
	 *            the menu item to push
	 * @throws Exception
	 */
	private void pushMenuItemForTest(int testIndex, String menuItem) throws Exception {
		JPopupMenuOperator pp = rightClickPopUpManu(testIndex);
		pp.pushMenuNoBlock(menuItem);
	}

	private JPopupMenuOperator rightClickPopUpManu(int testIndex) throws Exception {
		TreePath foundPath = scenarioTreeOperator.getPathForRow(testIndex);
		if (foundPath == null) {
			throw new Exception("Path not found test index: " + testIndex);
		}

		JPopupMenuOperator pp = new JPopupMenuOperator(scenarioTreeOperator.callPopupOnPath(foundPath));
		new EventTool().waitNoEvent(AWTEvent.MOUSE_EVENT_MASK);
		return pp;
	}
}

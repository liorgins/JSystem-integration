package org.jsystemtest.integration.pageobjects;

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTreeOperator;

public class ScenarioTree extends AbstractPageObject {

	JTreeOperator scenarioTreeOperator;

	public ScenarioTree(JFrameOperator app) {
		this.scenarioTreeOperator = new JTreeOperator(app);
	}

	public void selectTestByRow(int row) {
		TreePath path = scenarioTreeOperator.getPathForRow(row);
		scenarioTreeOperator.selectPath(path);
	}

	public int getRowCount() {
		return scenarioTreeOperator.getRowCount();
	}

	public void markAsKnownIssue(int testIndex, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getScenarioMarkAsKnownIssueMenuItem() : jmap.getScenarioUnMarkAsKnownIssueMenuItem();
		pushMenuItemForTest(testIndex, menuItem);
	}

	/**
	 * will open the selected sub scenario in it's own root
	 * 
	 * @throws Exception
	 */
	public void navigateToSubScenario(int scenarioIndex) throws Exception {
		pushMenuItemForTest(scenarioIndex, jmap.getNavigateToSubScenario());
	}

	public void markScenarioAsTest(int index, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getScenarioMarkAsTestMenuItem() : jmap.getScenarioUnMarkAsTestMenuItem();
		Thread.sleep(2000);
		pushMenuItemForTest(index, menuItem);
	}

	public void markAsNegative(int testIndex, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getMarkAsNegativeTestMenuItem() : jmap.getUnMarkAsNegativeTestMenuItem();
		Thread.sleep(2000);
		pushMenuItemForTest(testIndex, menuItem);
	}

	private void pushMenuItemForTest(int testIndex, final String menuItem) throws Exception {
		JPopupMenuOperator pp = rightClickPopUpManu(testIndex);
		pp.pushMenuNoBlock(menuItem);
	}

	private JPopupMenuOperator rightClickPopUpManu(int testIndex) throws Exception {
		TreePath foundPath = scenarioTreeOperator.getPathForRow(testIndex);
		if (foundPath == null) {
			throw new Exception("Path not found test index: " + testIndex);
		}

		JPopupMenuOperator pp = new JPopupMenuOperator(scenarioTreeOperator.callPopupOnPath(foundPath));
		Thread.sleep(500);
		return pp;
	}

	public int getScenarioDirectChildrenCount(int row) throws Exception {
		TreePath path = scenarioTreeOperator.getPathForRow(row);
		return scenarioTreeOperator.getChildPaths(path).length;
	}
	
}


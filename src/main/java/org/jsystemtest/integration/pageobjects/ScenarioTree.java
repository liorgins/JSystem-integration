package org.jsystemtest.integration.pageobjects;

import java.util.logging.Level;

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.python.modules.thread;

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
	
	
	/**
	 * @param row test row index 
	 * @param map true map, false unmap
	 * @param isScenario true for scenario, false for test
	 * @throws Exception
	 */
	public void mapTest(int row, boolean map, boolean isScenario) throws Exception {
		String menuItemText = "";
		
		if(isScenario) {
			menuItemText = map ? jmap.getTestMapAllMenuItem() : jmap.getTestUnmapAllMenuItem();
		}else {
			menuItemText = map ? jmap.getTestMapMenuItem() : jmap.getTestUnmapMenuItem();
		}
		pushMenuItemForTest(row, menuItemText);
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

		Thread.sleep(200);
		JPopupMenuOperator pp = new JPopupMenuOperator(scenarioTreeOperator.callPopupOnPath(foundPath));
		Thread.sleep(200);
		return pp;
	}

	public int getScenarioDirectChildrenCount(int row) throws Exception {
		TreePath path = scenarioTreeOperator.getPathForRow(row);
		return scenarioTreeOperator.getChildPaths(path).length;
	}

	
}


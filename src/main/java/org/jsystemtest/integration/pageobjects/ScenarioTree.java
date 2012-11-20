package org.jsystemtest.integration.pageobjects;

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JFrameOperator;
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
}

package org.jsystemtest.integration.pageobjects;

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JSplitPaneOperator;
import org.netbeans.jemmy.operators.JTreeOperator;

public class TestsTree extends AbstractPageObject {

	private JTreeOperator testsTreeOperator;

	public TestsTree(JSplitPaneOperator testsTabOperator) {
		testsTreeOperator = new JTreeOperator(testsTabOperator);
	}

	public TestsTree selectTestByRow(int row) {

		TreePath path = testsTreeOperator.getPathForRow(row);
		testsTreeOperator.selectPath(path);
		return this;
	}

}

package org.jsystemtest.integration.pageobjects;

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JSplitPaneOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class TestsTree extends AbstractPageObject {

	private JTreeOperator testsTreeOperator;

	public TestsTree(JSplitPaneOperator testsTabOperator) {
		testsTreeOperator = new JTreeOperator(testsTabOperator, new NameComponentChooser("testsTree"));
	}

	public TestsTree selectTestByRow(int row) {

		TreePath path = testsTreeOperator.getPathForRow(row);
		testsTreeOperator.selectPath(path);
		return this;
	}

}

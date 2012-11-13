package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JSplitPaneOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class TestsTreeController extends AbstractPageObject{
	
	private JSplitPaneOperator testsTabOperator;
		
	public TestsTreeController(JFrameOperator app) {
		testsTabOperator = new JSplitPaneOperator(app, new NameComponentChooser("testsTreeTab"));	
	}
	
	public void addTestsToScenario() {
		new JButtonOperator(testsTabOperator, new TooltipChooser(jmap.getAddTestsButton())).push();
	}
	
	public TestsTree getTestsTree() {
		return new TestsTree(testsTabOperator);
	}
}

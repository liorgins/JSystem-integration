package org.jsystemtest.integration.pageobjects;


import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.python.modules.thread;

public class TestsTableController extends AbstractPageObject {

	
	private JFrameOperator appOperator;
	
	public TestsTableController(JFrameOperator app) {
		this.appOperator = app;
	}
	
	public ScenarioTree getScenarioTree() {
		return new ScenarioTree(appOperator);
	}
	
	public void removeAllTests() throws InterruptedException {
		ScenarioTree scenarioTree = getScenarioTree();
		final int rowCount = scenarioTree.getRowCount();
				
		int i = rowCount - 1;
		
		while (i > 0) {
			scenarioTree.selectTestByRow(i);
			pushRemoveButton();
			i--;
			Thread.sleep(500);
		}
	}
	
	public void pushRemoveButton() {
		new JButtonOperator(appOperator, new TooltipChooser("Remove")).clickMouse();
	}
}

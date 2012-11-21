package org.jsystemtest.integration.pageobjects;


import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

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

	public boolean isNavigateButtonEnabled(String direction) {
		String navButtonDirection = null;
		if(direction.equals("backward")) {
			navButtonDirection = jmap.getScenarioNavigateBackword(); 
		}else if(direction.equals("forward")){
			navButtonDirection = jmap.getScenarioNavigateForward();
		}
		return new JButtonOperator(appOperator, new TooltipChooser(navButtonDirection)).isEnabled();
	}
	
	public void pushNavigateToScenarioButton(String direction) {
		String navButtonDirection = null;
		if(direction.equals("backward")) {
			navButtonDirection = jmap.getScenarioNavigateBackword(); 
		}else if(direction.equals("forward")){
			navButtonDirection = jmap.getScenarioNavigateForward();
		}
		 new JButtonOperator(appOperator, new TooltipChooser(navButtonDirection)).clickMouse();
	}
	
	public String getCurrentScenarioName() {
		return JSystemProperties.getInstance().getPreference(FrameworkOptions.CURRENT_SCENARIO).substring("scenarios".length()+1);
	}
}

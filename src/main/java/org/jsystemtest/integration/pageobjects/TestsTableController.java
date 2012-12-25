package org.jsystemtest.integration.pageobjects;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

public class TestsTableController extends AbstractPageObject {

	public static final String FORWARD = "forward";
	public static final String BACKWARD = "backward";

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
		}
	}

	public void pushRemoveButton() throws InterruptedException {
		new JButtonOperator(appOperator, new TooltipChooser("Remove")).clickMouse();
		Thread.sleep(750);
	}

	public boolean isNavigateButtonEnabled(String direction) {
		String navButtonDirection = null;
		if (direction.equals(BACKWARD)) {
			navButtonDirection = jmap.getScenarioNavigateBackword();
		} else if (direction.equals(FORWARD)) {
			navButtonDirection = jmap.getScenarioNavigateForward();
		}
		return new JButtonOperator(appOperator, new TooltipChooser(navButtonDirection)).isEnabled();
	}

	public void pushNavigateToScenarioButton(String direction) throws InterruptedException {
		String navButtonDirection = null;
		if (direction.equals(BACKWARD)) {
			navButtonDirection = jmap.getScenarioNavigateBackword();
		} else if (direction.equals(FORWARD)) {
			navButtonDirection = jmap.getScenarioNavigateForward();
		}
		
		new JButtonOperator(appOperator, new TooltipChooser(navButtonDirection)).clickMouse();
		Thread.sleep(750);
	}

	public String getCurrentScenarioName() {
		return JSystemProperties.getInstance().getPreference(FrameworkOptions.CURRENT_SCENARIO).substring("scenarios".length() + 1);
	}
	
	public void pushAddNotificationEvent() throws InterruptedException {
		new JButtonOperator(appOperator, new TooltipChooser(jmap.getPublishEventButton())).clickMouse();
		Thread.sleep(500);
	}
}

package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.NameChooser;
import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.python.modules.thread;

public class MainToolBar extends AbstractPageObject {

	private JFrameOperator app;

	public MainToolBar(JFrameOperator app) {
		this.app = app;
	}

	// TODO add more methods to cover all the tool bar's functionality

	// main tool bar operations
	public void pushNewScenarioButton() {
		new JButtonOperator(app, new TooltipChooser(jmap.getNewScenarioButton())).push();
	}

	public void pushSaveScenarioButton() throws InterruptedException {
		JButtonOperator jButtonOperator = new JButtonOperator(app, new TooltipChooser(jmap.getSaveScenarioButton()));
		waitEnableAndClick(jButtonOperator);
	}

	public void pushSaveAsScenarioButton() {
		new JButtonOperator(app, new TooltipChooser(jmap.getCopyScenarioButton())).push();

	}

	public void pushDeleteScenarioButton() throws InterruptedException {
		new JButtonOperator(app, new TooltipChooser(jmap.getClearScenarioButton())).push();
		Thread.sleep(500);
	
	}

	public void pushFaildSequenceButton() {
		new JButtonOperator(app, new TooltipChooser(jmap.getSaveFailedSequences())).push();
	}

	public void pushRefreshButton() throws InterruptedException {
		new JButtonOperator(app, new TooltipChooser(jmap.getRefreshButton())).push();
		
		Thread.sleep(2000);
	}

	// inner tool bar operations
	public void pushPlayButton() throws Exception {
		JButtonOperator jButtonOperator = new JButtonOperator(app, new TooltipChooser(jmap.getPlayButton()));
		waitEnableAndClick(jButtonOperator);
	}

	public void pushPauseButton() {
		new JButtonOperator(app, new TooltipChooser(jmap.getPauseButton())).push();
	}

	public void pushStopButton() {
		new JButtonOperator(app, new TooltipChooser(jmap.getStopButton())).push();
	}

	public void clickFreezeOnFail() {
		new JCheckBoxOperator(app, new TooltipChooser("Freeze on Fail")).push();
	}

	public void waitEnableAndClick(JButtonOperator jButtonOperator) throws InterruptedException {
		if (jButtonOperator.isEnabled()) {
			jButtonOperator.clickMouse();
		} else {
			while (!jButtonOperator.isEnabled()) {
				Thread.sleep(500);
				jButtonOperator.clickMouse();
			}
		}
	}

	public boolean isPlayButtonEnable() {
		return new JButtonOperator(app, new TooltipChooser(jmap.getPlayButton())).isEnabled();
	}

	public void selectSUTFile(final String sutName) throws InterruptedException {

		new JComboBoxOperator(app, new NameChooser("SUT_COMBO_NAME")).selectItem(sutName);
		Thread.sleep(500);
	}
}

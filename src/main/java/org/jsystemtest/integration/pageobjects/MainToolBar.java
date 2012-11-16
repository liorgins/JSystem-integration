package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

public class MainToolBar extends AbstractPageObject{
	
	private JFrameOperator app;
	
	public MainToolBar(JFrameOperator app) {
		this.app = app;
	}
	
	//TODO add more methods to cover all the tool bar's functionality  
	// main tool bar operations
		public void pushNewScenarioButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getNewScenarioButton())).push();
		}

		public void pushSaveScenarioButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getSaveScenarioButton())).push();
		}

		public void pushAsSaveScenarioButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getCopyScenarioButton())).push();
		}

		public void pushDeleteScenarioButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getClearScenarioButton())).push();
		}

		public void pushFaildSequenceButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getSaveFailedSequences())).push();
		}

		public void pushRefreshButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getRefreshButton())).push();
		}

		// inner tool bar operations
		public void pushPlayButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getPlayButton())).push();
		}

		public void pushPauseButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getPauseButton())).push();
		}

		public void pushStopButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getStopButton())).push();
		}

//		public void toggleDebug() {
//			JCheckBoxOperator jCheckBoxOperator = new JCheckBoxOperator(app, new TooltipChooser(jmap.getToggleDebugButton()));
//			if (isDebugMode()) {
//				jCheckBoxOperator.setSelected(false);
//			} else {
//				jCheckBoxOperator.setSelected(true);
//			}
//		}
//		
//		public boolean isDebugMode() {
//			JCheckBoxOperator jCheckBoxOperator = new JCheckBoxOperator(app, new TooltipChooser(jmap.getToggleDebugButton()));
//			if (jCheckBoxOperator.isSelected()) {
//				return true;
//			} else {
//				return false;
//			}
//		}

}

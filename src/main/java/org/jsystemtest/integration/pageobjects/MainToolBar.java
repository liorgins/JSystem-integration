package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.TooltipChooser;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.Operator.StringComparator;
import org.python.modules.thread;

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

		public void pushSaveScenarioButton() throws InterruptedException {
			JButtonOperator jButtonOperator = new JButtonOperator(app, new TooltipChooser(jmap.getSaveScenarioButton()));
			waitEnableAndClick(jButtonOperator);
		}

		public void pushSaveAsScenarioButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getCopyScenarioButton())).push();
		}

		public void pushDeleteScenarioButton() {
			new JButtonOperator(app, new TooltipChooser(jmap.getClearScenarioButton())).push();
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

		
		public void waitEnableAndClick(JButtonOperator jButtonOperator) throws InterruptedException {
			if(jButtonOperator.isEnabled()) {
				jButtonOperator.clickMouse();
			}else{
				while(!jButtonOperator.isEnabled()) {
					Thread.sleep(500);
					jButtonOperator.clickMouse();
				}
			}
		}
		
		public boolean isPlayButtonEnable() {
			return new JButtonOperator(app, new TooltipChooser(jmap.getPlayButton())).isEnabled();
		}
		
		public void createNewSUTFile(final String sutName) throws InterruptedException {
			JComboBoxOperator jcb = new JComboBoxOperator(app, 0);
			Thread.sleep(200);
			jcb.selectItem("Create a new SUT file...", new StringComparator() {
				
				@Override
				public boolean equals(String caption, String match) {
					if(caption.equals(match)) {
						return true;
					}
					return false;
				}
			});
			Thread.sleep(1000);
			String.valueOf(System.currentTimeMillis());
			JFileChooserOperator jfc = new JFileChooserOperator(app);
			Thread.sleep(200);
			jfc.chooseFile(sutName);
		}

}

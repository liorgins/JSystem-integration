package org.jsystemtest.integration.pageobjects;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;

import jsystem.guiMapping.JsystemMapping;
import jsystem.treeui.TestRunner;
import junit.framework.Assert;

import org.jsystem.jemmy.tests.JFrameOperatorTest;
import org.jsystemtest.integration.NameChooser;
import org.jsystemtest.integration.TooltipChooser;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

public class JSystemApplication extends AbstractPageObject {

	private JFrameOperator app;

	public void launch() {
		try {
			new ClassReference(TestRunner.class.getName()).startApplication();
			app = new JFrameOperator(jmap.getJSyetemMain());
			Assert.assertNotNull("JSystem frame not captured", app);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (app != null) {
			app.close();
		}
	}

	public TestsTreeController getTestsTreeController() {
		return new TestsTreeController(app);
	}

	public TestsTableController getTestTableController() {
		return new TestsTableController(app);
	}

	public MenuBar getMenuBar() {
		return new MenuBar(app);
	}

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

	public void toggleDebug() {
		JCheckBoxOperator jCheckBoxOperator = new JCheckBoxOperator(app, new TooltipChooser(jmap.getToggleDebugButton()));
		if (jCheckBoxOperator.isSelected()) {
			jCheckBoxOperator.setSelected(false);
		} else {
			jCheckBoxOperator.setSelected(true);
		}
	}

	public JFileChooserOperator getFileChooserOerator() {
		return new JFileChooserOperator(app);
	}

}

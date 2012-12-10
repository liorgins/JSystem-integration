package org.jsystemtest.integration.pageobjects.menus;

import org.jsystemtest.integration.pageobjects.AbstractPageObject;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class FileMenu extends AbstractPageObject {

	private JMenuOperator jMenuOperator;

	private static final String FILE = "File";

	public FileMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, FILE);
	}

	public void Refresh() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getRefreshButton());
	}

	public JFileChooserOperator newSceario() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getNewScenarioButton());
		return new JFileChooserOperator();
	}

	public JFileChooserOperator openSceario() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getOpenScenarioButton());
		return new JFileChooserOperator();
	}

	public JFileChooserOperator deleteSceario() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getDeleteScenarioWindow());
		return new JFileChooserOperator();
	}

	public void saveScenario() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getSaveScenarioButton());
	}

	public JFileChooserOperator saveScenarioAs() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getCopyScenarioButton());
		return new JFileChooserOperator();
	}
	
	public JFileChooserOperator switchProject() {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getSwitchProjectButton());
		return new JFileChooserOperator();
	}
	
	public void Exit()  {
		jMenuOperator.pushMenuNoBlock(FILE + "|" + jmap.getExitButton());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		JDialogOperator dialog = new JDialogOperator("Exit Confirmation");
		new JButtonOperator(dialog, "Yes").clickMouse();
	}

}

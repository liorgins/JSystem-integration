package org.jsystemtest.integration.pageobjects.menus;

import org.jsystemtest.integration.pageobjects.AbstractPageObject;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ExecutionMenu extends AbstractPageObject {
	
	private JMenuOperator jMenuOperator;

	private static final String EXECUTION = "Execution";

	public ExecutionMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, EXECUTION);
	}

	public void run() {
		jMenuOperator.pushMenuNoBlock(EXECUTION + "|Run");
	}
	
	public void pause() {
		jMenuOperator.pushMenuNoBlock(EXECUTION + "|Pause");
	}
	
	public void stop() {
		jMenuOperator.pushMenuNoBlock(EXECUTION + "|Stop");
	}

}

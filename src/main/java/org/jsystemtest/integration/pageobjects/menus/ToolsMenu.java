package org.jsystemtest.integration.pageobjects.menus;

import org.jsystemtest.integration.pageobjects.AbstractPageObject;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ToolsMenu extends AbstractPageObject {
	
	//TODO - NOT COMPLETE
	
	
	private JMenuOperator jMenuOperator;

	private static final String TOOLS = "Tools";

	public ToolsMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, TOOLS);
	}

	public void InitReporters() {
		jMenuOperator.pushMenuNoBlock(jmap.getInitReportMenu());
	}
}

package org.jsystemtest.integration.pageobjects.menus;

import org.jsystemtest.integration.pageobjects.AbstractPageObject;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ViewMenu extends AbstractPageObject {
	
	private JMenuOperator jMenuOperator;
	public ToolbarsMenu toolbarsMenu;
	
	private static final String VIEW = "View";

	public ViewMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, VIEW);
		toolbarsMenu = new ToolbarsMenu(jMenuOperator);
	}
	
	public void log() {
		jMenuOperator.pushMenuNoBlock(VIEW + "|Log");
	}

	public void initReporters() {
		jMenuOperator.pushMenuNoBlock(VIEW + "|View Test Code" );
	}
	
	public ToolbarsMenu getToolbarsMenu() {
		return toolbarsMenu;
	}


}

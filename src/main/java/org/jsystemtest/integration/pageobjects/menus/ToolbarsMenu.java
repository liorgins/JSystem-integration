package org.jsystemtest.integration.pageobjects.menus;

import org.jsystemtest.integration.JemmyHelper;
import org.jsystemtest.integration.pageobjects.AbstractPageObject;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ToolbarsMenu extends AbstractPageObject{
	private JMenuOperator jMenuOperator;

	private static final String TOOLBARS = "Toolbars";

	public ToolbarsMenu(JMenuOperator jMenuOperator) {
		this.jMenuOperator = jMenuOperator;
	}
	
	public void changeToolbarState(String toolbarName) {
		jMenuOperator.pushMenuNoBlock("View|" + TOOLBARS + "|" + toolbarName);
	}
	
	public boolean getToolbarState(String toolbarName) throws Exception {
		return JemmyHelper.getInstance().getToolbarViewState(toolbarName);
	}
}

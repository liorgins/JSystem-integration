package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

public class MenuBar extends AbstractPageObject {

	private JMenuBarOperator menuBarOperator;
	
	public MenuBar(JFrameOperator app) {
		menuBarOperator = new JMenuBarOperator(app);
	}
	
	public FileMenu getFileMenu() {
		return new FileMenu(menuBarOperator);	
	}
	
	public EditMenu getEditMenu() {
		return new EditMenu(menuBarOperator);
	}
	
	public ViewMenu getViewMenu() {
		return new ViewMenu(menuBarOperator);
	}
	
	public ToolsMenu getToolsMenu() {
		return new ToolsMenu(menuBarOperator);
	}
	
	public ExecutionMenu getExecutionMenu() {
		return new ExecutionMenu(menuBarOperator);
	}
	
	public HelpMenu getHelpMenu() {
		return new HelpMenu(menuBarOperator);
		
	}
}
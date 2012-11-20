package org.jsystemtest.integration.pageobjects;


import org.jsystemtest.integration.pageobjects.menus.EditMenu;
import org.jsystemtest.integration.pageobjects.menus.ExecutionMenu;
import org.jsystemtest.integration.pageobjects.menus.FileMenu;
import org.jsystemtest.integration.pageobjects.menus.HelpMenu;
import org.jsystemtest.integration.pageobjects.menus.ToolsMenu;
import org.jsystemtest.integration.pageobjects.menus.ViewMenu;
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
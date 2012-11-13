package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

public class MenuBar extends AbstractPageObject {

	private JMenuBarOperator menuBarOperator;

	public MenuBar(JFrameOperator app) {
		menuBarOperator = new JMenuBarOperator(app);
	}

	
	/**
	 * navigate to the item path and push it
	 * 
	 * @param path - string delimited by '|'
	 */
	public void pushMenu(String path) {
		menuBarOperator.pushMenuNoBlock(path);
	}
	
	
	/**
	 * return Jemmy operator for further control
	 * 
	 * @return
	 */
	public JMenuBarOperator getOperator() {
		return menuBarOperator;
	}
}
package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ViewMenu extends AbstractPageObject {
	
	private JMenuOperator jMenuOperator;

	private static final String VIEW = "View";

	public ViewMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, VIEW);
	}
	
	public void log() {
		jMenuOperator.pushMenuNoBlock(VIEW + "|Log");
	}

	public void initReporters() {
		jMenuOperator.pushMenuNoBlock(VIEW + "|View Test Code" );
	}
	
	public void getToolBars() {
		//no implementation yet
	}

}

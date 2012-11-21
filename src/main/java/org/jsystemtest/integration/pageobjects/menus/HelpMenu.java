package org.jsystemtest.integration.pageobjects.menus;


import org.jsystemtest.integration.pageobjects.AbstractPageObject;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;



public class HelpMenu extends AbstractPageObject{
	
	private static final String HELP = "Help";
	
	private JMenuOperator jMenuOperator;
	
	public HelpMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, HELP);
	}
	
	public void aboutVersion () {
		jMenuOperator.pushMenuNoBlock(jmap.getHelpMenu());
	}
}

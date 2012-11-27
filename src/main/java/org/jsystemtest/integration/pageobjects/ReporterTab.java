package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;

public class ReporterTab extends AbstractPageObject {

	JTabbedPaneOperator reporterTabbedPaneOperator;

	public ReporterTab(JTabbedPaneOperator jTabbedPaneOperator) {
		jTabbedPaneOperator.selectPage(1);
		reporterTabbedPaneOperator = jTabbedPaneOperator;
	}
	
	public void pushInitReportersButton() {
		reporterTabbedPaneOperator.selectPage(1);
		new JButtonOperator(reporterTabbedPaneOperator, "Init Reporters").clickMouse();
	}
	
}

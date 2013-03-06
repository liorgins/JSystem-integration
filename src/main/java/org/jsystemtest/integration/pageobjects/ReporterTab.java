package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.testng.Reporter;

public class ReporterTab extends AbstractPageObject {

	JTabbedPaneOperator reporterTabbedPaneOperator;

	public ReporterTab(JTabbedPaneOperator jTabbedPaneOperator) {
		jTabbedPaneOperator.selectPage(1);
		reporterTabbedPaneOperator = jTabbedPaneOperator;
	}

	public void pushInitReportersButton() {
		reporterTabbedPaneOperator.selectPage(1);
		try {
			new JButtonOperator(reporterTabbedPaneOperator, "Init Reporters").clickMouse();
		} catch (org.netbeans.jemmy.JemmyException e) {
			Reporter.log("Caught JemmyException while trying to push 'Init Reporters' button", true);
		}
	}

}

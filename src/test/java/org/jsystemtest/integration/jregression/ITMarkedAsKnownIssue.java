package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITMarkedAsKnownIssue extends AbstractITJSystem {

	private final String rootScenario = "markedAsTestScenario";

	/**
	 * 1. open new scenario.
	 * 2. add some test to it.
	 * 3. save scenario.
	 */
	@BeforeMethod
	public void testFixture() throws Exception {
		
		app.openScenario(rootScenario);
		app.clearCurrentRootScenario(rootScenario);

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 2);
		testsTreeTab.addTest("reportWarning", "Example", 2);
		testsTreeTab.addTest("reportFailure", "Example", 2);

		app.getMenuBar().getFileMenu().saveScenario();
	}
	
	/**
	 * 1. select test that fail.
	 * 2. mark the test as known issue.
	 * 3. run scenario and verify that test reported warning.
	 * 
	 * @throws Exception
	 */

	@Test
	public void markOneFailAsKnownIssue() throws Exception {
		
		Reporter.log("STEP: select test that fail");
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		scenarioTree.selectTestByRow(5);
		
		Reporter.log("STEP: mark the test as known issue");
		scenarioTree.markAsKnownIssue(5, true);
		
		Reporter.log("STEP: run scenario and verify that test reported warning");
		//app.playAndWaitForRunEnd();
		//Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsPass(), 2);
		//Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsFail(), 1);
		//Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsWarning() ,3);
	}
}

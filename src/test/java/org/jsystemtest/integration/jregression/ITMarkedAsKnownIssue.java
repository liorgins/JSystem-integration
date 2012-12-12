package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstracITJSystem;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.Before;
import org.junit.Test;

public class ITMarkedAsKnownIssue extends AbstracITJSystem {

	private final String rootScenario = "markedAsTestScenario";

	/**
	 * 1. open new scenario.
	 * 2. add some test to it.
	 * 3. save scenario.
	 */
	@Before
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
		
		System.out.println("STEP: select test that fail");
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		scenarioTree.selectTestByRow(5);
		
		System.out.println("STEP: mark the test as known issue");
		scenarioTree.markAsKnownIssue(5, true);
		
		System.out.println("STEP: run scenario and verify that test reported warning");
		app.playAndWaitForRunEnd();
		Assert.assertEquals(2, XmlReportHandler.getInstance().getNumberOfTestsPass());
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsFail());
		Assert.assertEquals(3, XmlReportHandler.getInstance().getNumberOfTestsWarning());
	}
}

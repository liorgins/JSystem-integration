package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.Test;

public class ITMarkedAsKnownIssue extends AbstractJSystemIT {

	private final String rootScenario = "markedAsTestScenario";

	/**
	 * 1. open new scenario
	 * 2. add some test to it 
	 * 3. save scenario
	 */
	public void classFixture() throws Exception {
		app.openScenario(rootScenario);
		app.clearScenario(rootScenario);

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 2);
		testsTreeTab.addTest("reportWarning", "Example", 2);
		testsTreeTab.addTest("reportFailure", "Example", 2);

		app.getMenuBar().getFileMenu().saveScenario();
	}
	
	/**
	 * 1. select test that fail<br/>
	 * 2. mark the test as known issue<br/>
	 * 3. run scenario and verify that test reported warning
	 * 
	 * @throws Exception
	 */

	@Test
	public void markOneFailAsKnownIssue() throws Exception {
		
		classFixture();
		
		System.out.println("select a test that is a test that fails, and mark it as know issue");
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		scenarioTree.selectTestByRow(5);
		
		scenarioTree.markAsKnownIssue(5, true);
		app.getToolBar().pushPlayButton();

		Assert.assertEquals(0, app.waitForRunEnd());
		
		int testFail = XmlReportHandler.getInstance().getNumberOfTestsFail();
		int testWarn = XmlReportHandler.getInstance().getNumberOfTestsWarning();
		Assert.assertEquals("TESTS RUN - Expected: 1 ,  Actual: " +  testFail, 1, testFail);
		Assert.assertEquals("TESTS RUN - Expected: 3 ,  Actual: " +  testWarn, 3, testWarn);
	}

}

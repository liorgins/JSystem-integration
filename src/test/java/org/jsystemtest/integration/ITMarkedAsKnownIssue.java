package org.jsystemtest.integration;

import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.FrameworkOptions;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITMarkedAsKnownIssue {

	private static JSystemApplication app;
	private final String rootScenario = "markedAsTestScenario";

	@BeforeClass
	public static void prepareEnv() throws InterruptedException {
		app = new JSystemApplication();
		app.setJSystemStandartProperties(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.DEFAULT_SUT_FILE);
		app.setJSystemOptionalProperties(new PropertyPair(FrameworkOptions.AUTO_DELETE_NO_CONFIRMATION, JSystemApplication.TRUE), new PropertyPair(FrameworkOptions.AUTO_SAVE_NO_CONFIRMATION, JSystemApplication.TRUE));
		app.launch();

	}

	/**
	 * 1. open new scenario
	 * 2. add some test to it 
	 * 3. save scenario
	 */
	@Before
	public void setUp() throws Exception {
		app.openScenario(rootScenario);
		app.clearScenario(rootScenario);

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "TestsExamples", 2);
		testsTreeTab.addTest("reportWarning", "TestsExamples", 2);
		testsTreeTab.addTest("reportFailure", "TestsExamples", 2);

		app.getMenuBar().getFileMenu().saveScenario();
	}

	@Test
	public void markOneFailAsKnownIssue() throws Exception {
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

	@After
	public void cleanTest() {
		System.out.println("@After");

		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);

		System.out.println("Cleanning generated logs and properties files");
		JSystemTestUtils.cleanPropertiesAndLogs(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}

}

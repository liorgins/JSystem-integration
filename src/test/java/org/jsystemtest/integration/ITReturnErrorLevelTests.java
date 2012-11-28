package org.jsystemtest.integration;

import jsystem.framework.FrameworkOptions;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITReturnErrorLevelTests {

	private static JSystemApplication app;
	
	@BeforeClass
	public static void prepareEnv() throws InterruptedException {
		app = new JSystemApplication();
		app.setJSystemStandartProperties(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.DEFAULT_SUT_FILE);
		app.setJSystemOptionalProperties(new PropertyPair(FrameworkOptions.AUTO_DELETE_NO_CONFIRMATION, JSystemApplication.TRUE),
										 new PropertyPair(FrameworkOptions.AUTO_SAVE_NO_CONFIRMATION, JSystemApplication.TRUE));
		app.launch();

	}
	
	/**
	 * Test the runner exists with error level 101 when one test fails
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testFailureErrorLevel() throws Throwable {
		System.out.println("Creating and running scenario with error tests");
		app.createScenario("FailureErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportFailure", "Example", 1);
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);
		
		app.getToolBar().pushPlayButton();
		Assert.assertEquals(0, app.waitForRunEnd());

		System.out.println("Exiting application and asserting error level");
		Assert.assertEquals(101, app.closeAndGetErrorLevel());
	}
//
//	/**
//	 * Test the runner exists with error level 102 when one test has warning
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testWarningErrorLevel() throws Exception {
//		report.step("Creating and running scenario with warning tests");
//		scenarioClient.cleanScenario("WarningErrorLevel");
//		scenarioClient.addTest("testThatWarns", "SimpleTests", 1);
//		scenarioClient.addTest("testThatPass", "SimpleTests", 2);
//		applicationClient.play();
//
//		report.step("Exiting application and asserting error level");
//		analyzer.setTestAgainstObject(applicationClient.closeApp());
//		analyzer.analyze(new NumberCompare(compareOption.EQUAL, 102, 0));
//	}
//
//	/**
//	 * Test the runner exists with error level 0 when all tests pass
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testSuccessErrorLevel() throws Exception {
//		report.step("Creating and running scenario with only pass tests");
//		scenarioClient.cleanScenario("SuccessErrorLevel");
//		scenarioClient.addTest("testThatPass", "SimpleTests", 3);
//		applicationClient.play();
//
//		report.step("Exiting application and asserting error level");
//		analyzer.setTestAgainstObject(applicationClient.closeApp());
//		analyzer.analyze(new NumberCompare(compareOption.EQUAL, 0, 0));
//	}
	
	
	
	@After
	public void cleanTest() {
		System.out.println("@After");

		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);

		System.out.println("Cleanning generated logs and properties files");
		JSystemTestUtils.cleanPropertiesAndLogs(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}
}

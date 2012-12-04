package org.jsystemtest.integration.jregression;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.junit.Test;

public class ITReturnErrorLevelTests extends AbstractJSystemIT{

	/**
	 * Test the runner exists with error level 101 when one test fails
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testFailureErrorLevel() throws Throwable {
//		System.out.println("Creating and running scenario with error tests");
//		app.createScenario("FailureErrorLevel");
//		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
//		testsTreeTab.addTest("reportFailure", "Example", 1);
//		testsTreeTab.addTest("reportWarning", "Example", 1);
//		testsTreeTab.addTest("reportSuccess", "Example", 2);
//
//		app.getToolBar().pushPlayButton();
//		Assert.assertEquals(0, app.waitForRunEnd());
//
//		System.out.println("Exiting application and asserting error level");
//
//		File logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
//		mark = 0;
//		if (logFile.exists()) {
//			mark = logFile.length();
//		}
//		try {
//			app.exitThroughMenu();
//		} catch (SecurityException e) {
//
//		}
//
//		if (logFile.exists()) {
//			String newText = JSystemTestUtils.readFromPosition(logFile, mark);
//			int pos = newText.indexOf("System exit") + ("System exit".length());
//			String errorLevelString = newText.substring(pos);
//			errorLevel = Integer.parseInt(errorLevelString);
//		}
//
//		System.out.println("System exit with error level: " + errorLevel);
//		Assert.assertEquals(101, errorLevel);
//
	}

	//
	// /**
	// * Test the runner exists with error level 102 when one test has warning
	// *
	// * @throws Exception
	// */
	 @Test
	 public void testWarningErrorLevel() throws Exception {
	// report.step("Creating and running scenario with warning tests");
	// scenarioClient.cleanScenario("WarningErrorLevel");
	// scenarioClient.addTest("testThatWarns", "SimpleTests", 1);
	// scenarioClient.addTest("testThatPass", "SimpleTests", 2);
	// applicationClient.play();
	//
	// report.step("Exiting application and asserting error level");
	// analyzer.setTestAgainstObject(applicationClient.closeApp());
	// analyzer.analyze(new NumberCompare(compareOption.EQUAL, 102, 0));
	 }
	//
	// /**
	// * Test the runner exists with error level 0 when all tests pass
	// *
	// * @throws Exception
	// */
	 @Test
	 public void testSuccessErrorLevel() throws Exception {
	// report.step("Creating and running scenario with only pass tests");
	// scenarioClient.cleanScenario("SuccessErrorLevel");
	// scenarioClient.addTest("testThatPass", "SimpleTests", 3);
	// applicationClient.play();
	//
	// report.step("Exiting application and asserting error level");
	// analyzer.setTestAgainstObject(applicationClient.closeApp());
	// analyzer.analyze(new NumberCompare(compareOption.EQUAL, 0, 0));
	 }

}

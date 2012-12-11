package org.jsystemtest.integration.jregression;

import java.io.File;

import jsystem.framework.report.RunnerListenersManager;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.jsystemtest.integration.NoExitSecurityManager;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITReturnErrorLevelTests extends AbstractJSystemIT {

	private static int errorLevelpos = -1;
	private static File logFile;

	@Before
	public void beforeTest() throws Exception {
		System.out.println("**************** @Before From Test Class");
		RunnerListenersManager.hadFailure = false;
		RunnerListenersManager.hadWarning = false;
		System.setSecurityManager(new NoExitSecurityManager());

	}

	/**
	 * 1. create scenario add tests that fail, warns and pass.<br>
	 * 2. push play and wait for execution to end.<br>
	 * 3. save current log file position.<br>
	 * 4. exit application through menu.<br>
	 * 5. check that log appended with "Exit code: 101".<br>
	 * 
	 * @throws Exception
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testFailureErrorLevel() throws Exception {

		System.out.println("Creating and running scenario with error tests");
		app.createScenario("FailureErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportFailure", "Example", 1);
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);

		app.playAndWaitForRunEnd();

		System.out.println("Exiting application and asserting error level");

		logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
		long mark = logFile.length();

		app.exitVirtualyThroughMenu();

		checkErrorLevel(mark, "101");

	}

	/**
	 * 1. create scenario add tests that warns and tests that pass.<br>
	 * 2. push play and wait for execution to end.<br>
	 * 3. save current log file position.<br>
	 * 4. exit application through menu.<br>
	 * 5. check that log appended with "Exit code: 102".<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWarningErrorLevel() throws Exception {

		System.out.println("Creating and running scenario with error tests");
		app.createScenario("WarningErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);

		app.playAndWaitForRunEnd();

		System.out.println("Exiting application and asserting error level");

		logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
		long mark = logFile.length();

		app.exitVirtualyThroughMenu();
		checkErrorLevel(mark, "102");

	}

	/**
	 * 1. create scenario add tests pass.<br>
	 * 2. push play and wait for execution to end.<br>
	 * 3. save current log file position.<br>
	 * 4. exit application through menu.<br>
	 * 5. check that log appended with "Exit code: 0".<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSuccessErrorLevel() throws Exception {

		System.out.println("Creating and running scenario with only passing tests");
		app.createScenario("PassErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);

		app.playAndWaitForRunEnd();

		System.out.println("Exiting application and asserting error level");

		logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
		long mark = logFile.length();

		app.exitVirtualyThroughMenu();
		checkErrorLevel(mark, "0");

	}

	/**
	 * 1. wait for log to be updated.<br>
	 * 2. get the appended text for the log file.<br>
	 * 3. assert that position of the error level found.<br>
	 * 
	 * @param mark - the position from where to start look for the appended text
	 * @param exitCode - string represents the code to look for
	 * @throws Exception
	 */
	private void checkErrorLevel(long mark, String exitCode) throws Exception {
		System.out.println("************** Waiting for the logs to get updated");
		Thread.sleep(2000);

		if (logFile.exists()) {
			String newText = JSystemTestUtils.readFromPosition(logFile, mark);
			errorLevelpos = newText.indexOf("System exit " + exitCode);
		}

		System.out.println("**************** FOUND ERROR CODE AT: " + errorLevelpos);
		Assert.assertNotSame(-1, errorLevelpos);

	}

	@After
	public void afterTest() throws Exception {
		System.out.println("************* @After from Test Class");
		System.setSecurityManager(null);
		JSystemTestUtils.releaseRunnerLock();

	}
}

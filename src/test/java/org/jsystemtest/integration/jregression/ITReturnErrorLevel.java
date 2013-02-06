package org.jsystemtest.integration.jregression;

import java.io.File;

import jsystem.framework.report.RunnerListenersManager;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.NoExitSecurityManager;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITReturnErrorLevel extends AbstractITJSystem {

	private static int errorLevelpos = -1;
	private static File logFile;

	@BeforeMethod
	public void before() throws Exception {
		Reporter.log("Configure SecurityManager that we can catch the System.exit() exception");
	
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
	public void failureErrorLevel() throws Exception {

		Reporter.log("Creating and running scenario with error tests");
		app.createScenario("FailureErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportFailure", "Example", 1);
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);

		app.playAndWaitForRunEnd();

		Reporter.log("Exiting application and asserting error level");

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
	public void warningErrorLevel() throws Exception {

		Reporter.log("Creating and running scenario with error tests");
		app.createScenario("WarningErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);

		app.playAndWaitForRunEnd();

		Reporter.log("Exiting application and asserting error level");

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
	public void successErrorLevel() throws Exception {

		Reporter.log("Creating and running scenario with only passing tests");
		app.createScenario("PassErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);

		app.playAndWaitForRunEnd();

		Reporter.log("Exiting application and asserting error level");

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
		Reporter.log("Waiting for the logs to get updated");
		Thread.sleep(2000);

		if (logFile.exists()) {
			String newText = JSystemTestUtils.readFromPosition(logFile, mark);
			errorLevelpos = newText.indexOf("System exit " + exitCode);
		}

		Reporter.log("Found error level code at: " + errorLevelpos + "as expected!");
		Assert.assertNotSame(errorLevelpos, -1);

	}

	@AfterMethod
	public void after() throws Exception {
		Reporter.log("Setting SecurityManamger to null");
		System.setSecurityManager(null);
		Reporter.log("Relaese runner lock");
		JSystemTestUtils.releaseRunnerLock();

	}
}

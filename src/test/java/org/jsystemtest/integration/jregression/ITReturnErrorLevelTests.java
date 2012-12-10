package org.jsystemtest.integration.jregression;

import java.io.File;

import jsystem.framework.common.CommonResources;
import jsystem.framework.report.RunnerListenersManager;
import jsystem.utils.FileLock;
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
		releaseRunnerLock();
		RunnerListenersManager.hadFailure = false;
		RunnerListenersManager.hadWarning = false;
	}

	/**
	 * Test the runner exists with error level 101 when one test fails
	 * 
	 * @throws Exception
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testFailureErrorLevel() throws Exception {
		System.out.println("********************Setting the noExitSecurityManager");
		

		System.out.println("Creating and running scenario with error tests");
		app.createScenario("FailureErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportFailure", "Example", 1);
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);

		app.getToolBar().pushPlayButton();
		app.waitForRunEnd();

		System.out.println("Exiting application and asserting error level");

		logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
		long mark = logFile.length();
		
		app.exitVirtualyhroughMenu();
		
		checkErrorLevel(mark ,"101");

	}

	/**
	 * Test the runner exists with error level 102 when one test has warning
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWarningErrorLevel() throws Exception {
		System.setSecurityManager(new NoExitSecurityManager());

		System.out.println("Creating and running scenario with error tests");
		app.createScenario("WarningErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportWarning", "Example", 1);
		testsTreeTab.addTest("reportSuccess", "Example", 2);

		app.getToolBar().pushPlayButton();
		app.waitForRunEnd();

		System.out.println("Exiting application and asserting error level");

		logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
		final long mark = logFile.length();
		try {
			app.exitThroughMenu();
		} catch (SecurityException e) {
			System.out.println("SecurityException cought");
		} finally {
			app.setRunning(false);
		}
		checkErrorLevel(mark, "102");
		System.setSecurityManager(null);
	}

	/**
	 * Test the runner exists with error level 0 when all tests pass
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSuccessErrorLevel() throws Exception {
		System.setSecurityManager(new NoExitSecurityManager());

		System.out.println("Creating and running scenario with only passing tests");
		app.createScenario("PassErrorLevel");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);

		app.getToolBar().pushPlayButton();
		app.waitForRunEnd();

		System.out.println("Exiting application and asserting error level");

		logFile = new File(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.LOCAL_JSYSTEM_LOG_FILE_NAME);
		final long mark = logFile.length();
		try {
			app.exitThroughMenu();

		} catch (SecurityException e) {
			System.out.println("SecurityException cought");
		} finally {
			app.setRunning(false);
		}
		checkErrorLevel(mark, "0");
		System.setSecurityManager(null);
		releaseRunnerLock();
	}

	private void checkErrorLevel(long mark, String exitCode) throws Exception {
		System.out.println("************** Waiting for the logs to get updated");
		Thread.sleep(2000);
		if (logFile.exists()) {
			String newText = JSystemTestUtils.readFromPosition(logFile, mark);
			errorLevelpos = newText.indexOf("System exit " + exitCode);

		}
		System.out.println("**************** FOUND ERROR CODE AT: " + errorLevelpos);
		Assert.assertNotSame(-1, errorLevelpos);
		releaseRunnerLock();
	}

	@After
	public void afterFromTest() throws Exception {
		System.out.println("************* @After from Test Class");
		releaseRunnerLock();

	}

	public void releaseRunnerLock() throws Exception {
		FileLock lock = FileLock.getFileLock(CommonResources.LOCK_FILE);
		lock.releaseLock();
	}

}

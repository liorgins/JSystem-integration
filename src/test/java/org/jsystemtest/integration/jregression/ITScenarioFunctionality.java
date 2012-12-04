package org.jsystemtest.integration.jregression;

import junit.framework.Assert;

import org.jsystemtest.integration.AbstractJSystemIT;
import org.jsystemtest.integration.TestType;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.Test;

public class ITScenarioFunctionality extends AbstractJSystemIT {

	/**
	 * 1. create scenario and add some test to it
	 * 2. add the the same scenario to it self as son scenario.
	 * 3. assert that warning dialog popped up.
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkPreventAddingScenarioToItself() throws Exception {
		
		app.createScenario("checkPreventAddingScenarioToItselfScnario");
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);
		
		testsTreeTab.addTest("checkPreventAddingScenarioToItselfScnario", TestType.SCENARIO.getType(), 1);
		
		boolean isWarningOpened = app.checkIfWarningDialogOpenedAndCloseIt();
		Assert.assertEquals(true, isWarningOpened);
	}

	/**
	 * 1. create new scenario A and add 3 test that pass to it.
	 * 2. create new scenario A' and add 3 test that pass, test with params, the scenario A. 
	 * 3. select the test with the params and set one of the params value.
	 * 4. set scenario A files to read only
	 * 5. select the test with the params and change one of the params value.
	 * 6. save scenario as new name and assert the new scenario is loaded.
	 * 7. get the the value from the test with the params and hold it.
	 * 8. run the tests and that assert number of tests that ran is ok.
	 * 9. open the original scenario that was saved as and get the value from the test with the params
	 * 	  and assert that the values are equal.
	 *
	 * @throws Exception
	 * */

	@Test
	public void testSaveAs() throws Exception {

//		final String subScenarioName = "sonScenario";
//		app.createScenario(subScenarioName);
//		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
//		testsTreeTab.addTest("reportSuccess", "Example", 3);
//		app.getToolBar().pushSaveScenarioButton();
//
//		final String originalScenarioName = "saveAsScenarioOriginal";
//		app.createScenario(originalScenarioName);
//		testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
//		testsTreeTab.addTest("reportSuccess", "Example", 3);
//		testsTreeTab.addTest("testWithParameters", "Example", 1);
//		testsTreeTab.addTest(subScenarioName, TestType.SCENARIO.getType(), 1);
//
//		final String expectedOriginalValue = "original";
//		app.getTestTableController().getScenarioTree().selectTestByRow(4);
//		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();
//		testInfoTab.setTestParameter("General", "str", expectedOriginalValue, false);
//		app.getToolBar().pushSaveScenarioButton();
//
//		if (!app.setScenarioFilesReadable(originalScenarioName, false)) {
//			System.out.println("Failed to set scenario files as readable");
//		}
//
//		app.getToolBar().pushRefreshButton();
//		System.out.println("Changing parameter value");
//		final String expectedNewValue = "new";
//		app.getTestTableController().getScenarioTree().selectTestByRow(4);
//		testInfoTab.setTestParameter("General", "str", expectedNewValue, false);
//
//		System.out.println("Saving scenario as new name");
//		app.getTestTableController().getScenarioTree().selectTestByRow(0);
//		final String newScenarioName = "saveAsScenarioNew";
//		app.saveScenarioAs(newScenarioName);
//		System.out.println("Asserting that the new scenario was loaded");
//		final String currentScenario = app.getTestTableController().getCurrentScenarioName();
//		Assert.assertEquals("The new scenario was not loaded", newScenarioName, currentScenario);
//		
//		app.getTestTableController().getScenarioTree().selectTestByRow(4);
//		String actualValue = testInfoTab.getTestParameter("General", "str");
//		Assert.assertEquals(expectedNewValue, actualValue);
//		
//		app.getTestsTreeController().getReporterTab().pushInitReportersButton();
//		app.getToolBar().pushPlayButton();
//		Assert.assertEquals(0, app.waitForRunEnd());
//		int testpass = XmlReportHandler.getInstance().getNumberOfTestsPass();
//		Assert.assertEquals("TESTS RUN - PASS: ", 7, testpass);
//
//		 app.openScenario(originalScenarioName);
//		 app.getTestTableController().getScenarioTree().selectTestByRow(4);
//	     actualValue = testInfoTab.getTestParameter("General", "str");
//		 Assert.assertEquals(expectedOriginalValue, actualValue);
	}
}
package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.TestType;
import org.jsystemtest.integration.pageobjects.TestInfoTab;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.testng.annotations.Test;


public class ITScenarioFunctionality extends AbstractITJSystem {

	final String subScenarioName = "sonScenario";
	final String originalScenarioName = "saveAsScenarioOriginal";
	final String expectedOriginalValue = "original";
	final String expectedNewValue = "new";
	final String newScenarioName = "saveAsScenarioNew";

	/**
	 * 1. create scenario and add some test to it.<br>
	 * 2. add the the same scenario to it self as son scenario.<br>
	 * 3. assert that warning dialog popped up.<br>
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
		Assert.assertEquals(isWarningOpened, true);
	}

	/**
	 * 1. create new scenario A and add 3 test that pass to it.<br>
	 * 2. create new scenario A' and add 3 test that pass, test with params, the scenario A.<br>
	 * 3. select the test with the params and set one of the params value.<br>
	 * 4. set scenario A files to read only.<br>
	 * 5. select the test with the params and change one of the params value.<br>
	 * 6. save scenario as new name and assert the new scenario is loaded.<br>
	 * 7. get the the value from the test with the params and hold it.<br>
	 * 8. run the tests and that assert number of tests that ran is ok.<br>
	 * 9. open the original scenario that was saved as and get the value from
	 * 	  the test with the params and assert that the values are equal.<br>
	 * 
	 * @throws Exception
	 * */
	@Test
	public void saveAs() throws Exception {

		app.createScenario(subScenarioName);
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);
		app.getToolBar().pushSaveScenarioButton();

		app.createScenario(originalScenarioName);
		testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.addTest("reportSuccess", "Example", 3);
		testsTreeTab.addTest("testWithParameters", "Example", 1);
		testsTreeTab.addTest(subScenarioName, TestType.SCENARIO.getType(), 1);

		app.getTestTableController().getScenarioTree().selectTestByRow(4);
		TestInfoTab testInfoTab = app.getTestsTreeController().getTestInfoTab();
		testInfoTab.setTestParameter("General", "str", expectedOriginalValue, false);
		app.getToolBar().pushSaveScenarioButton();

		if (!app.setScenarioFilesReadable(originalScenarioName, false)) {
			System.out.println("Failed to set scenario files as readable");
		}

		app.getToolBar().pushRefreshButton();
		app.getTestTableController().getScenarioTree().selectTestByRow(4);
		testInfoTab.setTestParameter("General", "str", expectedNewValue, false);

	
		app.getTestTableController().getScenarioTree().selectTestByRow(0);
		app.saveScenarioAs(newScenarioName);
		final String currentScenario = app.getTestTableController().getCurrentScenarioName();
		Assert.assertEquals(currentScenario, newScenarioName);

		app.getTestTableController().getScenarioTree().selectTestByRow(4);
		String actualValue = testInfoTab.getTestParameter("General", "str");
		Assert.assertEquals(actualValue, expectedNewValue);

		app.getTestsTreeController().getReporterTab().pushInitReportersButton();
		app.playAndWaitForRunEnd();
		Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsPass(),7);

		app.openScenario(originalScenarioName);
		app.getTestTableController().getScenarioTree().selectTestByRow(4);
		actualValue = testInfoTab.getTestParameter("General", "str");
		Assert.assertEquals(actualValue, expectedOriginalValue);
	}
}
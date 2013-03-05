package org.jsystemtest.integration.jregression;

import jsystem.framework.scenario.ScenariosManager;
import jsystem.treeui.teststable.ScenarioTreeNode;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.TestsTableController;
import org.jsystemtest.integration.pageobjects.TestsTreeController;
import org.testng.annotations.Test;

public class ITFeatureEditOnlyLocal extends AbstractITJSystem {

	/**
	 * 1. Create a Scenario and Add tests to it.<br>
	 * 2. Mark as edit only local.<br>
	 * 3. Clean the scenario and Save it.<br>
	 * 4. Make another scenario and Add the first scenario to that scenario.<br>
	 * 5. check if the scenario is uneditable.<br>
	 */
	@Test
	public void editOnlyLocalFeatureBasicOperation() throws Exception {

		final String subEditOnlyLocalScenarioName = "Local only editable scenario";
		final String rootScenarioName = "Root Scenario";
		
		app.createScenario(subEditOnlyLocalScenarioName);
		TestsTreeController testsTreeController = app.getTestsTreeController();
		testsTreeController.getTestsTreeTab().addTest("testWithParameters", "Example", 2);

		TestsTableController testsTableController = app.getTestTableController();
		testsTableController.getScenarioTree().markAsEditLocalOnly(0);

		Assert.assertEquals(ScenariosManager.getInstance().getCurrentScenario().isEditLocalOnly(), true);
		app.getToolBar().pushSaveScenarioButton();

		app.createScenario(rootScenarioName);
		testsTreeController.getTestsTreeTab().addTest(subEditOnlyLocalScenarioName, "scenarios", 1);

		ScenarioTreeNode scenarioTreeNode = testsTableController.getScenarioTree().selectTestByIndexesPath(0);
		boolean isEditable = testsTreeController.getTestInfoTab().isScenarioFieldsAreEditable(scenarioTreeNode);
		Assert.assertEquals(isEditable, false);

	}
}

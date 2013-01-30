package org.jsystemtest.integration.jregression;

import jsystem.framework.TestProperties;
import jsystem.framework.scenario.ScenariosManager;
import jsystem.treeui.teststable.ScenarioTreeNode;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.TestsTableController;
import org.jsystemtest.integration.pageobjects.TestsTreeController;
import org.junit.Test;

public class ITFeatureEditOnlyLocal extends AbstractITJSystem{

	
	/**
	 * 1. Create a Scenario 2. Add tests to it 3. Mark as edit only local
	 * 4. Clean the scenario. 5.Save it 6.Make another scenario
	 * 7. Add the first scenario to this scenario 8. check if its uneditable
	 */
	@Test
	@TestProperties(name = "Test local only edit feature basic operation.")
	public void editOnlyLocalFeatureBasicOperation() throws Exception {
	
		final String subEditOnlyLocalScenarioName = "Local only editable scenario";
		final String rootScenarioName = "Root Scenario";
		
		app.createScenario(subEditOnlyLocalScenarioName);
		TestsTreeController testsTreeController = app.getTestsTreeController();
		testsTreeController.getTestsTreeTab().addTest("testWithParameters", "Example", 2);
		
		TestsTableController testsTableController = app.getTestTableController();
		testsTableController.getScenarioTree().markAsEditLocalOnly(0);
		
		Assert.assertEquals(true, ScenariosManager.getInstance().getCurrentScenario().isEditLocalOnly());
		
		app.getToolBar().pushSaveScenarioButton();
		
		app.createScenario(rootScenarioName);
		
		testsTreeController.getTestsTreeTab().addTest(subEditOnlyLocalScenarioName, "scenarios", 1);
		
		ScenarioTreeNode scenarioTreeNode = testsTableController.getScenarioTree().selectTestByIndexesPath(0);
		
		boolean isEditable = testsTreeController.getTestInfoTab().isScenarioFieldsAreEditable(scenarioTreeNode);
		Assert.assertEquals(false, isEditable);
	
	}
}

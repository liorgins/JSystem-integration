package org.jsystemtest.integration.jregression;

import java.awt.event.KeyEvent;

import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.TestProperties;
import jsystem.framework.scenario.ScenariosManager;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.ScenarioTree;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITUIKeyboardSupport extends AbstractITJSystem {
	
	/**
	 * 1. open the default scenario.
	 * 2. clear it add 2 tests that pass to scenario.
	 * 
	 * @throws Exception
	 */
	@Before
	public void fixture() throws Exception {
		
		app.openScenario("default");
		
		app.clearCurrentRootScenario("default");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 2);
		
	}

	
	/**
	 * tests mapping and unMapping using keys
	 * 
	 * 1. assert that 2 tests are mapped.<br>
	 * 2. unMap first test using keys.<br>
	 * 3. assert that only 1 test is mapped. <br>
	 * 4. reMap first test using keys <br>
	 * 5. assert that 2 tests are mapped.<br>
	 * @throws Exception
	 */
	
	@Test
	@TestProperties(name = "Map tests with 'Space' key")
	public void mapTestsUsigKeys() throws Exception {
		
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		int currentMappedTestCount = JSystemTestUtils.getMappedTestCount();
		Assert.assertEquals(2, currentMappedTestCount);

		scenarioTree.selectTestByRow(1);
		app.pressKey(KeyEvent.VK_SPACE);
		currentMappedTestCount--;
		app.getToolBar().pushSaveScenarioButton();
		
		Assert.assertEquals(currentMappedTestCount, JSystemTestUtils.getMappedTestCount());
		
		scenarioTree.selectTestByRow(1);
		app.pressKey(KeyEvent.VK_SPACE);
		currentMappedTestCount++;
		Thread.sleep(500);
		app.getToolBar().pushSaveScenarioButton();
		
		Assert.assertEquals(currentMappedTestCount, JSystemTestUtils.getMappedTestCount());
	}

	@After
	public void clean() {
		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}
	
	/**
	 * tests delete test using keys
	 * 
	 * 1. assert that 2 tests are mapped.<br>
	 * 2. delete first test using keys.<br>
	 * 3. assert that only 1 test exist. <br>

	 * @throws Exception
	 */
	
	@Test
	@TestProperties(name = "Map tests with 'Delete' key")
	public void deleteTestUsingKeys() throws Exception {
		
		ScenarioTree scenarioTree = app.getTestTableController().getScenarioTree();
		int currentMappedTestCount = JSystemTestUtils.getMappedTestCount();
		Assert.assertEquals(2, currentMappedTestCount);

		scenarioTree.selectTestByRow(1);
		app.pressKey(KeyEvent.VK_DELETE);
		currentMappedTestCount--;
		app.getToolBar().pushSaveScenarioButton();
		
		Assert.assertEquals(currentMappedTestCount, JSystemTestUtils.getMappedTestCount());
		
	}
	
}

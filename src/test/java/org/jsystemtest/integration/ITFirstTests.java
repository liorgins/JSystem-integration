 package org.jsystemtest.integration;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeController;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFileChooserOperator;


public class ITFirstTests {

	private static JSystemApplication app;

	@BeforeClass
	public static void prepareEnv() throws InterruptedException {
		app = new JSystemApplication();
		app.launch();
	}
	
	/**
	 * 1.create new scenario.
	 * 2.select test and add it.
	 * 3.run scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void current() throws Exception {
	
		app.getMenuBar().pushMenu("File|New Scenario");
		
		JFileChooserOperator newScenarioFileChooser = app.getFileChooserOerator();
		newScenarioFileChooser.chooseFile("newScenarioTest");
		
		TestsTreeController testTreeController = app.getTestsTreeController();
		testTreeController.getTestsTree().selectTestByRow(4);
		testTreeController.addTestsToScenario();
		
		app.pushSaveScenarioButton();
		
		app.pushPlayButton();	
	}
	
}
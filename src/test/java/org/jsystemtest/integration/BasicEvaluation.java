package org.jsystemtest.integration;


import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTreeOperator;

public class BasicEvaluation {

	private static JSystemApplication jSystemApplication;

	@BeforeClass
	public void prepareEnv() throws InterruptedException {
		System.out.println("Preparing env - starting jsystem-runner");
		jSystemApplication = new JSystemApplication();
		jSystemApplication.launch();

	}

	@Test
	public void cleanJemmy() throws InterruptedException {
		
		JFrameOperator main = new JFrameOperator("JSystem");
		
		new JMenuBarOperator(main, new NameChooser("TEST1")).pushMenuNoBlock("File|New Scenario");
		JFileChooserOperator newScenarioFileChooser = new JFileChooserOperator();
		
		new JTextFieldOperator(newScenarioFileChooser).setText("JEMMY SCENARIO");
		new JButtonOperator(newScenarioFileChooser, "Save As").clickMouse();
		
		new JTreeOperator(main, new NameChooser("testsTree")).selectRow(4);
		
		new JButtonOperator(main, new TooltipChooser("Add Tests to Scenario"));

	}
	
	@Test
	public void current() throws Exception {
		
		jSystemApplication.getMenuBar().pushMenu("File|New Scenario");
		
		JFileChooserOperator newScenarioFileChooser = jSystemApplication.getFileChooserOerator();
		new JTextFieldOperator(newScenarioFileChooser).setText("JEMMY SCENARIO");
		new JButtonOperator(newScenarioFileChooser, "Save As").clickMouse();
		
		TestsTreeController testTreeController = jSystemApplication.getTestsTreeController();
		testTreeController.getTestsTree().selectTestByRow(4);
		testTreeController.addTestsToScenario();
		
		
		
	}
	
	@AfterClass
	public void tearDown() {
		
		new EventTool().waitNoEvent(1000);
		
		jSystemApplication.close();
	}

}

 package org.jsystemtest.integration;

import java.io.File;

import jsystem.extensions.report.html.Report;
import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.common.CommonResources;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeController;
import org.jsystemtest.integration.pageobjects.MainToolBar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;


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
	 * 3.save scenario and run it
	 * 
	 * @throws Exception
	 */
	@Test
	public void current() throws Exception {
		
		JFileChooserOperator newScenarioFileChooser = app.getMenuBar().getFileMenu().newSceario();	
		newScenarioFileChooser.chooseFile("newScenarioTest");

		TestsTreeController testTreeController = app.getTestsTreeController();
		testTreeController.getTestsTreeTab().getTestTree().selectTestByRow(4);
		testTreeController.getTestsTreeTab().pushAddTestsButton();
		
		MainToolBar toolBar  = app.getToolBar();
		toolBar.pushSaveScenarioButton();
		toolBar.pushPlayButton();			
		Assert.assertEquals(0, app.waitForRunEnd(30000));
		System.out.println("******** Number of tests executed: " + XmlReportHandler.getInstance().getNumberOfTests());	
	}
	
	@After
	public void cleanTest() {
		System.out.println("cleanning test fired");
	}

}
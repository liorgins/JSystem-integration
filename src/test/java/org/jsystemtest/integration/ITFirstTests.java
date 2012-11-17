 package org.jsystemtest.integration;

import java.io.File;

import jsystem.extensions.report.html.Report;
import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.common.CommonResources;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeController;
import org.jsystemtest.integration.pageobjects.MainToolBar;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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
	 * 4.verify number of tests run.
	 * 
	 * @throws Exception
	 */
	@Test
	public void current() throws Exception {
		
		JFileChooserOperator newScenarioFileChooser = app.getMenuBar().getFileMenu().newSceario();	
		newScenarioFileChooser.chooseFile("newScenarioTest");

		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("reportFailure", "TestsExamples");
		testsTreeTab.setTestsNumberSpinner(1);
		testsTreeTab.pushAddTestsButton();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("reportSuccess", "TestsExamples");
		testsTreeTab.setTestsNumberSpinner(2);
		testsTreeTab.pushAddTestsButton();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("reportWarning", "TestsExamples");
		testsTreeTab.setTestsNumberSpinner(3);
		testsTreeTab.pushAddTestsButton();
		
		
		MainToolBar toolBar  = app.getToolBar();
		toolBar.pushSaveScenarioButton();
		toolBar.pushPlayButton();			
		Assert.assertEquals(0, app.waitForRunEnd(30000));
		
		Assert.assertEquals(6, XmlReportHandler.getInstance().getNumberOfTests());
		Assert.assertEquals(2, XmlReportHandler.getInstance().getNumberOfTestsPass());
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsFail());
		Assert.assertEquals(3, XmlReportHandler.getInstance().getNumberOfTestsWarning());
		
	}
	
	
	@After
	public void cleanTest() {
		System.out.println("@After");
		System.out.println("Cleanning generated scenarios");
		File scenarioDirectory = new File("target/test-classes/jsystem-base-tests/target/classes/scenarios");
		for(File file : scenarioDirectory.listFiles()) {
			if(!file.getName().equals("default.xml") && !file.getName().equals("default.properties")) {
				file.delete();
				System.out.println(file.getName() + " has been deleted seccessfuly from /target/classes/scenarios");
			}
		}
		
		scenarioDirectory = new File("target/test-classes/jsystem-base-tests/src/main/resources/scenarios");
		for(File file : scenarioDirectory.listFiles()) {
			if(!file.getName().equals("default.xml") && !file.getName().equals("default.properties")) {
				file.delete();
				System.out.println(file.getName() + " has been deleted seccessfuly /src/main/resources/scenarios");
			}
		}
	}

}
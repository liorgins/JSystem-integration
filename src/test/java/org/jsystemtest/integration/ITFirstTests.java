 package org.jsystemtest.integration;

import jsystem.extensions.report.xml.XmlReportHandler;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.MainToolBar;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFileChooserOperator;

import utils.JSystemTestUtils;


public class ITFirstTests {

	private static JSystemApplication app;

	@BeforeClass
	public static void prepareEnv() throws InterruptedException {
		app = new JSystemApplication();
		app.setJSystemProperties(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.DEFAULT_SUT_FILE);
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
		Assert.assertEquals(0, app.waitForRunEnd());
		
		Assert.assertEquals(6, XmlReportHandler.getInstance().getNumberOfTests());
		Assert.assertEquals(2, XmlReportHandler.getInstance().getNumberOfTestsPass());
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsFail());
		Assert.assertEquals(3, XmlReportHandler.getInstance().getNumberOfTestsWarning());
		
	}
	
	
	@After
	public void cleanTest() {
		System.out.println("@After");
		
		System.out.println("Cleanning generated scenarios");	
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);
		
		System.out.println("Cleanning generated logs and properties files");
		JSystemTestUtils.cleanPropertiesAndLogs(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}
	


}
package org.jsystemtest.integration.jregression;

import java.io.File;

import jsystem.extensions.report.xml.XmlReportHandler;



import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITReporterFunctionality extends AbstractITJSystem {
	
	
	/**
	 * 1. open the default scenario.
	 * 2. clear it.
	 * 3. init reporter
	 * 
	 * @throws Exception
	 */
	@BeforeMethod
	public void classFixture() throws Exception {
		
		app.openScenario("default");
		
		app.clearCurrentRootScenario("default");
		
		app.getTestsTreeController().getReporterTab().pushInitReportersButton();
		
	}
	
	/**
	 * Check that the method getCurrentTestFolder works.
	 * Also check that it works after init of the reports.
	 * @throws Exception
	 */
	@Test
	public void GetCurrentTestFolder() throws Exception{
	
		Reporter.log("execute test that get the current test folder");

		app.getTestsTreeController().getTestsTreeTab().addTest("testGetCurrentTestFolder", "Example", 1);
		
		app.playAndWaitForRunEnd();
		
		Reporter.log("check that the test end successful and the report step was added");
		Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsPass(), 1);
		Assert.assertEquals(JSystemTestUtils.checkXmlTestAttribute(1, "steps", "log" + File.separator + "current" + File.separator + "test_1"), true);
		
		app.playAndWaitForRunEnd();
		

		Reporter.log("check that the test end successful and now the current dir is test_2");
		
		Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsPass(), 1);
		Assert.assertEquals(JSystemTestUtils.checkXmlTestAttribute(2, "steps", "log" + File.separator + "current" + File.separator + "test_2"), true);
	
		
		app.getTestsTreeController().getReporterTab().pushInitReportersButton();
		
		app.playAndWaitForRunEnd();
		
		
		Reporter.log("check that the test end successful and now the current dir returned to test_1");
		Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTestsPass(), 1);
		Assert.assertEquals(JSystemTestUtils.checkXmlTestAttribute(1, "steps", "log" + File.separator + "current" + File.separator + "test_1"), true);
		
	}
}

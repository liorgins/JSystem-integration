package org.jsystemtest.integration.jregression;

import java.io.File;

import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.TestProperties;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.Before;
import org.junit.Test;

public class ITReporterFunctionality extends AbstractITJSystem {
	
	
	/**
	 * 1. open the default scenario.
	 * 2. clear it.
	 * 3. init reporter
	 * 
	 * @throws Exception
	 */
	@Before
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
	@TestProperties(name = "5.2.8.2.5 check a report getCurrentTestFolder method")
	public void testGetCurrentTestFolder() throws Exception{
	
		System.out.println("execute test that get the current test folder");

		app.getTestsTreeController().getTestsTreeTab().addTest("testGetCurrentTestFolder", "Example", 1);
		
		app.playAndWaitForRunEnd();
		
		System.out.println("check that the test end successful and the report step was added");
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsPass());
		Assert.assertEquals(true,JSystemTestUtils.checkXmlTestAttribute(1, "steps", "log" + File.separator + "current" + File.separator + "test_1"));
		
		app.playAndWaitForRunEnd();
		

		System.out.println("check that the test end successful and now the current dir is test_2");
		
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsPass());
		Assert.assertEquals(true,JSystemTestUtils.checkXmlTestAttribute(2, "steps", "log" + File.separator + "current" + File.separator + "test_2"));
	
		
		app.getTestsTreeController().getReporterTab().pushInitReportersButton();
		
		app.playAndWaitForRunEnd();
		
		
		System.out.println("check that the test end successful and now the current dir returned to test_1");
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTestsPass());
		Assert.assertEquals(true,JSystemTestUtils.checkXmlTestAttribute(1, "steps", "log" + File.separator + "current" + File.separator + "test_1"));
		
	}
}

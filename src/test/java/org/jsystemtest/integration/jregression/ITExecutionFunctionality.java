package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.TestProperties;
import junit.framework.Assert;

import org.jsystemtest.integration.AbstractITJSystem;
import org.junit.Before;
import org.junit.Test;

public class ITExecutionFunctionality extends AbstractITJSystem {

	/**
	 * 1. open the default scenario. 2. clear it.
	 * 
	 * @throws Exception
	 */
	@Before
	public void fixture() throws Exception {

		app.openScenario("default");

		app.clearCurrentRootScenario("default");

		
	}
	
	/**
	 * 1. Create a scenario with tests that fail and test that pass 2. Set the freeze on fail to
	 * true 3. Run the scenario and wait for the freeze dialog 4. set the freeze
	 * on fail to false 5. Run the scenario and wait for it to finish. 6. Check
	 * that 2 tests run and 0 passes.
	 * 
	 * @throws Exception
	 * @params.include
	 */
	@Test
	@TestProperties(name = "test freeze on fail functionlity")
	public void FreezeOnFail() throws Exception {

		
		app.getTestsTreeController().getTestsTreeTab().addTest("reportFailure", "Example", 1);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 1);

		app.getToolBar().clickFreezeOnFail();
		app.getToolBar().pushPlayButton();

		app.waitForFreezeDialog();
		
		Assert.assertEquals(1, XmlReportHandler.getInstance().getNumberOfTests());
		
		app.getToolBar().clickFreezeOnFail();
		app.playAndWaitForRunEnd();
		
		Assert.assertEquals(2, XmlReportHandler.getInstance().getNumberOfTests());

	}
}

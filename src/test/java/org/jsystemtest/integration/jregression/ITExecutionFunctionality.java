package org.jsystemtest.integration.jregression;

import jsystem.extensions.report.xml.XmlReportHandler;

import org.jsystemtest.infra.assertion.Assert;
import org.jsystemtest.integration.AbstractITJSystem;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITExecutionFunctionality extends AbstractITJSystem {

	/**
	 * 1. open the default scenario.<br>
	 * 2. clear it.<br>
	 * 
	 * @throws Exception
	 */
	@BeforeMethod
	public void fixture() throws Exception {

		app.openScenario("default");

		app.clearCurrentRootScenario("default");

	}

	/**
	 * 1. Create a scenario with tests that fail and test that pass.<br>
	 * 2. Set the freeze on fail to true.<br>
	 * 3. Run the scenario and wait for the freeze dialog.<br>
	 * 4. set the freeze on fail to false.<br>
	 * 5. Run the scenario and wait for it to finish.<br>
	 * 6. Check that 2 tests run and 0 passes.<br>
	 * 
	 * @throws Exception
	 */
	@Test
	public void FreezeOnFail() throws Exception {

		app.getTestsTreeController().getTestsTreeTab().addTest("reportFailure", "Example", 1);
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 1);

		app.getToolBar().clickFreezeOnFail();
		app.getToolBar().pushPlayButton();

		app.waitForFreezeDialog();

		Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTests(), 1);

		app.getToolBar().clickFreezeOnFail();
		app.playAndWaitForRunEnd();

		Assert.assertEquals(XmlReportHandler.getInstance().getNumberOfTests(), 2);

	}
}

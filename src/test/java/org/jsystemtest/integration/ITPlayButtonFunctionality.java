package org.jsystemtest.integration;

import jsystem.framework.FrameworkOptions;
import junit.framework.Assert;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.pageobjects.TestsTreeTab;
import org.junit.BeforeClass;
import org.junit.Test;


public class ITPlayButtonFunctionality {
 
	
	private static JSystemApplication app;

	@BeforeClass
	public static void prepareEnv() throws InterruptedException {
		app = new JSystemApplication();
		app.setJSystemStandartProperties(JSystemApplication.CURRENT_WORKING_DIRECTORY, JSystemApplication.DEFAULT_SUT_FILE);
		app.setJSystemOptionalProperties(new PropertyPair(FrameworkOptions.AUTO_DELETE_NO_CONFIRMATION, JSystemApplication.TRUE));
		app.launch();
		
	}
	
	/**
	 * 1. add 5 test to scenario tree and check that 5 test are checked
	 * 2. check that play button is enabled
	 * 3. delete the tests 
	 * 4. check that play button is disabled
	 * @throws Exception 
	 */
	@Test
	public void chechPlayButtonEnabledDisabled() throws Exception{
		
		TestsTreeTab testsTreeTab = app.getTestsTreeController().getTestsTreeTab();
		testsTreeTab.getTestTree().selectPathByNodeAndParentNode("reportSuccess", "TestsExamples");
		testsTreeTab.setTestsNumberSpinner(5);
		testsTreeTab.pushAddTestsButton();
	
		Assert.assertEquals(true, app.getToolBar().isPlayButtonEnable());

		app.getTestTableController().removeAllTests();

		Assert.assertEquals(false, app.getToolBar().isPlayButtonEnable());
	}
}

package org.jsystemtest.integration;

import org.jsystemtest.infra.report.Reporter;
import org.jsystemtest.integration.database.DatabaseSystemModule;
import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class AbstractITJSystem extends AbstractTestNGSpringContextTests {

	@Autowired
	protected DatabaseSystemModule db;

	@Autowired
	protected JSystemApplication app;

	@BeforeMethod
	public void setup() throws Exception {
		Reporter.log("Try Launch()");
		app.launch();
	}

	@AfterClass
	public static void cleanClass() {

		Reporter.log("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);

		Reporter.log("Cleanning generated logs and properties files");
		JSystemTestUtils.cleanPropertiesAndLogs(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}

	@AfterMethod
	public void afterFromAbstract() {
	}
}

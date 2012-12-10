package org.jsystemtest.integration;

import jsystem.framework.common.CommonResources;
import jsystem.framework.report.RunnerListenersManager;
import jsystem.utils.FileLock;

import org.jsystemtest.integration.pageobjects.JSystemApplication;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class AbstractJSystemIT {

	@Autowired
	protected JSystemApplication app;
	
	@Before
	public void setup() throws Exception {
		
		System.out.println("@Before from Abstaract Class*****************Try Launch()");
		app.launch();
	
	}
	
	@AfterClass
	public static void cleanClass() {
		System.out.println("@AfterClass");

		System.out.println("Cleanning generated scenarios");
		JSystemTestUtils.cleanScenarios(JSystemApplication.CURRENT_WORKING_DIRECTORY);

		System.out.println("Cleanning generated logs and properties files");
		JSystemTestUtils.cleanPropertiesAndLogs(JSystemApplication.CURRENT_WORKING_DIRECTORY);
	}
	
	@After
	public void afterFromAbstract() {
		System.out.println("************* @After from abstarct class");
	}
}

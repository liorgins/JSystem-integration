package org.jsystemtest.integration;

import java.io.File;
import java.io.FileNotFoundException;

import jsystem.extensions.report.html.HtmlTestReporter;
import jsystem.framework.FrameworkOptions;
import jsystem.utils.FileUtils;
import junit.framework.Assert;

import org.jsystemtest.integration.NoExitSecurityManager;
import org.jsystemtest.integration.PropertyPair;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CloseJSystemOperationTests extends AbstractITJSystem {

	@Before
	public void before() throws Exception {
		System.out.println("**************** @Before From Test Class");
		System.setSecurityManager(new NoExitSecurityManager());
	}
	
	/**
	 * 1. open runner and initReportes, check no logs available
	 * 2. add tests and run them, check that logs were created.
	 * 3. close runner gracefully and check that logs were backed up in old dir
	 * 4. open runner and initReporters, check no logs available
	 * 5. add tests and run them, check that logs were created.
	 * 6. close runner forcibly and check that logs were not backed up
	 * 7. open runner and check that logs were backed up in old directory.
	 * @throws Throwable 
	 */
	@Test
	public void checkLogsBackupOnRunnerReopenIfClosedFocibly() throws Throwable{
		boolean logsExist = false;
		boolean backupExist = false;
		File tmpDir = null;
		app.getMenuBar().getToolsMenu().InitReporters();
		app.setJSystemOptionalProperties(new PropertyPair(FrameworkOptions.HTML_ZIP_DISABLE, "false"));
		
		System.out.println("***********************logs dir is: " + getLogsDir("current"));
		logsExist = runCreatedRemoteLogs(getLogsDir("current"));
		
		System.out.println("clear remote logs directory");
		//remove all logs from current directory
		tmpDir = getLogsDir("current");
		FileUtils.deltree(tmpDir);
		new File(tmpDir.getAbsolutePath()).mkdirs();
		
		System.out.println("clear logs backup directory");
		//remove all logs backups from old directory
		tmpDir = getLogsDir("old");
		FileUtils.deltree(tmpDir);
		new File(tmpDir.getAbsolutePath()).mkdirs();
		
		System.out.println("test that logs doesn't exist before play");
		Assert.assertEquals(false, logsExist);
		
		app.createScenario("tempScenario");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 5);
		app.playAndWaitForRunEnd();
		
		System.out.println("test that after play logs exist");
		logsExist = runCreatedRemoteLogs(getLogsDir("current"));
		Assert.assertEquals(true, logsExist);
		
		
		System.out.println("#############################before call for exit");
		app.exitVirtualyThroughMenu();
		System.out.println("####################################After Call for exit");
		
		JSystemTestUtils.releaseRunnerLock();
		
		System.out.println("test that logs backup exist after runner standard close");
		backupExist = backupLogsCreated(getLogsDir("old"));
		//Assert.assertEquals(true, backupExist);
		
		System.out.println("clear all log backup");
		//remove all logs backups from old directory
		tmpDir = getLogsDir("old");
		FileUtils.deltree(tmpDir);
		new File(tmpDir.getAbsolutePath()).mkdirs();
		
//		report.step("navigate out and back in to the fixture to create remote env again in an orderly fassion.");
//		FixtureManager.getInstance().goTo(CreateEnvFixture.class.getName());
//		FixtureManager.getInstance().goTo(NewActivateRunnerFixture.class.getName());
//		super.setUp(); 
		
		app.launch();
		
		System.out.println("create a new run of tests");
		app.setJSystemOptionalProperties(new PropertyPair(FrameworkOptions.HTML_ZIP_DISABLE, "false"));
		app.createScenario("tempScenario");
		app.getTestsTreeController().getTestsTreeTab().addTest("reportSuccess", "Example", 5);
		app.playAndWaitForRunEnd();
		
		//kill remote runner brutally.
		System.out.println("kill remote process forcibly");
		JSystemTestUtils.killRunnerProcess();
		
		System.out.println("test that backup logs doesn't exist");
		backupExist = backupLogsCreated(getLogsDir("old"));
		Assert.assertEquals(false, backupExist);

//		report.step("navigate out and back in to the fixture to create remote env again in an orderly fassion.");
//		FixtureManager.getInstance().goTo(CreateEnvFixture.class.getName());
//		FixtureManager.getInstance().goTo(NewActivateRunnerFixture.class.getName());

		app.launch();
		
		backupExist = backupLogsCreated(getLogsDir("old"));
		Assert.assertEquals(false, backupExist);
	}
	
	@After
	public void after() throws Exception {
		System.out.println("************* @After from Test Class");
		System.setSecurityManager(null);
		JSystemTestUtils.releaseRunnerLock();

	}
	
	private boolean backupLogsCreated(File remoteBackupLogsdir){
		File[] files = remoteBackupLogsdir.listFiles();
		for(File file:files){
			if(file.getAbsolutePath().endsWith(".zip")){
				return true;
			}
		}
		return false;
	}
	
	private boolean runCreatedRemoteLogs(File remoteLogsRootDir){
		File[] files = remoteLogsRootDir.listFiles();
		for(File file:files){
			if(file.getAbsolutePath().contains("test_") && file.isDirectory()){
				return true;
			}
		}
		return false;
	}
	
	private File getLogsDir(String oldORcurrent) throws Exception{
	
		File tmp = new File("log"+ File.separator + oldORcurrent);
		if(tmp.exists()){
			return tmp;
		}
		else{
			throw new FileNotFoundException("no file with the name " + tmp.getAbsolutePath()+" exists");
		}
	}
}

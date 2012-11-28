package org.jsystemtest.integration.pageobjects;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.framework.report.ExtendTestListener;
import jsystem.framework.report.ListenerstManager;
import jsystem.framework.scenario.JTestContainer;
import jsystem.framework.scenario.flow_control.AntForLoop;
import jsystem.treeui.TestRunner;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.jsystemtest.integration.PropertyPair;
import org.jsystemtest.integration.RunnerComponentChooser;
import org.jsystemtest.integration.utils.JSystemTestUtils;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.DialogWaiter;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;


public class JSystemApplication extends AbstractPageObject implements ExtendTestListener {

	private JFrameOperator app;
	private boolean runEnd = false;
	public static final String CURRENT_WORKING_DIRECTORY = System.getProperty("user.dir");
	public static final String LOCAL_JSYSTEM_LOG_FILE_NAME = "jsystem0.log";
	public static final String DEFAULT_SUT_FILE = "default.xml";
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	
	public void launch() {
		try {
		
			new ClassReference(TestRunner.class.getName()).startApplication();
	
			app = new JFrameOperator(jmap.getJSyetemMain());
			Assert.assertNotNull("JSystem frame not captured", app);
			
			ListenerstManager.getInstance().addListener(this);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void exitThroughMenu() throws InterruptedException {
		getMenuBar().getFileMenu().Exit();
		
	}
	
	public int closeAndGetErrorLevel() throws InterruptedException {
		File logFile = new File(CURRENT_WORKING_DIRECTORY, LOCAL_JSYSTEM_LOG_FILE_NAME);
		long mark = 0;
		if (logFile.exists()) {
			mark = logFile.length();
		}
		
		exitThroughMenu();
		
		int errorLevel = -1;
		if (logFile.exists()) {
			String newText = JSystemTestUtils.readFromPosition(logFile, mark);
			int pos = newText.indexOf("System exit") + ("System exit".length());
			String errorLevelString = newText.substring(pos);
			errorLevel = Integer.parseInt(errorLevelString);
		}
		
		System.out.println("System exit with error level: " + errorLevel);
		return errorLevel;	
	}
	
	
	/**
	 * 1. get instance of JSystem properties
	 * 2. search for valid tests classes directory in the given path
	 * 3. set the relevant properties 
	 * 
	 * @param testsClassesPath  path to the classes directory or parent of that directory 
	 * @param sutFileName
	 */
	public void setJSystemStandartProperties(String testsClassesPath, String sutFileName) {
		
		JSystemProperties jSystemProperties = JSystemProperties.getInstance();
		
		String testDir = JSystemTestUtils.findValidClassDirectory(testsClassesPath);
		jSystemProperties.setPreference(FrameworkOptions.TESTS_CLASS_FOLDER, testDir);
		jSystemProperties.setPreference(FrameworkOptions.USED_SUT_FILE, sutFileName);
	}
	
	
	public void setJSystemOptionalProperties(PropertyPair...props) {
		JSystemProperties jSystemProperties = JSystemProperties.getInstance();
		
		for (PropertyPair propertyPair : props) {
			jSystemProperties.setPreference(propertyPair.getkey(), propertyPair.getValue());
		}
	}

	public MenuBar getMenuBar() {
		return new MenuBar(app);
	}

	public MainToolBar getToolBar() {
		return new MainToolBar(app);
	}

	public TestsTreeController getTestsTreeController() {
		return new TestsTreeController(app);
	}

	public TestsTableController getTestTableController() {
		return new TestsTableController(app);
	}

	public JFileChooserOperator getFileChooserOerator() {
		return new JFileChooserOperator(app);
	}

	@Override
	public void addError(Test test, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFailure(Test test, AssertionFailedError t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTest(Test test) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTest(Test test) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWarning(Test test) {
		// TODO Auto-generated method stub

	}


	@Override
	public void endRun() {
		System.out.println("endRun Event has fired");
		runEnd = true;
	}

	@Override
	public void startLoop(AntForLoop loop, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endLoop(AntForLoop loop, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startContainer(JTestContainer container) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContainer(JTestContainer container) {
		// TODO Auto-generated method stub

	}

	public int waitForRunEnd() {
		while (!isRunEnd()) {}
		runEnd = false;
		return 0;
	}

	public int waitForRunEnd(long milliseconds) {
		long entry = new Date().getTime();
		while (!isRunEnd()) {
			if(new Date().getTime() - entry > milliseconds) {
				System.out.println("waitForRunEnd has timed out");
				return -1;
			}
		}
		runEnd = false;
		return 0;
	}
	
	public boolean isRunEnd() {
		return runEnd;
	}

	public void setRunEnd(boolean runEnd) {
		this.runEnd = runEnd;
	}

	public void openScenario(String rootScenario) {
		File scenariosFile = new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.TESTS_CLASS_FOLDER), "scenarios");
		JFileChooserOperator openScenarioFileChooser = getMenuBar().getFileMenu().openSceario();
		openScenarioFileChooser.setCurrentDirectory(scenariosFile);
		openScenarioFileChooser.chooseFile(rootScenario);
	}
	
	public void clearScenario(String rootScenario) throws InterruptedException {
		getTestTableController().getScenarioTree().selectTestByRow(0);
		Thread.sleep(500);
		getToolBar().pushDeleteScenarioButton();
		Thread.sleep(500);
		JDialogOperator jDialogOperator = new JDialogOperator("Delete Scenario");
		new JButtonOperator(jDialogOperator, "OK").clickMouse();
		openScenario(rootScenario);
	}
	
	public void createScenario(String rootScenario) throws Exception {
		File scenariosFile = new File(JSystemProperties.getInstance().getPreference(FrameworkOptions.TESTS_CLASS_FOLDER), "scenarios");
		JFileChooserOperator newScenarioFileChooser = getMenuBar().getFileMenu().newSceario();
		newScenarioFileChooser.setCurrentDirectory(scenariosFile);
		newScenarioFileChooser.chooseFile(rootScenario);
		getToolBar().pushSaveScenarioButton();
	}
	
	public boolean checkIfWarningDialogOpenedAndCloseIt() {
		JDialogOperator dialogOperator = getDialogIfExists("Warning", 3000);
		if (dialogOperator != null) {
			dialogOperator.close();
			return true;
		} else {
			return false;
		}
	}
	
	public JDialogOperator getDialogIfExists(String title, int secondsToWait){
		DialogWaiter waiter = new DialogWaiter();
		waiter.getTimeouts().setTimeout("DialogWaiter.WaitDialogTimeout", secondsToWait * 1000);
		JDialogOperator dialog;
		try{
			waiter.waitDialog(new RunnerComponentChooser(title));
			dialog = new JDialogOperator(new RunnerComponentChooser(title));
		}catch (Exception e) {
			return null;
		}
		return dialog;
	}
	
	public boolean setScenarioFilesReadable(final String scenarioName, final boolean readable) throws Exception {
		List<File> scenarioFiles = getScenarioFiles(scenarioName);
		for (File scenarioFile : scenarioFiles) {
			System.out.println("Setting scenario file " + scenarioFile + " permissions");
			if (!scenarioFile.exists()) {
				System.out.println("Scenario file " + scenarioFile + " is not exist");
				return false;
			}
			if (!readable) {
				if (!scenarioFile.setReadOnly()) {
					System.out.println("Failed to set file to read only");
					return false;
				}
			} else {
				System.out.println("Unsupported operation");
				return false;
			}
		}
		return true;
	}
	
	public List<File> getScenarioFiles(String scenarioName) throws Exception {
		// TODO: Can be replaced with scenario.getScenarioFiles() call in the
		// handler side
		List<File> scenarioFileList = new ArrayList<File>();
		final String testsClassFolder = JSystemProperties.getInstance().getPreference(FrameworkOptions.TESTS_CLASS_FOLDER);
		scenarioFileList.add(new File(testsClassFolder + File.separator + "scenarios", scenarioName + ".xml"));
		scenarioFileList.add(new File(testsClassFolder + File.separator + "scenarios", scenarioName + ".properties"));
		return scenarioFileList;
	}

	@Override
	public void startTest(jsystem.framework.report.TestInfo testInfo) {
		// TODO Auto-generated method stub
		
	}

	public void saveScenarioAs(String newScenarioName) {
		JFileChooserOperator jFileChooserOperator = getMenuBar().getFileMenu().saveScenarioAs();
		jFileChooserOperator.chooseFile(newScenarioName);
	}

}

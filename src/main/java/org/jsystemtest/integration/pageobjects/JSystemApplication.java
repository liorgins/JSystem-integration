package org.jsystemtest.integration.pageobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import jsystem.framework.report.ExtendTestListener;
import jsystem.framework.report.ListenerstManager;
import jsystem.framework.report.TestInfo;
import jsystem.framework.scenario.JTestContainer;
import jsystem.framework.scenario.flow_control.AntForLoop;
import jsystem.treeui.TestRunner;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

public class JSystemApplication extends AbstractPageObject implements ExtendTestListener {

	private JFrameOperator app;
	private boolean runEnd = false;

	public boolean isRunEnd() {
		return runEnd;
	}

	public void setRunEnd(boolean runEnd) {
		this.runEnd = runEnd;
	}

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

	public void close() {
		if (app != null) {
			app.close();
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
	public void startTest(TestInfo testInfo) {
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

}
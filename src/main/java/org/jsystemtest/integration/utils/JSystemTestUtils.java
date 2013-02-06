package org.jsystemtest.integration.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.framework.common.CommonResources;
import jsystem.framework.scenario.ScenariosManager;
import jsystem.utils.FileLock;
import jsystem.utils.FileUtils;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class JSystemTestUtils {

	/**
	 * clear all scenarios from project except the default scenario
	 * 
	 * @param path
	 */
	public static void cleanScenarios(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		for (File f : list) {
			if (f.isDirectory()) {
				cleanScenarios(f.getAbsolutePath());
			} else {
				if (root.getName().equals("scenarios") && (!f.getName().equals("default.xml") && !f.getName().equals("default.properties"))) {
					f.delete();
					System.out.println("File: " + f.getAbsoluteFile() + " has been deleted");
				}

			}
		}
	}

	/**
	 * clear all properties and log files generated during jsystem run
	 * 
	 * @param path
	 */
	public static void cleanPropertiesAndLogs(String path) {
		File projectDir = new File(path);

		FilenameFilter genFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".properties") || name.endsWith(".properties.bu") || name.endsWith(".log");
			}
		};
		for
		(File file : projectDir.listFiles(genFilter)) {
			System.out.println(file.getName() + " has been deleted seccessfuly from project root");
			file.delete();

		}
	}

	public static String findValidClassDirectory(String parentPath) {
		if (!verifyClassesDirectory(parentPath)) {
			File parentFile = new File(parentPath);

			FileFilter folderFilter = new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			};

			for (File file : parentFile.listFiles(folderFilter)) {
				if (verifyClassesDirectory(file.getPath())) {
					parentPath = file.getPath();
					break;
				} else {
					parentPath = findValidClassDirectory(file.getPath());
					if (verifyClassesDirectory(parentPath)) {
						break;
					}
				}
			}

		}
		return parentPath;
	}

	public static boolean verifyClassesDirectory(String path) {
		if (path == null) {
			return false;
		}
		boolean sutFolderExists, scenariosFolderExists, isClassesDirectory;

		File selectedDirectory = new File(path);
		String[] list = selectedDirectory.list();
		isClassesDirectory = path.endsWith("classes");
		sutFolderExists = searchFor(list, "sut");
		scenariosFolderExists = searchFor(list, "scenarios");

		return sutFolderExists && scenariosFolderExists && isClassesDirectory;
	}

	private static boolean searchFor(String[] list, String item) {
		boolean result = false;
		if (list == null) {
			return false;
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(item)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static String readFromPosition(final File file, final long fromPosition) {
		RandomAccessFile rfile = null;
		StringBuilder sb = null;
		try {
			rfile = new RandomAccessFile(file, "r");
			rfile.seek(fromPosition);
			sb = new StringBuilder();
			String line = null;
			while ((line = rfile.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
		}

		finally {
			try {
				rfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * Use this method if you shutdown jsystem but not the jvm. In that case the
	 * the .runner.lock file will prevent you from starting jsystem again there
	 * for you must release the lock on that file.
	 * 
	 * @throws Exception
	 */
	public static void releaseRunnerLock() throws Exception {
		FileLock lock = FileLock.getFileLock(CommonResources.LOCK_FILE);
		lock.releaseLock();
	}

	public static boolean killRunnerProcess() throws Exception {

		String pid = ManagementFactory.getRuntimeMXBean().getName();
		pid = pid.split("@")[0];
		// jemmySupport.report("pid is " + pid);
		if ("linux".equalsIgnoreCase(System.getProperty("os.name"))) {
			String s = null;
			Process p = Runtime.getRuntime().exec("kill -9 " + pid);
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			if ((s = stdError.readLine()) != null) {
				// jemmySupport.report("an error occured trying to execute kill process.");
				// jemmySupport.report("the error details are: " + s);
				// jemmySupport.report("error were printed to error stream.");
				return false;
			}
			// jemmySupport.report("on linux, returning successfully after kill the process");
			return true;
		} else if ("windows".equalsIgnoreCase(System.getProperty("os.name"))) {
			String s = null;
			Process p = Runtime.getRuntime().exec("taskkill /PID " + pid);
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			if ((s = stdError.readLine()) != null) {
				// jemmySupport.report("an error occured trying to execute kill process.");
				// jemmySupport.report("the error details are: " + s);
				// jemmySupport.report("error were printed to error stream.");
				return false;
			}
			// jemmySupport.report("on linux, returning successfully after kill the process");
			return true;
		} else {
			throw new Exception("system is not supported yes.\nonly Windows and Linux are supported");
		}
	}

	/**
	 * using ScenarionManager class and counts the enables tests
	 * 
	 * @return number of mapped tests
	 */
	public static int getMappedTestCount() {
		return ScenariosManager.getInstance().getCurrentScenario().getEnabledTestsIndexes().length;
	}

	/**
	 * using ScenarionManager class to get indexes array of tests that are
	 * mapped
	 * 
	 * @return array representing the tests that are mapped
	 */
	public static int[] getMappedTestIndexes() {
		return ScenariosManager.getInstance().getCurrentScenario().getEnabledTestsIndexes();
	}

	/**
	 * get path for report dir
	 */
	public Properties getSummaryProperties() throws Exception {
		String dir = System.getProperty("user.dir") + File.separator + "summary.properties";
		return FileUtils.loadPropertiesFromFile(dir);
	}

	public static String getReportDir() throws Exception {
		return System.getProperty("user.dir") + File.separator + JSystemProperties.getInstance().getPreference(FrameworkOptions.LOG_FOLDER)
				+ File.separator + "current" + File.separator + "reports.0.xml";
	}

	public static boolean checkXmlTestAttribute(int testIndex, String attribute, String value) throws Exception {
		File reportXml = new File(getReportDir());
		if (!reportXml.exists()) {
			System.out.println("checkXmlTestAttribute file, reporter xml file: " + reportXml.getAbsolutePath() + ", wasn't found");
			throw new FileNotFoundException(reportXml.getAbsolutePath());
		}
		Document doc = FileUtils.readDocumentFromFile(reportXml);
		return isXPathAttribContainText(doc, "/reports/test[" + testIndex + "]", attribute, value);
	}

	public static boolean checkNumberOfTestsPass(int numberOfTests) throws Exception {
		File reportXml = new File(getReportDir());
		if (!reportXml.exists()) {
			System.out.println("checkTestPath file, reporter xml file: " + reportXml.getAbsolutePath() + ", wasn't found");

			throw new FileNotFoundException(reportXml.getAbsolutePath());
		}
		Document doc = FileUtils.readDocumentFromFile(reportXml);
		return isXPathNumberOfElementsEquals(doc, "/reports/test[@status=\"true\"]", numberOfTests);
	}

	private static boolean isXPathAttribContainText(Document doc, String xpath, String attrib, String value) {

		String message = "XPathAttribContainText: xpath: " + xpath + ",attrib: " + attrib + ", value: " + value;

		try {
			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			// pretty format the XML output
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			// get the XML in a String
			xformer.transform(source, result);

		} catch (Exception e) {
			return false;
		}

		try {
			NodeList nodeList = XPathAPI.selectNodeList(doc, xpath);
			int elementsFound = nodeList.getLength();
			if (elementsFound > 0) {
				String text = ((Element) nodeList.item(0)).getAttribute(attrib);
				if (text.indexOf(value) >= 0) {
					return true;
				} else {

					System.out.println("message: " + message + " actual: " + text);
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isXPathNumberOfElementsEquals(Document doc, String xpath, int expectedNumber) {

		try {
			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			// pretty format the XML output
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			// get the XML in a String
			xformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		try {
			NodeList nodeList = XPathAPI.selectNodeList(doc, xpath);
			int elementsFound = nodeList.getLength();
			if (elementsFound == expectedNumber) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

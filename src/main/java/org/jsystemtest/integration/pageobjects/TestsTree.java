package org.jsystemtest.integration.pageobjects;

import java.util.Random;

import javax.swing.tree.TreePath;

import jsystem.utils.StringUtils;

import org.jsystemtest.integration.TestType;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JSplitPaneOperator;
import org.netbeans.jemmy.operators.JTreeOperator;


public class TestsTree extends AbstractPageObject {

	private JTreeOperator testsTreeOperator;

	public TestsTree(JSplitPaneOperator testsTabOperator) {
		testsTreeOperator = new JTreeOperator(testsTabOperator);
	}

	public int selectTestByRow(int row) {
		int treeRowCount = testsTreeOperator.getRowCount();
		if (row > treeRowCount || row < 0) {
			return -1;
		}
		TreePath path = testsTreeOperator.getPathForRow(row);
		testsTreeOperator.selectPath(path);
		return 0;
	}

	public int selectInTree(String node) throws Exception {
		testsTreeOperator.expandRow(0);
		int c = testsTreeOperator.getRowCount();
		TreePath foundPath = null;

		for (int i = 0; i < c; i++) {
			TreePath path = testsTreeOperator.getPathForRow(i);
			System.out.println("Path: " + path);
			Object[] pathElements = path.getPath();
			if (pathElements == null || pathElements.length == 0) {
				continue;
			}
			if ((pathElements[pathElements.length - 1].toString()).startsWith(node)) {
				foundPath = path;
				break;
			}
		}
		if (foundPath == null) {
			throw new Exception("Path not found node: " + node);
		}
		new EventTool().waitNoEvent(500);
		if (foundPath != null) {
			testsTreeOperator.expandPath(foundPath);
			testsTreeOperator.selectPath(foundPath);
		}
		return 0;
	}
	
	

	
	public void selectPathByNodeAndParentNode(String node, String parentNode) throws Exception {
		TreePath treePath = getTreePath(node, parentNode);
		testsTreeOperator.selectPath(treePath);
	}

	
	private TreePath getTreePath(String node, String parentNode) throws Exception{
		//if parent node or method name are empty string, throw exception
		if ( StringUtils.isEmpty(node) || StringUtils.isEmpty(parentNode)) {
			throw new Exception("empty test Parent node or method is not aloud: Give both parent and method name!!!");
		}
		if (TestType.SCENARIO.getType().equals(parentNode) || TestType.SCRIPT.getType().equals(parentNode) || TestType.RANDOM.getType().equals(parentNode)){
			parentNode = "";
		}

		int c = testsTreeOperator.getRowCount();
		System.out.println("Row count: " + c);
		TreePath foundPath = null;
		//method is not null and is equal to Random
		if (node != null && node.equals("Random")) { // Then we want to select a test Randomly
			
			Random generator = new Random();
			TreePath path = testsTreeOperator.getPathForRow(generator.nextInt(c));//get path to a random row in row count
			Object[] pathElements = path.getPath();
			String CurrentPath = pathElements[pathElements.length - 1].toString();

			while (CurrentPath.indexOf("test") == -1) { // If we didn't find a test
				path = testsTreeOperator.getPathForRow(generator.nextInt(c));
				pathElements = path.getPath();
				CurrentPath = pathElements[pathElements.length - 1].toString();
				System.out.println("CurrentPath " + CurrentPath);

			}
			System.out.println("selected random Node");
			foundPath = path;
		}
		
		//if a parentNode and a node had been passed, run the node under that 
		//specific Parent.
		else if(!node.equals("") && !parentNode.trim().equals("")){
			try{
				for (int i = 0; i < c; i++) {
					TreePath path = testsTreeOperator.getPathForRow(i);
					System.out.println("Path: " + path);
					Object[] pathElements = path.getPath();
					
					//if path to test has less then two elements it surely
					//doesn't have test and testClass in it to run.
					if (pathElements == null || pathElements.length <2) {
						continue;
					}
					
					Object node1 = pathElements[pathElements.length - 1];
					Object node2 = pathElements[pathElements.length - 2];
					if (node1 == null || node2 == null){
						continue;
					}
					
					//check that node and parent node exist.
					if (node1.toString().startsWith(node) && parentNode.equals(node2.toString()) && node1 != null && node2 != null) {
						foundPath = path;
						break;
					}
				}
			}catch (Exception e) {
				throw new Exception("My Exception \n\n"+StringUtils.getStackTrace(e));
			}			System.out.println("searched for test With parent ->"+parentNode+" , is empty= "+parentNode.trim().isEmpty());
		}else if (!node.equals("") && parentNode.trim().equals("")) {
			System.out.println("search for test Without parent");
			for (int i = 0; i < c; i++) {
				TreePath path = testsTreeOperator.getPathForRow(i);
				System.out.println("Path: " + path);
				Object[] pathElements = path.getPath();
				if (pathElements == null || pathElements.length == 0) {
					continue;
				}

				if (pathElements[pathElements.length - 1].toString().startsWith(node)) {
					foundPath = path;
					break;
				}
			}
			System.out.println("searched for test Without parent");
		}

		if (foundPath == null) {
			throw new Exception("Path not found node: " + node + ", parrent: " + parentNode);
		}
		return foundPath;
	}
}

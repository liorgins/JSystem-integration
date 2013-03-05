package org.jsystemtest.integration.pageobjects;

import java.util.Random;

import javax.swing.JTextField;
import javax.swing.tree.TreePath;

import jsystem.treeui.teststable.ScenarioTreeNode;
import jsystem.utils.StringUtils;

import org.jsystemtest.integration.TestType;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTreeOperator;

public class ScenarioTree extends AbstractPageObject {

	JTreeOperator scenarioTreeOperator;

	public ScenarioTree(JFrameOperator app) {
		this.scenarioTreeOperator = new JTreeOperator(app);
	}

	public int selectTestByRow(int row) {
		int treeRowCount = scenarioTreeOperator.getRowCount();
		if (row > treeRowCount || row < 0) {
			return -1;
		}
		TreePath path = scenarioTreeOperator.getPathForRow(row);
		scenarioTreeOperator.selectPath(path);
		return 0;
	}

	public int getRowCount() {
		return scenarioTreeOperator.getRowCount();
	}

	public void markAsKnownIssue(int testIndex, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getScenarioMarkAsKnownIssueMenuItem() : jmap.getScenarioUnMarkAsKnownIssueMenuItem();
		pushMenuItemForTest(testIndex, menuItem);
	}
	
	public void markAsEditLocalOnly(int testIndex) throws Exception {
		pushMenuItemForTest(testIndex, jmap.getScenarioEditOnlyLocallyItem());
	}

	public void updateMeaningfulName(int testIndex, String name) throws Exception {
		pushMenuItemForTest(testIndex, jmap.getUpdateMeaningfulNameMenuItem());
		JDialogOperator input = new JDialogOperator("Input");
		new JTextFieldOperator(input, 0).setText(name);
		new JButtonOperator(input, "OK").clickMouse();
		Thread.sleep(3000);
	}
	
	/**
	 * will open the selected sub scenario in it's own root
	 * 
	 * @throws Exception
	 */
	public void navigateToSubScenario(int scenarioIndex) throws Exception {
		pushMenuItemForTest(scenarioIndex, jmap.getNavigateToSubScenario());
	}

	public void markScenarioAsTest(int index, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getScenarioMarkAsTestMenuItem() : jmap.getScenarioUnMarkAsTestMenuItem();
		Thread.sleep(2000);
		pushMenuItemForTest(index, menuItem);
	}

	public void markAsNegative(int testIndex, boolean mark) throws Exception {
		String menuItem = mark ? jmap.getMarkAsNegativeTestMenuItem() : jmap.getUnMarkAsNegativeTestMenuItem();
		Thread.sleep(2000);
		pushMenuItemForTest(testIndex, menuItem);
	}
	
	
	/**
	 * @param row test row index 
	 * @param map true map, false unmap
	 * @param isScenario true for scenario, false for test
	 * @throws Exception
	 */
	public void mapTest(int row, boolean map, boolean isScenario) throws Exception {
		String menuItemText = "";
		
		if(isScenario) {
			menuItemText = map ? jmap.getTestMapAllMenuItem() : jmap.getTestUnmapAllMenuItem();
		}else {
			menuItemText = map ? jmap.getTestMapMenuItem() : jmap.getTestUnmapMenuItem();
		}
		pushMenuItemForTest(row, menuItemText);
	}

	private void pushMenuItemForTest(int testIndex, final String menuItem) throws Exception {
		JPopupMenuOperator pp = rightClickPopUpManu(testIndex);
		pp.pushMenuNoBlock(menuItem);
		Thread.sleep(750);
	}

	private JPopupMenuOperator rightClickPopUpManu(int testIndex) throws Exception {
		TreePath foundPath = scenarioTreeOperator.getPathForRow(testIndex);
		if (foundPath == null) {
			throw new Exception("Path not found test index: " + testIndex);
		}

		Thread.sleep(500);
		JPopupMenuOperator pp = new JPopupMenuOperator(scenarioTreeOperator.callPopupOnPath(foundPath));
		Thread.sleep(500);
		return pp;
	}

	public int getScenarioDirectChildrenCount(int row) throws Exception {
		TreePath path = scenarioTreeOperator.getPathForRow(row);
		return scenarioTreeOperator.getChildPaths(path).length;
	}

	
	public void selectPathByNodeAndParentNode(String node, String parentNode) throws Exception {
		TreePath treePath = getTreePath(node, parentNode);
		scenarioTreeOperator.selectPath(treePath);
	}

	
	private TreePath getTreePath(String node, String parentNode) throws Exception{
		//if parent node or method name are empty string, throw exception
		if ( StringUtils.isEmpty(node) || StringUtils.isEmpty(parentNode)) {
			throw new Exception("empty test Parent node or method is not aloud: Give both parent and method name!!!");
		}
		if (TestType.SCENARIO.getType().equals(parentNode) || TestType.SCRIPT.getType().equals(parentNode) || TestType.RANDOM.getType().equals(parentNode)){
			parentNode = "";
		}

		int c = scenarioTreeOperator.getRowCount();
		System.out.println("Row count: " + c);
		TreePath foundPath = null;
		//method is not null and is equal to Random
		if (node != null && node.equals("Random")) { // Then we want to select a test Randomly
			
			Random generator = new Random();
			TreePath path = scenarioTreeOperator.getPathForRow(generator.nextInt(c));//get path to a random row in row count
			Object[] pathElements = path.getPath();
			String CurrentPath = pathElements[pathElements.length - 1].toString();

			while (CurrentPath.indexOf("test") == -1) { // If we didn't find a test
				path = scenarioTreeOperator.getPathForRow(generator.nextInt(c));
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
					TreePath path = scenarioTreeOperator.getPathForRow(i);
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
				TreePath path = scenarioTreeOperator.getPathForRow(i);
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
	
	/**
	 * This method gets an array of indexes and go deep inside the scenario tree
	 * according to the indexes and return the selected ScenarioTreeNode
	 * 
	 * @param indexes
	 * @return
	 */
	public ScenarioTreeNode selectTestByIndexesPath(int... indexes) throws Exception {
		ScenarioTreeNode currentScenarioTreeNode = (ScenarioTreeNode) scenarioTreeOperator.getRoot();
		TreePath PathBuild = new TreePath(currentScenarioTreeNode);
		for (int i = 0; i < indexes.length; i++) {

			currentScenarioTreeNode = (ScenarioTreeNode) currentScenarioTreeNode.getChildAt(indexes[i]);
			PathBuild = PathBuild.pathByAddingChild(currentScenarioTreeNode);
		}
		scenarioTreeOperator.clickOnPath(PathBuild);

		return currentScenarioTreeNode;
	}
	
}


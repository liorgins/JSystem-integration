package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;

public class TestInfoTab extends AbstractPageObject {

	private JTabbedPaneOperator infoTabbedPaneOperator;

	public TestInfoTab(JTabbedPaneOperator jTabbedPaneOperator) {
		jTabbedPaneOperator.selectPage(2);
		infoTabbedPaneOperator = jTabbedPaneOperator;
	}

	public void setTestParameter(String tab, String paramName, String value, boolean isCombo) throws Exception {
		setTestParameter(tab, paramName, value, isCombo, false);
	}

	public void setTestParameter(String tab, String paramName, String value, boolean isCombo, boolean isScenario)
			throws Exception {
		setTestParameter(tab, paramName, value, isCombo, isScenario, true);
	}

	public void setTestParameter(String tab, String paramName, String value, boolean isCombo, boolean isScenario,
			boolean approve) throws Exception {

		infoTabbedPaneOperator.selectPage(0);
		JTableOperator paramTable = openParamTabAndGetParamTable(tab, infoTabbedPaneOperator);

		int numOfParamRows = paramTable.getRowCount();
		for (int i = 0; i < numOfParamRows; i++) {
			Object tableValue = paramTable.getValueAt(i, 0);
			if (tableValue != null
					&& (tableValue.toString().equalsIgnoreCase(paramName + "*") || tableValue.toString().equalsIgnoreCase(paramName))) {
				setTableCell(paramTable, i, 3, value, isCombo);
				infoTabbedPaneOperator.getSource().repaint();
				break;
			}
		}

		Thread.sleep(200);
		if (isScenario) {
			String buttonText = approve ? "Yes" : "No";
			jemmyHelper.pushButtonAndWaitAndApproveDialog("Apply for Scenario", "Apply Scenario Parameters",
					buttonText);
		}
	}

	private JTableOperator openParamTabAndGetParamTable(String tab, JTabbedPaneOperator testInfoTab) throws Exception {
		testInfoTab.selectPage(2);
		JTabbedPaneOperator paramTab = new JTabbedPaneOperator(testInfoTab, 1);
		paramTab.selectPage(tab);
		return new JTableOperator(paramTab);
	}

	private void setTableCell(JTableOperator paramTable, int row, int column, String value, boolean isCombo) throws Exception {
		int i = 0;
		String currentValue = "";

		int retryNumber = 5;
		while ((!currentValue.equals(value)) && (i < retryNumber)) {
			if (isCombo) {
				paramTable.clickOnCell(row, column);
				JComboBoxOperator combo = new JComboBoxOperator(paramTable);
				combo.setEnabled(true);
				combo.selectItem(value);
			} else {
				paramTable.clickOnCell(row, column);
				paramTable.changeCellObject(row, column, value);
			}

			Thread.sleep(1000);
			System.out.println("----- Setting Table cell,Row=" + row + ",column=" + column + ",value=" + value + "-attempt " + i);
			currentValue = paramTable.getValueAt(row, column).toString();
			i++;
		}
		if (currentValue.equalsIgnoreCase(value)) {
			System.out.println("Table was updated successfully");
		} else {
			System.out.println("Fail to update table");
		}
	}

	public String getTestParameter(String tab, String paramName) throws Exception {	
	
		String returnValue = "";
		
		infoTabbedPaneOperator.selectPage(0);
		JTableOperator paramTable = openParamTabAndGetParamTable(tab, infoTabbedPaneOperator);
		int numOfParamRows = paramTable.getRowCount();
		
		for (int i = 0; i < numOfParamRows; i++) {
			Object tableVal = paramTable.getValueAt(i, 0);
			if (tableVal != null
					&& (tableVal.toString().equalsIgnoreCase(paramName + "*") || tableVal.toString().equalsIgnoreCase(
							paramName))) {

				Object param = paramTable.getValueAt(i, 3);
				if (param instanceof String) {
					returnValue = param.toString();
					break;
				}
			}
		}
		return returnValue;
	}
	
	public void setTestUserDocumentation(String documentation) throws Exception {
		infoTabbedPaneOperator.selectPage(2);
		JTabbedPaneOperator docTab = new JTabbedPaneOperator(infoTabbedPaneOperator, 0);
		docTab.selectPage(1);

		JTextAreaOperator textAreaOperator = new JTextAreaOperator(docTab);
		textAreaOperator.setText(documentation);
		Thread.sleep(1000);
		new JButtonOperator(infoTabbedPaneOperator, "Apply").clickMouse();
	}

	public String getTestUserDocumentationForSelectedBlock() throws Exception {
		infoTabbedPaneOperator.selectPage(2);
		JTabbedPaneOperator docTab = new JTabbedPaneOperator(infoTabbedPaneOperator, 0);
		docTab.selectPage(1);

		JTextAreaOperator textAreaOperator = new JTextAreaOperator(docTab);
		return textAreaOperator.getText();
	}

}

package org.jsystemtest.integration.pageobjects;

import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class EditMenu extends AbstractPageObject {

	private JMenuOperator jMenuOperator;

	private static final String EDIT = "Edit";

	public EditMenu(JMenuBarOperator jMenuBarOperator) {
		jMenuOperator = new JMenuOperator(jMenuBarOperator, EDIT);
	}

	public void ScenarioUndo() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Scenario Undo");
	}

	public void ScenarioRedo() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Scenario Redo");
	}

	public void clear() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Clear");
	}

	public void Edit() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Edit");
	}

	public void Copy() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Copy");
	}

	public void Cut() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Cut");
	}

	public void Paste() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Paste");
	}

	public void PasteAfter() {
		jMenuOperator.pushMenuNoBlock(EDIT + "|Paste After");
	}
}

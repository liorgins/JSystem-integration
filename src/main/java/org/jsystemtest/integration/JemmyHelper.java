package org.jsystemtest.integration;

import java.awt.Component;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;


public class JemmyHelper {
	
	static JemmyHelper jemmyHelper;
	
	private static JFrameOperator app;
	
	public static JemmyHelper getInstance(){
		if (jemmyHelper == null){
			 jemmyHelper = new JemmyHelper();
			
			 
		}
		return jemmyHelper;
	}

	public void pushButtonAndWaitAndApproveDialog(String buttonName,String windowName,String dialogButton)throws Exception{
		JFrameOperator mainFrame = new JFrameOperator("JSystem");    
		new JButtonOperator(mainFrame , new TooltipChooser(buttonName)).push();
		Thread.sleep(500);
		JDialogOperator dialog = new JDialogOperator(windowName);
		dialog.setModal(true);
		if (!dialog.isActive()){
			System.out.println("The Dialog "+windowName+" was not opened successfully.  is visible="+dialog.isVisible());
			Thread.sleep(1000);
			System.out.println("The Dialog "+windowName+" after 1 second sleep active= " + dialog.isActive() + " is visible="+dialog.isVisible());
		}else {
			System.out.println("The Dialog "+windowName+" was opened successfully");
		}
		if (dialogButton != null) {
			new JButtonOperator(dialog , new TooltipChooser(dialogButton)).push();
		}

	}
	
	
	public  boolean getToolbarViewState (String toolbarName) throws Exception {
		Boolean state = true;
		JFrameOperator app = new JFrameOperator("JSystem");
		JMenuItemOperator menuItem = chooseMenuItem(app, false, "View", "Toolbars", toolbarName);
		Component checkBox = menuItem.getSource();
		if (checkBox instanceof JCheckBoxMenuItem) {
			state = ((JCheckBoxMenuItem)checkBox).isSelected();
		} else {
			throw new Exception(toolbarName + "Toolbar menu item is not a checkbox");
		}
		
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.keyPress(KeyEvent.VK_ESCAPE);
		
		return state;
	}
	
	/* Method name: chooseMenuItem
	 * Description:  This method is designed to choose an item from the menu, regardless of its 
	 *               depth. Wait 1000 milliseconds between each level
	 * Parameters:
	 * 				@param mainFrame - The main frame
	 * 				@param menuNames - Array of menu titles.
	 * 				@throws Exception
	 * 
	 * Return Values: JMenuItemOperator
	 */
	
	private JMenuItemOperator chooseMenuItem (JFrameOperator mainFrame, boolean pushLastLevel, Object...menuNames) throws Exception {
		int waitBetweenItems = 1000;
		JMenuItemOperator selectedItem = null;
		
		for (int index = 0; index < menuNames.length; index++) {
			
			String name = (String)menuNames[index];
			selectedItem = new JMenuItemOperator(mainFrame, name);
			// Push every level, and the last level only if asked
			if ((index != menuNames.length-1) || pushLastLevel) {
				// Pushing "no block" since in some cases we just want to see the
				// sub menu and not make an action
				selectedItem.pushNoBlock();
			}
			Thread.sleep(waitBetweenItems);
		}
		return selectedItem;
	}
	
}

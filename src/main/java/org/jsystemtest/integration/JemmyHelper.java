package org.jsystemtest.integration;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;


public class JemmyHelper {
	
	static JemmyHelper jemmyHelper;
	
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
	
}

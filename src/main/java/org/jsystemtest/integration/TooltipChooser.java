package org.jsystemtest.integration;

import java.awt.Component;

import javax.swing.JComponent;

import org.netbeans.jemmy.ComponentChooser;

public class TooltipChooser implements ComponentChooser {

	private String tooltipText;

	public TooltipChooser(String tooltipText) {
		this.tooltipText = tooltipText;
	}

	@Override
	public boolean checkComponent(Component component) {
		if (null == tooltipText) {
			return false;
		}
		if (!(component instanceof JComponent)) {
			return false;
		}
		final String compToolTip = ((JComponent) component).getToolTipText();
		if (null == compToolTip) {
			return false;
		}
		if (compToolTip.equals(tooltipText)) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
	
		return "Choose Component with tooltipText '" + tooltipText + "'"; 
	}

}

package org.jsystemtest.integration.pageobjects;

import org.jsystemtest.integration.JemmyHelper;

import jsystem.guiMapping.JsystemMapping;


public abstract class AbstractPageObject {
	
	static protected JsystemMapping jmap = JsystemMapping.getInstance();
	
	static protected JemmyHelper jemmyHelper = JemmyHelper.getInstance();
	
}

package org.jsystemtest.integration;

import jsystem.framework.FrameworkOptions;

public class PropertyPair {

	  private final FrameworkOptions key;
	  private final String value;

	  public PropertyPair(FrameworkOptions key, String value) {
	    this.key = key;
	    this.value = value;
	  }

	  public FrameworkOptions getkey() { return key; }
	  public String getValue() { return value; }

	}
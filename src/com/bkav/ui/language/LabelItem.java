package com.bkav.ui.language;

import com.bkav.ui.language.Xml;

public class LabelItem {

	public LabelItem(Xml xml) {
		 this.key = xml.getAttrib("key");
		 this.value = xml.getAttrib("value");
		 this.description = xml.getAttrib("description");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String key;
	private String value;
	private String description;
	// Phai co ham update.
}

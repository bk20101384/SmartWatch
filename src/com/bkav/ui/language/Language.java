package com.bkav.ui.language;

public class Language {
	public Language() {
	}

	public Language(String value, String path, String isDefault) {
		this.value = value;
		this.source = path;
		this.isDefault = isDefault;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String path) {
		this.source = path;
	}
	
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	private String value;
	private String source;
	private String isDefault;
}

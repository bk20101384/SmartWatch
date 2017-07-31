package com.bkav.home.component;

import com.bkav.home.common.Attribute;

public abstract class GenericAttribute<T> extends GenericField<T> implements Attribute {

	public GenericAttribute(Component component, String name, T value) {
		super(name, value);
		this.component = component;
	}
	
	@Override
	public void setValue(T value) {
		super.setValue(value);
		this.component.setModified();
	}
	
	private Component component;
}

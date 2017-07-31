package com.bkav.home.component;

public class PersistentIntegerProperty extends IntegerProperty {

	public PersistentIntegerProperty(Component component, String name, Integer value) {
		super(name, component.loadIntegerProperty(name, value));
		this.component = component;
	}
	
	@Override
	public void setValue(Integer value) {
		super.setValue(value);
		this.component.saveIntegerProperty(getName(), getValue());
	}
	
	private Component component;
}

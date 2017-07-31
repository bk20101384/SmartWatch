package com.bkav.home.component;

import com.bkav.home.data.Data;

public class IntegerAttribute extends GenericAttribute<Integer> {

	public IntegerAttribute(Component component, String name, Integer value) {
		super(component, name, component.loadIntegerAttribute(name, value));
	}

	@Override
	public void importData(Data data) {
		try {
			setValue(Integer.valueOf(data.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

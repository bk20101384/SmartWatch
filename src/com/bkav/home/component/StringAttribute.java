package com.bkav.home.component;

import com.bkav.home.common.Importable;
import com.bkav.home.data.Data;

public class StringAttribute extends GenericAttribute<String> implements Importable {

	public StringAttribute(Component component, String name) {
		super(component, name, component.loadStringAttribute(name));
	}

	@Override
	public void importData(Data data) {
		setValue(data.toString());
	}
}

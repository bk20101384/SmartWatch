package com.bkav.home.component;

import com.bkav.home.common.Importable;
import com.bkav.home.data.Data;

public class IntegerProperty extends GenericProperty<Integer> implements Importable {

	public IntegerProperty(String name, Integer value) {
		super(name, value);
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

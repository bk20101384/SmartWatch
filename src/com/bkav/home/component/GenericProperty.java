package com.bkav.home.component;

import com.bkav.home.common.Property;

public abstract class GenericProperty<T> extends GenericField<T> implements Property {
	public GenericProperty(String name, T value) {
		super(name, value);
	}
}

package com.bkav.home.component;

import com.bkav.home.common.Changable;
import com.bkav.home.common.Field;
import com.bkav.home.data.ValidString;

public abstract class GenericField<T> implements Field, Changable {
	public GenericField(String name, T value) {
		this.name = name;
		this.value = value;
		this.changed = true;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public void setValue(T value) {
		this.value = value;
		this.changed = true;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String exportChange() {
		if (this.changed) {
			this.changed = false;
			return ValidString.validate(this.value.toString());
		}
		else
			return null;
	}

	@Override
	public String exportData() {
		if (this.value == null)
			return null;
		else
			return ValidString.validate(this.value.toString());
	}
	
	private String name;
	private T value;
	private boolean changed;
}

package com.bkav.ui.language;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.RadioButton;

@SuppressLint("ViewConstructor")
public class RadioButtonLanguage extends RadioButton {
	public RadioButtonLanguage(Context context, Language language) {
		super(context);
		this.language = language;
		this.setText(this.language.getValue());
		this.setTextColor(Color.BLACK);
	}
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
		this.setText(this.language.getValue());
	}

	private Language language;
}

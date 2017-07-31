package com.bkav.ui.setting;

import com.bkav.utils.Variables;
import com.example.bwatchdevice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class NumberPickerDialog extends Activity {
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.number_picker_dialog);
		Bundle bundle = getIntent().getExtras();
		values = bundle.getStringArray(Variables.VALUE_PICKER); 
		position = bundle.getInt(Variables.POSITION_PICKER);
		saveButton = (Button) findViewById(R.id.save_number_picker);
		cancelButton = (Button) findViewById(R.id.cancel_number_picker);
		
		numberPicker = (NumberPicker) findViewById(R.id.number_picker);  
		numberPicker.setMaxValue(values.length - 1);
		numberPicker.setMinValue(0);
		numberPicker.setValue(position);
		numberPicker.setDisplayedValues(values);
		numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		numberPicker.setWrapSelectorWheel(true);
		
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra(Variables.MESSAGE_RESULT, 
						values[numberPicker.getValue()]);
				setResult(Variables.RESULT_OK, returnIntent);
				finish();
			}
		});
	}
	
	private NumberPicker numberPicker;
	private String[] values;
	private Button saveButton;
	private Button cancelButton;
	private int position;
}

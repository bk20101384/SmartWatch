package com.bkav.ui.about;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity{

	public static final String MODEL = "model";
	public static final String MODEL_CONTENT = "modelContent";
	public static final String VERSION = "version";
	public static final String VERSION_CONTENT = "versionContent";
    public static final String SERIAL = "serial";
    public static final String SERIAL_CONTENT = "serialContent";
    public static final String BUILD_NUMBER = "buildNumber";
    public static final String BUILD_NUMBER_CONTENT = "buildNumberContent";
    public static final String BATTERY = "battery";
    public static final String BATTERY_CONTENT = "batteryContent";
    public static final String SYSTEM_UPDATE = "systemUpdates";
    public static final String SYSTEM_UPDATE_CONTENT = "systemUpdatesContent";
    public static final String LEGAL_NOTICES = "legalNotices";
    public static final String LEGAL_NOTICES_CONTENT = "legalNoticesContent";
    public static final String BACK = "back";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		model = (TextView) findViewById(R.id.model);
	    modelContent = (TextView) findViewById(R.id.modelContent);
		version = (TextView) findViewById(R.id.version);
	    versionContent = (TextView) findViewById(R.id.versionContent);
	    serial = (TextView) findViewById(R.id.serial);
	    serialContent = (TextView) findViewById(R.id.serialContent);
	    buildNumber = (TextView) findViewById(R.id.buildNumber);
	    buildNumberContent = (TextView) findViewById(R.id.buildNumberContent);
	    battery = (TextView) findViewById(R.id.battery);
	    batteryContent = (TextView) findViewById(R.id.batteryContent);
	    systemUpdates = (TextView) findViewById(R.id.systemUpdates);
	    systemUpdatesContent = (TextView) findViewById(R.id.systemUpdatesContent);
	    legalNotices = (TextView) findViewById(R.id.legalNotices);
	    legalNoticesContent = (TextView) findViewById(R.id.legalNoticesContent);
	    back = (Button) findViewById(R.id.back);
	    
	    setTypeLanguage();
	    
		updateLanguageAbout();
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	public void setTypeLanguage() {
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(getAssets());
	    if (typeFaceRegular != null) {
	    	model.setTypeface(typeFaceRegular);
	    	modelContent.setTypeface(typeFaceRegular);
	    	version.setTypeface(typeFaceRegular);
	    	versionContent.setTypeface(typeFaceRegular);
	    	serial.setTypeface(typeFaceRegular);
	    	serialContent.setTypeface(typeFaceRegular);
	    	buildNumber.setTypeface(typeFaceRegular);
	    	buildNumberContent.setTypeface(typeFaceRegular);
	    	battery.setTypeface(typeFaceRegular);
	    	batteryContent.setTypeface(typeFaceRegular);
	    	systemUpdates.setTypeface(typeFaceRegular);
	    	systemUpdatesContent.setTypeface(typeFaceRegular);
	    	legalNotices.setTypeface(typeFaceRegular);
	    	legalNoticesContent.setTypeface(typeFaceRegular);
	    	back.setTypeface(typeFaceRegular);
	    }
	}
	public void updateLanguageAbout() {
		model.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(MODEL).getValue());
		modelContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(MODEL_CONTENT).getValue());
		version.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(VERSION).getValue());
		versionContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(VERSION_CONTENT).getValue());
		serial.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(SERIAL).getValue());
		serialContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(SERIAL_CONTENT).getValue());
		buildNumber.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(BUILD_NUMBER).getValue());
		buildNumberContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(BUILD_NUMBER_CONTENT).getValue());
		battery.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(BATTERY).getValue());
		batteryContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(BATTERY_CONTENT).getValue());
		systemUpdates.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(SYSTEM_UPDATE).getValue());
		systemUpdatesContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(SYSTEM_UPDATE_CONTENT).getValue());
		legalNotices.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(LEGAL_NOTICES).getValue());
		legalNoticesContent.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(LEGAL_NOTICES_CONTENT).getValue());
		back.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(BACK).getValue());
	}
	
	private TextView model;
    private TextView modelContent;
	private TextView version;
    private TextView versionContent;
    private TextView serial;
    private TextView serialContent;
    private TextView buildNumber;
    private TextView buildNumberContent;
    private TextView battery;
    private TextView batteryContent;
    private TextView systemUpdates;
    private TextView systemUpdatesContent;
    private TextView legalNotices;
    private TextView legalNoticesContent;
    private Button back;
}

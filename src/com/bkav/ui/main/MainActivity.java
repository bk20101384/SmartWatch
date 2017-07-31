package com.bkav.ui.main;

import java.io.File;

import com.bkav.home.lib.FileSystem;
import com.bkav.home.system.Platform;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;

public class MainActivity extends Activity {
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		startSynchronizeThread();
		
	}
	public void startSynchronizeThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				FileSystem.init(getFileDirXml());
				if (!Platform.isAlives()) {
					Platform.start(); 
				}
				startActivity(new Intent(getApplicationContext(), WatchActivity.class));
				finish();
			}
		}).start();
	}
	public String getFileDirXml() {
		File SDCardRoot = Environment.getExternalStorageDirectory();
		File dirXml = new File(SDCardRoot.getAbsolutePath() + "/dist");
		if (!dirXml.exists()) {
			if (!dirXml.mkdirs()) {
				return "";
			}
		}
		return dirXml.getAbsolutePath();
	}
}

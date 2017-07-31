package com.bkav.ui.pedometer;

import com.bkav.ui.call.CallDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.utils.SystemManager;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

public class IdleWarningThread extends Thread {
	public int timeInactiveValue;
	
	public IdleWarningThread(Context context) { 
		timeInactiveValue = 0;
	}
	
	@Override
	public void run() { 
		super.run(); 
		try {
			Log.e("Idle warning motion", "start thread idle warning motion");
			while (true) {
				
				beforeStep = WatchActivity.sysManager.getPreferences().getInt("step", 0);
				Thread.sleep(60000);
				currentStep = WatchActivity.sysManager.getPreferences().getInt("step", 0);
				Log.e("IdleWarningThread", "currentStep:" + currentStep);
				if (currentStep - beforeStep >= SystemManager.STEP_ACTIVE_VALUE) {
					Log.e("IdleWarningThread", "Reset hoat dong");
					timeInactiveValue = 0;
				} else {
					timeInactiveValue ++;
					Log.e("IdleWarningThread", "timeInactiveValue:" + timeInactiveValue);
				}
				 
				if (timeInactiveValue == WatchActivity.sysManager.getPreferences().getInt("motion", 1)) {
					Log.e("IdleWarningThread", "Ban can hoat dong");
					Message msg = new Message();
					msg.what = WatchActivity.MOTION_WARNING_ACTIVE;
					WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(msg);
				} else {
					Log.e("motion get from preference:", "" + WatchActivity.sysManager.getPreferences().getInt("motion", 1));
				}
				
				beforeStep = currentStep;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int beforeStep = 0;
	private int currentStep;
}

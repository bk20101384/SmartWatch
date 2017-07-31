package com.bkav.home.system;

import android.util.Log;

import com.bkav.home.common.Context;
import com.bkav.home.net.BluetoothManager;
import com.bkav.home.net.Connection;
import com.bkav.watch.Watch;

public class Platform {
	public static final Context context;
	public static final SystemThread thread;
	public static final Watch watch;
	public static final Connection connection;
	public static final BluetoothManager bluetoothManager;
	
	static {
		context = new SystemContext();		
		watch = new Watch();
		thread = new SystemThread((SystemContext)context, watch);
		connection = new Connection(watch);
		watch.add(connection);
//		bluetoothManager = new BluetoothManager(12555, connection);		
		bluetoothManager = new BluetoothManager(connection);	
	}
	
	public static void start() {
		Log.e("", "platform startttttttttt");	
		thread.start();
		Log.e("", "blue starttttttttttt"); 
		bluetoothManager.start();
	}
	
	public static boolean isAlives() {
		return thread.isAlive() || bluetoothManager.isAlive();
	}
}

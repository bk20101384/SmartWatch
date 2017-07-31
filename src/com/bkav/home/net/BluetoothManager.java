package com.bkav.home.net;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.bkav.home.system.Platform;

public class BluetoothManager extends Thread {
	public BluetoothManager(SessionProcessor processor) {
		this.processor = processor;
		this.serverSocket = null;
		this.adapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				while (this.serverSocket == null) {
					try {
						Log.e("-------------", "Wait to accept");
						serverSocket = adapter.listenUsingRfcommWithServiceRecord("BLTServer", uuid);
						BluetoothSocket socket = this.serverSocket.accept();
						Log.e("-------------", "CONNECTED");
						BluetoothSession session = new BluetoothSession(socket, this.processor);
						Platform.thread.notify(session);
					} catch (IOException e) {
						Thread.sleep(1000);
						continue;
					}
				}
				
				if (!((Connection)this.processor).isAlive()) {
					try {
						Log.e("-------------", "Close");
						this.serverSocket.close();
					} catch (Exception e) {
						
					} finally {
						this.serverSocket = null;
					}
				}
				
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			Log.e("Interrupt", "aaaaa");
		} 
	}
	
//	private int port;
	private SessionProcessor processor;
//	private ServerSocket serverSocket;
	private BluetoothServerSocket serverSocket;
	private UUID uuid = UUID.fromString("4e5d48e0-75df-11e3-981f-0800200c9a66");
	private BluetoothAdapter adapter = null;
}

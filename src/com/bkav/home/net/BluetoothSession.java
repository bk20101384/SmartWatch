package com.bkav.home.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;

import com.bkav.home.common.Process;

public class BluetoothSession implements Session, Process {
	public BluetoothSession(BluetoothSocket socket, SessionProcessor processor) {
		this.socket = socket;
		this.processor = processor;		
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (this.inputStream == null) {
			this.inputStream = this.socket.getInputStream();
		}
		return this.inputStream;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		if (this.outputStream == null) {
			this.outputStream = this.socket.getOutputStream();
		}
		return this.outputStream;
	}

	@Override
	public void close() {
		try {
			this.inputStream.close();
		} catch (Exception e) {
			
		}

		try {
			this.outputStream.close();
		} catch (Exception e) {
			
		}		
	}
	
	@Override
	public void process() {
		this.processor.process(this);
	}
	
	private BluetoothSocket socket;
	private SessionProcessor processor;
	private InputStream inputStream;
	private OutputStream outputStream;
}

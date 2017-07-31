package com.bkav.home.net;

import android.util.Log;

import com.bkav.home.common.Importable;
import com.bkav.home.common.Process;
import com.bkav.home.data.Data;
import com.bkav.home.system.Platform;

public class Connection implements Process, SessionProcessor {
	
	public Connection(Importable importee) {
		this.importee = importee;
	}
	
	public void write(String string) {
		if (this.exportThread != null) {
			this.exportThread.write(string);
//			Log.e("Connection", "Send:" + string);
		}
	}
	
	@Override
	public void process(Session session) { 
		Log.e("-------------", "init input output stream");
		this.importThread = new ImportThread(session);
		this.exportThread = new ExportThread(session);
		Platform.context.reschedule();
		
		// Test
		Platform.watch.build();
	}
	
	@Override
	public void process() {
		if (this.importThread == null)
			return;
		
		Data data = this.importThread.read();
		while (data != null) {
			this.importee.importData(data);
			data = this.importThread.read();
		}
	}
	
	public boolean isAlive() {
		if (this.importThread == null || this.exportThread == null) {
			return true;
		}
		if (!this.importThread.isAlive() || !this.exportThread.isAlive()) {
			this.importThread.close();
			this.exportThread.close();
			this.importThread = null;
			this.exportThread = null;
			return false;
		}
		return true;
	}
	
	private	ExportThread exportThread;
	private ImportThread importThread;
	private Importable importee;
}

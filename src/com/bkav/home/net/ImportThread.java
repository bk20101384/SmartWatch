package com.bkav.home.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

import com.bkav.home.common.Process;
import com.bkav.home.data.Data;
import com.bkav.home.data.DataParser;
import com.bkav.home.system.Platform;

public class ImportThread extends Thread implements Process {
	public ImportThread(Session session) {
		this.session = session;
		this.queue = new LinkedBlockingQueue<Data>();
		setDaemon(true);
		start();
	}
	
	public Data read() {
		return this.queue.poll();
	}
	
	public void close() {
		this.session.close();
	}
	
	@Override
	public void run() {
		try {
			Reader reader = new InputStreamReader(this.session.getInputStream());
			while (true) {
				Data data = DataParser.tryParse(reader);
				this.queue.add(data);
				Log.e("", "import anh chuoi");
				Platform.thread.notify(this);
			}
		} 
		catch (IOException e) {
			System.out.println("IO error: " + e.getMessage());
		}
		catch (ParseException e) {
			System.out.println("Parse error");			
		}
		finally {
			System.out.println("session close");	
			this.session.close();
			this.interrupt();
		}
	}
	
	@Override
	public void process() {
		if (!this.queue.isEmpty())
			Platform.context.reschedule();
	}

	private Session session;
	private LinkedBlockingQueue<Data> queue;
}

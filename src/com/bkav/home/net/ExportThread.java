package com.bkav.home.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

public class ExportThread extends Thread {
	public ExportThread(Session session) {
		this.session = session;
		this.queue = new LinkedBlockingQueue<String>();
		setDaemon(true);
		start();
	}
	
	public void write(String string) {
		this.queue.add(string);
	}

	public void close() {
		this.session.close();
	}
	
	@Override
	public void run() {
		try {
			PrintWriter writer = new PrintWriter(this.session.getOutputStream());
			while (true) {
				String string = this.queue.take();
				writer.write(string);
				writer.flush();
				System.out.println("Writer: " + string); 
			} 
			
		} catch (InterruptedException e) {
			
		} catch (IOException e) {
			
		} finally {
			System.out.println("session close");	
			this.session.close();
			this.interrupt();
		}
	}
	
	private Session session;
	private LinkedBlockingQueue<String> queue;
}

package com.bkav.home.system;

import java.util.Date;

import com.bkav.home.common.Context;

public class SystemContext implements Context {
	public SystemContext() {
		update();
	}
	
	@Override
	public long getTime() {
		return this.time;
	}
	
	@Override
	public synchronized void reschedule(long time) {
		if (this.next < 0)
			this.next = time;
		else if (time < this.next) {
			this.next = time;				
		}
	}
	
	@Override
	public synchronized void reschedule() {
		this.next = 0;
	}

	public synchronized void update() {
		this.time = new Date().getTime();
		this.next = -1;
	}
	
	public long getNext() {
		return this.next;
	}

	private long time;
	private long next;
}

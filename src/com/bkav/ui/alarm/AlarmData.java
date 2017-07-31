package com.bkav.ui.alarm;

public class AlarmData {

	public AlarmData(String time, boolean[] repeat) {
		this.time = time;
		this.repeat = repeat;
		this.active = false;
	}
	
	public void update(String time, boolean[] repeat) {
		this.time = time;
		this.repeat = repeat;
	}
	public String getTime() { 
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean[] getRepeat() {
		return repeat;
	}

	public void setRepeat(boolean[] repeat) {
		this.repeat = repeat;
	}

	private String time;
	private boolean[] repeat;
	private boolean active;
}

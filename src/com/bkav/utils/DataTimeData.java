package com.bkav.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeData {
	public Date date;
	public SimpleDateFormat simpleDateFormat;
	public DataTimeData(Date date) { 
		this.date = date;
	}
	public String getTime(){
		return new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy ").format(date);
	}
	public int getYear(){
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
	}
	public int getMonth(){
		return Integer.parseInt(new SimpleDateFormat("MM").format(date));
	}
	public int getDay(){
		return Integer.parseInt(new SimpleDateFormat("dd").format(date));
	}
	public int getHour(){
		return Integer.parseInt(new SimpleDateFormat("HH").format(date));
	}
	public int getMinute(){
		return Integer.parseInt(new SimpleDateFormat("mm").format(date));
	}
	public int getSecond(){
		return Integer.parseInt(new SimpleDateFormat("ss").format(date));
	}

}

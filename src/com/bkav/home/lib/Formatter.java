package com.bkav.home.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
	public static Date stringToDate(String text) throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.parse(text);
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.format(date);
	}
	
	public static long serialToLong(String serial) {
		long result = 0;
		int index = 0;
		int digits = 0;
		char[] arr = serial.toCharArray();
		while (digits < 16 && index < arr.length) {
			int digit = 0;
			if (arr[index] >= '0' && arr[index] <= '9')
				digit = arr[index] - '0';
			else if (arr[index] >= 'A' && arr[index] <= 'F')
				digit = arr[index] - 'A' + 0xA;
			else if (arr[index] >= 'a' && arr[index] <= 'f')
				digit = arr[index] - 'a' + 0xA;
			else if (arr[index] == 0)
				return 0;
			else {
				index++;
				continue;
			}
			result <<= 4;
			result |= (digit & 0x0F);
			index++;
			digits++;
		}
		if (digits == 16)
			return result;
		else
			return 0;
	}
	
	public static String longToSerial(long serial) {
		StringBuilder builder = new StringBuilder();
		int count = 8;
		while (count > 0) {
			int item = (int)(serial & 0xFF);
			char high = hex[item >> 4];
			char low = hex[item & 0x0F];
			if (count < 8)
				builder.insert(0, ' ');
			builder.insert(0, low);
			builder.insert(0, high);
			count--;
			serial = serial >> 8;
		}		
		return builder.toString();		
	}
	
	public static String byteArrayToString(byte[] data) {
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < data.length; index++) {
			int item = data[index] & 0xFF;
			builder.append(hex[item >> 4]);
			builder.append(hex[item & 0x0F]);
		}
		return builder.toString();
	}
	
	public static byte[] stringToByteArray(String text) {
		if (text == null)
			return null;
		if ((text.length() & 1) != 0)
			return null;
		int length = text.length() / 2;
		char[] arr = text.toCharArray();
		byte[] data = new byte[length];
		int digits = 0;
		int item = 0;
		for (int index = 0; index < arr.length; index++) {
			int digit = 0;			
			if (arr[index] >= '0' && arr[index] <= '9')
				digit = arr[index] - '0';
			else if (arr[index] >= 'A' && arr[index] <= 'F')
				digit = arr[index] - 'A' + 0xA;
			else if (arr[index] >= 'a' && arr[index] <= 'f')
				digit = arr[index] - 'a' + 0xA;
			else 
				return null;
			item = item << 4;
			item = item | digit;
			digits++;
			if (digits == 2) {
				data[index / 2] = (byte)item;
				item = 0;
				digits = 0;
			}			
		}
		return data;	
	}
	
	public static int stringToInteger(String text, int defaultValue) {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	private static final char[] hex = "0123456789ABCDEF".toCharArray(); 
}

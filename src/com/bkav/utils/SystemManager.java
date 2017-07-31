package com.bkav.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import com.bkav.home.system.Platform;
import com.bkav.ui.alarm.AlarmData;
import com.bkav.ui.call.CallAdapter;
import com.bkav.ui.call.MissCallIcon;
import com.bkav.ui.email.EmailIcon;
import com.bkav.ui.email.IncomEmailIcon;
import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.sms.IncomSmsIcon;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.Call;
import com.bkav.watch.calls.CallsManager;
import com.bkav.watch.email.Email;
import com.bkav.watch.email.EmailManager;
import com.bkav.watch.event.EventItem;
import com.bkav.watch.event.EventManager;
import com.bkav.watch.sms.Sms;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SystemManager {
	public static final int STEP_ACTIVE_VALUE = 20;

	public SystemManager() {
		this.stepCountValue = 0;
		this.distanceValue = 0;
		this.caloriesValue = 0;
		this.currentStylesScreen = 0;
		this.screenTimeout = 15000;
		this.isActiveCallActivity = false;
		this.contentNotify = "";
		this.numberCall = 10;
		this.numberSms = 10;
		this.numberEmail = 10;
		this.setIdNotify("");
		this.setBitmapNotify("");
		setmThumbIds("0%1%2%3%4%5");
	}

	public void setArrayCall() {
		CallsManager callManager = (CallsManager) Platform.watch
				.getElement("calls");
		Iterator<Object> listCall = callManager.iterator();
		this.arrayCall.clear();

		ArrayList<Call> arrayListCall = new ArrayList<Call>();
		while (listCall.hasNext()) {
			Object object = listCall.next();
			if (object instanceof Call) {
				Call call = (Call) object;
				if (call.getIsIncomming().getValue().equals("false")) {
					arrayListCall.add(call);
				}
			}
		}
		ArrayList<Call> curArray = bubleShortCall(arrayListCall);
		for (int index = 0; index < curArray.size(); index++) {
			if (index == WatchActivity.sysManager.getNumberCall()) {
				break;
			}
			this.arrayCall.add(curArray.get(index));
		}
	}

	public void setArraySms() {
		Log.e("System Manager", "set arrrSms");
		SmsManager smsManager = (SmsManager) Platform.watch.getElement("sms");

		Iterator<Object> listSms = smsManager.iterator();

		this.arraySms.clear();

		ArrayList<Sms> arrayListSms = new ArrayList<Sms>();
		while (listSms.hasNext()) {
			Object object = listSms.next();
			if (object instanceof Sms) {
				Sms sms = (Sms) object;
				if (sms.getIsIncoming().getValue().equals("false")) {
					arrayListSms.add(sms);
				}
			}
		}

		ArrayList<Sms> curArray = bubleShortSms(arrayListSms);

		for (int index = 0; index < curArray.size(); index++) {
			if (index == WatchActivity.sysManager.getNumberSms()) {
				break;
			}

			this.arraySms.add(curArray.get(index));
		}
	}

	public synchronized void setArrayEmail() {
		EmailManager emailManager = (EmailManager) Platform.watch
				.getElement("email");
		Iterator<Object> listEmail = emailManager.iterator();
		this.arrayEmail.clear();

		ArrayList<Email> arrayListEmail = new ArrayList<Email>();
		while (listEmail.hasNext()) {
			Object object = listEmail.next();
			if (object instanceof Email) {
				Email email = (Email) object;
				arrayListEmail.add(email);
			}
		}

		ArrayList<Email> curArray = bubleShortEmail(arrayListEmail);
		for (int index = 0; index < curArray.size(); index++) {
			if (index == WatchActivity.sysManager.getNumberEmail()) {
				break;
			}

			this.arrayEmail.add(curArray.get(index));
		}
	}

	public ArrayList<Call> bubleShortCall(ArrayList<Call> arrayCall) {
		int iIndex, jIndex;
		Call temp;
		int count = arrayCall.size();
		for (iIndex = 0; iIndex < count - 1; iIndex++) {
			for (jIndex = iIndex + 1; jIndex < count - 1; jIndex++) {
				if ((!arrayCall.get(jIndex).getCallId().getValue().equals(""))
						&& (!arrayCall.get(iIndex).getCallId().getValue()
								.equals(""))) {
					int jCallId = Integer.parseInt(arrayCall.get(jIndex)
							.getCallId().getValue());
					int iCallId = Integer.parseInt(arrayCall.get(iIndex)
							.getCallId().getValue());
					if (jCallId < iCallId) {
						temp = arrayCall.get(iIndex);
						arrayCall.set(iIndex, arrayCall.get(jIndex));
						arrayCall.set(jIndex, temp);
					}
				}
			}
		}

		return arrayCall;
	}

	public ArrayList<Sms> bubleShortSms(ArrayList<Sms> arraySms) {
		int iIndex, jIndex;
		Sms temp;
		int count = arraySms.size();
		for (iIndex = 0; iIndex < count - 1; iIndex++) {
			for (jIndex = iIndex + 1; jIndex < count - 1; jIndex++) {
				if ((!arraySms.get(jIndex).getSmsId().getValue().equals(""))
						&& (!arraySms.get(iIndex).getSmsId().getValue()
								.equals(""))) {
					int jSmsId = Integer.parseInt(arraySms.get(jIndex)
							.getSmsId().getValue());
					int iSmsId = Integer.parseInt(arraySms.get(iIndex)
							.getSmsId().getValue());
					if (jSmsId < iSmsId) {
						temp = arraySms.get(iIndex);
						arraySms.set(iIndex, arraySms.get(jIndex));
						arraySms.set(jIndex, temp);
					}
				}
			}
		}

		return arraySms;
	}

	public ArrayList<Email> bubleShortEmail(ArrayList<Email> email) {
		int iIndex, jIndex;
		Email temp;
		int count = email.size();
		for (iIndex = 0; iIndex < count - 1; iIndex++) {
			for (jIndex = iIndex + 1; jIndex < count - 1; jIndex++) {
				if ((!email.get(jIndex).getEmailID().getValue().equals(""))
						&& (!email.get(iIndex).getEmailID().getValue()
								.equals(""))) {
					int jEmailId = Integer.parseInt(email.get(jIndex)
							.getEmailID().getValue());
					int iEmailId = Integer.parseInt(email.get(iIndex)
							.getEmailID().getValue());
					if (jEmailId < iEmailId) {
						temp = email.get(iIndex);
						email.set(iIndex, email.get(jIndex));
						email.set(jIndex, temp);
					}
				}
			}
		}

		return email;
	}

	public void setNumberSms(int numberSms) {
		this.numberSms = numberSms;
	}

	public void setNumberEmail(int numberEmail) {
		this.numberEmail = numberEmail;
	}

	public void setNumberCall(int numberCall) {
		this.numberCall = numberCall;
	}

	public void setListStyles(String listStyle) {
		listStyles.clear();
		Log.e("listStyles", " " + listStyle);
		if (!"".equals(listStyle)) {
			String[] list = listStyle.split("%");
			for (int index = 0; index < list.length; index++) {
				this.listStyles.add(list[index]);
				Log.e("setListStyles", list[index]);
			}
		} else {
			this.listStyles.add("0");
		}
	}

	public String convertTimeForCall(String timeDate) {
		Date date = new Date();
		DataTimeData dataTime = new DataTimeData(date);
		int year = 0;
		int month = 0;
		int day = 0;
		int hours = 0;
		int minute = 0;

		String arrayDateTime[] = timeDate.split(" ");
		if (arrayDateTime[0] != null && arrayDateTime[1] != null) {
			String arrayDate[] = arrayDateTime[0].split("/");
			String arrayTime[] = arrayDateTime[1].split(":");

			year = Integer.parseInt(arrayDate[2]);
			month = Integer.parseInt(arrayDate[1]);
			day = Integer.parseInt(arrayDate[0]);
			hours = Integer.parseInt(arrayTime[0]);
			minute = Integer.parseInt(arrayTime[1]);

			if (dataTime.getYear() > year) {
				return (dataTime.getYear() - year) + " years ago";
			} else if (dataTime.getMonth() > month) {
				return (dataTime.getMonth() - month) + " month ago";
			} else if (dataTime.getDay() > day) {
				return (dataTime.getDay() - day) + " day ago";
			} else if (dataTime.getHour() > hours) {
				return (dataTime.getHour() - hours) + " hours ago";
			} else if (dataTime.getMinute() > minute) {
				return (dataTime.getMinute() - minute) + " minutes ago";
			}
		}

		return timeDate;
	}

	public String convertTimeForSms(String timeDate) {
		String arrayDateTime[] = timeDate.split(" ");
		if (arrayDateTime[0] != null && arrayDateTime[1] != null) {
			String arrayDate[] = arrayDateTime[0].split("/");
			String arrayTime[] = arrayDateTime[1].split(":");
			String time = arrayDate[0] + "/" + arrayDate[1] + " - "
					+ arrayTime[0] + ":" + arrayTime[1];
			return time;
		} else {
			return "";
		}
	}

	public void setArrayEvent() {
		EventManager eventManager = (EventManager) Platform.watch
				.getElement("event");
		Iterator<Object> listEvent = eventManager.iterator();
		arrayEvent.clear();
		while (listEvent.hasNext()) {
			Object object = listEvent.next();
			if (object instanceof EventItem) {
				EventItem eventItem = (EventItem) object;
				if (!eventItem.getContent().getValue().isEmpty()) {
					arrayEvent.add(eventItem);
				}
			}
		}
	}

	public void setActiveCallActivity(boolean isActiveCallActivity) {
		this.isActiveCallActivity = isActiveCallActivity;
	}

	public void setLanguageManager(LanguageManager languageManager) {
		this.languageManager = languageManager;
	}

	@SuppressLint("SimpleDateFormat")
	public String convertTimeDate(String timeDate) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateSimple = new SimpleDateFormat("dd/MM/yyyy");
		String dateCurrent = dateSimple.format(calendar.getTime());
		String arrayDateTime[] = timeDate.split(" ");

		if (arrayDateTime.length == 2) {
			String date = arrayDateTime[0];
			String time = arrayDateTime[1];
			String arrayTime[] = time.split(":");
			if (date.equals(dateCurrent)) {
				return arrayTime[0] + ":" + arrayTime[1];
			} else {
				return date + " " + arrayTime[0] + ":" + arrayTime[1];
			}
		}
		return timeDate;
	}

	public void setStepCountValue(int stepCountValue) {
		this.stepCountValue = stepCountValue;
	}

	public void setDistanceValue(double distanceValue) {
		this.distanceValue = distanceValue;
	}

	public void setCaloriesValue(double caloriesValue) {
		this.caloriesValue = caloriesValue;
	}

	public void setCurrentStylesScreen(int currentScreen) {
		Log.e("", "current Screen: " + currentScreen);
		this.currentStylesScreen = currentScreen;
	}

	public void setMessageHandlerWatch(Handler messageHandlerWatch) {
		this.messageHandlerWatch = messageHandlerWatch;
	}

	public void setMessageHandlerCallDialog(Handler messageHandlerCallDialog) {
		this.messageHandlerCallDialog = messageHandlerCallDialog;
	}

	public void setMessageHandlerQuickSms(Handler messageHandlerQuickSms) {
		this.messageHandlerQuickSms = messageHandlerQuickSms;
	}

	public void setScreenTimeout(int screenTimeout) {
		this.screenTimeout = screenTimeout;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public void addListLap(String lap) {
		ListLap.add(lap);
	}

	public void addArrayAlarm(AlarmData alarm) {
		this.arrayAlarm.add(alarm);
	}

	public void setContentNotify(String contentNotify) {
		this.contentNotify = contentNotify;
	}

	public void setIdNotify(String idNotify) {
		this.idNotify = idNotify;
	}

	public void setBitmapNotify(String bitmapNotify) {
		this.bitmapNotify = bitmapNotify;
	}

	public void setTimerStart(boolean isTimerStart) {
		this.isTimerStart = isTimerStart;
	}

	public void setStopwatchStart(boolean isStopwatchStart) {
		this.isStopwatchStart = isStopwatchStart;
	}

	public int getStepCountValue() {
		return stepCountValue;
	}

	public double getDistanceValue() {
		return distanceValue;
	}

	public double getCaloriesValue() {
		return caloriesValue;
	}

	public int getCurrentStylesScreen() {
		return currentStylesScreen;
	}

	public Handler getMessageHandlerWatch() {
		return messageHandlerWatch;
	}

	public Handler getMessageHandlerCallDialog() {
		return messageHandlerCallDialog;
	}

	public Handler getMessageHandlerQuickSms() {
		return messageHandlerQuickSms;
	}

	public int getScreenTimeout() {
		return screenTimeout;
	}

	public ArrayList<Call> getArrayCall() {
		return arrayCall;
	}

	public ArrayList<Sms> getArraySms() {
		return this.arraySms;
	}

	public ArrayList<EmailIcon> getArrayEmailIcon() {
		return arrayEmailIcon;
	}

	public synchronized ArrayList<Email> getArrayEmail() {
		return arrayEmail;
	}

	public ArrayList<String> getListStyles() {
		return listStyles;
	}

	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public ArrayList<String> getListLap() {
		return ListLap;
	}

	public void clearListLap() {
		ListLap.clear();
	}

	public ArrayList<View> getListIconForWatchs() {
		return listIconForWatchs;
	}

	public ArrayList<AlarmData> getArrayAlarm() {
		return arrayAlarm;
	}

	public void removeArrayAlarm(int alarmIndex) {
		this.arrayAlarm.remove(alarmIndex);
	}

	public boolean isActiveCallActivity() {
		return isActiveCallActivity;
	}

	public Typeface getTypeFaceLight(AssetManager assetManager) {
		Typeface typeFace = Typeface
				.createFromAsset(assetManager, ROBOTO_LIGHT);
		return typeFace;
	}

	public Typeface getTypeFaceRegular(AssetManager assetManager) {
		Typeface typeFace = Typeface.createFromAsset(assetManager,
				ROBOTO_REGULAR);
		return typeFace;
	}

	public Typeface getTypeFaceItalic(AssetManager assetManager) {
		Typeface typeFace = Typeface.createFromAsset(assetManager,
				ROBOTO_ITALIC);
		return typeFace;
	}

	public Typeface getTypeFaceBold(AssetManager assetManager) {
		Typeface typeFace = Typeface.createFromAsset(assetManager, ROBOTO_BOLD);
		return typeFace;
	}

	public String getContentNotify() {
		return contentNotify;
	}

	public String getIdNotify() {
		return idNotify;
	}

	public String getBitmapNotify() {
		return bitmapNotify;
	}

	public boolean isTimerStart() {
		return isTimerStart;
	}

	public boolean isStopwatchStart() {
		return isStopwatchStart;
	}

	public ArrayList<EventItem> getArrayEvent() {
		return arrayEvent;
	}

	public int getNumberSms() {
		return numberSms;
	}

	public int getNumberEmail() {
		return numberEmail;
	}

	public int getNumberCall() {
		return numberCall;
	}

	public ArrayList<Integer> getmThumbIds() {
		return mThumbIds;
	}

	public void setmThumbIds(String arrangeIcon) {
		mThumbIds.clear(); 
		String[] list = arrangeIcon.split("%"); 
		for (int index = 0; index < list.length; index++) {
			int value = Integer.parseInt(list[index]);
			Log.e("SystemManager", "value" + value); 
			switch (value) { 
			case 0:
				mThumbIds.add(R.drawable.pedometer_icon);
				break;
			case 1:
				mThumbIds.add(R.drawable.heart_icon);
				break;  
			case 2:
				mThumbIds.add(R.drawable.alarm_icon);
				break; 
			case 3:
				mThumbIds.add(R.drawable.stopwatch_icon);
				break;
			case 4:
				mThumbIds.add(R.drawable.timer_icon);
				break;
			case 5:
				mThumbIds.add(R.drawable.setting_icon);
				break;
			default:
				break;
			}
		}

	}

	private int stepCountValue;
	private double distanceValue;
	private double caloriesValue;

	private String contentNotify;
	private String idNotify;
	private String bitmapNotify;
	public static final String ROBOTO_LIGHT = "fonts/Roboto-Light.ttf";
	public static final String ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";
	public static final String ROBOTO_ITALIC = "fonts/Roboto-Italic.ttf";
	public static final String ROBOTO_BOLD = "fonts/Roboto-Bold.ttf";

	private boolean isTimerStart = false;
	private boolean isStopwatchStart = false;

	private int numberSms;
	private int numberCall;
	private int numberEmail;
	private int currentStylesScreen;
	private int screenTimeout;
	private LanguageManager languageManager;
	private SharedPreferences preferences = null;
	private Handler messageHandlerWatch;
	private Handler messageHandlerCallDialog;
	private Handler messageHandlerQuickSms;
	
	private ArrayList<Integer> mThumbIds = new ArrayList<Integer>();

	private boolean isActiveCallActivity;
	private ArrayList<Call> arrayCall = new ArrayList<Call>();
	private ArrayList<Sms> arraySms = new ArrayList<Sms>();

	private ArrayList<EventItem> arrayEvent = new ArrayList<EventItem>();
	private ArrayList<Email> arrayEmail = new ArrayList<Email>();
	private ArrayList<EmailIcon> arrayEmailIcon = new ArrayList<EmailIcon>();
	private ArrayList<String> listStyles = new ArrayList<String>();
	private ArrayList<String> ListLap = new ArrayList<String>();
	private ArrayList<View> listIconForWatchs = new ArrayList<View>();
	private ArrayList<AlarmData> arrayAlarm = new ArrayList<AlarmData>();
}

package com.bkav.ui.watchactivity;

import java.io.File;
import java.util.ArrayList;
import com.bkav.home.lib.FileSystem;
import com.bkav.home.system.Platform;
import com.bkav.ui.alarm.AlarmScreen;
import com.bkav.ui.call.CallDialog;
import com.bkav.ui.call.CallScreen;
import com.bkav.ui.clock.ClockScreen;
import com.bkav.ui.clock.ClockStylesAnalog;
import com.bkav.ui.clock.ClockStylesDigital;
import com.bkav.ui.email.EmailScreen;
import com.bkav.ui.event.EventScreen;
import com.bkav.ui.heartrate.HeartRateScreen;
import com.bkav.ui.language.FileManager;
import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.music.MusicScreen;
import com.bkav.ui.notification.NotifyDialog;
import com.bkav.ui.pedometer.IdleWarningThread;
import com.bkav.ui.pedometer.MotionDialog;
import com.bkav.ui.pedometer.PedometerScreen;
import com.bkav.ui.setting.SettingScreen;
import com.bkav.ui.sms.SmsDialog;
import com.bkav.ui.sms.SmsScreen;
import com.bkav.ui.stopwatch.StopwatchScreen;
import com.bkav.ui.timer.TimerScreen;
import com.bkav.ui.watchactivity.SimpleGestureFilter.SimpleGestureListener;
import com.bkav.utils.SystemManager;
import com.bkav.watch.calls.CallsManager;
import com.bkav.watch.contact.ContactManager;
import com.bkav.watch.email.EmailManager;
import com.bkav.watch.event.EventManager;
import com.bkav.watch.health.HealthManager;
import com.bkav.watch.notification.NotificationManager;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;
import com.nineoldandroids.animation.ValueAnimator;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WatchActivity extends FragmentActivity implements
		SimpleGestureListener {

	public static final SystemManager sysManager;
	public static final int REQUEST_UPDATE = 1;
	public static final int COMING_CALL = 2;
	public static final int UPDATE_CALL = 3;
	public static final int COMING_SMS = 4;
	public static final int UPDATE_SMS = 5;
	public static final int COMING_NOTIFY = 6;
	public static final int UPDATE_EMAIL = 7;
	public static final int CHANGE_STYLE_WATCH_UP = 8;
	public static final int CHANGE_STYLE_WATCH_DOWN = 9;
	public static final int SEND_DATA_HEALTHCARE = 10;
	public static final int UPDATE_CLOCK_STYLES = 11;
	public static final int UPDATE_SCREEN_TIMEOUT = 12;
	public static final int UPDATE_MISS_CALL_ICON = 13;
	public static final int UPDATE_NEW_EMAIL_ICON = 14;
	public static final int UPDATE_NEW_SMS_ICON = 15;
	public static final int UPDATE_LANGUAGE = 16;
	public static final int UPDATE_TIME = 17;
	public static final int UPDATE_ALARM = 18;
	public static final int UPDATE_PERSON = 19;
	public static final int MOTION_WARNING_ACTIVE = 20;
	public static final int MOTION_COMPLETE_TARGET = 21;
	public static final int HEART_RATE_LOW = 22;
	public static final int HEART_RATE_HIGH = 23;
	public static final int UPDATE_EVENTS = 24;
	public static final int UPDATE_ARRANGE_ICON = 25;
	public static final int RESET_DATA = 26;

	public static final String ACTIVE_ALARM = "active_alarm";
	public static final String CANCEL_ALARM = "cancel_alarm";
	public static final String NAME = "name";
	public static final String NUMBER = "number";
	public static final String RESULT_CODE_SMS = "result_code_sms";
	public static final String REJECT_CALL = "{calls:{CallMethod:REJECT_CALL}}";
	public static final String ACCEPT_CALL = "{calls:{CallMethod:ACCEPT_CALL}}";
	public static final String SILENT_CALL = "{calls:{CallMethod:SILENT_CALL}}";

	public ViewPager viewPager;
	public ViewPagerAdapter viewPagerAdapter;
	public ArrayList<Fragment> fragmentArray = new ArrayList<Fragment>();
	public int height, width;
	public GridView gridview;
	static {
		sysManager = new SystemManager();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.watch_activity);
		FileSystem.init(getFileDirXml());
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();

		if (!bluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, 0);
		}

		if (!bluetoothAdapter.isDiscovering()) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			startActivityForResult(discoverableIntent,
					DISCOVERABLE_REQUEST_CODE);
		}
		sysManager.setMessageHandlerWatch(createMessageHandle());

		FileManager.create(getApplicationContext());
		sysManager.setPreferences(getSharedPreferences("BwatchDevice",
				Context.MODE_PRIVATE));
		sysManager.setLanguageManager(new LanguageManager(
				getApplicationContext()));

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		height = displayMetrics.heightPixels;
		width = displayMetrics.widthPixels;
		slideDownView = (RelativeLayout) findViewById(R.id.slide_down_view);
		paramsDown = slideDownView.getLayoutParams();
		paramsDown.height = (int) dpToPixels(getApplicationContext(), 50);
		slideDownView.setLayoutParams(paramsDown);
		focusDown = findViewById(R.id.focusDown);

		focusDown.setOnTouchListener(new View.OnTouchListener() {
			float yStart = 0;
			float closedHeight = dpToPixels(getApplicationContext(), 50);
			float openHeight = height;
			float currentHeight;
			float yLast = 0;
			boolean directionDown = false;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					yStart = event.getRawY();
					yLast = event.getRawY();
					currentHeight = slideDownView.getHeight();
					break;
				case MotionEvent.ACTION_MOVE:
					float totalHeightDiff = Math.abs(event.getRawY() - yStart);
					paramsDown.height = (int) (currentHeight + totalHeightDiff);
					slideDownView.setLayoutParams(paramsDown);
					if (event.getRawY() > yLast) {
						directionDown = true;
						// stateFocus = 1;
						// slideUpView.setVisibility(View.INVISIBLE);
					} else {
						directionDown = false;
						// stateFocus = 0;
						// slideUpView.setVisibility(View.VISIBLE);
					}
					yLast = event.getRawY();
					break;
				case MotionEvent.ACTION_CANCEL:
					break;
				case MotionEvent.ACTION_UP:

					if (directionDown) {
						// open
						int startHeight = slideDownView.getHeight();

						ValueAnimator animation = ValueAnimator.ofObject(
								new HeightEvaluator(slideDownView),
								startHeight, (int) openHeight).setDuration(300);
						animation
								.setInterpolator(new OvershootInterpolator(50));
						animation.start();
					} else {
						// Close the sliding view.
						int startHeight = slideDownView.getHeight();
						ValueAnimator animation = ValueAnimator.ofObject(
								new HeightEvaluator(slideDownView),
								startHeight, (int) closedHeight).setDuration(
								300);
						animation
								.setInterpolator(new OvershootInterpolator(50));
						animation.start();
					}
					break;
				}
				return true;
			}
		});

		viewPager = (ViewPager) findViewById(R.id.activityWatch);
		gridview = (GridView) findViewById(R.id.gridview);
		imageAdapter = new ImageAdapter(getApplicationContext(),
				sysManager.getmThumbIds());
		gridview.setAdapter(imageAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "" + position,
						Toast.LENGTH_SHORT).show();
			}

		});

		initFragment();

		sysManager.setArrayCall();
		sysManager.setArraySms();
		sysManager.setArrayEmail();
		sysManager.setListStyles("0");

		simpleGestureFilter = new SimpleGestureFilter(this, this);
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				fragmentArray);

		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setCurrentItem(0);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int currentScreen) {
				switch (currentScreen) {
				case 1:
					Platform.connection
							.write("{calls:{CallMethod:CHANGE_STATE}}");
					Log.e("WatchActivity", "write clear call");
					break;
				case 2:
					Platform.connection
							.write("{sms:{SendSMSMethod:CHANGE_STATE}}");
					Log.e("WatchActivity", "write clear sms");
					break;
				case 3:
					Platform.connection
							.write("{email:{EmailMethod:CHANGE_STATE}}");
					Log.e("WatchActivity", "write clear email");
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
 
		idleWarningThread = new IdleWarningThread(getApplicationContext());
		idleWarningThread.start();
	} 

	public void resetData() {
		Log.e("WatchActivity", "reset dataaaaaaaa");
		ContactManager contactManager = (ContactManager) Platform.watch
				.getElement("contact");
		CallsManager callManager = (CallsManager) Platform.watch
				.getElement("calls");
		SmsManager smsManager = (SmsManager) Platform.watch.getElement("sms");
		EmailManager emailManager = (EmailManager) Platform.watch
				.getElement("email");
		EventManager eventManager = (EventManager) Platform.watch
				.getElement("event");
		HealthManager healthManager = (HealthManager) Platform.watch
				.getElement("health");
		NotificationManager notificationManager = (NotificationManager) Platform.watch
				.getElement("notify");
		synchronized (Platform.thread) {
			Platform.watch.remove(contactManager);
			Platform.watch.remove(callManager);
			Platform.watch.remove(smsManager);
			Platform.watch.remove(emailManager);
			Platform.watch.remove(eventManager);
			Platform.watch.remove(healthManager);
			Platform.watch.remove(notificationManager);
			Platform.watch.resetData();
		}
		Platform.connection.write("{UpdateMethod:UPDATE}");
	}

	public String getFileDirXml() {
		File SDCardRoot = Environment.getExternalStorageDirectory();
		File dirXml = new File(SDCardRoot.getAbsolutePath() + "/dist");
		if (!dirXml.exists()) {
			if (!dirXml.mkdirs()) {
				return "";
			}
		}
		return dirXml.getAbsolutePath();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void initFragment() {
		clock = new ClockScreen();
		call = new CallScreen();
		sms = new SmsScreen();
		email = new EmailScreen();
		pedometer = new PedometerScreen();
		heartRate = new HeartRateScreen();
		stopwatch = new StopwatchScreen();
		timerScreen = new TimerScreen();
		alarm = new AlarmScreen();
		event = new EventScreen();
		setting = new SettingScreen();
		music = new MusicScreen();

		fragmentArray.add(clock);
		fragmentArray.add(call);
		fragmentArray.add(sms);
		fragmentArray.add(email);
		fragmentArray.add(event);
		fragmentArray.add(pedometer);
		fragmentArray.add(heartRate);
		fragmentArray.add(music);
		fragmentArray.add(stopwatch);
		fragmentArray.add(timerScreen);
		fragmentArray.add(alarm);
		fragmentArray.add(setting);
	}

	@SuppressLint({ "HandlerLeak", "DefaultLocale" })
	public Handler createMessageHandle() {

		Handler messageHandlerWatch = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case REQUEST_UPDATE:
					Log.e("WatchActivity", "write {UpdateMethod:UPDATE}");
					Platform.connection.write("{UpdateMethod:UPDATE}");
					break;
				case COMING_CALL:
					Log.e("WatchActivity", "COMING_CALL");
					if (!sysManager.isActiveCallActivity()) {
						WatchActivity.sysManager.setActiveCallActivity(true);
						Intent intentCall = new Intent(getBaseContext(),
								CallDialog.class);
						startActivityForResult(intentCall, COMING_CALL);
					}
					break;
				case COMING_SMS:
					Log.e("WatchActivity", "COMING_SMS");
					Intent intentSms = new Intent(getBaseContext(),
							SmsDialog.class);
					startActivityForResult(intentSms, COMING_SMS);
					break;
				case COMING_NOTIFY:
					Log.e("WatchActivity", "COMING_NOTIFY");
					Intent intentNotify = new Intent(getBaseContext(),
							NotifyDialog.class);
					startActivityForResult(intentNotify, COMING_NOTIFY);
					break;
				case MOTION_WARNING_ACTIVE:
					Intent intentMotionWarning = new Intent(getBaseContext(),
							MotionDialog.class);
					intentMotionWarning
							.putExtra(
									"contentMotion",
									"Bạn đã ngồi quá lâu không hoạt động! Hãy đứng dậy vươn vai và tập thể dục nhé");
					startActivityForResult(intentMotionWarning, 7);
					break;
				case MOTION_COMPLETE_TARGET:
					Log.e("WatchActivity", "COMING_CALL");
					Intent intentMotionTarget = new Intent(getBaseContext(),
							MotionDialog.class);
					intentMotionTarget
							.putExtra(
									"contentMotion",
									"Chúc mừng. Bạn đã hoàn thành mục tiêu số bước chạy trong ngày hôm nay");
					startActivityForResult(intentMotionTarget, 8);
					break;
				case HEART_RATE_LOW:
					Intent intentHeartRateLow = new Intent(getBaseContext(),
							MotionDialog.class);
					intentHeartRateLow.putExtra("contentMotion",
							"Nhịp tim của bạn quá thấp...");
					startActivityForResult(intentHeartRateLow, 9);
					break;
				case HEART_RATE_HIGH:
					Log.e("WatchActivity", "COMING_CALL");
					Intent intentHeartRateHigh = new Intent(getBaseContext(),
							MotionDialog.class);
					intentHeartRateHigh.putExtra("contentMotion",
							"Nhịp tim của bạn quá cao...");
					startActivityForResult(intentHeartRateHigh, 10);
					break;
				case CHANGE_STYLE_WATCH_UP:
					int postionScreenUp = viewPager.getCurrentItem();
					if (0 == postionScreenUp
							&& sysManager.getListStyles().size() > 1) {
						clock.transactionUp();
					}
					break;
				case CHANGE_STYLE_WATCH_DOWN:
					int postionScreenDown = viewPager.getCurrentItem();
					if (0 == postionScreenDown
							&& sysManager.getListStyles().size() > 1) {
						clock.transactionDown();
					}
					break;
				case SEND_DATA_HEALTHCARE:
					Platform.connection.write("{health:{CountDistanceMethod:"
							+ sysManager.getDistanceValue() + "}}");
					Platform.connection.write("{health:{CountStepMethod:"
							+ sysManager.getStepCountValue() + "}}");
					Platform.connection.write("{health:{CountCaloriesMethod:"
							+ sysManager.getCaloriesValue() + "}}");
					break;
				case UPDATE_CALL:
					Log.e("WatchActivity", "UPDATE_CALL");
					sysManager.setArrayCall();
					call.updateListCall();
					break;
				case UPDATE_SMS:
					Log.e("WatchActivity", "UPDATE_SMS");
					sysManager.setArraySms();
					sms.updateListSms();
					break;
				case UPDATE_EMAIL:
					Log.e("WatchActivity", "UPDATE_EMAIL");
					sysManager.setArrayEmail();
					sysManager.setArrayEmail();
					email.updateListEmail();
					break;
				case UPDATE_EVENTS:
					Log.e("WatchActivity", "UPDATE_EVENT");
					sysManager.setArrayEvent();
					event.updateListEvent();
					break;
				case UPDATE_MISS_CALL_ICON:
					Log.e("Watch activity", "UPDATE MISS CALL ICON");
					clock.updateIconState();
					break;
				case UPDATE_NEW_SMS_ICON:
					Log.e("Watch activity", "UPDATE NEW SMS ICON");
					clock.updateIconState();
					break;
				case UPDATE_NEW_EMAIL_ICON:
					Log.e("UPDATE_NEW_EMAIL", "UPDATE NEW EMAIL ICON");
					clock.updateIconState();
					break;
				case UPDATE_SCREEN_TIMEOUT:
					setting.updateChoiceTimeout();
					break;
				case UPDATE_CLOCK_STYLES:
					boolean checkIsCurrentClock = false;
					String currentItem = ""
							+ sysManager.getCurrentStylesScreen();
					Log.e("", "currentItem:" + currentItem);
					for (int index = 0; index < sysManager.getListStyles()
							.size(); index++) {
						if (currentItem.equals(sysManager.getListStyles().get(
								index))) {
							checkIsCurrentClock = true;
						}
					}
					Log.e("", "checkIsCurrentClock:" + checkIsCurrentClock);
					if (!checkIsCurrentClock) {
						clock.transactionDefaultClockStyles();
					}
					break;
				case UPDATE_TIME:
					// update time when connect app
					break;
				case UPDATE_ALARM:
					String[] object = (String[]) msg.obj;
					if (ACTIVE_ALARM.equals(object[0])) {
						Log.e("WatchActivity", "active");
						alarm.setAlarm(Integer.parseInt(object[1]));
					} else if (CANCEL_ALARM.equals(object[0])) {
						Log.e("WatchActivity", "cancel");
						alarm.cancelAlarm(Integer.parseInt(object[1]));
					}
					break;
				case UPDATE_LANGUAGE:
					call.updateLanguageCall();
					sms.updateLanguageSms();
					email.updateLanguageEmail();
					pedometer.updateLanguageHealth();
					heartRate.updateLanguageHealthCare();
					alarm.updateLanguageAlarm();
					timerScreen.updateLanguageStopwatch();
					stopwatch.updateLanguageStopwatch();
					setting.updateLanguageSetting();
					switch (sysManager.getCurrentStylesScreen()) {
					case 0:
						new ClockStylesAnalog().updateLanguageWatch();
						break;
					case 1:
						new ClockStylesDigital(1).updateLanguageWatch();
						break;
					case 2:
						new ClockStylesDigital(2).updateLanguageWatch();
						break;
					case 3:
						new ClockStylesDigital(3).updateLanguageWatch();
						break;
					default:
						new ClockStylesAnalog().updateLanguageWatch();
						break;
					}
					break;
				case UPDATE_PERSON:
					pedometer.updatePerson();
					break;
				case UPDATE_ARRANGE_ICON:
					updateArrangeIcon();
					break;
				case RESET_DATA:
					resetData();
					break;
				default:
					break;
				}
			}
		};
		return messageHandlerWatch;
	}

	public void updateArrangeIcon() {
		Log.e("WatchActivity", "update arrangeIcon");
		imageAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent motionEvent) {
		this.simpleGestureFilter.onTouchEvent(motionEvent);
		return super.dispatchTouchEvent(motionEvent);
	}

	@Override
	public void onSwipe(int direction) {
		switch (direction) {
		case SimpleGestureFilter.SWIPE_UP:
			Message messageUp = new Message();
			messageUp.what = CHANGE_STYLE_WATCH_UP;
			sysManager.getMessageHandlerWatch().sendMessage(messageUp);
			break;
		case SimpleGestureFilter.SWIPE_DOWN:
			Message messageDown = new Message();
			messageDown.what = CHANGE_STYLE_WATCH_DOWN;
			sysManager.getMessageHandlerWatch().sendMessage(messageDown);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDoubleTap() {

	}

	@Override
	public void onBackPressed() {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == COMING_CALL) {
			if (resultCode == RESULT_OK) {
				viewPager.setCurrentItem(COMING_CALL);
			}
		} else if (requestCode == COMING_SMS) {
			if (resultCode == RESULT_OK) {
				viewPager.setCurrentItem(COMING_SMS);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private static final int DISCOVERABLE_REQUEST_CODE = 99;

	private SimpleGestureFilter simpleGestureFilter;
	private ClockScreen clock;
	private CallScreen call;
	private SmsScreen sms;   
	private EmailScreen email;
	private EventScreen event;
	private PedometerScreen pedometer;
	private HeartRateScreen heartRate;  
	private StopwatchScreen stopwatch;
	private TimerScreen timerScreen;
	private SettingScreen setting;
	private AlarmScreen alarm;
	private MusicScreen music;
	public IdleWarningThread idleWarningThread;
	private RelativeLayout slideDownView;
	private View focusDown;
	private LayoutParams paramsDown;
	private ImageAdapter imageAdapter;

	public static float dpToPixels(Context context, float dipValue) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
				metrics);
	}

	// 0: nothing
	// 1: down
	// 2: up
}

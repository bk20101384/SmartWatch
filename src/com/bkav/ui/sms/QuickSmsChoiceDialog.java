package com.bkav.ui.sms;

import java.util.ArrayList;

import com.bkav.home.system.Platform;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class QuickSmsChoiceDialog extends Activity {
	public static final int UPDATE_QUICK_SMS = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_message);
		WatchActivity.sysManager.setMessageHandlerQuickSms(createMessageHandle());
		quickMessage = (ListView) findViewById(R.id.quickMessage);
		cancel = (Button) findViewById(R.id.cancel);
		Bundle bundle = getIntent().getExtras();
		number = bundle.getString(WatchActivity.NUMBER);
		updateQuickSms();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.type_xml, R.id.textView1, arrayQuickSms);
		quickMessage.setAdapter(adapter);
		quickMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Platform.connection.write("{sms:{SendSMSMethod:" + number + ";" + arrayQuickSms.get(position) + "}}");
//				Log.e(number, arrayQuickSms.get(position));
				onBackPressed();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	public void updateQuickSms() {
		SmsManager smsManager = (SmsManager) Platform.watch.getElement("sms");
		listQuickSms = smsManager.getListQuickSms().getValue();
		if (listQuickSms != null) {
			arrayQuickSms.clear();
			String[] messages = listQuickSms.split(";");
			for (int index = 0; index < messages.length; index++) {
				arrayQuickSms.add(messages[index]);
//				Log.e("quick message " + index, messages[index]);
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.type_xml, R.id.textView1, arrayQuickSms);
		quickMessage.setAdapter(adapter);
	}

	@SuppressLint("HandlerLeak")
	public Handler createMessageHandle() {
		Handler messageHandlerSmsDialog = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case UPDATE_QUICK_SMS:
					updateQuickSms(); 
					break;
				default:
					break;
				}  
			}
		};
		return messageHandlerSmsDialog;
	}

	private String listQuickSms;
	private ArrayList<String> arrayQuickSms = new ArrayList<String>();
	private ListView quickMessage;
	private Button cancel;
	private String number;
}

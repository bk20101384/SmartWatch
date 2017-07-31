package com.bkav.ui.call;

import com.bkav.home.system.Platform;
import com.bkav.ui.sms.QuickSmsChoiceDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDialog extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer_dialog);
		Bundle bundle = getIntent().getExtras();
		name = (TextView) findViewById(R.id.name);
		callBack = (ImageView) findViewById(R.id.callBack);
		quickReply = (ImageView) findViewById(R.id.quickReply);
		
		number = bundle.getString(WatchActivity.NUMBER);
		name.setText(bundle.getString(WatchActivity.NAME));
		callBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Platform.connection.write("{calls:{CallMethod:"+ number +"}}");
				Log.e("number", "number" + number);
				finish();
			}
		});
		quickReply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						QuickSmsChoiceDialog.class);
				intent.putExtra(WatchActivity.NUMBER, number);
				startActivity(intent);
				finish();
			}
		});
	}

	private TextView name;
	private ImageView callBack;
	private ImageView quickReply;
	private String number;
}

package com.bkav.ui.sms;

import com.bkav.home.system.Platform;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class IncomSmsIcon {
	public IncomSmsIcon(Context context) {
		incomeSmsView = View.inflate(context, R.layout.incom_sms_icon, null);
		incomeSms = (TextView) incomeSmsView.findViewById(R.id.incomeSms);
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
		if (typeFaceRegular != null) {
			incomeSms.setTypeface(typeFaceRegular);
		}
		updateNewSms();
	}

	public void updateNewSms() {
		SmsManager smsManager = (SmsManager) Platform.watch.getElement("sms");
		if (incomeSms != null) { 
			Log.e("incomeSmsIcon", "new sms:" + smsManager.getMissCountSms().getValue());
			incomeSms.setText(smsManager.getMissCountSms().getValue());
		} 
	}

	public View getView() {
		return incomeSmsView;
	}

	private View incomeSmsView;
	private TextView incomeSms;
}
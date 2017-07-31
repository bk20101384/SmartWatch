package com.bkav.ui.email;

import com.bkav.home.system.Platform;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.email.EmailManager;
import com.example.bwatchdevice.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class IncomEmailIcon {
	
	public IncomEmailIcon(Context context) {
		incomeEmailView = View.inflate(context, R.layout.incom_email_icon, null);
		incomeEmail = (TextView) incomeEmailView.findViewById(R.id.newEmail);
		
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
		if (typeFaceRegular != null) {
			incomeEmail.setTypeface(typeFaceRegular);
		}
		updateNewEmail();
	}

	public void updateNewEmail() {
		EmailManager emailManager = (EmailManager) Platform.watch.getElement("email");
		if (incomeEmail != null) {
			incomeEmail.setText(emailManager.getNumberOfUnreadMsg().getValue());
		}
	}

	public View getView() {
		return incomeEmailView;
	}

	private View incomeEmailView;
	private TextView incomeEmail;
}
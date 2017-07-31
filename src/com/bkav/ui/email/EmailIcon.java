package com.bkav.ui.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class EmailIcon {
	public View view;

	public EmailIcon(Context context, String time, String address,
			String subject) {
		view = View.inflate(context, R.layout.email_icon, null);
		timeEmail = (TextView) view.findViewById(R.id.timeEmail);
		addressEmail = (TextView) view.findViewById(R.id.nameEmail);
		subjectEmail = (TextView) view.findViewById(R.id.contentEmail);
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
	    Typeface typeFaceItalic = WatchActivity.sysManager.getTypeFaceItalic(context.getAssets());
	    if (typeFaceItalic != null && typeFaceRegular != null) {
	    	timeEmail.setTypeface(typeFaceItalic);
	    	addressEmail.setTypeface(typeFaceRegular);
	    	subjectEmail.setTypeface(typeFaceRegular);
	    }

		timeEmail.setText(WatchActivity.sysManager.convertTimeDate(time));

		Pattern pattern = Pattern.compile("<(.*?)>");
		Matcher matcher = pattern.matcher(address);
		if (matcher.find()) {
			addressEmail.setText(matcher.group(1)); // => "3"
		}
		subjectEmail.setText(subject);
	}

	public View getView() {
		return view;
	}

	private TextView timeEmail;
	private TextView addressEmail;
	private TextView subjectEmail;
}

package com.bkav.ui.email;

import java.util.ArrayList;
import com.bkav.ui.call.ContactDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.email.Email;
import com.bkav.watch.sms.Sms;
import com.example.bwatchdevice.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmailAdapter extends ArrayAdapter<Email> {

	public EmailAdapter(Context context, int resource, ArrayList<Email> arrayEmail) {
		super(context, resource, arrayEmail);
		this.context = context;
		this.arrayEmail = arrayEmail;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int indexEmail = position;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.email_icon, parent, false);
		nameEmail = (TextView) view.findViewById(R.id.nameEmail);
		timeEmail = (TextView) view.findViewById(R.id.timeEmail);
		contentEmail = (TextView) view.findViewById(R.id.contentEmail);

		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
	    Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(context.getAssets());
	    Typeface typeFaceItalic = WatchActivity.sysManager.getTypeFaceItalic(context.getAssets());
	    if (typeFaceLight != null && typeFaceRegular != null) {
	    	nameEmail.setTypeface(typeFaceRegular);
	    	contentEmail.setTypeface(typeFaceRegular);   
	    	timeEmail.setTypeface(typeFaceItalic);
	    }

		nameEmail.setText(arrayEmail.get(indexEmail).getAddress().getValue());
		timeEmail.setText(WatchActivity.sysManager.convertTimeForSms(arrayEmail.get(indexEmail).getTime().getValue()));
		contentEmail.setText(arrayEmail.get(indexEmail).getSubject().getValue());

		return view;
	}

	private Context context;
	private View view;
	private ArrayList<Email> arrayEmail;
	private TextView timeEmail;
	private TextView nameEmail;
	private TextView contentEmail;
}

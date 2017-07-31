package com.bkav.ui.sms;

import java.util.ArrayList;
import com.bkav.ui.call.ContactDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.sms.Sms;
import com.example.bwatchdevice.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmsAdapter extends ArrayAdapter<Sms> {

	public SmsAdapter(Context context, int resource, ArrayList<Sms> arraySms) {
		super(context, resource, arraySms);
		this.context = context;
		this.arraySms = arraySms;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int indexSms = position;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.sms_icon, parent, false);
		smsIcon = (LinearLayout) view.findViewById(R.id.smsIcon);
		nameSms = (TextView) view.findViewById(R.id.nameSms); 
		timeSms = (TextView) view.findViewById(R.id.timeSms);
		contentSms = (TextView) view.findViewById(R.id.contentSms);
		smsIcon.setOnClickListener(new View.OnClickListener() {

			@Override 
			public void onClick(View v) {
				Intent intent = new Intent(context, ContactDialog.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(WatchActivity.NAME, arraySms.get(indexSms)
						.getContactName().getValue());
				intent.putExtra(WatchActivity.NUMBER, arraySms.get(indexSms)
						.getNumber().getValue());
				context.startActivity(intent);
			}  
		}); 
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
	    Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(context.getAssets());
	    Typeface typeFaceItalic = WatchActivity.sysManager.getTypeFaceItalic(context.getAssets());
	    if (typeFaceLight != null && typeFaceRegular != null) {
	    	nameSms.setTypeface(typeFaceRegular);
	    	contentSms.setTypeface(typeFaceRegular);   
	    	timeSms.setTypeface(typeFaceItalic);
	    }

		nameSms.setText(arraySms.get(indexSms).getContactName().getValue());
		timeSms.setText(WatchActivity.sysManager.convertTimeForSms(arraySms.get(indexSms).getTime().getValue()));
		contentSms.setText(arraySms.get(indexSms).getContent().getValue());

		return view;
	} 

	private Context context;
	private View view;
	private ArrayList<Sms> arraySms;
	private LinearLayout smsIcon;
	private TextView timeSms;
	private TextView nameSms;
	private TextView contentSms;
}

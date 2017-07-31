package com.bkav.ui.event;

import java.util.ArrayList;
import com.bkav.ui.call.ContactDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.event.EventItem;
import com.bkav.watch.sms.Sms;
import com.example.bwatchdevice.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<EventItem> {

	public EventAdapter(Context context, int resource,
			ArrayList<EventItem> arrayEvent) {
		super(context, resource, arrayEvent);
		this.context = context;
		this.arrayEvent = arrayEvent;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int indexCall = position;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.event_icon, parent, false);
		startTimeEvent = (TextView) view.findViewById(R.id.startTimeEvent);
		endTimeEvent = (TextView) view.findViewById(R.id.endTimeEvent);
		contentEvent = (TextView) view.findViewById(R.id.contentEvent);
		Typeface typeFaceRegular = WatchActivity.sysManager
				.getTypeFaceRegular(context.getAssets());
		Typeface typeFaceItalic = WatchActivity.sysManager
				.getTypeFaceItalic(context.getAssets());
		if (typeFaceItalic != null && typeFaceRegular != null) {
			contentEvent.setTypeface(typeFaceRegular);
			startTimeEvent.setTypeface(typeFaceItalic);
			endTimeEvent.setTypeface(typeFaceItalic);
		}

		startTimeEvent.setText("From: "
				+ arrayEvent.get(indexCall).getStartTime().getValue());
		endTimeEvent.setText("To: "
				+ arrayEvent.get(indexCall).getEndTime().getValue());
		contentEvent.setText(arrayEvent.get(indexCall).getContent().getValue());
		return view;
	}

	private Context context;
	private View view;
	private ArrayList<EventItem> arrayEvent;
	private TextView startTimeEvent;
	private TextView endTimeEvent;
	private TextView contentEvent;
}

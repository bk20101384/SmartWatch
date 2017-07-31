package com.bkav.ui.alarm;

import java.util.ArrayList;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ViewHolder") 
public class AlarmAdapter extends ArrayAdapter<AlarmData> {

	public AlarmAdapter(Context context, int textViewResourceId,
			ArrayList<AlarmData> listAlarm) {
		super(context, textViewResourceId, listAlarm);
		this.context = context; 
		this.arrayAlarm = listAlarm;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int indexAlarm = position;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.alarm_icon, parent, false);
		imageAlarmIcon = (ImageView) view.findViewById(R.id.imageAlarmIcon);
		time = (TextView) view.findViewById(R.id.timeAlarm);
		time.setText(arrayAlarm.get(position).getTime());

		if (arrayAlarm.get(indexAlarm).isActive()) {
			imageAlarmIcon.setBackgroundResource(R.drawable.icon_alarm_visible);
			time.setTextColor(Color.rgb(255, 255, 255));
		} else {
			imageAlarmIcon
					.setBackgroundResource(R.drawable.icon_alarm_invisible);
			time.setTextColor(Color.rgb(100, 100, 100));
		}

		imageAlarmIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (arrayAlarm.get(indexAlarm).isActive()) {
					arrayAlarm.get(indexAlarm).setActive(false);
					imageAlarmIcon
							.setBackgroundResource(R.drawable.icon_alarm_invisible);
					time.setTextColor(Color.rgb(100, 100, 100));
					String[] object = { WatchActivity.ACTIVE_ALARM,
							String.valueOf(indexAlarm) };
					Message message = new Message();
					message.what = WatchActivity.UPDATE_ALARM;
					message.obj = object;
					WatchActivity.sysManager.getMessageHandlerWatch()
							.sendMessage(message);
				} else {
					arrayAlarm.get(indexAlarm).setActive(true);
					imageAlarmIcon.setBackgroundResource(R.drawable.icon_alarm_visible);
					time.setTextColor(Color.rgb(255, 255, 255));
					String[] object = { WatchActivity.CANCEL_ALARM,
							String.valueOf(indexAlarm) };
					Message message = new Message();
					message.what = WatchActivity.UPDATE_ALARM;
					message.obj = object;
				}
			}
		});
		return view;
	}

	private Context context;
	private ArrayList<AlarmData> arrayAlarm;
	private View view;
	ImageView imageAlarmIcon;
	TextView time;

}

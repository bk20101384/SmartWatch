package com.bkav.ui.stopwatch;

import java.util.ArrayList;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LapAdapter extends ArrayAdapter<String>{

	public LapAdapter(Context context, ArrayList<String> time) {
		super(context,R.layout.lap_icon, time);
		this.context = context;
		this.time = time;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.lap_icon, parent, false);
		TextView nameLap = (TextView) rowView.findViewById(R.id.nameLap);
		TextView timeLap = (TextView) rowView.findViewById(R.id.timeLap);
		Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(context.getAssets());
		if (typeFaceLight != null) {
			nameLap.setTypeface(typeFaceLight);
			timeLap.setTypeface(typeFaceLight);
		}
		nameLap.setText("Lap " + (position + 1));
		timeLap.setText(time.get(position));
		return rowView;
	}

	private Context context;
	private ArrayList<String> time;
}

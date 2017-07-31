package com.bkav.ui.stopwatch;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class LapIcon {
	public View view;

	public LapIcon(Context context, String name, String time) {
		view = View.inflate(context, R.layout.lap_icon, null);
		nameLap = (TextView) view.findViewById(R.id.nameLap);
		timeLap = (TextView) view.findViewById(R.id.timeLap);
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
		Typeface typeFaceBold = WatchActivity.sysManager.getTypeFaceBold(context.getAssets());
		if (typeFaceBold != null && typeFaceRegular != null) {
			nameLap.setTypeface(typeFaceRegular);
			timeLap.setTypeface(typeFaceBold);
		}

		nameLap.setText(name);
		timeLap.setText(time);
	}

	private TextView nameLap;
	private TextView timeLap;
}

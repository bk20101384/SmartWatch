package com.bkav.ui.call;

import java.util.ArrayList;

import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.Call;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class CallAdapter extends ArrayAdapter<Call>{

	public CallAdapter(Context context, int resource, ArrayList<Call> arrayCalls) {
		super(context, resource, arrayCalls);
		this.context = context;
		this.arrayCalls = arrayCalls;    
	}     
 
	@Override   
	public View getView(int position, View convertView, ViewGroup parent) {
		final int indexCall = position;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.call_icon, parent, false); 
		callIcon = (LinearLayout) view.findViewById(R.id.callIcon);
		timeCall = (TextView) view.findViewById(R.id.timeCall);
		nameCall = (TextView) view.findViewById(R.id.nameCall);
		imageCall = (ImageView) view.findViewById(R.id.imageCall); 
		
		callIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ContactDialog.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(WatchActivity.NAME, arrayCalls.get(indexCall).getContactName().getValue());
				intent.putExtra(WatchActivity.NUMBER, arrayCalls.get(indexCall).getNumber().getValue());
				context.startActivity(intent); 
			}
		});
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
	    Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(context.getAssets());
	    if (typeFaceLight != null && typeFaceRegular != null) {
	    	nameCall.setTypeface(typeFaceRegular);
	    	timeCall.setTypeface(typeFaceLight);
	    }
//		Log.e("CallAdapter", "time:" + WatchActivity.sysManager.convertTimeForCall(arrayCalls.get(indexCall).getTime().getValue()));
		timeCall.setText(WatchActivity.sysManager.convertTimeForCall(arrayCalls.get(indexCall).getTime().getValue()));
		nameCall.setText(arrayCalls.get(indexCall).getContactName().getValue());

		if ("OUTGOING".equals(arrayCalls.get(indexCall).getState().getValue())) {
			imageCall.setImageResource(R.drawable.outcall);
		} else if ("INCOMING".equals(arrayCalls.get(indexCall).getState().getValue())) {
			imageCall.setImageResource(R.drawable.incall);
		} else if ("MISSED".equals(arrayCalls.get(indexCall).getState().getValue())) {
			imageCall.setImageResource(R.drawable.misscall);
		}
		return view;
	}
	
	private Context context;
	private View view;
	private ArrayList<Call> arrayCalls;
	private LinearLayout callIcon;
	private TextView timeCall;
	private TextView nameCall;
	private ImageView imageCall;
}

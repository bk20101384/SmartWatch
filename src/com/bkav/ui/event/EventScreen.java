package com.bkav.ui.event;

import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.sms.SmsAdapter;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EventScreen extends Fragment {

	public static final String ALL_SMS = "all_sms";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event, container, false);
		nameBarEvent = (TextView) view.findViewById(R.id.nameBarEvent);
		listEvent = (ListView) view.findViewById(R.id.listEvent);
		adapter = new EventAdapter(getActivity().getApplicationContext(), 0,
				WatchActivity.sysManager.getArrayEvent());
		listEvent.setAdapter(adapter);
		return view;
	}

	public void updateListEvent() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetInvalidated();
			listEvent.invalidateViews();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onStart() { 
		super.onStart();
	}

	private TextView nameBarEvent;
	private EventAdapter adapter;
	private ListView listEvent;
}

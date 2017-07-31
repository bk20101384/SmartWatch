package com.bkav.ui.call;

import com.bkav.ui.sms.SmsAdapter;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CallScreen extends Fragment {

	public static final String ALL_CALL = "all_call";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.call, container, false);
		listCall = (ListView) view.findViewById(R.id.listCall);
		nameCall = (TextView) view.findViewById(R.id.nameBarCall);
		Typeface typeFaceRegular = WatchActivity.sysManager
				.getTypeFaceRegular(getActivity().getAssets());
		if (typeFaceRegular != null) {
			nameCall.setTypeface(typeFaceRegular);
		}

		updateLanguageCall();
		adapter = new CallAdapter(getActivity().getApplicationContext(), 0,
				WatchActivity.sysManager.getArrayCall());
		listCall.setAdapter(adapter);
		return view;
	}

	public void updateLanguageCall() {
		nameCall.setText(WatchActivity.sysManager.getLanguageManager()
				.getLabel(ALL_CALL).getValue());
	}

	public void updateListCall() {
		if (adapter != null) {
			Log.e("CallScreen", "size curr:"
					+ WatchActivity.sysManager.getArrayCall().size());
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetInvalidated();
			listCall.invalidateViews();

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

	private TextView nameCall;
	private CallAdapter adapter;
	private ListView listCall;
}

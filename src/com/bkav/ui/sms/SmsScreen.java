package com.bkav.ui.sms;

import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SmsScreen extends Fragment {

	public static final String ALL_SMS = "all_sms";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sms, container, false);
		nameBarSms = (TextView) view.findViewById(R.id.nameBarSms);
		listSms = (ListView) view.findViewById(R.id.listSms);
		updateLanguageSms();
		adapter = new SmsAdapter(getActivity().getApplicationContext(), 0,
				WatchActivity.sysManager.getArraySms());
		listSms.setAdapter(adapter);
		return view;
	}
 
	public void updateListSms() {
		if (adapter != null) {
			Log.e("SmsScreen", "size curr:" + WatchActivity.sysManager.getArraySms().size()); 
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetInvalidated();
			listSms.invalidateViews();
		}
	}

	public void updateLanguageSms() {
		LanguageManager languageManager = WatchActivity.sysManager
				.getLanguageManager();
		if (languageManager != null) {
			nameBarSms.setText(languageManager.getLabel(ALL_SMS).getValue());
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

	private TextView nameBarSms;
	private SmsAdapter adapter;
	private ListView listSms;
}

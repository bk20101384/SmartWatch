package com.bkav.ui.email;

import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.sms.SmsAdapter;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class EmailScreen extends Fragment {

	public static final String ALL_EMAIL = "all_email";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.email, container, false);
		nameBarEmail = (TextView) view.findViewById(R.id.nameBarEmail);
		listEmail = (ListView) view.findViewById(R.id.listEmail);
		updateLanguageEmail();
		adapter = new EmailAdapter(getActivity().getApplicationContext(), 0, WatchActivity.sysManager.getArrayEmail());
		listEmail.setAdapter(adapter);
		return view;
	}
	
	public void updateLanguageEmail() {
		LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
		if (languageManager != null) {
			nameBarEmail.setText(languageManager.getLabel(ALL_EMAIL).getValue());
		}
	}

	public  void updateListEmail() {
		if (adapter != null) {
			// Log.e("Sms Screen", "notify change sms: " +
			// WatchActivity.sysManager.getArraySms().size());
			// adapter = new SmsAdapter(getActivity().getApplicationContext(),
			// 0, WatchActivity.sysManager.getArraySms());
			// listSms.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetInvalidated();
			listEmail.invalidateViews();
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
	
	private TextView nameBarEmail;  
	private EmailAdapter adapter; 
	private ListView listEmail;
}

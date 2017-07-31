package com.bkav.ui.language;

import java.util.ArrayList;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class LanguageSettingDialog extends Activity {
	public static final int WIDTH = 350;
	public static final int HEIGHT = 300;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(getApplicationContext(), R.layout.language, null);
		RelativeLayout chooseLanguage = (RelativeLayout) view.findViewById(R.id.chooseLanguage);
		ArrayList<Language> languages = WatchActivity.sysManager.getLanguageManager().getLanguages();
		RadioButtonLanguage[] buttonLanguages = new RadioButtonLanguage[languages.size()];
		RadioGroup radioGroup = new RadioGroup(getApplicationContext());
		radioGroup.setOrientation(RadioGroup.VERTICAL);
		radioGroup.setLayoutParams(new LayoutParams(WIDTH, HEIGHT));
		radioGroup.setTag("radioGroup");
		for (int index = 0; index < languages.size(); index++) {
			Language language = languages.get(index);
			buttonLanguages[index] = new RadioButtonLanguage(getApplicationContext(), language);
			radioGroup.addView(buttonLanguages[index]);
			if (language.getIsDefault().equalsIgnoreCase("1")) {
				radioGroup.check(buttonLanguages[index].getId());
			}
		}
		
		chooseLanguage.addView(radioGroup);
		setContentView(view);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButtonLanguage radioButton = (RadioButtonLanguage) group
						.findViewById(checkedId);
				WatchActivity.sysManager.getLanguageManager().updateLanguage(radioButton.getLanguage());
				Message message = new Message();
				message.what = WatchActivity.UPDATE_LANGUAGE;  
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(message);
//				Log.e("", "language:" + WatchActivity.sysManager.getLanguageManager().getLabel("setting").getValue());
				// gá»?i hÃ m update
				onBackPressed();
			}
		});
	}
}

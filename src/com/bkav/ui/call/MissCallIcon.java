package com.bkav.ui.call;

import com.bkav.home.system.Platform;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.CallsManager;
import com.example.bwatchdevice.R;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MissCallIcon {
	public MissCallIcon(Context context) {
		missCallView = View.inflate(context, R.layout.miss_call_icon, null);
		missCall = (TextView) missCallView.findViewById(R.id.missCall);
		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(context.getAssets());
		if (typeFaceRegular != null) {
			missCall.setTypeface(typeFaceRegular);
		}
		updateMissCall();
	}      
 
	public void updateMissCall() {
		CallsManager callManager = (CallsManager) Platform.watch.getElement("calls");
		if (missCall != null) {
			Log.e("MissCallIcon", "misscall: " + callManager.getMissCallCount().getValue());
			missCall.setText(callManager.getMissCallCount().getValue());
		}
	}

	public View getView() {
		return missCallView;
	}

	private View missCallView;
	private TextView missCall;
}

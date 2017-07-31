package com.bkav.ui.notification;

import com.bkav.home.lib.Formatter;
import com.bkav.home.system.Platform;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NotifyDialog extends Activity {

	public static final String ADD = "add";
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(getApplicationContext(), R.layout.notify_dialog, null);
		setContentView(view);
		contentDialogNotify = (TextView) view.findViewById(R.id.contentDialogNotify); 
		iconNotify = (ImageView) view.findViewById(R.id.iconNotify); 
		
		String bitmapString = WatchActivity.sysManager.getBitmapNotify();
		
		Log.e("Notifydialog", "bitmap: " + bitmapString);
		byte bitmapArr[] = Formatter.stringToByteArray(bitmapString);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArr, 0, bitmapArr.length);
		iconNotify.setImageBitmap(bitmap);  
		 
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(getAssets());
		if (typeFaceRegular != null) {
			contentDialogNotify.setTypeface(typeFaceRegular);
		}
		vibrator.vibrate(pattern, -1);

		threadCloseDialog = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) { 
					if (count == 5) {
						vibrator.cancel(); 
						setCount(ADD);
						finish();
					} else {
						setCount(ADD);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		threadCloseDialog.start();

		contentDialogNotify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("notifydialog", "onclick");
				Platform.connection.write("{notify:{NotifyMethod:" + WatchActivity.sysManager.getIdNotify() + "}}");
				setResult(RESULT_OK); 
				finish();
			}
		});
		
		contentDialogNotify.setText(WatchActivity.sysManager.getContentNotify());

	}

	public synchronized void setCount(String state) {
		if (ADD.equals(state)) {
			count++;
		} else {
			count = 0;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		threadCloseDialog.interrupt();
		vibrator.cancel();
//		Log.e("smsdialog", "onDestroy");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		threadCloseDialog.interrupt();
		Log.e("smsdialog", "threadCloseDialog onStop");
	}

	private View view;
	private TextView contentDialogNotify;
	private ImageView iconNotify;
	private Vibrator vibrator;
	private int count = 0;
	private Thread threadCloseDialog;
	private long pattern[] = { 0, 900, 100, 900, 100, 900, 100, 900, 100, 900 };
}

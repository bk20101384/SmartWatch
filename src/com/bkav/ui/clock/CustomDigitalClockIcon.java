package com.bkav.ui.clock;

import java.util.Calendar;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomDigitalClockIcon extends TextView {

	public final static String STYLE_12H = "h:mm aa";
	public final static String STYLE_24H = "k:mm";

	public CustomDigitalClockIcon(Context context) {
		super(context);
		initClock(context);
	}

	public CustomDigitalClockIcon(Context context, AttributeSet attribute) {
		super(context, attribute);
		initClock(context);
	}

	@Override
	protected void onAttachedToWindow() {
		tickerStopped = false;
		super.onAttachedToWindow();
		handler = new Handler();

		ticker = new Runnable() {
			@Override
			public void run() {
				if (tickerStopped) {
					return;
				}
				calendar.setTimeInMillis(System.currentTimeMillis());
				setText(DateFormat.format(format, calendar));
				invalidate();
				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				handler.postAtTime(ticker, next);
			}
		};
		ticker.run();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		tickerStopped = true;
	}

	private FormatChangeObserver formatChangeObserver;
	private Runnable ticker;
	private Handler handler;
	private boolean tickerStopped = false;
	private Calendar calendar;
	private String format;

	private void initClock(Context context) {
		if (calendar == null) {
			calendar = Calendar.getInstance();
		}

		formatChangeObserver = new FormatChangeObserver();
		getContext().getContentResolver().registerContentObserver(
				Settings.System.CONTENT_URI, true, formatChangeObserver);

		setFormat();
	}

	private boolean get24HourMode() {
		// return android.text.format.DateFormat.is24HourFormat(getContext());
		return true;
	}

	private void setFormat() {
		if (get24HourMode()) {
			format = STYLE_24H;
		} else {
			format = STYLE_12H;
		}
	}

	private class FormatChangeObserver extends ContentObserver {
		public FormatChangeObserver() {
			super(new Handler());
		}

		@Override
		public void onChange(boolean selfChange) {
			setFormat();
		}
	}
}

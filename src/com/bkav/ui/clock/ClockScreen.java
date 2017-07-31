package com.bkav.ui.clock;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClockScreen extends Fragment {

	public static final String ENGLISH = "English";
	public static final String VIETNAMESE = "VietNam";
	public static final String ENGLISH_DATE_FORMAT = "E, MMM d";
	public static final String VIETNAMESE_DATE_FORMAT = "E, d MMM";

	public View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.watch, container, false);
		fragmentManager = getFragmentManager();
//		clockStylesAnalog = WatchActivity.sysManager.getClockStylesAnalog();
//		clockStylesDigital1 = WatchActivity.sysManager.getClockStylesDigital1();
//		clockStylesDigital2 = WatchActivity.sysManager.getClockStylesDigital2();
//		clockStylesDigital3 = WatchActivity.sysManager.getClockStylesDigital3();
		clockStylesAnalog = new ClockStylesAnalog();
		clockStylesDigital1 = new ClockStylesDigital(1);
		clockStylesDigital2 = new ClockStylesDigital(2);
		clockStylesDigital3 = new ClockStylesDigital(3);

		if (fragmentManager != null) {
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			currentIndex = WatchActivity.sysManager.getListStyles().get(
					WatchActivity.sysManager.getCurrentStylesScreen());
			switch (Integer.parseInt(currentIndex)) {
			case 0:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commitAllowingStateLoss();
				break;
			case 1:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital1);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commitAllowingStateLoss();
				break;
			case 2:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital2);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commitAllowingStateLoss();
				break;
			case 3:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital3);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commitAllowingStateLoss();
				break;
			default:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commitAllowingStateLoss();
				break;
			}
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public void updateIconState() {
		currentIndex = WatchActivity.sysManager.getListStyles().get(
				WatchActivity.sysManager.getCurrentStylesScreen());
		switch (Integer.parseInt(currentIndex)) {
		case 0:
			Log.e("", "update miss calllllllllll in styles 1");
			clockStylesAnalog.updateIconState();
			break;
		case 1:
			Log.e("", "update miss calllllllllll in styles 2");
			clockStylesDigital1.updateIconState();
			break;
		case 2:
			Log.e("", "update miss calllllllllll in styles 3");
			clockStylesDigital2.updateIconState();
			break;
		case 3:
			Log.e("", "update miss calllllllllll in styles 4");
			clockStylesDigital3.updateIconState();
			break;
		default:
			Log.e("", "update miss calllllllllll in styles 1");
			clockStylesAnalog.updateIconState();
			break;
		}
	}

	public void transactionDefaultClockStyles() {
		if (fragmentManager != null) {
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.drawable.slideinboth,
					R.drawable.slideoutboth);
			switch (Integer.parseInt(WatchActivity.sysManager.getListStyles()
					.get(0))) {
			case 0:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
				break;
			case 1:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital1);
				break;
			case 2:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital2);
				break;
			case 3:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital3);
				break;
			default:
				fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
				break;
			}
			WatchActivity.sysManager
					.setCurrentStylesScreen(Integer.parseInt(WatchActivity.sysManager.getListStyles()
							.get(0)));
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}

	public void transactionUp() {
		int upIndex = WatchActivity.sysManager.getCurrentStylesScreen();
		if (fragmentManager != null) {
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.drawable.slideintop,
					R.drawable.slideouttop);
			if ((WatchActivity.sysManager.getListStyles().size() - 1) == upIndex) {
				upIndex = 0;
				WatchActivity.sysManager.setCurrentStylesScreen(upIndex);
				switch (Integer.parseInt(WatchActivity.sysManager
						.getListStyles().get(upIndex))) {
				case 0:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				case 1:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital1);
					break;
				case 2:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital2);
					break;
				case 3:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital3);
					break;
				default:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				}
			} else {
				upIndex = upIndex + 1;
				WatchActivity.sysManager.setCurrentStylesScreen(upIndex);
				switch (Integer.parseInt(WatchActivity.sysManager
						.getListStyles().get(upIndex))) {
				case 0:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				case 1:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital1);
					break;
				case 2:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital2);
					break;
				case 3:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital3);
					break;
				default:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				}
			}
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}

	public void transactionDown() {
		int downIndex = WatchActivity.sysManager.getCurrentStylesScreen();
		FragmentManager fragmentManager = getFragmentManager();
		if (fragmentManager != null) {
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.drawable.slideinboth,
					R.drawable.slideoutboth);
			if (downIndex == 0) {
				downIndex = WatchActivity.sysManager.getListStyles().size() - 1;
				WatchActivity.sysManager.setCurrentStylesScreen(downIndex);
				switch (Integer.parseInt(WatchActivity.sysManager
						.getListStyles().get(downIndex))) {
				case 0:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				case 1:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital1);
					break;
				case 2:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital2);
					break;
				case 3:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital3);
					break;
				default:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				}
			} else {
				downIndex = downIndex - 1;
				WatchActivity.sysManager.setCurrentStylesScreen(downIndex);
				switch (Integer.parseInt(WatchActivity.sysManager
						.getListStyles().get(downIndex))) {
				case 0:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				case 1:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital1);
					break;
				case 2:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital2);
					break;
				case 3:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesDigital3);
					break;
				default:
					fragmentTransaction.replace(R.id.watchLayout, clockStylesAnalog);
					break;
				}
			}
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}

	private FragmentManager fragmentManager;
	private String currentIndex;
	private ClockStylesAnalog clockStylesAnalog;
	private ClockStylesDigital clockStylesDigital1, clockStylesDigital2, clockStylesDigital3;
}

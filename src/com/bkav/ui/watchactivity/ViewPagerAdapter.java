package com.bkav.ui.watchactivity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	public ViewPagerAdapter(FragmentManager fragmentManager,
			ArrayList<Fragment> arrayFragments) {
		super(fragmentManager);
		this.listFragments = arrayFragments;
	}

	@Override
	public int getCount() {
		return this.listFragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return this.listFragments.get(position);
	}
	
	private List<Fragment> listFragments;
}
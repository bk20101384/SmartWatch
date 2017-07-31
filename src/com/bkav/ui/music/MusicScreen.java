package com.bkav.ui.music;

import com.bkav.home.system.Platform;
import com.example.bwatchdevice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MusicScreen extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.music_control, container, false);
		back = (ImageView) view.findViewById(R.id.backSong);
		next = (ImageView) view.findViewById(R.id.nextSong);
		play = (ImageView) view.findViewById(R.id.playSong);
		
		if (isPlay) {
			play.setImageResource(R.drawable.play);
		} else {
			play.setImageResource(R.drawable.pause);
		}
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Platform.connection.write("{MediaMethod:PREVIOUS}");
			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Platform.connection.write("{MediaMethod:NEXT}");
			}
		});
		
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				if (isPlay) {
					play.setImageResource(R.drawable.pause_button); 
					isPlay = false;
					Platform.connection.write("{MediaMethod:PAUSE}");
				} else { 
					play.setImageResource(R.drawable.play_button);
					isPlay = true;
					Platform.connection.write("{MediaMethod:PLAY}");
				}
			}
		});  
		return view;
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	private boolean isPlay = false;
	private ImageView back;
	private ImageView next;
	private ImageView play;

}

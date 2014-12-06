package com.iuce.diaryandroidproject2;

import com.iuce.services.MusicService;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class MusicFragment extends Fragment {

	private Button btnOpenMusicPlayer;
	private Switch swStartStop;
	private Intent mIntent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_music, container, false);
		swStartStop = (Switch) view.findViewById(R.id.swStartStop);
		btnOpenMusicPlayer = (Button) view.findViewById(R.id.btnMusicPlayer);
		//open background default music
		swStartStop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					initMusicService();
				} else {
					getActivity().stopService(mIntent);
				}
			}
		});
		//open music player
		btnOpenMusicPlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mpIntent = new Intent("android.intent.action.MUSIC_PLAYER");
				startActivity(mpIntent);
			}
		});
		return view;
	}
	//init music service
	private void initMusicService() {
		mIntent = new Intent(getActivity(), MusicService.class);
		getActivity().startService(mIntent);
	}

}

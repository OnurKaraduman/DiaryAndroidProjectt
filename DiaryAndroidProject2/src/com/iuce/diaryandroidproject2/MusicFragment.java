package com.iuce.diaryandroidproject2;

import com.iuce.constants.DBConstants;
import com.iuce.control.ILoginControl;
import com.iuce.control.LoginControl;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class MusicFragment extends Fragment {

	private Button btnOpenMusicPlayer;
	private Button btnChangePassword;
	private EditText txtCurrentPassword;
	private EditText txtNewPassword;

	private EditText txtNewPasswordAgain;
	private Switch swStartStop;
	private Intent mIntent;
	private ILoginControl logControl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_music, container, false);
		logControl = new LoginControl(getActivity());
		swStartStop = (Switch) view.findViewById(R.id.swStartStop);
		btnOpenMusicPlayer = (Button) view.findViewById(R.id.btnMusicPlayer);
		btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
		txtCurrentPassword = (EditText) view.findViewById(R.id.edttxtCurrentPassword);
		txtNewPassword = (EditText) view.findViewById(R.id.edttxtNewPassword);
		txtNewPasswordAgain = (EditText) view.findViewById(R.id.edttxtNewPasswordAgain);
		if (DBConstants.musicPlaying) {
			swStartStop.setChecked(true);
		}
		
		//open background default music
		swStartStop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					initMusicService();
					DBConstants.musicPlaying = true;
				} else {
					try {
						getActivity().stopService(mIntent);
						DBConstants.musicPlaying = false;
					} catch (Exception e) {
						// TODO: handle exception
					}
					
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
		btnChangePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String currentPassword = txtCurrentPassword.getText().toString();
				String newPassword = txtNewPassword.getText().toString();
				String newPasswordAgain = txtNewPasswordAgain.getText().toString();
				if (newPassword.equals(newPasswordAgain)) {
					if (logControl.changePassword(currentPassword, newPasswordAgain)) {
						Toast.makeText(getActivity(), "Succesfullly Changed password", Toast.LENGTH_LONG).show();
						
					}
					else
						Toast.makeText(getActivity(), "Error! couldnt be changed", Toast.LENGTH_LONG).show();;
					
				}
				else
					Toast.makeText(getActivity(), "Error! couldnt be matched password", Toast.LENGTH_LONG).show();;
					
						
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

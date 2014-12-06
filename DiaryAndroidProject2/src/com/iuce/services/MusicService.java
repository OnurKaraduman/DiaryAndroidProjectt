package com.iuce.services;

import java.io.InputStream;

import com.iuce.diaryandroidproject2.R.raw;

import android.R;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {

	private MediaPlayer mediaPlayer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.i("MediaPlayer-------","started");
		initPlayer();
		startPlaying();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopPlaying();
	}

	private void initPlayer(){
		mediaPlayer = new MediaPlayer();
		mediaPlayer = MediaPlayer.create(this, com.iuce.diaryandroidproject2.R.raw.istanbul);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setLooping(true);
	}
	private void startPlaying(){
		Log.i("MediaPlayer-------","started");
		mediaPlayer.start();
		
	}
	private void stopPlaying(){
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
}

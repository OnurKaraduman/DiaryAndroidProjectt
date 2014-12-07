package com.iuce.diaryandroidproject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class AcilisActivity extends Activity {

	private static String TAG = AcilisActivity.class.getName();
	private static long SLEEP_TIME = 4; // Bekletilecek saniye

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Splash ekrandan
															// basligi kaldirir
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // Bilgi cubugunu
																// kaldirir

		setContentView(R.layout.activity_acilisekrani);

		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}

	private class IntentLauncher extends Thread {
		@Override
		public void run() {
			try {
				// Sleeping
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}

			// Start main activity
			Intent intent = new Intent(AcilisActivity.this, LoginActivity.class);
			AcilisActivity.this.startActivity(intent);
			AcilisActivity.this.finish();
		}
	}
}

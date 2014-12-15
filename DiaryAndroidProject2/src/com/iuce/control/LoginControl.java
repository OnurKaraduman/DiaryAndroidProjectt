package com.iuce.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginControl implements ILoginControl {

	private Context context;
	private SharedPreferences preferences;
	private String PASSWORD_TAG = "diaryPassword";

	public LoginControl(Context context) {
		// TODO Auto-generated constructor stub
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		this.context = context;
	}

	@Override
	public boolean controlPassword(String p) {
		// TODO Auto-generated method stub

		// SharedPreferences.Editor editor = preferences.edit();
		String password = preferences.getString(PASSWORD_TAG, null).toString();
		if (password.equals(p)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean resetPassword(String password) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(PASSWORD_TAG, password);
		editor.commit();
		return false;
	}

	@Override
	public boolean savePassword(String password) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(PASSWORD_TAG, password);
		editor.commit();
		return false;
	}

	// if program first opened by user
	@Override
	public boolean controlFirstOpen() {
		// TODO Auto-generated method stub
		try {
			String password = preferences.getString(PASSWORD_TAG, null).toString();
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean changePassword(String currentPassword, String newPassword) {
		// TODO Auto-generated method stub
		String currentPass = preferences.getString(PASSWORD_TAG, null).toString();
		if (currentPass.equals(currentPassword)) {
			resetPassword(newPassword);
			return true;
		}
		return false;
	}

}

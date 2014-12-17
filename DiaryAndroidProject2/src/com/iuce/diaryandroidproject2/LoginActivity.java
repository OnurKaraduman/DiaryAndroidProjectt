package com.iuce.diaryandroidproject2;

import com.iuce.control.ILoginControl;
import com.iuce.control.LoginControl;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button btnLogin;
	private EditText edtTxtPassword;
	private boolean isFirst = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		edtTxtPassword =(EditText) findViewById(R.id.edttxtPincode);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		
		final ILoginControl logControl = new LoginControl(getApplicationContext());
		isFirst = logControl.controlFirstOpen();
		if (isFirst) {
			btnLogin.setText("Save Password");
			
		}
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = edtTxtPassword.getText().toString();
				if (isFirst) {
					logControl.savePassword(password);
					Toast.makeText(getApplicationContext(), "Succefully, saved password", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				}
				else{
					if (logControl.controlPassword(password)) {
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
					}
					else
						Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
					
				}
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
	
}

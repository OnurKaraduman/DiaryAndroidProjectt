package com.iuce.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SendingData {
	Context context;

	public SendingData(Context context) {
		// TODO Auto-generated method stub
		this.context = context;
	}

	public void sendEmail(String subject, String message) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, message);

		email.setType("message/rfc822");
		context.startActivity(Intent.createChooser(email,
				"Choose an Email client"));
	}

	public void sendSms(String body) {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("sms:"));
		smsIntent.putExtra("sms_body", body);
		context.startActivity(smsIntent);
	}
}

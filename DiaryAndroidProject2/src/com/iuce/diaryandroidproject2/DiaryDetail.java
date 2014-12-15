package com.iuce.diaryandroidproject2;

import com.iuce.control.DiaryOperations;
import com.iuce.control.IDiaryOperations;
import com.iuce.entity.Diary;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryDetail extends Fragment {

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	//
	// }
	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_diary_detail);
	// }

	private Button btnSendEmail;
	private Button btnSendSms;
	private int diaryID;
	private IDiaryOperations diaryOperation;
	private TextView txtDiaryDetailTitle;
	private TextView txtDiaryDetailContent;
	private ImageView imgDiaryDetailPhoto;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_diary_detail, container,
				false);
		diaryOperation = new DiaryOperations(getActivity());
		
				
		btnSendEmail = (Button) view.findViewById(R.id.btnSendEmail);
		btnSendSms = (Button) view.findViewById(R.id.btnSendDiarySms);
		txtDiaryDetailTitle = (TextView) view.findViewById(R.id.txtDiaryDetailTitle);
		txtDiaryDetailContent = (TextView) view.findViewById(R.id.txtDiaryDetailContent);
		imgDiaryDetailPhoto = (ImageView) view.findViewById(R.id.imageView1);
		initUpdateForm();
		btnSendEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String to = "onrkrdmn@gmail.com";
				String subject = "ornek";
				String message = "ornek mesaj";

				sendEmail(to, subject, message);
			}
		});
		btnSendSms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// sms send test
				sendSms("onur karaduman");
			}
		});
		return view;
	}
	

	private void initUpdateForm(){
		Bundle b = new Bundle();
		b = getArguments();
		diaryID = b.getInt("id");
		Diary diary = new Diary();
		diary = diaryOperation.getDiaryWithId(diaryID);
		Toast.makeText(getActivity(), diary.getTitle(), Toast.LENGTH_LONG).show();
		
		txtDiaryDetailTitle.setText(diary.getTitle());
		txtDiaryDetailContent.setText(diary.getContent());
		if (diary.getPhotoPath() != null) {
			imgDiaryDetailPhoto.setImageURI(Uri.parse(diary.getPhotoPath()));
		}
		
	}
	
	private void sendEmail(String to, String subject, String message) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, message);

		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email, "Choose an Email client"));
	}

	private void sendSms(String body) {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("sms:"));
		smsIntent.putExtra("sms_body", body);
		startActivity(smsIntent);
	}

}

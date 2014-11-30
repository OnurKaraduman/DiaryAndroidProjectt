package com.iuce.diaryandroidproject2;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

public class AddDiaryActivity extends ActionBarActivity {

	private TextView txtTitle;
	private TextView txtContent;
	private CalendarView calViewNowDate;
	private Button btnSaveDiary;
	private Button btnOpenCamera;
	private Button btnOpenGallery;
	private static final int SELECT_PICTURE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 2;
	private String selectedImagePath;
	String filemanagerstring;

	private ImageView imgView;
	private TextView txtDeneme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diary);
		btnOpenGallery = (Button) findViewById(R.id.btnOpenGallery);
		btnOpenCamera = (Button) findViewById(R.id.btnOpenCamera);
		imgView = (ImageView) findViewById(R.id.imageView1);
		txtDeneme = (TextView) findViewById(R.id.textView1);
		btnOpenGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);
			}
		});
		btnOpenCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent takePictureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(takePictureIntent,
							REQUEST_IMAGE_CAPTURE);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.add_diary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();

				filemanagerstring = selectedImageUri.getPath();

				selectedImagePath = getPath(selectedImageUri);
				imgView.setImageURI(selectedImageUri);
				txtDeneme.setText(selectedImageUri.toString());
				
			} else if (requestCode == REQUEST_IMAGE_CAPTURE) {
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				imgView.setImageBitmap(imageBitmap);

			}
		}
	}

	private String getPath(Uri uri) {
		// TODO Auto-generated method stub
		if (uri == null) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int columnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(columnIndex);

		}
		return null;
	}
}

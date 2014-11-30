package com.iuce.diaryandroidproject2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

public class AddDiaryActivity extends ActionBarActivity {

	private static int MEDIA_TYPE_IMAGE = 1;
	private static int MEDIA_TYPE_VIDEO = 2;
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

				// dispatchTakePictureIntent();
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
				byte[] imageData = convertBitmapToByteArray(imageBitmap);
				onPictureTaken(imageData);
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

	public byte[] convertBitmapToByteArray(Bitmap bmp) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}

	//fotografýn kaydedilecegi dosyayý oluþtur
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		String myPath;
		if (type == MEDIA_TYPE_IMAGE) {
			myPath = mediaStorageDir.getPath() + File.separator + "IMG_"
					+ timeStamp + ".jpg";
			mediaFile = new File(myPath);
		} else if (type == MEDIA_TYPE_VIDEO) {
			myPath = mediaStorageDir.getPath() + File.separator + "VID_"
					+ timeStamp + ".mp4";
			mediaFile = new File(myPath);
		} else {
			return null;
		}

		return mediaFile;
	}

	//fotografý galeriye kaydet
	public void onPictureTaken(byte[] data) {
		// TODO Auto-generated method stub
		File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
		if (pictureFile == null) {
			Log.d("---", "error creating media file, check storage permissions");
			return;

		}
		try {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();

		} catch (FileNotFoundException e) {
			Log.d("", "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d("", "Error accessing file: " + e.getMessage());
		}

	}
}

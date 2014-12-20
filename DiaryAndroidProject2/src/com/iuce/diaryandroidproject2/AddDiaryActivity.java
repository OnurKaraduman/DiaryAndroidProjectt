package com.iuce.diaryandroidproject2;

//yapilacak speech to text
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import com.iuce.control.DiaryOperations;
import com.iuce.entity.Diary;
import com.iuce.services.SendingData;
import com.iuce.services.VoiceRecord;

import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable.Callback;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddDiaryActivity extends Fragment {

	private static int MEDIA_TYPE_IMAGE = 1;
	private static int MEDIA_TYPE_VIDEO = 2;
	private TextView txtTitle;
	private TextView txtContent;
	private CalendarView calViewNowDate;
	private Button btnSaveDiary;
	private Button btnOpenCamera;
	private Button btnOpenGallery;
	private Button btnRecordVoice;
	private Button btnCalendarDay;
	private TextView txtMonthAndYear;
	private Button btnSpeechToText;
	private ImageView imgView;
	private TextView txtDeneme;
	private TextView txtAudioPath;
	private Button btnDeleteAudio;
	private Button btnPlayAudio;
	private Button btnDeleteDiary;
	private Button btnOpenPaint;

	private static final int SELECT_PICTURE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 2;
	private final int FRAGMENT_CODE = 0;
	private int RESULT_SPEECH = 3;
	private boolean isRecord = false;
	private String selectedImagePath;
	String filemanagerstring;

	// voice record manager
	private VoiceRecord vRecord;
	// Location Manager
	private LocationManager locManager;
	private DiaryOperations dOperations;

	private int id;
	private String date;
	private String title;
	private String content;
	private String images;
	private double longitude;
	private double latitude;
	private String photoPath;
	private String audioPath;
	private String months[] = { null, "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	private String audioName;
	private String photoName;
	private boolean isNew = true;
	private boolean isStartedPlaying = false;
	private boolean isUpdate = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		View view = inflater.inflate(R.layout.activity_add_diary, container,
				false);

		vRecord = new VoiceRecord();
		dOperations = new DiaryOperations(getActivity());
		locManager = (LocationManager) getActivity().getSystemService(
				getActivity().LOCATION_SERVICE);
		btnOpenGallery = (Button) view.findViewById(R.id.btnOpenGallery);
		btnOpenCamera = (Button) view.findViewById(R.id.btnOpenCamera);

		imgView = (ImageView) view.findViewById(R.id.imgViewIconHoroscope);
		imgView.setDrawingCacheEnabled(true);

		txtDeneme = (TextView) view.findViewById(R.id.txtDetailHoroscopeTitle);
		btnSpeechToText = (Button) view.findViewById(R.id.btnSpeectToText);
		btnRecordVoice = (Button) view.findViewById(R.id.btnRecordVoice);
		btnCalendarDay = (Button) view.findViewById(R.id.btnCalDay);
		txtMonthAndYear = (TextView) view.findViewById(R.id.txtMonthAndYear);
		btnSaveDiary = (Button) view.findViewById(R.id.btnSaveDiary);
		txtTitle = (EditText) view.findViewById(R.id.edttxtTitleAddDiary);
		txtContent = (EditText) view.findViewById(R.id.edttxtContentAddDiary);
		txtAudioPath = (TextView) view.findViewById(R.id.txtAudioPath);
		btnDeleteAudio = (Button) view.findViewById(R.id.btnDeleteAudio);
		btnPlayAudio = (Button) view.findViewById(R.id.btnPlayAudio);
		btnDeleteDiary = (Button) view.findViewById(R.id.btnAddDiaryDelete);
		btnOpenPaint = (Button) view.findViewById(R.id.btnOpenPaint);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"EngineerHand.ttf");
		txtContent.setTypeface(font);
		btnOpenGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openGallery();
			}
		});
		btnOpenCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openCamera();
				// dispatchTakePictureIntent();
			}
		});
		btnSpeechToText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openRecognizer();
			}
		});
		btnRecordVoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stuba

				onRecord();
			}
		});
		imgView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PhotoFragment photoFrag = new PhotoFragment();
				Bundle b = new Bundle();
				b.putString("photoPath", photoPath);
				photoFrag.setArguments(b);
				FragmentTransaction ft = getActivity().getFragmentManager()
						.beginTransaction();
				ft.add(R.id.content_frame, photoFrag);
				ft.addToBackStack(null);
				ft.commit();

			}
		});
		getCurrentLocation();
		getBundles();
		onEditable();
		btnSaveDiary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// edittext field
				if (txtTitle.getText().toString().trim().equals("")
						|| txtContent.getText().toString().trim().equals("")) {
					Toast.makeText(getActivity(),
							"Enter some text to title or content",
							Toast.LENGTH_LONG).show();
				} else {
					title = txtTitle.getText().toString();
					content = txtContent.getText().toString();
					// eger yeni bir tane gunluk eklenecek ise
					if (isNew) {
						if (saveDiary()) {
							Toast.makeText(getActivity(), "Saved diary",
									Toast.LENGTH_LONG).show();
							txtContent.setEnabled(false);
							txtTitle.setEnabled(false);
							btnSaveDiary.setBackgroundResource(R.drawable.ic_not_editable);
							isUpdate = true;
							isNew = false;
						
						} else
							Toast.makeText(getActivity(),
									"Error! Something wrong", Toast.LENGTH_LONG)
									.show();
					}

					// eger olan bir gunluk guncellenmek istenirse
					else {

						if (isUpdate) {
							if (updateDiary()) {
								Toast.makeText(getActivity(), "Updated diary",
										Toast.LENGTH_LONG).show();
							} else
								Toast.makeText(getActivity(),
										"Error! Something wrong",
										Toast.LENGTH_LONG).show();
							isUpdate = false;
							btnSaveDiary
									.setBackgroundResource(R.drawable.ic_not_editable);

						} else {
							isUpdate = true;
							btnSaveDiary
									.setBackgroundResource(R.drawable.ic_update);

						}
						onEditable();

					}

				}
			}
		});
		btnDeleteAudio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean deleted = vRecord.deleteRecord(audioPath);
				if (deleted) {
					Toast.makeText(getActivity(), "Deleted audio",
							Toast.LENGTH_LONG).show();
					audioPath = null;
					txtAudioPath.setText("No record");
					btnDeleteAudio.setVisibility(View.INVISIBLE);
					btnPlayAudio.setVisibility(View.INVISIBLE);
					// ses kaydýný sildikten sonra yeniden voiceRecord nesnesi
					// oluþturalim
					vRecord = new VoiceRecord();
				} else
					Toast.makeText(getActivity(), "Error! couldnt be deleted",
							Toast.LENGTH_LONG).show();
			}
		});

		btnPlayAudio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onStartPlayAudio();
			}
		});
		btnDeleteDiary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onDelete();
			}
		});
		btnOpenPaint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PaintFragment pFrag = new PaintFragment();
				FragmentManager fm = getActivity().getFragmentManager();

				pFrag.setTargetFragment(AddDiaryActivity.this, FRAGMENT_CODE);
				FragmentTransaction ft = getActivity().getFragmentManager()
						.beginTransaction();
				ft.add(R.id.content_frame, pFrag);
				ft.addToBackStack(null);
				ft.commit();

			}
		});
		return view;
	}

	private boolean saveDiary() {
		Diary d = new Diary();
		d.setDate(date);
		d.setTitle(title);
		d.setContent(content);
		d.setLatitude(latitude);
		d.setLongitude(longitude);
		d.setAudioPath(audioPath);
		d.setPhotoPath(photoPath);
		return dOperations.addDiary(d);
	}

	private boolean updateDiary() {
		Diary d = new Diary();
		d.setDate(date);
		d.setTitle(title);
		d.setContent(content);
		d.setLatitude(latitude);
		d.setLongitude(longitude);
		d.setAudioPath(audioPath);
		d.setPhotoPath(photoPath);
		return dOperations.updateDiary(id, d);
	}

	String day;
	int month;
	String year;

	private void getBundles() {

		Bundle bundle = this.getArguments();
		boolean isNew = bundle.getBoolean("isNew");
		this.isNew = isNew;
		if (isNew) {
			day = bundle.getString("day");
			month = Integer.parseInt(bundle.getString("month"));
			year = bundle.getString("year");
			date = day + "." + month + "." + year;
			txtMonthAndYear.setText(months[month] + " " + year);
			btnCalendarDay.setText(day);
			btnSaveDiary.setBackgroundResource(R.drawable.ic_add_diary2);
		} else {
			id = bundle.getInt("id");
			initUIelementWithId(id, bundle);
		}
		txtMonthAndYear.setText(months[month] + " " + year);
		btnCalendarDay.setText(day);

	}

	private void initUIelementWithId(int id, Bundle bundle) {

		Diary diary = dOperations.getDiaryWithId(id);
		date = diary.getDate();
		photoPath = diary.getPhotoPath();
		audioPath = diary.getAudioPath();
		title = diary.getTitle();
		content = diary.getContent();
		String[] dateArray = date.split(Pattern.quote("."));
		day = dateArray[0];
		month = Integer.parseInt(dateArray[1]);
		year = dateArray[2];
		txtTitle.setText(title);
		txtContent.setText(content);
		btnSaveDiary.setBackgroundResource(R.drawable.ic_not_editable);
		if (photoPath != null) {
			imgView.setImageURI(Uri.parse(photoPath));
			imgView.setVisibility(View.VISIBLE);
		}
		if (audioPath != null) {
			txtAudioPath.setText("1 record");
			btnDeleteAudio.setVisibility(View.VISIBLE);
			btnPlayAudio.setVisibility(View.VISIBLE);
		}
	}

	private void onDelete() {
		AlertDialog.Builder deleteDialog = new AlertDialog.Builder(
				getActivity());
		deleteDialog.setTitle("Delete?");
		deleteDialog.setMessage("Are you sure?");
		deleteDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						boolean deleted = dOperations.removeDiary(id);
						if (deleted) {
							Toast.makeText(getActivity(),
									"Diary has been deleted", Toast.LENGTH_LONG)
									.show();
							ListDiaryActivity listDiary = new ListDiaryActivity();
							FragmentTransaction ft = getActivity()
									.getFragmentManager().beginTransaction();
							ft.replace(R.id.content_frame, listDiary);
							ft.commit();
						} else
							Toast.makeText(getActivity(),
									"Error! couldnt be deleted",
									Toast.LENGTH_LONG).show();
					}
				});
		deleteDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		deleteDialog.show();
	}

	public void openGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				SELECT_PICTURE);
	}

	public void openCamera() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent
				.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}

	}

	public void openRecognizer() {
		Intent intRecognize = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intRecognize.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intRecognize.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Speech recognition demo");
		startActivityForResult(intRecognize, RESULT_SPEECH);
	}

	public void onStartPlayAudio() {
		if (isStartedPlaying) {
			vRecord.stopPlaying();
			isStartedPlaying = false;
			btnPlayAudio.setBackgroundResource(R.drawable.ic_play);
		} else {
			
			vRecord.startPlaying(audioPath, this);
			isStartedPlaying = true;
			btnPlayAudio.setBackgroundResource(R.drawable.ic_stop_play);
			
		}
	}
	public void btnPlayStopImage(){
		btnPlayAudio.setBackgroundResource(R.drawable.ic_play);
		isStartedPlaying = false;
	}

	public void onRecord() {
		if (btnDeleteAudio.getVisibility() == View.INVISIBLE) {
			if (isRecord) {
				isRecord = false;
				btnRecordVoice
						.setBackgroundResource(R.drawable.ic_btn_audio_record);
				txtAudioPath.setText("1 record saved");
				btnDeleteAudio.setVisibility(View.VISIBLE);
				btnPlayAudio.setVisibility(View.VISIBLE);

			} else {
				isRecord = true;
				btnRecordVoice
						.setBackgroundResource(R.drawable.ic_btn_audio_record_start);
				txtAudioPath.setText("recording.....");

			}
			audioPath = vRecord.onRecord(isRecord);
		} else
			Toast.makeText(getActivity(), "Just one record please",
					Toast.LENGTH_LONG).show();

	}

	// take current location and assing to text of txtDeneme
	public void getCurrentLocation() {
		LocationListener mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				// txtDeneme.setText(String.valueOf(location.getLatitude()));
				longitude = location.getLongitude();
				latitude = location.getLatitude();
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
				10, mLocationListener);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {

				Uri selectedImageU = data.getData();
				System.out.println("-----" + selectedImageU.toString());
				String pathImage = getRealPathFromURI(selectedImageU);
				photoPath = pathImage;
				System.out.println("--------" + pathImage);
				imgView.setImageURI(Uri.parse(photoPath));
				imgView.setVisibility(View.VISIBLE);

			} else if (requestCode == REQUEST_IMAGE_CAPTURE) {
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				byte[] imageData = convertBitmapToByteArray(imageBitmap);
				onPictureTaken(imageData);
				imgView.setImageBitmap(imageBitmap);
				imgView.setVisibility(View.VISIBLE);

			} else if (requestCode == RESULT_SPEECH) {
				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				// set text field with return value
				txtContent.setText(text.get(0));

			} else if (requestCode == FRAGMENT_CODE
					&& resultCode == Activity.RESULT_OK) {
				if (data.getStringExtra("PhotoPath") != null) {
					photoPath = data.getStringExtra("PhotoPath");
					imgView.setImageURI(Uri.parse(photoPath));
					imgView.setVisibility(View.VISIBLE);
				}
				
			}
		}
	}

	// gerçek resim yolunu uriden yola çeviriyoruz
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String result = null;

		// CursorLoader cursorLoader = new CursorLoader(
		// null);
		CursorLoader cursorLoader = new CursorLoader(getActivity(), contentUri,
				proj, null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();

		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		}

		return result;
	}

	public byte[] convertBitmapToByteArray(Bitmap bmp) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}

	// fotografýn kaydedilecegi dosyayý oluþtur
	private File getOutputMediaFile(int type) {
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
		// photopath to database
		photoName = File.separator + "IMG_" + timeStamp + ".jpg";
		photoPath = myPath;
		return mediaFile;
	}

	// fotografý galeriye kaydet
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		// menu.clear();
		inflater.inflate(R.menu.add_diary_fragment_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SendingData sendData = new SendingData(getActivity());
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.itemSendEmail:
			sendData.sendEmail(date, txtContent.getText().toString());
			break;
		case R.id.itemSendSms:
			sendData.sendSms(txtContent.getText().toString());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onEditable() {
		if (isNew || isUpdate) {
			txtTitle.setEnabled(true);
			txtContent.setEnabled(true);
		} else {
			txtTitle.setEnabled(false);
			txtContent.setEnabled(false);
		}

	}

}

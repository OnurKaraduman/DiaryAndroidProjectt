package com.iuce.diaryandroidproject2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PaintFragment extends Fragment implements OnClickListener {

	private float smallBrush, mediumBrush, largeBrush;
	private DrawingView drawView;
	private ImageButton currPaint, drawBtn, saveBtn;;
	private ImageView imgFoto;
	private int MEDIA_TYPE_IMAGE = 1;
	private int MEDIA_TYPE_VIDEO = 2;

	private String photoPath;
	private Button btnExit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_paint, container, false);
		getActivity().getActionBar().setIcon(R.drawable.firca);
		drawView = (DrawingView) view.findViewById(R.id.drawingView1);
		LinearLayout paintLayout = (LinearLayout) view
				.findViewById(R.id.paint_colors);
		currPaint = (ImageButton) paintLayout.getChildAt(0);
		currPaint.setImageDrawable(getResources().getDrawable(
				R.drawable.paint_pressed));
		drawView.setDrawingCacheEnabled(true);
		drawBtn = (ImageButton) view.findViewById(R.id.draw_btn);
		drawBtn.setOnClickListener(this);

		// Sİlgi boyutlarını atıyoruz...
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);
		imgFoto = (ImageView) view.findViewById(R.id.imgFoto);
		saveBtn = (ImageButton) view.findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(this);
		btnExit = (Button) view.findViewById(R.id.btnExitPaint);
		btnExit.setOnClickListener(this);
		initImageButtonColor(view);
		return view;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.draw_btn) {
			final Dialog brushDialog = new Dialog(getActivity());
			brushDialog.setTitle("Silgi Boyutu:");
			brushDialog.setContentView(R.layout.brush_chooser);
			ImageButton smallBtn = (ImageButton) brushDialog
					.findViewById(R.id.small_brush);
			smallBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(smallBrush);
					drawView.setLastBrushSize(smallBrush);
					brushDialog.dismiss();
				}
			});
			ImageButton mediumBtn = (ImageButton) brushDialog
					.findViewById(R.id.medium_brush);
			mediumBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(mediumBrush);
					drawView.setLastBrushSize(mediumBrush);
					brushDialog.dismiss();
				}
			});

			ImageButton largeBtn = (ImageButton) brushDialog
					.findViewById(R.id.large_brush);
			largeBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(largeBrush);
					drawView.setLastBrushSize(largeBrush);
					brushDialog.dismiss();
				}
			});
			brushDialog.show();
			drawView.setBrushSize(mediumBrush);
		} else if (view.getId() == R.id.save_btn) {
			// save drawing

			AlertDialog.Builder saveDialog = new AlertDialog.Builder(
					getActivity());
			saveDialog.setTitle("Kaydet?");
			saveDialog.setMessage("Galeriye kayıt edilsin mi?");
			saveDialog.setPositiveButton("Evet",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							byte[] data = convertBitmapToByteArray(drawView
									.getDrawingCache());

							onPictureTaken(data);

							Intent intent = new Intent();
							intent.putExtra("PhotoPath", photoPath);
							getTargetFragment().onActivityResult(
									getTargetRequestCode(), Activity.RESULT_OK,
									intent);
							getFragmentManager().popBackStack();
						}
					});
			saveDialog.setNegativeButton("İptal",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			saveDialog.show();
		} else if (view.getId() == R.id.btnExitPaint) {
			getActivity().getFragmentManager().popBackStack();
		}

	}

	public void initImageButtonColor(View view) {
		ImageButton imgBtn1 = (ImageButton) view.findViewById(R.id.imgBtn1);
		ImageButton imgBtn2 = (ImageButton) view.findViewById(R.id.imgBtn2);
		ImageButton imgBtn3 = (ImageButton) view.findViewById(R.id.imgBtn3);
		ImageButton imgBtn4 = (ImageButton) view.findViewById(R.id.imgBtn4);
		ImageButton imgBtn5 = (ImageButton) view.findViewById(R.id.imgBtn5);
		ImageButton imgBtn6 = (ImageButton) view.findViewById(R.id.imgBtn6);
		ImageButton imgBtn7 = (ImageButton) view.findViewById(R.id.imgBtn7);
		ImageButton imgBtn8 = (ImageButton) view.findViewById(R.id.imgBtn8);
		imgBtn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
		imgBtn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paintClicked(v);
			}
		});
	}

	public void paintClicked(View view) {

		ImageButton imgView = (ImageButton) view;
		String color = view.getTag().toString();
		drawView.setColor(color);
		imgView.setImageDrawable(getResources().getDrawable(
				R.drawable.paint_pressed));
		currPaint
				.setImageDrawable(getResources().getDrawable(R.drawable.paint));
		currPaint = (ImageButton) view;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public byte[] convertBitmapToByteArray(Bitmap bmp) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
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
		System.out.println("---------" + myPath);
		photoPath = myPath;
		return mediaFile;
	}
}

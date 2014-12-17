package com.iuce.diaryandroidproject2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PhotoFragment extends Fragment{

	private ImageView imgView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_image_view, container, false);
		imgView = (ImageView) view.findViewById(R.id.imageView1);
		Bundle b = getArguments();
		String photoPath = b.getString("photoPath");
		
		imgView.setImageURI(Uri.parse(photoPath));
		imgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
						
				FragmentManager fManager = getActivity().getFragmentManager();
				fManager.popBackStackImmediate();
			}
		});
		return view;
	}

	
}

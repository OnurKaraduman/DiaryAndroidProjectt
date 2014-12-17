package com.iuce.adapters;

import java.util.List;

import com.iuce.diaryandroidproject2.HoroscopDetailActivity;
import com.iuce.diaryandroidproject2.HoroscopeFragment;
import com.iuce.diaryandroidproject2.MainActivity;
import com.iuce.diaryandroidproject2.R;
import com.iuce.entity.Horoscope;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HoroscopeListAdapter extends ArrayAdapter<Horoscope> {

	Context context;
	TypedArray horoscopIcons;
	public HoroscopeListAdapter(Context context, int resource,
			List<Horoscope> objects) {
		super(context, resource, objects);
		this.context = context;
		// TODO Auto-generated constructor stub
		horoscopIcons = context.getResources().obtainTypedArray(R.array.horoscope_icons);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//MainActivity context = new MainActivity();
		// TODO Auto-generated method stub
		final FragmentTransaction ft = ((Activity) context).getFragmentManager()
				.beginTransaction();

		final Horoscope horoscope = getItem(position);
		View myRow = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myRow = inflater.inflate(R.layout.horoscope_list_row, null);
		} else {
			myRow = convertView;
		}
		TextView txtTitle = (TextView) myRow
				.findViewById(R.id.txtHoroscopeTitle);
		TextView txtDescription = (TextView) myRow.findViewById(R.id.txtHoroscopeDescription);
		ImageView imgViewIcHoroscop = (ImageView) myRow.findViewById(R.id.imgViewIconHoroscope);
		imgViewIcHoroscop.setImageResource(horoscopIcons.getResourceId(position, -1));
		Typeface font = Typeface.createFromAsset(((Activity)context).getAssets(),
				"EngineerHand.ttf");
		txtTitle.setTypeface(font);
		txtTitle.setText(horoscope.getTitle());
		txtDescription.setText(horoscope.getDescription());
		return myRow;
	}

}

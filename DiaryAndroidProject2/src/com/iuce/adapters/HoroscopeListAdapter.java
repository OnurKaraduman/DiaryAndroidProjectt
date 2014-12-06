package com.iuce.adapters;

import java.util.List;

import com.iuce.diaryandroidproject2.R;
import com.iuce.entity.Horoscope;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class HoroscopeListAdapter extends ArrayAdapter<Horoscope>{

	public HoroscopeListAdapter(Context context, int resource,
			List<Horoscope> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Horoscope horoscope = getItem(position);
		View myRow = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myRow = inflater.inflate(R.layout.horoscope_list_row, null);
		}
		else{
			myRow = convertView;
		}
		TextView txtTitle = (TextView) myRow.findViewById(R.id.txtHoroscopeTitle);
		Button btnDetail = (Button) myRow.findViewById(R.id.btnHoroscopeDetail);
		txtTitle.setText(horoscope.getTitle());
		return myRow;
	}
	

}

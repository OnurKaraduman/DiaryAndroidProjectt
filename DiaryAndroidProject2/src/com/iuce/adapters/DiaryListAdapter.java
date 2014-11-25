package com.iuce.adapters;

import java.util.List;

import com.iuce.entity.Diary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DiaryListAdapter extends ArrayAdapter<Diary> {

	
	public DiaryListAdapter(Context context, int resource, List<Diary> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}
	

}

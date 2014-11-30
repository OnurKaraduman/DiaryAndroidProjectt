package com.iuce.adapters;

import java.util.List;

import com.iuce.diaryandroidproject2.R;
import com.iuce.entity.Diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DiaryListAdapter extends ArrayAdapter<Diary> {

	public DiaryListAdapter(Context context, int resource, List<Diary> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Diary diary = getItem(position);
		View myRow = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myRow = inflater.inflate(R.layout.diary_list_row, null);
		}
		else{
			myRow = convertView;
			
		}
		TextView txtDay = (TextView) myRow.findViewById(R.id.txtDay);
		TextView txtMouth = (TextView) myRow.findViewById(R.id.txtMouth);
		TextView txtTitle = (TextView) myRow.findViewById(R.id.txtTitle);
		TextView txtContent = (TextView) myRow.findViewById(R.id.txtContent);
		Button btnDetail = (Button) myRow.findViewById(R.id.btnDetail);
		
		txtTitle.setText(diary.getTitle());
		txtContent.setText(diary.getContent());
		return myRow;
	}

}

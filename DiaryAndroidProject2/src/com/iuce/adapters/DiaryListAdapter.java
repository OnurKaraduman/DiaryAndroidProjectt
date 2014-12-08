package com.iuce.adapters;

import java.util.List;
import java.util.regex.Pattern;

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
	private String months[] = { null, "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
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
		TextView txtTitle = (TextView) myRow.findViewById(R.id.txtDiaryListTitle);
		TextView txtMonthYear = (TextView) myRow.findViewById(R.id.txtDiaryListMonthYear);
		Button btnDetail = (Button) myRow.findViewById(R.id.btnDiaryListDetail);
		Button btnDay = (Button) myRow.findViewById(R.id.btnDiaryListDay);
		String txtDate =  diary.getDate();
		String[] date =txtDate.split(Pattern.quote("."));
		btnDay.setText(date[0]);
		int month = Integer.parseInt(date[1]);
		txtMonthYear.setText(months[month]+" "+date[2]);
		txtTitle.setText(diary.getTitle());
		return myRow;
	}

}

package com.iuce.diaryandroidproject2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class DateSelectFragment extends Fragment{

	
	private CalendarView calenderView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_date_selector, container,false);
		calenderView = (CalendarView) view.findViewById(R.id.calendarView1);
		calenderView.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				AddDiaryActivity addDiaryFragment = new AddDiaryActivity();
				getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, addDiaryFragment).commit();
			}
			
		});
		return view;
	}

}

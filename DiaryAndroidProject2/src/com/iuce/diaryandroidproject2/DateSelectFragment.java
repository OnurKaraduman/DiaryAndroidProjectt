package com.iuce.diaryandroidproject2;

import com.iuce.control.DiaryOperations;
import com.iuce.control.IDiaryOperations;
import com.iuce.entity.Diary;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class DateSelectFragment extends Fragment {

	private CalendarView calenderView;
	private IDiaryOperations diaryOperations;
	private ProgressDialog proDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_date_selector,
				container, false);
		getActivity().getActionBar().setIcon(R.drawable.ic_calendar);
		proDialog = new ProgressDialog(getActivity());
		diaryOperations = new DiaryOperations(getActivity());
		calenderView = (CalendarView) view.findViewById(R.id.calendarView1);
		calenderView.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				// TODO Auto-generated method stub
				proDialog.show();
				Fragment addDiaryFragment = new AddDiaryActivity();
				Bundle bundle = new Bundle();
				String day1 = String.valueOf(dayOfMonth);
				String month1 = String.valueOf(month);
				String year1 = String.valueOf(year);
				addDiaryFragment.setArguments(bundle);
				FragmentTransaction ft = getActivity().getFragmentManager()
						.beginTransaction();
				Diary diary = diaryOperations.getDiaryWithDate(day1 + "."
						+ month1 + "." + year1);
				if (diary == null) {
					// secilen tarihi fragmenta gonder
					bundle.putString("day", day1);
					bundle.putString("month", month1);
					bundle.putString("year", year1);
					bundle.putBoolean("isNew", true);
					proDialog.dismiss();
				
				} else {

					// secilen tarihte eger diary var ise duzenleme sayfasýna bu
					// diary ile ilgili ozellileri gonder
					Bundle b = new Bundle();
					bundle.putBoolean("isNew", false);
					b.putInt("id", diary.getId());
					addDiaryFragment.setArguments(b);

				}
				proDialog.dismiss();
				ft.replace(R.id.content_frame, addDiaryFragment).commit();


			}

		});
		return view;
	}

}

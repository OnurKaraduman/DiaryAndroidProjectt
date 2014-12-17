package com.iuce.diaryandroidproject2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.iuce.diaryandroidproject2.R;
import com.iuce.adapters.DiaryListAdapter;
import com.iuce.control.DiaryOperations;
import com.iuce.control.IDiaryOperations;
import com.iuce.entity.Diary;

import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListDiaryActivity extends Fragment {

	private IDiaryOperations diaryOperation;
	private ListView listDiary;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.activity_list_diary, container,false);
		
		diaryOperation = new DiaryOperations(getActivity());
		listDiary = (ListView) view.findViewById(R.id.listDiary);
		listDiary.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AddDiaryActivity addDiaryAct = new AddDiaryActivity();
				Bundle bundle = new Bundle();
				bundle.putInt("id", Integer.parseInt(((TextView)view.findViewById(R.id.txtDiaryListId)).getText().toString()));
				bundle.putBoolean("isNew", false);
				addDiaryAct.setArguments(bundle);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.content_frame, addDiaryAct);
				ft.commit();
			}
          
        });
		Diary d = new Diary();
//		d.setTitle("denme");
//		d.setContent("ilk deneme");
//		d.setLatitude(34.0);
//		d.setLongitude(23.3);
//		
//		
//        d.setDate("11.12.2014");
//		boolean result = diaryOperation.addDiary(d);
//		if (result) {
//			Toast.makeText(this, "Eklendi", Toast.LENGTH_LONG).show();
//		}
//		else
//			Toast.makeText(this, "hata", Toast.LENGTH_LONG).show();
		List<Diary> diaries = diaryOperation.listDiary();
		DiaryListAdapter diaryAdapter = new DiaryListAdapter(getActivity(), R.layout.diary_list_row, diaries);
		listDiary.setAdapter(diaryAdapter);
		return view;
	}

	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
}

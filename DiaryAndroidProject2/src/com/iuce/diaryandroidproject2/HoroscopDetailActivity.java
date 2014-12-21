package com.iuce.diaryandroidproject2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class HoroscopDetailActivity extends Fragment {

	private TextView txtTitle;
	private WebView webView;
	private Button btnBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_horoscop_detail,
				container, false);
		getActivity().getActionBar().setIcon(R.drawable.ic_horoscope);
		txtTitle = (TextView) view.findViewById(R.id.txtDetailHoroscopeTitle);
		webView = (WebView) view.findViewById(R.id.webView1);
		Bundle bundle = this.getArguments();
		txtTitle.setText(bundle.getString("title"));
//		webView.loadUrl("http://onurkaraduman.com");
		btnBack = (Button) view.findViewById(R.id.btnHoroscopeBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().getFragmentManager().popBackStack();
			}
		});
	
		webView.loadData((bundle.getString("description")), "text/html", "utf-8");
//		btnExit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//fragmenti tekrardan stackten sil
//				getActivity().getFragmentManager().beginTransaction().remove(HoroscopDetailActivity.this).commit();
//			}
//		});
		
		return view;
	}

}

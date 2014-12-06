package com.iuce.diaryandroidproject2;



import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitle = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ListDiaryActivity fragementList = new ListDiaryActivity();
		ft.add(R.id.content_frame, fragementList);
		ft.commit();
		mTitle = "Diary Project";
		getActionBar().setTitle(mTitle);
		mDrawerLayout = (DrawerLayout )findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		// iconu ve a��l�p kapand���nda g�r�necek texti veriyoruz.
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			// drawer kapat�ld���nda tetiklenen method
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			// drawer a��ld���nda tetiklenen method
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Navigation Drawer GY");
				invalidateOptionsMenu();
			}

		};
		// A��l�p kapanmay� dinlemek i�in register
				mDrawerLayout.setDrawerListener(mDrawerToggle);

				// Navigationdaki Drawer i�in listview adapteri
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), 
						R.layout.drawer_lit_item, getResources().getStringArray(R.array.leftMenu));

				// adapteri listviewe set ediyoruz
				mDrawerList.setAdapter(adapter);

				// actionbar home butonunu aktif ediyoruz
				getActionBar().setHomeButtonEnabled(true);

				// navigationu t�klanabilir hale getiriyoruz
				getActionBar().setDisplayHomeAsUpEnabled(true);

				// sol slider a��ld���nda gelen listviewin t�klama eventi
				mDrawerList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// itemleri arraya tekrar ald�k
						String[] menuItems = getResources().getStringArray(R.array.leftMenu);

						// dinamik title yapmak i�in actionbarda t�klanan�n titlesi g�r�necek
						mTitle = menuItems[position];
						
						FragmentManager fragmentManager = getFragmentManager();
						FragmentTransaction ft = fragmentManager.beginTransaction();
						if (position == 0) {
							AddDiaryActivity fragmentAddDiary = new AddDiaryActivity();
							ft.replace(R.id.content_frame, fragmentAddDiary);
							ft.commit();
							
						}
						else if (position == 1) {
							ListDiaryActivity fragmentListDiary = new ListDiaryActivity();
							ft.replace(R.id.content_frame, fragmentListDiary);
							ft.commit();
//							DenemeFragment df = new DenemeFragment();
//							ft.replace(R.id.content_frame, df);
//							ft.commit();
						}
						else if (position == 2) {
							MusicFragment musicFragment = new MusicFragment();
							ft.replace(R.id.content_frame, musicFragment);
							ft.commit();
						}
						// fragmenti contente yerle�tirme.
					
					
						
						// draweri kapat
						mDrawerLayout.closeDrawer(mDrawerList);

					}
				});
		
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//draweri sadece swipe ederek a�ma yerine sol tepedeki butona basarak a�mak i�in
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// navigationDrawer a��ld���nda ayarlar� gizlemek i�in
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

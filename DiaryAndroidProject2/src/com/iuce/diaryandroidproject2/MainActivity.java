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
		mTitle = "gelecegi yazanlar";
		getActionBar().setTitle(mTitle);
		mDrawerLayout = (DrawerLayout )findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		// iconu ve açýlýp kapandýðýnda görünecek texti veriyoruz.
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			// drawer kapatýldýðýnda tetiklenen method
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			// drawer açýldýðýnda tetiklenen method
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Navigation Drawer GY");
				invalidateOptionsMenu();
			}

		};
		// Açýlýp kapanmayý dinlemek için register
				mDrawerLayout.setDrawerListener(mDrawerToggle);

				// Navigationdaki Drawer için listview adapteri
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), 
						R.layout.drawer_lit_item, getResources().getStringArray(R.array.leftMenu));

				// adapteri listviewe set ediyoruz
				mDrawerList.setAdapter(adapter);

				// actionbar home butonunu aktif ediyoruz
				getActionBar().setHomeButtonEnabled(true);

				// navigationu týklanabilir hale getiriyoruz
				getActionBar().setDisplayHomeAsUpEnabled(true);

				// sol slider açýldýðýnda gelen listviewin týklama eventi
				mDrawerList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// itemleri arraya tekrar aldýk
						String[] menuItems = getResources().getStringArray(R.array.leftMenu);

						// dinamik title yapmak için actionbarda týklananýn titlesi görünecek
						mTitle = menuItems[position];
						
						FragmentManager fragmentManager = getFragmentManager();
						FragmentTransaction ft = fragmentManager.beginTransaction();

						// fragmenti contente yerleþtirme.
					
					
						
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
		//draweri sadece swipe ederek açma yerine sol tepedeki butona basarak açmak için
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// navigationDrawer açýldýðýnda ayarlarý gizlemek için
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

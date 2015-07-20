package com.asiantech.mp3pro.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asiantech.mp3pro.adapter.ItemAdapter;
import com.asiantech.mp3pro.bean.ItemNavigation;
import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.controls.Controls;
import com.asiantech.mp3pro.fragment.FragmentPlaybar;
import com.asiantech.mp3pro.fragment.MainFragment;
import com.asiantech.mp3pro.fragment.MainFragment.onItemClick;
import com.asiantech.mp3pro.fragment.MyMusicFragment;
import com.asiantech.mp3pro.fragment.MyMusicFragment.onMyItemClick;
import com.asiantech.mp3pro.service.ServicePlayMusic;
import com.asiantech.mp3pro.utils.Constants;
import com.asiantech.mp3pro.utils.Utilities;
import com.example.mp3pro.R;

@SuppressWarnings("deprecation")
@SuppressLint({ "NewApi", "CutPasteId" })
public class MainActivity extends ActionBarActivity {
	// public static LinearLayout mPlayMusic;
	NotificationManager mNotificationManager;
	private DrawerLayout mDrawerLayout;
	private FrameLayout layout_play;
	private MainFragment mainFragMent;
	private FragmentPlaybar fragmentPlay;
	private ListView mleftList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ArrayList<ItemNavigation> arrLeft;
	String[] mListMenu = { "", "Bài Hát", "Album", "Nghệ sỹ", "Playlist",
			"Thư mục", "Download", "", "Yêu thích", "Playlist", "Nhạc upload" };
	int[] mDrawable = { 0, R.drawable.ic_mm_song, R.drawable.ic_album,
			R.drawable.ic_artist, R.drawable.ic_playlist_off,
			R.drawable.ic_folder, R.drawable.ic_mm_download, 0,
			R.drawable.ic_fav_active, R.drawable.ic_playlist_on,
			R.drawable.ic_upload };
	ArrayList<com.asiantech.mp3pro.bean.Menu> listMenu;
	private static ImageView btn_prev, btn_play, btn_next, btn_pause;
	private static TextView mSongName, mTacGia;
	private RelativeLayout playMusic;

	private boolean OncePress = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		// overridePendingTransition(R.drawable.stay, R.drawable.stay);

		initData();
	}

	@Override
	protected void onResume() {
		if (Utilities.checkServiceRunning(ServicePlayMusic.class,
				getApplicationContext())) {
			layout_play.setVisibility(View.VISIBLE);
			FragmentPlaybar.updateUI();
		} else {
			layout_play.setVisibility(RelativeLayout.GONE);

		}
		super.onResume();

	}

	/**
	 * init dữ liệu
	 */
	public void initData() {
		Utilities.scanSdcard(this);
		playMusic = (RelativeLayout) findViewById(R.id.rlPlayMusic);

		btn_next = (ImageView) findViewById(R.id.imgNextButton);
		btn_prev = (ImageView) findViewById(R.id.imgPrevious);
		btn_play = (ImageView) findViewById(R.id.imgPlayButton);
		btn_pause = (ImageView) findViewById(R.id.imgPauseButton);
		mSongName = (TextView) findViewById(R.id.txtSongName);
		mTacGia = (TextView) findViewById(R.id.txtTacGia);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// Drawable background = mDrawerLayout.getBackground();
		// background.setAlpha(255);
		mleftList = (ListView) findViewById(R.id.lvLeftDrawer);
		// layout_play = (RelativeLayout) findViewById(R.id.layout_play);
		// play_music = (RelativeLayout)
		// findViewById(R.id.rlButtonConTrolMusic);
		layout_play = (FrameLayout) findViewById(R.id.layout_playmusic);
		fragmentPlay = new FragmentPlaybar();
		FragmentTransaction transtraction = getSupportFragmentManager()
				.beginTransaction();
		transtraction.replace(R.id.layout_playmusic, fragmentPlay);
		transtraction.addToBackStack("fragment");
		transtraction.commit();

		initArrayForListViewDrawer();
		ItemAdapter adapterLeft = new ItemAdapter(this, arrLeft);
		mleftList.setAdapter(adapterLeft);

		mainFragMent = new MainFragment();
		replaceFragment(mainFragMent, "main");
		// layout_play.setVisibility(View.VISIBLE);
		setOnClickPlayTabBar();

	}

	@Override
	protected void onDestroy() {
		Log.d("destroy", "destroy");
		super.onDestroy();
	}

	// Them du lieu vao array
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initArrayForListViewDrawer() {
		arrLeft = new ArrayList();

		ItemNavigation ItemNavigation1 = new ItemNavigation(
				R.drawable.ic_signin, "Đăng nhập");
		arrLeft.add(ItemNavigation1);
		ItemNavigation ItemNavigation2 = new ItemNavigation(
				R.drawable.ic_mymusic, "My Music");
		arrLeft.add(ItemNavigation2);
		arrLeft.add(new ItemNavigation(0, "Mp3 Zing"));
		ItemNavigation ItemNavigation3 = new ItemNavigation(
				R.drawable.ic_nhachot, "Nhạc HOT");
		arrLeft.add(ItemNavigation3);
		ItemNavigation ItemNavigation4 = new ItemNavigation(
				R.drawable.ic_album, "Album");
		arrLeft.add(ItemNavigation4);
		ItemNavigation ItemNavigation5 = new ItemNavigation(
				R.drawable.ic_video, "Video Clip");
		arrLeft.add(ItemNavigation5);

		ItemNavigation ItemNavigation6 = new ItemNavigation(
				R.drawable.ic_artist, "Nghệ sĩ");
		arrLeft.add(ItemNavigation6);
		ItemNavigation ItemNavigation7 = new ItemNavigation(
				R.drawable.ic_chart, "Bảng xếp hạng");
		arrLeft.add(ItemNavigation7);
		ItemNavigation ItemNavigation8 = new ItemNavigation(
				R.drawable.ic_top100, "Top 100");
		arrLeft.add(ItemNavigation8);
		ItemNavigation ItemNavigation9 = new ItemNavigation(R.drawable.zma_ic,
				"Zing Music Awards");
		arrLeft.add(ItemNavigation9);
		arrLeft.add(new ItemNavigation(-100, "Công cụ"));
		ItemNavigation ItemNavigation10 = new ItemNavigation(R.drawable.ic_lrc,
				"LyricsAnyWhere");
		arrLeft.add(ItemNavigation10);
		ItemNavigation ItemNavigation11 = new ItemNavigation(
				R.drawable.ic_restore, "Restore");
		arrLeft.add(ItemNavigation11);
		ItemNavigation ItemNavigation12 = new ItemNavigation(
				R.drawable.ic_notif, "Thông báo");
		arrLeft.add(ItemNavigation12);
		ItemNavigation ItemNavigation13 = new ItemNavigation(
				R.drawable.ic_vip_menu, "Zing Vip");
		arrLeft.add(ItemNavigation13);
		ItemNavigation ItemNavigation14 = new ItemNavigation(R.drawable.ic_app,
				"Ứng dụng liên quan");
		arrLeft.add(ItemNavigation14);
		ItemNavigation ItemNavigation15 = new ItemNavigation(
				R.drawable.ic_setting, "Cài đặt");
		arrLeft.add(ItemNavigation15);
		ItemNavigation ItemNavigation16 = new ItemNavigation(
				R.drawable.ic_exit, "Đóng ứng dụng");
		arrLeft.add(ItemNavigation16);

	}

	/**
	 * Khi su dung ActionBarDrawerToggle phai goi toi 2 phuong thuc
	 * onPostCreate() va onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		Log.d("debug", "onPostCreate");
		// mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		Log.d("debug", "onConfigurationChanged");
	}

	/*
	 * Khi goi invalidateOptionsMenu() thi chung ta phai override lai phuong
	 * thuc nay
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.d("debug", "onPrepareOptionsMenu");

		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar, menu);
		// itemmenu = (MenuItem) menu.findItem(R.id.action_alarm);
		// itemmenu.setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Log.d("debug", "click home");
			boolean drawerleftOpen = mDrawerLayout.isDrawerOpen(mleftList);
			if (!drawerleftOpen) {
				mDrawerLayout.openDrawer(Gravity.START);

			} else {
				mDrawerLayout.closeDrawer(mleftList);

			}

			return true;
		case R.id.action_search:
			Log.d("debug", "click setting");

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 * @param fragMent
	 *            : các fragment thay thế cho fragment main khi có sự thay đổi
	 */
	public void replaceFragment(Fragment fragMent, String tag) {
		FragmentTransaction transtraction = getSupportFragmentManager()
				.beginTransaction();
		transtraction.replace(R.id.content_frame, fragMent, tag);
		transtraction.addToBackStack("fragment");
		transtraction.commit();
	}

	/**
	 * 
	 * @param baiHat
	 *            thông tin của một bài hát
	 */
	public static void setDataPlayBar(Song baiHat) {
		String name = "", tacgia = "";
		if (baiHat.getmName().length() > 20) {
			name = baiHat.getmName().substring(0, 18);
		} else {
			name = baiHat.getmName();
		}
		if (baiHat.getmTacgia().length() > 20) {
			tacgia = baiHat.getmTacgia().substring(0, 15);
		} else {
			tacgia = baiHat.getmTacgia();
		}
		mSongName.setText(name + "...");
		mTacGia.setText(tacgia + "...");
	}

	/**
	 * bắt sự kiện cho các button
	 */
	public void setOnClickPlayTabBar() {

		mleftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				mleftList.setItemChecked(pos, true);
				// Dong Drawer khi click xong
				mDrawerLayout.closeDrawer(mleftList);

			}
		});

		layout_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,
						PlayMusicActivity.class));
			}
		});

		mainFragMent.setOnItemClick(new onItemClick() {

			@Override
			public void onClick(View v) {
				Log.d("sau", "sau");
				// playMusic.setVisibility(View.VISIBLE);
				final MyMusicFragment myMusic = new MyMusicFragment(
						Constants.INDEX);
				replaceFragment(myMusic, "mysic");

				myMusic.setMyOnItemClick(new onMyItemClick() {

					@Override
					public void onClick(View v) {

						layout_play.setVisibility(View.VISIBLE);
						FragmentPlaybar.updateUI();
						// updateUI();
						startActivity(new Intent(MainActivity.this,
								PlayMusicActivity.class));
					}
				});

			}
		});

		// // Set Adapter cho list ben trai

		// mDrawerLayout.setDrawerShadow(R.drawable.ic_arrow,
		// GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.abc_action_bar_home_description_format,
				R.string.abc_action_bar_home_description) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle("Zing Mp3");
				Log.d("debug", "onDrawerClosed");
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("Zing Mp3");
				Log.d("debug", "onDrawerOpened");
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

	}

	/**
	 * cập nhật giao diện khi trạng thái phát nhạc thay đổi hoặc thay đổi một
	 * bài hát
	 */
	public static void updateUI() {
		if (Constants.SONG_PAUSE) {
			btn_pause.setVisibility(View.INVISIBLE);
			btn_play.setVisibility(View.VISIBLE);
		} else {
			btn_pause.setVisibility(View.VISIBLE);
			btn_play.setVisibility(View.INVISIBLE);
		}
		setDataPlayBar(Constants.mBaiHat);
	}

	@Override
	public void onBackPressed() {
		int count = getFragmentManager().getBackStackEntryCount();
		// Toast.makeText(getApplicationContext(), ""+count,
		// Toast.LENGTH_LONG).show();
		if (count == 0) {
			super.onBackPressed();
			if (OncePress) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						OncePress = false;
					}
				}, 500);
				// Intent Myintent = new Intent(this, ServicePlayMusic.class);
				// stopService(Myintent);
				this.finish();

			}
			OncePress = true;
		} else {
			getFragmentManager().popBackStack();
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}

package com.asiantech.mp3pro.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.asiantech.mp3pro.adapter.PlayMusicAdapter;
import com.asiantech.mp3pro.controls.Controls;
import com.asiantech.mp3pro.fragment.PlayMusicFragMent;
import com.asiantech.mp3pro.utils.Constants;
import com.asiantech.mp3pro.utils.Utilities;
import com.example.mp3pro.R;

@SuppressWarnings("deprecation")
public class PlayMusicActivity extends ActionBarActivity {
	public ViewPager mViewPager;
	public ImageView tab1, tab2, tab3, btn_random, btn_prev;
	public static ImageView btn_play;
	public ImageView btn_next;
	public ImageView btn_loop;
	public TextView txtStartTime, txtMaxtime;
	public SeekBar mSeekbar;
	public RelativeLayout rlLoop,rlRandom;
	Handler seekHandler = new Handler();

	long duration, currentDuration = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_play_music);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		overridePendingTransition(R.drawable.slide_in_up,
				R.drawable.slide_out_up);
		initData();
		actionbarSetup();
		setChangeButton();
		setListenner();
	}

	/**
	 * thay đổi button khi nhấn
	 */
	public static void setChangeButton() {
		if (Constants.SONG_PAUSE) {
			btn_play.setImageResource(R.drawable.ic_player_play);

		} else {

			btn_play.setImageResource(R.drawable.ic_player_pause);
		}
		
	}

	/**
	 * thay đổi title actionbar
	 */
	public void actionbarSetup() {
		String title = Constants.mBaiHat.getmName();
		String tacgia = Constants.mBaiHat.getmTacgia();
		if (title.length() > 18){
			title = title.substring(0, 18);
		}			
		if (tacgia.length() > 18){
			tacgia = tacgia.substring(0, 18);
		}
		getSupportActionBar().setTitle(title);
		getSupportActionBar().setSubtitle(tacgia);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		actionbarSetup();
		setChangeButton();
	}

	@Override
	protected void onPause() {
		super.onPause();
//		overridePendingTransition(R.drawable.slide_in_down,
//				R.drawable.slide_out_down);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_playmusic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finishActivity();
		}
		return super.onOptionsItemSelected(item);
	}
/**
 * kết thúc activity
 */
	public void finishActivity() {
		this.finish();
	}

	@Override
	public void onBackPressed() {
		finishActivity();
		super.onBackPressed();
	}

	/**
	 * bắt sự kiện cho các button
	 */
	public void setListenner() {
		mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					String progresss = ""
							+ (((duration / 1000) * progress) * 10);
					Log.d("progress", "" + progresss);
					Constants.PROGRESS_CHANGE
							.sendMessage(Constants.PROGRESS_CHANGE
									.obtainMessage(0, progresss));

				}
			}
		});

		setChangeButton();

		rlLoop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("loop", "loop");
				switch (Constants.LOOP) {
				case 0:
					btn_loop.setImageResource(R.drawable.ic_player_repeat_off);
					Constants.LOOP = 1;

					break;
				case 1:
					btn_loop.setImageResource(R.drawable.ic_player_repeat_all);
					Constants.LOOP = 2;
					break;
				case 2:
					btn_loop.setImageResource(R.drawable.ic_player_repeat_one);
					Constants.LOOP = 0;

					break;
				}

			}
		});

		rlRandom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("random", "random");
				if (Constants.RANDOM_PLAYING) {
					btn_random
							.setImageResource(R.drawable.ic_player_shuffle_off);
				} else
					btn_random.setImageResource(R.drawable.ic_player_shuffle);

				Constants.RANDOM_PLAYING = !Constants.RANDOM_PLAYING;
			}
		});

		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("next", "next");
				Controls.doNextSong(getApplicationContext());
				actionbarSetup();
			}
		});

		btn_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("play", "play");
				if (!Constants.SONG_PAUSE) {

					Controls.doPauseSong(getApplicationContext());

					PlayMusicFragMent.setRotateOff(getApplicationContext());
				} else {

					PlayMusicFragMent.setRotateOn(getApplicationContext());
					Controls.doPlaySong(getApplicationContext());

				}

			}
		});

		btn_prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("prev", "prev");
				actionbarSetup();
				Controls.doPrevSong(getApplicationContext());

			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos) {
				switch (pos) {
				case 0:
					tab1.setImageResource(R.drawable.ic_player_slider_active);
					tab2.setImageResource(R.drawable.ic_player_slider);
					tab3.setImageResource(R.drawable.ic_player_slider);
					break;
				case 1:
					tab2.setImageResource(R.drawable.ic_player_slider_active);
					tab1.setImageResource(R.drawable.ic_player_slider);
					tab3.setImageResource(R.drawable.ic_player_slider);
					break;
				case 2:
					tab3.setImageResource(R.drawable.ic_player_slider_active);
					tab1.setImageResource(R.drawable.ic_player_slider);
					tab2.setImageResource(R.drawable.ic_player_slider);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE) {

				}

			}
		});

	}

	/**
	 * init dữ liệu
	 */
	public void initData() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager_music);
		tab1 = (ImageView) findViewById(R.id.imgTab1);
		tab2 = (ImageView) findViewById(R.id.imgTab2);
		tab3 = (ImageView) findViewById(R.id.imgTab3);

		btn_random = (ImageView) findViewById(R.id.imgRanDom);
		btn_prev = (ImageView) findViewById(R.id.imgPreviousButton);
		btn_play = (ImageView) findViewById(R.id.imgPlayPauseButton);
		btn_next = (ImageView) findViewById(R.id.imgNextButton);
		btn_loop = (ImageView) findViewById(R.id.imgLoop);

		rlLoop = (RelativeLayout)findViewById(R.id.rlLoop);
		rlRandom = (RelativeLayout)findViewById(R.id.rlRandom);
		
		mSeekbar = (SeekBar) findViewById(R.id.seekBar);

		// mSeekbar.setMax(GetSetData.myPlayMusic.getDuration());

		txtStartTime = (TextView) findViewById(R.id.txtCurrentTime);
		txtMaxtime = (TextView) findViewById(R.id.txtMaxTime);

		PlayMusicAdapter adapter = new PlayMusicAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(1);
		tab2.setImageResource(R.drawable.ic_player_slider_active);

		Constants.PROGRESS_HANDLER = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				// Log.d("bat roi", "ok");
				Integer data[] = (Integer[]) msg.obj;
				duration = data[0];
				currentDuration = data[1];
				// setSeekBar();
				int progress = Utilities.getProgressPercentage(currentDuration,
						duration);
				mSeekbar.setProgress(progress);
				txtStartTime.setText(""
						+ Utilities.milliSecondsToTimer(currentDuration));
				txtMaxtime.setText("" + Utilities.milliSecondsToTimer(duration));
				return false;
			}
		});
		
		Constants.CHANGE_TITlE_BAR = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				actionbarSetup();
				return false;
			}
		});
		
		if (Constants.RANDOM_PLAYING)
			btn_random.setImageResource(R.drawable.ic_player_shuffle);
		else
			btn_random.setImageResource(R.drawable.ic_player_shuffle_off);
	}

}

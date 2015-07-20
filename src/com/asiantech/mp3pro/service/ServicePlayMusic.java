package com.asiantech.mp3pro.service;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.asiantech.mp3pro.activity.MainActivity;
import com.asiantech.mp3pro.activity.PlayMusicActivity;
import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.controls.Controls;
import com.asiantech.mp3pro.utils.Constants;
import com.example.mp3pro.R;

public class ServicePlayMusic extends Service {
	MediaPlayer mPlay;
	Notification notification;
	public static int NOTIFICATION_ID = 1111;
	public static final String NOTIFI_PLAY = "com.asiantech.mp3pro.play";
	public static final String NOTIFI_NEXT = "com.asiantech.mp3pro.next";
	public static final String NOTIFI_PAUSE = "com.asiantech.mp3pro.pause";
	public static final String NOTIFI_PREV = "com.asiantech.mp3pro.prev";
	public static final String NOTIFI_DELETE = "com.asiantech.mp3pro.delete";

	private static Timer mTimer;

	public class MainTask extends TimerTask {

		@Override
		public void run() {
			handle.sendEmptyMessage(0);
		}

	}

	@SuppressLint("HandlerLeak")
	private final Handler handle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (mPlay != null) {
				int duration = mPlay.getDuration();
				int currentDuration = mPlay.getCurrentPosition();
				Integer data[] = new Integer[2];
				data[0] = duration;
				data[1] = currentDuration;
				try {
					Constants.PROGRESS_HANDLER
							.sendMessage(Constants.PROGRESS_HANDLER
									.obtainMessage(0, data));
				} catch (Exception e) {

				}
			}

		}

	};

	@Override
	public void onCreate() {
		super.onCreate();
		mPlay = new MediaPlayer();
		
		
		mTimer = new Timer();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String songPath = Constants.mBaiHat.getmPath();
		Song baiHat = Constants.mBaiHat;
		playSong(songPath, baiHat);
		buildNotification();

		mPlay.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				Controls.doNextSong(getApplicationContext());
			}
		});
		Constants.SONG_CHANGE_HANDLER = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				Log.d("song_change_handler", "song change");
				String songPath = Constants.mBaiHat.getmPath();
				Song baiHat = Constants.mBaiHat;
				playSong(songPath, baiHat);
				buildNotification();
				return true;
			}
		});

		Constants.SONG_PAUSE_HANDLER = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				Log.d("song_pause_handler", "song pause");
				String sms = (String) msg.obj;
				if (mPlay == null)
					return false;
				if (sms.equals(Constants.PLAY_MSG)) {
					Constants.SONG_PAUSE = false;
					mPlay.start();
				} else if (sms.equals(Constants.PAUSE_MSG)) {

					Constants.SONG_PAUSE = true;
					mPlay.pause();

				}
				buildNotification();

				return false;
			}
		});

		Constants.PROGRESS_CHANGE = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				Log.d("progress_handler", "progress pause");
				String progress = (String) msg.obj;
				Log.d("progress", progress);
				int seek = Integer.parseInt(progress);
				if (mPlay != null) {
					mPlay.seekTo(seek);
				}
				return false;
			}
		});
		return START_STICKY;
	}

	public void buildNotification() {
		Intent intent = new Intent(getApplicationContext(), PlayMusicActivity.class);
		intent.setFlags(0x24000000);
		PendingIntent pending = PendingIntent.getActivity(
				getApplicationContext(), 0, intent, 0);
		
		notification = new NotificationCompat.Builder(getApplicationContext())
				.setSmallIcon(R.drawable.ic_notification_small)
				.setContentIntent(pending).build();

		RemoteViews contentView = new RemoteViews(getApplicationContext()
				.getPackageName(), R.layout.notification_custom);
		contentView
				.setTextViewText(R.id.txtTitleView, Constants.mBaiHat.getmName());
		contentView.setTextViewText(R.id.txtSubTitleView,
				Constants.mBaiHat.getmTacgia());
		setnListenner(contentView);
		notification.contentView = contentView;

		if (!Constants.SONG_PAUSE) {
			notification.contentView.setImageViewResource(R.id.imgPlayPauseView,
					R.drawable.ic_widget_pause);
		} else {
			notification.contentView.setImageViewResource(R.id.imgPlayPauseView,
					R.drawable.ic_widget_play);
		}

		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		startForeground(NOTIFICATION_ID, notification);
	}

	public void setnListenner(RemoteViews view) {
		Intent intentPlay = new Intent(NOTIFI_PLAY);
		Intent intentNext = new Intent(NOTIFI_NEXT);
		Intent intentDelete = new Intent(NOTIFI_DELETE);
		
		

		PendingIntent pendingPlay = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intentPlay,
				PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.imgPlayPauseView, pendingPlay);

		PendingIntent pendingDelete = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intentDelete, 0);
		view.setOnClickPendingIntent(R.id.lnImgRemoveView, pendingDelete);

		PendingIntent pendingNext = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intentNext,
				PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.imgNext_notif, pendingNext);

		// PendingIntent pendingPrev = PendingIntent.getBroadcast(
		// getApplicationContext(), 0, intentPrev, 0);
		// view.setOnClickPendingIntent(R.id.btnPrevious, pendingPrev);
	}

	private void playSong(String songPath, Song data) {
		try {

			mPlay.reset();
			mPlay.setDataSource(songPath);
			mPlay.prepare();
			mPlay.start();
			try {
				mTimer.scheduleAtFixedRate(new MainTask(), 0, 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onDestroy() {
		if (mPlay != null) {
			mPlay.stop();
			mPlay = null;
			
		}
		super.onDestroy();
	}
}

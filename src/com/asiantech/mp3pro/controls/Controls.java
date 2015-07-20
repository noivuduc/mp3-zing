package com.asiantech.mp3pro.controls;

import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.asiantech.mp3pro.activity.MainActivity;
import com.asiantech.mp3pro.activity.PlayMusicActivity;
import com.asiantech.mp3pro.fragment.FragmentPlaybar;
import com.asiantech.mp3pro.fragment.PlayMusicFragMent;
import com.asiantech.mp3pro.utils.Constants;

public class Controls {
	public static void doNextSong(Context context) {
		if (Constants.LOOP == 0) {
			Log.d("loop", "0");
			if (Constants.CURRENT_PLAY_ID == Constants.mBaiHats.size() - 1) {
				doPauseSong(context);
			} else {
				if (!Constants.RANDOM_PLAYING) {
					Constants.CURRENT_PLAY_ID++;
				} else {
					Constants.CURRENT_PLAY_ID = new Random()
							.nextInt(Constants.mBaiHats.size() - 1);
				}
				Constants.mBaiHat = Constants.mBaiHats
						.get(Constants.CURRENT_PLAY_ID);
				Constants.SONG_CHANGE_HANDLER
						.sendMessage(Constants.SONG_CHANGE_HANDLER
								.obtainMessage());
				Constants.SONG_PAUSE = false;
				//MainActivity.updateUI();
				PlayMusicFragMent.setRotateOn(context);
				PlayMusicActivity.setChangeButton();
				FragmentPlaybar.updateUI();
				sendMessageChangeTitle();
			}
			return;
		}
		if (Constants.LOOP == 1) {
			Log.d("loop", "1");
			if (!Constants.RANDOM_PLAYING) {
				if (Constants.CURRENT_PLAY_ID < Constants.mBaiHats.size() - 1) {
					Constants.CURRENT_PLAY_ID++;
				} else if (Constants.CURRENT_PLAY_ID == Constants.mBaiHats
						.size() - 1) {
					Constants.CURRENT_PLAY_ID = 0;
				}
			} else {
				Constants.CURRENT_PLAY_ID = new Random()
						.nextInt(Constants.mBaiHats.size() - 1);
			}

			Constants.mBaiHat = Constants.mBaiHats
					.get(Constants.CURRENT_PLAY_ID);
			Constants.SONG_CHANGE_HANDLER
					.sendMessage(Constants.SONG_CHANGE_HANDLER.obtainMessage());
			Constants.SONG_PAUSE = false;
			FragmentPlaybar.updateUI();
			PlayMusicFragMent.setRotateOn(context);
			PlayMusicActivity.setChangeButton();
			sendMessageChangeTitle();
			return;
		}
		if (Constants.LOOP == 2) {
			Log.d("loop", "2");
			doPlaySong(context);
			return;

		}

		// Constants.SONG_PAUSE = false;
		// FragMentPlayMusic.setRotateOn(context);
		// PlayMusicActivity.setChangeButton();
		// MainActivity.updateUI();

	}

	public static void doPlaySong(Context context) {

		sendMessage(Constants.PLAY_MSG);
		Constants.SONG_PAUSE = false;
		PlayMusicFragMent.setRotateOn(context);
		PlayMusicActivity.setChangeButton();
		FragmentPlaybar.updateUI();

	}

	public static void doPauseSong(Context context) {
		sendMessage(Constants.PAUSE_MSG);
		Constants.SONG_PAUSE = true;
		PlayMusicFragMent.setRotateOff(context);
		PlayMusicActivity.setChangeButton();
		FragmentPlaybar.updateUI();

	}

	public static void doPrevSong(Context context) {
		if (Constants.RANDOM_PLAYING) {
			Constants.CURRENT_PLAY_ID = new Random().nextInt(Constants.mBaiHats
					.size() - 1);
		} else if (Constants.CURRENT_PLAY_ID > 0) {
			Constants.CURRENT_PLAY_ID--;
		}
		Constants.mBaiHat = Constants.mBaiHats.get(Constants.CURRENT_PLAY_ID);
		Constants.SONG_CHANGE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER
				.obtainMessage());
		sendMessageChangeTitle();
		Constants.SONG_PAUSE = false;
		PlayMusicFragMent.setRotateOn(context);
		PlayMusicActivity.setChangeButton();
		FragmentPlaybar.updateUI();

	}

	public static void sendMessage(String message) {
		Constants.SONG_PAUSE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER
				.obtainMessage(0, message));
	}

	public static void sendMessageChangeTitle() {
		Constants.CHANGE_TITlE_BAR.sendMessage(Constants.CHANGE_TITlE_BAR
				.obtainMessage());
	}
}

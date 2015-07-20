package com.asiantech.mp3pro.broastcastreciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.asiantech.mp3pro.activity.MainActivity;
import com.asiantech.mp3pro.controls.Controls;
import com.asiantech.mp3pro.service.ServicePlayMusic;
import com.asiantech.mp3pro.utils.Constants;

public class AudioBroastCastReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(ServicePlayMusic.NOTIFI_NEXT)) {
			Log.d("next broast", "next");
			Controls.doNextSong(context);
		} else if (action.equals(ServicePlayMusic.NOTIFI_PLAY)) {
			if (Constants.SONG_PAUSE) {
				Log.d("play broast", "play");
				Controls.doPlaySong(context);
				//Constants.SONG_PAUSE = false;
			} else {
				Log.d("pause broast", "pause");
				Controls.doPauseSong(context);
				//Constants.SONG_PAUSE = true;
			}
		} else if (action.equals(ServicePlayMusic.NOTIFI_DELETE)) {
			Intent Myintent = new Intent(context, ServicePlayMusic.class);
			context.stopService(Myintent);
			Intent newIntent = new Intent(context, MainActivity.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(newIntent);

		}
	}

}

package com.asiantech.mp3pro.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.asiantech.mp3pro.utils.Constants;
import com.example.mp3pro.R;

public class PlayMusicFragMent extends Fragment{
	public static ImageView mPlayerCD;
	public static Animation anim;
	public interface onRotateCD{
		void onrotate(View v);
	}
	public onRotateCD rotate;
	
	public void setOnRotateCD(onRotateCD o){
		rotate = o;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_player_music, container, false);
		mPlayerCD = (ImageView)view.findViewById(R.id.imgCD);
		if(!Constants.SONG_PAUSE){
			setRotateOn(getActivity());
		}
		else
			setRotateOff(getActivity());
		return view;
	}
	
	public static void setRotateOn(Context context){
		//mPlayerCD.startAnimation(null);
		anim = AnimationUtils.loadAnimation(context, R.drawable.rotate_player_cd);
		anim.setRepeatCount(Animation.INFINITE);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatMode(Animation.RESTART);
		mPlayerCD.startAnimation(anim);
	}
	public static void setRotateOff(Context context){
		//mPlayerCD.startAnimation(null);
		anim = AnimationUtils.loadAnimation(context, R.drawable.stop_rotate_cd);
		mPlayerCD.startAnimation(anim);
	}
}

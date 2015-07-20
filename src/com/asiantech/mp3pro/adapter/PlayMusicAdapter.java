package com.asiantech.mp3pro.adapter;

import com.asiantech.mp3pro.fragment.PlayMusicFragMent;
import com.asiantech.mp3pro.fragment.ListSongFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PlayMusicAdapter extends FragmentPagerAdapter{

	public PlayMusicAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos){
		case 0:
			return new ListSongFragment();
		case 1:
			return new PlayMusicFragMent();
		default:
			return new ListSongFragment();
		}
	
	}

	@Override
	public int getCount() {
		return 3;
	}

}

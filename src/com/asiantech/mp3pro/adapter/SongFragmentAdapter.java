package com.asiantech.mp3pro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.fragment.SongFragment;
import com.asiantech.mp3pro.fragment.Fragment2;
import com.asiantech.mp3pro.fragment.SongFragment.onItemBaiHatClick;

public class SongFragmentAdapter extends FragmentPagerAdapter{
	final int PAGE_COUNT=4;
	Song mBaiHat;
	private String tablTitles[] = {"BÀI HÁT","ALBUM","NGHỆ SỸ","PLAYLIST"};
	public SongFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public interface setOnItemClick{
		void onClick(View v);
	}
	
	public setOnItemClick mOnClick;
	
	public void mysetOnItemClick(setOnItemClick l){
		mOnClick = l;
	}
	@Override
	public Fragment getItem(final int pos) {
		switch(pos){
		case 1:return new Fragment2();
				
		case 0:
			 final  SongFragment fragment = new SongFragment();
			   fragment.setonItemBaiHatClick(new onItemBaiHatClick() {
				
				@Override
				public void onItemClick(View v) {
					if(mOnClick!=null){
						mOnClick.onClick(v);
						
					}
				}
			});
			   
		return fragment;
		case 2:
			return new Fragment2();
		default:
			Log.d("debug", pos+"");
			return new Fragment2();
		}
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		
		return tablTitles[position];
	}
	
	public Song getDataClick(){
		return mBaiHat;
	}
}

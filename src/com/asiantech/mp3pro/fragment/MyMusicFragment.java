package com.asiantech.mp3pro.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiantech.mp3pro.adapter.SongFragmentAdapter;
import com.asiantech.mp3pro.adapter.SongFragmentAdapter.setOnItemClick;
import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.utils.PagerSlidingTabStrip;
import com.example.mp3pro.R;

public class MyMusicFragment extends Fragment {
	private int currentTab=0;
	Song mBaiHat;
	
	public MyMusicFragment(int index) {
		currentTab=index;
	}
	public interface onMyItemClick{
		void onClick(View v);
	}
	
	public onMyItemClick mClick;
	
	public void setMyOnItemClick(onMyItemClick l){
		mClick = l;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.activity_mymusic, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.vpContent);
	final	SongFragmentAdapter fragMent_BaiHat = new SongFragmentAdapter(getChildFragmentManager());
		viewPager.setAdapter(fragMent_BaiHat);
		PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
		viewPager.setCurrentItem(currentTab);
		tabStrip.setViewPager(viewPager);
		fragMent_BaiHat.mysetOnItemClick(new setOnItemClick() {
			
			@Override
			public void onClick(View v) {
				if(mClick!=null){
					
					mClick.onClick(v);
					
				}
				
			}
		});
		tabStrip.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		return view;
	}
	

	
}

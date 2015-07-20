package com.asiantech.mp3pro.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiantech.mp3pro.bean.Song;
import com.example.mp3pro.R;

public class Fragment2 extends Fragment {
	public static final String ARG_PAGE = "ARG_PAGE";
	
	public ArrayList<Song> BaiHats;

	public static SongFragment newInstance(int page) {
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_PAGE, page);
		SongFragment fragment = new SongFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_2, container,
				false);
		
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);

	}

	public void initData() {
		BaiHats = new ArrayList<Song>();
		for (int i = 0; i < 10; i++) {
			Song baiHat = new Song();
			baiHat.setmName("Bài " + i);
			baiHat.setmTacgia("tác giả " + i);
			BaiHats.add(baiHat);
		}
	}
}

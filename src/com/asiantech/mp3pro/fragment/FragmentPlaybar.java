package com.asiantech.mp3pro.fragment;

import com.asiantech.mp3pro.activity.MainActivity;
import com.asiantech.mp3pro.activity.PlayMusicActivity;
import com.asiantech.mp3pro.activity.SettingsActivity;
import com.asiantech.mp3pro.adapter.ItemAdapter;
import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.controls.Controls;
import com.asiantech.mp3pro.fragment.MainFragment.onItemClick;
import com.asiantech.mp3pro.fragment.MyMusicFragment.onMyItemClick;
import com.asiantech.mp3pro.utils.Constants;
import com.example.mp3pro.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentPlaybar extends Fragment {
	private static ImageView btn_prev, btn_play, btn_next, btn_pause;
	private static TextView mSongName, mTacGia;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_playmusic_bar, container,
				false);
		btn_next = (ImageView) v.findViewById(R.id.imgNextButton);
		btn_prev = (ImageView) v.findViewById(R.id.imgPrevious);
		btn_play = (ImageView) v.findViewById(R.id.imgPlayButton);
		btn_pause = (ImageView) v.findViewById(R.id.imgPauseButton);
		mSongName = (TextView) v.findViewById(R.id.txtSongName);
		mTacGia = (TextView) v.findViewById(R.id.txtTacGia);
		//updateUI();
		setOnClickPlayTabBar();
		return v;
	}

	public static void updateUI() {
		Song baiHat = Constants.mBaiHat;
		if (Constants.SONG_PAUSE) {
			btn_pause.setVisibility(View.INVISIBLE);
			btn_play.setVisibility(View.VISIBLE);
		} else {
			btn_pause.setVisibility(View.VISIBLE);
			btn_play.setVisibility(View.INVISIBLE);
		}
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

	public void setOnClickPlayTabBar() {
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Controls.doNextSong(getActivity());

			}
		});

		btn_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Controls.doPlaySong(getActivity());

			}
		});
		btn_pause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Controls.doPauseSong(getActivity());

			}
		});

		btn_prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Controls.doPrevSong(getActivity());

			}
		});

	}
}

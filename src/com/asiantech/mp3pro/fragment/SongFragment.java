/**
 * 
 */

package com.asiantech.mp3pro.fragment;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.asiantech.mp3pro.adapter.SongAdapter;
import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.service.ServicePlayMusic;
import com.asiantech.mp3pro.utils.Constants;
import com.asiantech.mp3pro.utils.Utilities;
import com.example.mp3pro.R;

public class SongFragment extends Fragment {
	public ArrayList<Song> BaiHats;
	Song mBaiHat;
	Intent myIntent;

	public interface onItemBaiHatClick {
		void onItemClick(View v);
	}

	public onItemBaiHatClick mOnclick;

	public void setonItemBaiHatClick(onItemBaiHatClick l) {
		mOnclick = l;
	}

	public static SongFragment newInstance(int page) {
		SongFragment fragment = new SongFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_bai_hat, container, false);
		// initData();
		ListView listBaiHat = (ListView) view.findViewById(R.id.lvBaiHat);
		listBaiHat
				.setAdapter(new SongAdapter(getActivity(), Constants.mBaiHats));
		// final LinearLayout playMusic =
		// (LinearLayout)view.findViewById(R.id.play_music);
		listBaiHat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (Constants.mBaiHats.size() > 0) {
					if (mOnclick != null) {
						switch (position) {
						case 0:
							Constants.CURRENT_PLAY_ID = new Random()
									.nextInt(Constants.mBaiHats.size() - 1);
							break;
						default:

							Constants.CURRENT_PLAY_ID = position - 1;

							break;
						}

						Constants.mBaiHat = Constants.mBaiHats
								.get(Constants.CURRENT_PLAY_ID);
						if (!Utilities.checkServiceRunning(
								ServicePlayMusic.class, getActivity())) {
							Intent intent = new Intent(getActivity(),
									ServicePlayMusic.class);
							getActivity().startService(intent);

						} else {
							Constants.SONG_CHANGE_HANDLER
									.sendMessage(Constants.SONG_CHANGE_HANDLER
											.obtainMessage());
							Constants.SONG_PAUSE = false;
							// MainActivity.updateUI();
							// PlayMusicFragMent.setRotateOn(getActivity());
							// PlayMusicActivity.setChangeButton();
						}
						// Constants.SERVICE_RUNNING = true;
						mOnclick.onItemClick(view);
					}
				}else{
					Toast.makeText(getActivity(), "Không có bài hát nào!", Toast.LENGTH_LONG).show();
				}

			}

		});

		return view;

	}
}

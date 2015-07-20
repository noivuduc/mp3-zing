package com.asiantech.mp3pro.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.asiantech.mp3pro.adapter.MyMusicAdapter;
import com.asiantech.mp3pro.bean.Song;
import com.asiantech.mp3pro.utils.Constants;
import com.example.mp3pro.R;

public class MainFragment extends Fragment {
	String[] mListMenu = { "", "Bài Hát", "Album", "Nghệ sỹ", "Playlist",
			"Thư mục", "Download", "", "Yêu thích", "Playlist", "Nhạc upload" };
	int[] mDrawable = { 0, R.drawable.ic_mm_song, R.drawable.ic_album,
			R.drawable.ic_artist, R.drawable.ic_playlist_off,
			R.drawable.ic_folder, R.drawable.ic_mm_download, 0,
			R.drawable.ic_fav_active, R.drawable.ic_playlist_on,
			R.drawable.ic_upload };
	ArrayList<com.asiantech.mp3pro.bean.Menu> listMenu;
	ListView mainListView;
	ArrayList<Song> listMp3;

	public interface onItemClick {
		void onClick(View v);
	}

	public onItemClick mOnItemClick;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (View) LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_main, container, false);
		mainListView = (ListView) view.findViewById(R.id.lvMain);
		initData();
		mainListView.setAdapter(new MyMusicAdapter(getActivity(), listMenu));
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (mOnItemClick != null) {
					if (position != 0 || position != 1 || position != 8) {
						Constants.INDEX = position - 2;
						mOnItemClick.onClick(view);
						Log.d("truoc", "truoc "+position );
					}
				}
			}
		});
		return view;
	}

	public void setOnItemClick(onItemClick l) {
		mOnItemClick = l;
	}

	/**
	 * init data cho listview
	 */
	public void initData() {
		getAlbum();
		listMenu = new ArrayList<com.asiantech.mp3pro.bean.Menu>();
		for (int i = 0; i < mListMenu.length; i++) {
			com.asiantech.mp3pro.bean.Menu menu = new com.asiantech.mp3pro.bean.Menu();
			menu.setmName(mListMenu[i]);
			menu.setmSrc(mDrawable[i]);
			if (i == 1)
				menu.setmNumCount(Constants.mBaiHats.size());
			if (i == 2)
				menu.setmNumCount(Constants.Albums.size());
			listMenu.add(menu);
		}

	}

	public void getAlbum() {
		Constants.Albums = new ArrayList<String>();
		for (int i = 0; i < Constants.mBaiHats.size(); i++) {
			if (Constants.mBaiHats.get(i).getmAlbum() != "") {
				Constants.Albums.add(Constants.mBaiHats.get(i).getmAlbum());
			}
		}
	}

}

package com.asiantech.mp3pro.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asiantech.mp3pro.bean.Menu;
import com.example.mp3pro.R;

public class MyMusicAdapter extends BaseAdapter {
	Context mContext;

	ArrayList<Menu> listMenu = new ArrayList<Menu>();

	public MyMusicAdapter(Context context, ArrayList<Menu> listMenus) {
		mContext = context;
		this.listMenu = listMenus;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMenu.size()+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return 0;
		if (position == 1 || position == 8) {
			return 1;
		} else
			return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Log.d("debug", "debug");
		if (convertView == null) {
			holder = new ViewHolder();
			switch (getItemViewType(position)) {
			case 2:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_menu_item, parent, false);
				holder.mImageView = (ImageView) convertView
						.findViewById(R.id.imgIcon);
				holder.mTextView = (TextView) convertView
						.findViewById(R.id.txtMenuName);
				holder.mNumberSong = (TextView) convertView
						.findViewById(R.id.txtnumberSong);
				// if (position < 8)
				// holder.mNumberSong.setText("0");
				// else
				// holder.mNumberSong.setText("");
				Log.d("1", "positon >0");
				break;
			case 0:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_img_title, parent, false);
				Log.d("0", "positon ==0");
				break;
			case 1:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_title, parent, false);
				holder.mTextView = (TextView) convertView
						.findViewById(R.id.txtTitle);
				convertView.setFocusable(false);
				if (position == 1){
					holder.mTextView.setText("Nhạc Offline");
				}
				else{
					holder.mTextView.setText("Nhạc online");
				}
				break;

			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (getItemViewType(position) == 2) {
			if (holder != null) {
				holder.mTextView.setText(listMenu.get(position - 1).getmName());
				holder.mImageView.setBackgroundResource(listMenu.get(
						position - 1).getmSrc());
				if (position < 8){
					holder.mNumberSong.setText(listMenu.get(position - 1)
							.getmNumCount()+"");
				}
				else{
					holder.mNumberSong.setText("");
				}
			}
		}
		return convertView;
	}

	public class ViewHolder {
		ImageView mImageView;
		TextView mTextView, mNumberSong;
	}
}

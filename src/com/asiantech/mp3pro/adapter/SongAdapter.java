package com.asiantech.mp3pro.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.asiantech.mp3pro.bean.Song;
import com.example.mp3pro.R;

public class SongAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<Song> listBaiHat;

	public SongAdapter(Context context, ArrayList<Song> baiHats) {
		mContext = context;
		listBaiHat = baiHats;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listBaiHat.size()+1;
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
	public int getItemViewType(int position) {
		return position == 0 ? 0 : 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			switch (getItemViewType(position)) {
			case 0:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_random_play, parent,false);
				break;

			case 1:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_bai_hat, parent,false);
				holder.mSongName = (TextView)convertView.findViewById(R.id.txtSongName);
				holder.mTacGia = (TextView)convertView.findViewById(R.id.txtTacGia);
				holder.mButtonRight = (ImageView)convertView.findViewById(R.id.imgPopup);
				
				holder.mButtonRight.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					android.widget.PopupMenu popup = new android.widget.PopupMenu(mContext, holder.mButtonRight);
					popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
					
					popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
						
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							return false;
						}
					});
					
					popup.show();
					}
				});
				break;
			}
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(getItemViewType(position)==1){
			holder.mSongName.setText(listBaiHat.get(position-1).getmName());
			holder.mTacGia.setText(listBaiHat.get(position-1).getmTacgia());
		}
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView mButtonRight;
		TextView mSongName,mTacGia;
	}
}

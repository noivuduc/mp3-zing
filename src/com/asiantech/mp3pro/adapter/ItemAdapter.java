package com.asiantech.mp3pro.adapter;

import java.util.ArrayList;
import java.util.TreeSet;

import com.asiantech.mp3pro.bean.ItemNavigation;
import com.example.mp3pro.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<ItemNavigation> arr;
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
	@SuppressWarnings("rawtypes")
	private TreeSet mSeparatorsSet = new TreeSet();

	public ItemAdapter(Context mContext, ArrayList<ItemNavigation> arr) {
		super();
		this.mContext = mContext;
		this.arr = arr;
		mInflater = LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return arr.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		Log.d("positon " + position, "" + position);
		if (convertView == null) {
			holder = new viewHolder();

			convertView = mInflater
					.inflate(R.layout.layout_menu_item_left, parent, false);
			holder.itemIcon = (ImageView) convertView
					.findViewById(R.id.imgIcon);
			holder.itemName = (TextView) convertView
					.findViewById(R.id.txtNameMenu);
			View vLine = (View) convertView.findViewById(R.id.vLine);
			if (position == 1 || position == 9)
				vLine.setVisibility(View.INVISIBLE);

			// if (position == 2 || position == 10) {
			// holder.itemName.setText(arr.get(position).getItemName());
			// holder.itemName.setTextColor(0x000000);
			// holder.itemName.setTextSize(13);
			// }
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}

		final ItemNavigation itemNavigation = (ItemNavigation) arr
				.get(position);

		holder.itemIcon.setImageResource(itemNavigation.getItemIcon());
		holder.itemName.setText(itemNavigation.getItemName());
		holder.itemIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "Ban da tap chon hinh anh",
						Toast.LENGTH_LONG).show();
			}
		});

		// bat su kien khi click vao icon

		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	public class viewHolder {
		public TextView itemName, itemTitle;
		public ImageView itemIcon;
	}
}

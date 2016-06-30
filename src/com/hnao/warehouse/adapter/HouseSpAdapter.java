package com.hnao.warehouse.adapter;

import java.util.List;

import com.hnao.warehouse.R;
import com.hnao.warehouse.domain.StoreHouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HouseSpAdapter extends BaseAdapter {	
	
	private List<StoreHouse> mList;
	
	private Context mContext;
	
	public HouseSpAdapter(Context context, List<StoreHouse> list) {
		super();
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public StoreHouse getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.house_sp_item, null);

			holder = new ViewHolder();
			holder.houseName = (TextView) convertView.findViewById(R.id.house_name);
			holder.houseMemo = (TextView) convertView.findViewById(R.id.house_memo);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		holder.houseName.setText(mList.get(position).Title);
		String memo = mList.get(position).Memo;
		if (memo != null && !memo.equals("")) {
			holder.houseMemo.setText("(" + memo + ")");
		} else {
			holder.houseMemo.setText("");
		}

		return convertView;
	}

	class ViewHolder {
		TextView houseName;
		TextView houseMemo;
	}
}

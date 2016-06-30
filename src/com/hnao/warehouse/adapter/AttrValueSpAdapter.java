package com.hnao.warehouse.adapter;

import java.util.List;

import com.hnao.warehouse.R;
import com.hnao.warehouse.domain.AttrValue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttrValueSpAdapter extends BaseAdapter {

	private List<AttrValue> mList;

	private Context mContext;

	public AttrValueSpAdapter(Context context, List<AttrValue> list) {
		super();
		// TODO Auto-generated constructor stub
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public AttrValue getItem(int position) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.one_text_sp_item, null);

			holder = new ViewHolder();
			holder.typeText = (TextView) convertView.findViewById(R.id.tv_one);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		holder.typeText.setText(mList.get(position).Text);

		return convertView;
	}

	class ViewHolder {
		TextView typeText;
	}
}

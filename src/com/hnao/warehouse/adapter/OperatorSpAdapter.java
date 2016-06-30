package com.hnao.warehouse.adapter;

import java.util.List;

import com.hnao.warehouse.R;
import com.hnao.warehouse.domain.Operator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OperatorSpAdapter extends BaseAdapter {

	private List<Operator> mList;

	private Context mContext;

	public OperatorSpAdapter(Context context, List<Operator> list) {
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
	public Operator getItem(int position) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.operator_sp_item, null);

			holder = new ViewHolder();
			holder.realName = (TextView) convertView.findViewById(R.id.real_name);
			holder.memo = (TextView) convertView.findViewById(R.id.memo);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		holder.realName.setText(mList.get(position).RealName);
		String memo = mList.get(position).Memo;
		if (memo != null && !memo.equals("")) {
			holder.memo.setText("(" + memo + ")");
		} else {
			holder.memo.setText("");
		}
		return convertView;
	}

	class ViewHolder {
		TextView realName;
		TextView memo;
	}
}

package com.hnao.warehouse.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hnao.warehouse.R;
import com.hnao.warehouse.domain.Message;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageLvAdapter extends BaseAdapter {

	private List<Message> mList;

	private Context mContext;

	public MessageLvAdapter(Context context, List<Message> list) {
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
	public Message getItem(int position) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);

			holder = new ViewHolder();
			holder.from = (TextView) convertView.findViewById(R.id.tv_from);
			holder.date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.unread = (ImageView) convertView.findViewById(R.id.iv_unread);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		holder.from.setText(mList.get(position).operatorName);

		Date date = mList.get(position).createTime;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		holder.date.setText(sdf.format(date));

		holder.content.setText(mList.get(position).messages);

		if (mList.get(position).isRead) {
			holder.unread.setVisibility(View.GONE);
		} else {
			holder.unread.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView unread;
		TextView date;
		TextView from;
		TextView content;
	}
}

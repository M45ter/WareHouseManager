package com.hnao.warehouse.fragment;

import java.util.List;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.adapter.MessageLvAdapter;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.ListMessage;
import com.hnao.warehouse.domain.Message;
import com.hnao.warehouse.domain.User;
import com.hnao.warehouse.task.GetAllMessagesTask;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MessageCenterFragment extends Fragment {

	public final static String TAG = "MessageCenterFragment";

	private Context mContext;

	private List<Message> messageList;

	private ListView lvMessage;

	private MessageLvAdapter messageLvAdapter;

	private TextView tvNoMessage;

	private Button btnRefresh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_message_center, container, false);

		mContext = getActivity();

		tvNoMessage = (TextView) view.findViewById(R.id.tv_no_msg);

		lvMessage = (ListView) view.findViewById(R.id.lv_message);

		btnRefresh = (Button) view.findViewById(R.id.btn_refresh);

		btnRefresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}
		});

		lvMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Message message = messageList.get(position);
				MessageDetailDialogFragment fragment = new MessageDetailDialogFragment(message);

				fragment.setEditMessageListener(new MessageDetailDialogFragment.EditMessageListener() {

					@Override
					public void editMessageSucceed(Message message) {
						// TODO Auto-generated method stub
						refresh();
					}

					@Override
					public void editMessageFaild() {
						// TODO Auto-generated method stub

					}
				});

				FragmentManager fragmentManager = getFragmentManager();

				fragment.show(fragmentManager, "ShowMessageDialog");
			}
		});

		refresh();

		return view;
	}

	private synchronized void refresh() {
		GetAllMessagesTask task = new GetAllMessagesTask(mContext);

		ListMessage listMessage = new ListMessage();

		listMessage.opRole = User.getInstance(mContext).operatorRole;

		listMessage.opName = User.getInstance(mContext).UserName;

		task.setCompletedListener(new GetAllMessagesTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<List<Message>> result) {
				// TODO Auto-generated method stub
				if (result.Data != null && result.Data.size() > 0) {
					// if (false) {
					messageList = result.Data;
					messageLvAdapter = new MessageLvAdapter(mContext, messageList);
					lvMessage.setAdapter(messageLvAdapter);
					tvNoMessage.setVisibility(View.GONE);
					lvMessage.setVisibility(View.VISIBLE);
				} else {
					tvNoMessage.setVisibility(View.VISIBLE);
					lvMessage.setVisibility(View.GONE);
				}
			}

			@Override
			public void resultFaild(BizResult<List<Message>> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "从服务器获取消息失败！");
			}
		});

		task.execute(listMessage);
	}
}

package com.hnao.warehouse.fragment;

import com.hnao.warehouse.Utils;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Message;
import com.hnao.warehouse.task.EditMessagesTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class MessageDetailDialogFragment extends DialogFragment {

	private Message message;

	public MessageDetailDialogFragment(Message message) {
		super();
		// TODO Auto-generated constructor stub
		this.message = message;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Context context = getActivity();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("消息详情").setMessage(message.messages).setNegativeButton("取消", null);

		if (!message.isRead) {
			builder.setPositiveButton("已读", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					EditMessagesTask task = new EditMessagesTask(getActivity(), false);

					task.setCompletedListener(new EditMessagesTask.TaskCompletedListener() {

						@Override
						public void resultSucceed(BizResult<Message> result) {
							// TODO Auto-generated method stub
							Utils.showStringToast(context, "已标记为已读!");
							if (mListener != null) {
								mListener.editMessageSucceed(result.Data);
							}
						}

						@Override
						public void resultFaild(BizResult<Message> result) {
							// TODO Auto-generated method stub
							Utils.showStringToast(context, "标记已读失败！");
							if (mListener != null) {
								mListener.editMessageFaild();
							}
						}
					});

					message.isRead = true;

					task.execute(message);

				}
			});
		}

		return builder.create();
	}

	public interface EditMessageListener {
		void editMessageSucceed(Message message);

		void editMessageFaild();
	}

	private EditMessageListener mListener;

	public void setEditMessageListener(EditMessageListener listener) {
		mListener = listener;
	}
}

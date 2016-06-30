package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Message;
import com.hnao.warehouse.poxy.ApplyProxy;
import com.hnao.warehouse.poxy.MessageProxy;
import com.hnao.warehouse.task.EditApplyStatusTask.TaskCompletedListener;

import android.content.Context;
import android.util.Log;

public class EditMessagesTask extends BaseAsyncTask<Message, String, BizResult<Message>> {

	public final static String TAG = "EditMessagesTask";

	private TaskCompletedListener mListener;

	public EditMessagesTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EditMessagesTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<Message> doInBackground(Message... params) {
		// TODO: attempt authentication against a network service.
		BizResult<Message> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = MessageProxy.getInstance(mContext).EditMessages(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<Message> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "EditMessages success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "EditMessages failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<Message> result);

		void resultFaild(BizResult<Message> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

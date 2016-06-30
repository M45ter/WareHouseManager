package com.hnao.warehouse.task;

import java.util.List;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.ListMessage;
import com.hnao.warehouse.domain.Message;
import com.hnao.warehouse.poxy.MessageProxy;

import android.content.Context;
import android.util.Log;

public class GetAllMessagesTask extends BaseAsyncTask<ListMessage, String, BizResult<List<Message>>> {

	public final static String TAG = "GetAllMessagesTask";

	private TaskCompletedListener mListener;

	public GetAllMessagesTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetAllMessagesTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<List<Message>> doInBackground(ListMessage... params) {
		// TODO: attempt authentication against a network service.
		BizResult<List<Message>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = MessageProxy.getInstance(mContext).GetAllMessages(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<List<Message>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetAllMessages success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetAllMessages failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<List<Message>> result);

		void resultFaild(BizResult<List<Message>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;

import com.hnao.warehouse.poxy.MessageProxy;

import android.content.Context;
import android.util.Log;

public class GetNotReadTask extends BaseAsyncTask<String, String, BizResult<Integer>> {

	public final static String TAG = "GetNotReadTask";

	private TaskCompletedListener mListener;

	public GetNotReadTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetNotReadTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<Integer> doInBackground(String... params) {
		// TODO: attempt authentication against a network service.
		BizResult<Integer> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = MessageProxy.getInstance(mContext).GetNotRead(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<Integer> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetNotRead success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetNotRead failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<Integer> result);

		void resultFaild(BizResult<Integer> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

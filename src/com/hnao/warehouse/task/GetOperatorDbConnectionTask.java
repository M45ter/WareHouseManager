package com.hnao.warehouse.task;


import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.EntValidateProxy;

import android.content.Context;
import android.util.Log;

public class GetOperatorDbConnectionTask extends BaseAsyncTask<String, String, BizResult<String>> {

	public final static String TAG = "GetOperatorDbConnectionTask";

	private TaskCompletedListener mListener;

	public GetOperatorDbConnectionTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetOperatorDbConnectionTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<String> doInBackground(String... params) {
		// TODO: attempt authentication against a network service.
		BizResult<String> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = EntValidateProxy.getInstance(mContext).GetOperatorDbConnection(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<String> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetOperatorDbConnection success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetOperatorDbConnection failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<String> result);

		void resultFaild(BizResult<String> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

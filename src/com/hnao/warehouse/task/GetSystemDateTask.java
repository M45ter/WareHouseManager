package com.hnao.warehouse.task;

import java.util.Date;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.SerialNumberProxy;

import android.content.Context;
import android.util.Log;

public class GetSystemDateTask extends BaseAsyncTask<String, String, BizResult<Date>> {

	public final static String TAG = "GetSystemDateTask";

	private TaskCompletedListener mListener;

	public GetSystemDateTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetSystemDateTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<Date> doInBackground(String... params) {
		// TODO: attempt authentication against a network service.
		BizResult<Date> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = SerialNumberProxy.getInstance(mContext).GetSystemDate();
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<Date> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetSystemDate success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetSystemDate failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<Date> result);

		void resultFaild(BizResult<Date> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

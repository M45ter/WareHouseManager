package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.args.JLWArgs;
import com.hnao.warehouse.poxy.JLWDeviceProxy;

import android.content.Context;
import android.util.Log;

public class BindMouldAndDeviceTask extends BaseAsyncTask<JLWArgs, String, BizResult<JLWArgs>> {

	public final static String TAG = "BindMouldAndDeviceTask";

	private TaskCompletedListener mListener;

	public BindMouldAndDeviceTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BindMouldAndDeviceTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<JLWArgs> doInBackground(JLWArgs... params) {
		// TODO: attempt authentication against a network service.
		BizResult<JLWArgs> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = JLWDeviceProxy.getInstance(mContext).BindMouldAndDevice(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<JLWArgs> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "BindMouldAndDevice success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "BindMouldAndDevice failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<JLWArgs> result);

		void resultFaild(BizResult<JLWArgs> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

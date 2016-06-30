package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.ApplyProxy;

import android.content.Context;
import android.util.Log;

public class AddApplyTask extends BaseAsyncTask<Apply, String, BizResult<Apply>> {

	public final static String TAG = "AddApplyTask";

	private TaskCompletedListener mListener;

	public AddApplyTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public AddApplyTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<Apply> doInBackground(Apply... params) {
		// TODO: attempt authentication against a network service.
		BizResult<Apply> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = ApplyProxy.getInstance(mContext).AddApply(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<Apply> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "AddApply success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "AddApply failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<Apply> result);

		void resultFaild(BizResult<Apply> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

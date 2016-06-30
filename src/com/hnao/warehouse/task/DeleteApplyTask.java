package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.ApplyProxy;

import android.content.Context;
import android.util.Log;

public class DeleteApplyTask extends BaseAsyncTask<Apply, String, BizResult<Boolean>> {

	public final static String TAG = "DeleteApplyTask";

	private TaskCompletedListener mListener;

	public DeleteApplyTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DeleteApplyTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<Boolean> doInBackground(Apply... params) {
		// TODO: attempt authentication against a network service.
		BizResult<Boolean> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = ApplyProxy.getInstance(mContext).DeleteApply(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<Boolean> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "DeleteApply success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "DeleteApply failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<Boolean> result);

		void resultFaild(BizResult<Boolean> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

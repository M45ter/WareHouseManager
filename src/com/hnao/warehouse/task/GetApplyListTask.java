package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.PageReturns;
import com.hnao.warehouse.domain.args.ApplyArgs;
import com.hnao.warehouse.poxy.ApplyProxy;

import android.content.Context;
import android.util.Log;

public class GetApplyListTask extends BaseAsyncTask<ApplyArgs, String, BizResult<PageReturns<Apply>>> {

	public final static String TAG = "GetApplyListTask";

	private TaskCompletedListener mListener;

	public GetApplyListTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetApplyListTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<PageReturns<Apply>> doInBackground(ApplyArgs... params) {
		// TODO: attempt authentication against a network service.
		BizResult<PageReturns<Apply>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = ApplyProxy.getInstance(mContext).GetApplyList(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<PageReturns<Apply>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetApplyList success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetApplyList failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<PageReturns<Apply>> result);

		void resultFaild(BizResult<PageReturns<Apply>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

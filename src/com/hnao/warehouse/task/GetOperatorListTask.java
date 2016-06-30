package com.hnao.warehouse.task;

import java.util.List;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.args.OperatorArgs;
import com.hnao.warehouse.poxy.OperatorProxy;

import android.content.Context;
import android.util.Log;

public class GetOperatorListTask extends BaseAsyncTask<OperatorArgs, String, BizResult<List<Operator>>> {

	public final static String TAG = "GetOperatorListTask";

	private TaskCompletedListener mListener;

	public GetOperatorListTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetOperatorListTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<List<Operator>> doInBackground(OperatorArgs... params) {
		// TODO: attempt authentication against a network service.
		BizResult<List<Operator>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = OperatorProxy.getInstance(mContext).GetOperatorList(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<List<Operator>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetOperatorList success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetOperatorList failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<List<Operator>> result);

		void resultFaild(BizResult<List<Operator>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

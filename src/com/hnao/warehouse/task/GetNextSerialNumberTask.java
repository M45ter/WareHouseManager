package com.hnao.warehouse.task;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.SheetTypeEnum;
import com.hnao.warehouse.poxy.SerialNumberProxy;

import android.content.Context;
import android.util.Log;

public class GetNextSerialNumberTask extends BaseAsyncTask<SheetTypeEnum, String, BizResult<String>> {

	// 入库单 17

	public final static String TAG = "GetNextSerialNumberTask";

	private TaskCompletedListener mListener;

	public GetNextSerialNumberTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetNextSerialNumberTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<String> doInBackground(SheetTypeEnum... params) {
		// TODO: attempt authentication against a network service.
		BizResult<String> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = SerialNumberProxy.getInstance(mContext).GetNextSerialNumber(params[0]);
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
			Log.d(TAG, "GetNextSerialNumber success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetNextSerialNumber failed!");
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

package com.hnao.warehouse.task;

import java.util.List;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.ApplyItem;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.ApplyProxy;

import android.content.Context;
import android.util.Log;

public class GetApplyItemListTask extends BaseAsyncTask<String, String, BizResult<List<ApplyItem>>> {

	public final static String TAG = "GetApplyItemListTask";

	private TaskCompletedListener mListener;

	public GetApplyItemListTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetApplyItemListTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<List<ApplyItem>> doInBackground(String... params) {
		// TODO: attempt authentication against a network service.
		BizResult<List<ApplyItem>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = ApplyProxy.getInstance(mContext).GetApplyItemList(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<List<ApplyItem>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetApplyItemList success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetApplyItemList failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<List<ApplyItem>> result);

		void resultFaild(BizResult<List<ApplyItem>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

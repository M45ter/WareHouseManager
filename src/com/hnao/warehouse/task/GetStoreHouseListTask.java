package com.hnao.warehouse.task;

import java.util.List;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.StoreHouse;
import com.hnao.warehouse.domain.args.StoreHouseArgs;
import com.hnao.warehouse.poxy.StoreHouseProxy;

import android.content.Context;
import android.util.Log;

public class GetStoreHouseListTask extends BaseAsyncTask<StoreHouseArgs, String, BizResult<List<StoreHouse>>> {

	public final static String TAG = "GetStoreHouseListTask";

	private TaskCompletedListener mListener;

	public GetStoreHouseListTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetStoreHouseListTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<List<StoreHouse>> doInBackground(StoreHouseArgs... params) {
		// TODO: attempt authentication against a network service.
		BizResult<List<StoreHouse>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = StoreHouseProxy.getInstance(mContext).GetStoreHouseList(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<List<StoreHouse>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetStoreHouseList success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetStoreHouseList failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<List<StoreHouse>> result);

		void resultFaild(BizResult<List<StoreHouse>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

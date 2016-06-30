package com.hnao.warehouse.task;

import java.util.List;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.AttrValue;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.AttrValueProxy;

import android.content.Context;
import android.util.Log;

public class GetAttrValueListTask extends BaseAsyncTask<String, String, BizResult<List<AttrValue>>> {

	// 入库类型 00007

	public final static String TAG = "GetAttrValueListTask";

	private TaskCompletedListener mListener;

	public GetAttrValueListTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetAttrValueListTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<List<AttrValue>> doInBackground(String... params) {
		// TODO: attempt authentication against a network service.
		BizResult<List<AttrValue>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = AttrValueProxy.getInstance(mContext).GetAttrValueList(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<List<AttrValue>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetAttrValueList success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetAttrValueList failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<List<AttrValue>> result);

		void resultFaild(BizResult<List<AttrValue>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

package com.hnao.warehouse.task;

import java.util.List;

import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Product;
import com.hnao.warehouse.domain.args.ProductArgs;
import com.hnao.warehouse.poxy.ProductProxy;

import android.content.Context;
import android.util.Log;

public class GetProductInfoListTask extends BaseAsyncTask<ProductArgs, String, BizResult<List<Product>>> {

	public final static String TAG = "GetProductInfoListTask";

	private TaskCompletedListener mListener;

	public GetProductInfoListTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GetProductInfoListTask(Context context, boolean showDialog) {
		super(context, showDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected BizResult<List<Product>> doInBackground(ProductArgs... params) {
		// TODO: attempt authentication against a network service.
		BizResult<List<Product>> Re = null;
		if (null != params && params.length > 0) {
			try {
				Re = ProductProxy.getInstance(mContext).GetProductInfoList(params[0]);
			} catch (Exception e) {
				Log.e(TAG, ComFunc.getExceptionMessage(e), e);
			}
		}
		return Re;
	}

	@Override
	protected void onPostExecute(BizResult<List<Product>> result) {
		super.onPostExecute(result);
		if (result != null && result.Success) {
			Log.d(TAG, "GetProductInfoList success!");
			if (mListener != null) {
				mListener.resultSucceed(result);
			}
		} else {
			Log.d(TAG, "GetProductInfoList failed!");
			if (mListener != null) {
				mListener.resultFaild(result);
			}
		}
	}

	public interface TaskCompletedListener {
		void resultSucceed(BizResult<List<Product>> result);

		void resultFaild(BizResult<List<Product>> result);
	}

	public void setCompletedListener(TaskCompletedListener listener) {
		mListener = listener;
	}
}

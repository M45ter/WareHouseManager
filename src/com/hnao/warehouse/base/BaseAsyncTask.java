package com.hnao.warehouse.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class BaseAsyncTask<C, C1, C2> extends AsyncTask<C, C1, C2>
{
	protected Context mContext;
	protected ProgressDialog mDialog;
	private String mMsg="正在加载，请稍后...";
	
	private boolean mShowDialog = true;
	
	public BaseAsyncTask(Context context)
	{
		mContext = context;
	}
	
	public BaseAsyncTask(Context context, boolean showDialog)
	{
		mContext = context;
		mShowDialog = showDialog;
	}
	
	public BaseAsyncTask(Context context,String strMsg)
	{
		mContext = context;
		if(null != strMsg && 0<strMsg.length())
			mMsg = strMsg;
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		if (mShowDialog){
			mDialog = new ProgressDialog(mContext);
			mDialog.setMessage(mMsg);
			mDialog.setIndeterminate(false);
			mDialog.setCancelable(false);
			mDialog.show();
		}
	}
	
	@Override
	protected C2 doInBackground(C... params)
	{
		return null;
	}
	
	@Override
	protected void onProgressUpdate(C1... values)
	{
		super.onProgressUpdate(values);
		if (mShowDialog){
			if (null != mDialog && mDialog.isShowing())
				mDialog.cancel();
		}
	}
	
	@Override
	protected void onPostExecute(C2 result)
	{
		super.onPostExecute(result);
		if (mShowDialog){
			if (null != mDialog && mDialog.isShowing())
				mDialog.cancel();
		}
	}	
}
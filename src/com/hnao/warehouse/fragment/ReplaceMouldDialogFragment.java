package com.hnao.warehouse.fragment;

import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.args.JLWArgs;
import com.hnao.warehouse.task.BindMouldAndDeviceTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ReplaceMouldDialogFragment extends DialogFragment {

	private JLWArgs mArgs;

	public ReplaceMouldDialogFragment(JLWArgs args) {
		super();
		this.mArgs = args;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Context context = getActivity();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("提示").setMessage(mArgs.message).setNegativeButton("取消", null);

		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				BindMouldAndDeviceTask task = new BindMouldAndDeviceTask(context, false);
				task.setCompletedListener(new BindMouldAndDeviceTask.TaskCompletedListener() {

					@Override
					public void resultSucceed(BizResult<JLWArgs> result) {
						// TODO Auto-generated method stub
						if (mListener != null) {
							mListener.ReplaceMouldSucceed(result.Data);
						}
					}

					@Override
					public void resultFaild(BizResult<JLWArgs> result) {
						// TODO Auto-generated method stub
						if (mListener != null) {
							mListener.ReplaceMouldFaild();
						}
					}
				});
				mArgs.action = 1;
				mArgs.message = null;
				task.execute(mArgs);
			}
		});

		return builder.create();
	}

	public interface ReplaceMouldListener {
		void ReplaceMouldSucceed(JLWArgs args);

		void ReplaceMouldFaild();
	}

	private ReplaceMouldListener mListener;

	public void setReplaceMouldListener(ReplaceMouldListener listener) {
		mListener = listener;
	}

}

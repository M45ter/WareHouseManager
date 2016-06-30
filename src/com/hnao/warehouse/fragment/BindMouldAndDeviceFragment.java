package com.hnao.warehouse.fragment;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.args.JLWArgs;
import com.hnao.warehouse.task.BindMouldAndDeviceTask;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BindMouldAndDeviceFragment extends Fragment {

	public final static String TAG = "BindMouldAndDeviceFragment";

	private Context mContext;

	private TextView tvWorkNo;

	private TextView tvMouldNo;

	private Button btnScanWork;

	private Button btnScanMould;

	private Button btnBind;

	private final static int REQ_CODE_WORK = 100;

	private final static int REQ_CODE_MOULD = 101;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_bind_mould, container, false);

		mContext = getActivity();

		tvWorkNo = (TextView) view.findViewById(R.id.tv_work_no);

		tvMouldNo = (TextView) view.findViewById(R.id.tv_mould_no);

		btnScanWork = (Button) view.findViewById(R.id.btn_scan_work);

		btnScanMould = (Button) view.findViewById(R.id.btn_scan_mould);

		btnBind = (Button) view.findViewById(R.id.btn_bind);

		btnScanWork.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent("com.zey.zxing.SCAN_CODE");
					startActivityForResult(intent, REQ_CODE_WORK);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "start intent failed!");
					Utils.showStringToast(mContext, "没有找到扫码的界面！");
				}
			}
		});

		btnScanMould.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent("com.zey.zxing.SCAN_CODE");
					startActivityForResult(intent, REQ_CODE_MOULD);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "start intent failed!");
					Utils.showStringToast(mContext, "没有找到扫码的界面！");
				}
			}
		});

		btnBind.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String idNo = tvWorkNo.getText().toString();
				String mouldNo = tvMouldNo.getText().toString();
				if (idNo.equals("")) {
					Utils.showStringToast(mContext, "工位编码不能为空！");
					return;
				}
				if (mouldNo.equals("")) {
					Utils.showStringToast(mContext, "模具编码不能为空！");
					return;
				}
				BindMouldAndDeviceTask task = new BindMouldAndDeviceTask(mContext);
				task.setCompletedListener(new BindMouldAndDeviceTask.TaskCompletedListener() {

					@Override
					public void resultSucceed(BizResult<JLWArgs> result) {
						// TODO Auto-generated method stub
						JLWArgs reArgs = result.Data;
						handleResult(reArgs);
					}

					@Override
					public void resultFaild(BizResult<JLWArgs> result) {
						// TODO Auto-generated method stub
						Utils.showStringToast(mContext, "绑定异常，检查网络或者服务器！");
					}
				});
				JLWArgs args = new JLWArgs();
				args.action = 0;
				args.idNo = idNo;
				args.mouldStockIdNo = mouldNo;
				task.execute(args);

			}
		});

		return view;

	}

	private void handleResult(JLWArgs reArgs) {
		FragmentManager fragmentManager = getFragmentManager();
		switch (reArgs.action) {
		case 2:
			ReplaceMouldDialogFragment replaceFragment = new ReplaceMouldDialogFragment(reArgs);
			replaceFragment.setReplaceMouldListener(new ReplaceMouldDialogFragment.ReplaceMouldListener() {

				@Override
				public void ReplaceMouldSucceed(JLWArgs args) {
					// TODO Auto-generated method stub
					handleResult(args);
				}

				@Override
				public void ReplaceMouldFaild() {
					// TODO Auto-generated method stub
					Utils.showStringToast(mContext, "更换失败！");
				}
			});

			replaceFragment.show(fragmentManager, "ShowReplaceDialog");
			break;

		case 8:
			SimpleDialogFragment fragment = new SimpleDialogFragment(reArgs.message);

			fragment.show(fragmentManager, "Action8Dialog");
			break;

		case 9:
			Utils.showStringToast(mContext, "装配成功！");
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onActivityResult");
		if (requestCode == REQ_CODE_WORK) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String result = extras.getString("result");
					tvWorkNo.setText(result);
				}
			}
		} else if (requestCode == REQ_CODE_MOULD) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String result = extras.getString("result");
					tvMouldNo.setText(result);
				}
			}
		}
	}

}

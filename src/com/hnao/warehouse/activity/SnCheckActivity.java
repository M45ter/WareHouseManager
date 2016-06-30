package com.hnao.warehouse.activity;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.beans.ComConst;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.task.GetOperatorDbConnectionTask;
import com.hnao.warehouse.task.GetOperatorDbConnectionTask.TaskCompletedListener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class SnCheckActivity extends Activity {

	public final static String TAG = "LoginActivity";

	private Context mContext;

	private EditText etSn;

	private Button btnCheck;

	private CheckBox cbSnCheck;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sn_check);

		mContext = this;

		etSn = (EditText) findViewById(R.id.et_sn);

		btnCheck = (Button) findViewById(R.id.btn_check);

		cbSnCheck = (CheckBox) findViewById(R.id.cb_sn_check);

		sp = getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);

		sp.registerOnSharedPreferenceChangeListener(mListener);

		boolean isSNCheck = sp.getBoolean(ComConst.SN_CHECK, false);

		cbSnCheck.setChecked(isSNCheck);

		cbSnCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor editor = sp.edit();
				editor.putBoolean(ComConst.SN_CHECK, isChecked);
				editor.commit();
				if (!isChecked) {
					setResult(RESULT_CANCELED);
					finish();
				}
			}
		});

		btnCheck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				snCheck();
			}
		});

		String sn = sp.getString(ComConst.SN, "");
		if (!sn.equals("")) {
			etSn.setText(sn);
			snCheck();
		}

	}

	private void snCheck() {
		final String sn = etSn.getText().toString();
		if (sn.equals("")) {
			Utils.showStringToast(mContext, "序列号不能为空！");
			return;
		}
		GetOperatorDbConnectionTask task = new GetOperatorDbConnectionTask(mContext);
		task.setCompletedListener(new TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<String> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "验证成功！");
				Editor editor = sp.edit();
				editor.putString(ComConst.SN, sn);
				editor.commit();
				setResult(RESULT_OK);
				finish();
			}

			@Override
			public void resultFaild(BizResult<String> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "验证失败！");
			}
		});
		task.execute(sn);
	}

	private OnSharedPreferenceChangeListener mListener = new OnSharedPreferenceChangeListener() {

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			// TODO Auto-generated method stub
			if (key.equals(ComConst.SN_CHECK)) {
				boolean isSNCheck = sharedPreferences.getBoolean(ComConst.SN_CHECK, false);
				cbSnCheck.setChecked(isSNCheck);
			}
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Editor editor = sp.edit();
		editor.putBoolean(ComConst.SN_CHECK, false);
		editor.commit();
		super.onBackPressed();
	}

}

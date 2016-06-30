package com.hnao.warehouse.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComConst;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.User;
import com.hnao.warehouse.domain.args.OperatorLogin;
import com.hnao.warehouse.fragment.ShowSnDialogFragment;
import com.hnao.warehouse.poxy.OperatorProxy;
import com.hnao.warehouse.task.GetOperatorDbConnectionByOperatorTask;

public class LoginActivity extends Activity {

	public final static String TAG = "LoginActivity";

	private EditText mEtId;

	private EditText mEtPwd;

	private Button mBtnLogin;

	private String mStringId;

	private String mStringPwd;

	private Context mContext;

	private boolean isSNCheck;

	private final static int REQ_CODE = 100;

	private SharedPreferences sp;

	private CheckBox cbSnCheck;

	private boolean isLogin = false;

	private boolean configCheckCompany;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		mEtId = (EditText) findViewById(R.id.login_edtId);
		mEtPwd = (EditText) findViewById(R.id.login_edtPwd);

		mBtnLogin = (Button) findViewById(R.id.login_btnLogin);

		cbSnCheck = (CheckBox) findViewById(R.id.cb_sn_check);

		configCheckCompany = getResources().getBoolean(R.bool.bool_check_company);

		if (configCheckCompany) {

			sp = getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);

			isSNCheck = sp.getBoolean(ComConst.SN_CHECK, false);

			sp.registerOnSharedPreferenceChangeListener(mListener);

			cbSnCheck.setChecked(isSNCheck);

			cbSnCheck.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d(TAG, "cbSnCheck onClick");
					boolean isChecked = cbSnCheck.isChecked();
					Editor editor = sp.edit();
					editor.putBoolean(ComConst.SN_CHECK, isChecked);
					editor.commit();
					isSNCheck = isChecked;
					if (isSNCheck) {
						Intent intent = new Intent(LoginActivity.this, SnCheckActivity.class);
						startActivityForResult(intent, REQ_CODE);
					}
				}
			});

			cbSnCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					Log.d(TAG, "cbSnCheck onCheckedChanged isChecked = " + isChecked);
					// Editor editor = sp.edit();
					// editor.putBoolean(ComConst.SN_CHECK, isChecked);
					// editor.commit();
					// isSNCheck = isChecked;
					// if (isSNCheck) {
					// Intent intent = new Intent(LoginActivity.this,
					// SnCheckActivity.class);
					// startActivityForResult(intent, REQ_CODE);
					// }
				}
			});

			if (isSNCheck) {
				Intent intent = new Intent(LoginActivity.this, SnCheckActivity.class);
				startActivityForResult(intent, REQ_CODE);
			}
		} else {
			isLogin = true;
			cbSnCheck.setVisibility(View.GONE);
			mEtId.setHint(R.string.idtxthint);
			mEtId.setText("");
			mEtPwd.setText("");
			mEtId.requestFocus();
			mBtnLogin.setText(R.string.loginbtntext);
		}

		mBtnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mStringId = mEtId.getText().toString();
				mStringPwd = mEtPwd.getText().toString();
				if (mStringId != null && mStringId != null && (mStringId.equals("") || mStringPwd.equals(""))) {
					Utils.showStringToast(mContext, "帐号和密码均不能为空！");
					return;
				}
				if (!Utils.isNetworkAvailable(mContext)) {
					Utils.showStringToast(mContext, "网络不可用，请检查！");
					return;
				}
				if (isLogin) {
					login();
				} else {
					check();
				}
			}
		});
	}

	private OnSharedPreferenceChangeListener mListener = new OnSharedPreferenceChangeListener() {

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			// TODO Auto-generated method stub
			if (key.equals(ComConst.SN_CHECK)) {
				isSNCheck = sharedPreferences.getBoolean(ComConst.SN_CHECK, false);
				cbSnCheck.setChecked(isSNCheck);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onActivityResult requestCode = " + requestCode + " resultCode = " + resultCode);
		if (requestCode == REQ_CODE) {
			if (resultCode == RESULT_CANCELED) {

			} else if (resultCode == RESULT_OK) {
				isLogin = true;
				cbSnCheck.setVisibility(View.GONE);
				mEtId.setHint(R.string.idtxthint);
				mEtId.setText("");
				mEtPwd.setText("");
				mEtId.requestFocus();
				mBtnLogin.setText(R.string.loginbtntext);
			}
		}
	}

	private void check() {
		Log.d(TAG, "check mStringId = " + mStringId + " mStringPwd = " + mStringPwd);
		GetOperatorDbConnectionByOperatorTask task = new GetOperatorDbConnectionByOperatorTask(mContext);
		task.setCompletedListener(new GetOperatorDbConnectionByOperatorTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<String> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "验证成功！");
				Editor editor = sp.edit();
				editor.putBoolean(ComConst.SN_CHECK, true);
				editor.putString(ComConst.SN, result.Data);
				editor.commit();
				isLogin = true;
				cbSnCheck.setVisibility(View.GONE);
				mEtId.setHint(R.string.idtxthint);
				mEtId.setText("");
				mEtPwd.setText("");
				mEtId.requestFocus();
				mBtnLogin.setText(R.string.loginbtntext);

				ShowSnDialogFragment fragment = new ShowSnDialogFragment();
				FragmentManager fragmentManager = getFragmentManager();

				fragment.show(fragmentManager, "ShowSnDialog");
			}

			@Override
			public void resultFaild(BizResult<String> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "验证失败！");
			}
		});
		task.execute(mStringId, mStringPwd);
	}

	private void login() {
		Log.d(TAG, "login mStringId = " + mStringId + " mStringPwd = " + mStringPwd);
		new BaseAsyncTask<String, String, BizResult<Operator>>(mContext, "正在登录...") {
			@Override
			protected BizResult<Operator> doInBackground(String... params) {
				/*
				 * try { Thread.sleep(3000); } catch (InterruptedException e) {
				 * // TODO Auto-generated catch block e.printStackTrace(); }
				 * return null;
				 */
				BizResult<Operator> Re = null;
				if (null != params && params.length > 1) {
					try {
						OperatorLogin model = new OperatorLogin();
						model.UserName = params[0];
						model.Password = params[1];
						Re = OperatorProxy.getInstance(mContext).Login(model);
					} catch (Exception e) {
						Log.e(TAG, ComFunc.getExceptionMessage(e), e);
					}
				}
				return Re;
			}

			@Override
			protected void onPostExecute(BizResult<Operator> result) {
				super.onPostExecute(result);

				if (result != null && result.Success) {
					Log.d(TAG, "login success!");
					Log.d(TAG, "result : " + result.Data);
					User.getInstance(mContext).login(result.Data);
					Intent intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Log.d(TAG, "login failed!");
					Utils.showStringToast(mContext, "用户名或密码错误！");
				}

			}
		}.execute(mStringId, mStringPwd);
	}

}

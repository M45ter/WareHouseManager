package com.hnao.warehouse.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.adapter.AttrValueSpAdapter;
import com.hnao.warehouse.adapter.HouseSpAdapter;
import com.hnao.warehouse.adapter.OperatorSpAdapter;
import com.hnao.warehouse.adapter.ProductSpAdapter;
import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.ApplyItem;
import com.hnao.warehouse.domain.AttrValue;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.Product;
import com.hnao.warehouse.domain.SheetTypeEnum;
import com.hnao.warehouse.domain.StoreHouse;
import com.hnao.warehouse.domain.User;
import com.hnao.warehouse.domain.args.AttrDefArgs;
import com.hnao.warehouse.domain.args.OperatorArgs;
import com.hnao.warehouse.domain.args.ProductArgs;
import com.hnao.warehouse.domain.args.StoreHouseArgs;
import com.hnao.warehouse.poxy.ApplyProxy;
import com.hnao.warehouse.poxy.AttrValueProxy;
import com.hnao.warehouse.poxy.OperatorProxy;
import com.hnao.warehouse.poxy.ProductProxy;
import com.hnao.warehouse.poxy.SerialNumberProxy;
import com.hnao.warehouse.poxy.StoreHouseProxy;
import com.hnao.warehouse.task.AddApplyTask;
import com.hnao.warehouse.task.GetAttrValueListTask;
import com.hnao.warehouse.task.GetNextSerialNumberTask;
import com.hnao.warehouse.task.GetOperatorListTask;
import com.hnao.warehouse.task.GetProductInfoListTask;
import com.hnao.warehouse.task.GetStoreHouseListTask;
import com.hnao.warehouse.task.GetSystemDateTask;
import com.hnao.warehouse.view.OneDayPickerView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.media.session.MediaButtonReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HouseInAddFragment extends Fragment {

	public final static String TAG = "HouseInAddFragment";

	private Context mContext;

	private EditText mEtBillNum;

	private TextView mTvBillDate;

	private CheckBox mCheckOrderBill;

	private String mStringBillDate = "";

	private OneDayPickerView mDatePickerView;

	private List<StoreHouse> storeHouseList = new ArrayList<StoreHouse>();

	private Spinner houseSp;

	private HouseSpAdapter houseSpAdapter;

	private StoreHouse mStoreHouse;

	private List<Operator> operatorList = new ArrayList<Operator>();

	private Spinner operatorSp;

	private Operator mOperator;

	private OperatorSpAdapter operatorSpAdapter;

	private List<AttrValue> attrValueList = new ArrayList<AttrValue>();

	private Spinner attrValueSp;

	private AttrValue mAttrValue;

	private AttrValueSpAdapter attrValueSpAdapter;

	private EditText EtapplyMemo;

	private Apply mApply;

	private Apply mReturnApply;

	private Button mBtnSave;

	private Button mBtnCancel;

	private TableLayout mTable;

	private List<Product> productList = new ArrayList<Product>();

	private ProductSpAdapter productSpAdapter;

	private List<String> productGuidList = new ArrayList<String>();

	private List<EditText> productQuantityEtList = new ArrayList<EditText>();;

	private List<String> productQuantityList = new ArrayList<String>();;

	private List<String> productMemoList = new ArrayList<String>();;

	private List<EditText> productMemoEtList = new ArrayList<EditText>();;

	private int mProductItemCount = 0;

	private Button mBtnAddProduct;

	private Button mBtnCutProduct;

	private Date readyApplyTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.house_in_data, container, false);

		mContext = getActivity();

		mEtBillNum = (EditText) view.findViewById(R.id.et_bill_num);

		// mTvBillDate = (TextView) view.findViewById(R.id.tv_bill_date);

		mDatePickerView = (OneDayPickerView) view.findViewById(R.id.date_picker_view);

		mCheckOrderBill = (CheckBox) view.findViewById(R.id.check_order_bill);

		EtapplyMemo = (EditText) view.findViewById(R.id.et_memo);

		mBtnSave = (Button) view.findViewById(R.id.btn_save);

		mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);

		mBtnAddProduct = (Button) view.findViewById(R.id.btn_add_row);

		mBtnCutProduct = (Button) view.findViewById(R.id.btn_cut_row);

		mBtnSave.setOnClickListener(clickListener);

		mBtnCancel.setOnClickListener(clickListener);

		mBtnAddProduct.setOnClickListener(clickListener);

		mBtnCutProduct.setOnClickListener(clickListener);

		mTable = (TableLayout) view.findViewById(R.id.product_list);

		// productSpAdapter = new ProductSpAdapter(mContext, productList);

		// mTvBillDate.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// DatePickerFragment datePickerFragment = new DatePickerFragment();
		// int year = 0, month = 0, day = 0;
		// if (!TextUtils.isEmpty(mStringBillDate)) {
		// String[] strings = mStringBillDate.split("-");
		// if (strings.length == 3) {
		// year = Integer.parseInt(strings[0]);
		// month = Integer.parseInt(strings[1]) - 1;
		// day = Integer.parseInt(strings[2]);
		// }
		// }
		// Bundle bundle = new Bundle();
		// bundle.putInt("year", year);
		// bundle.putInt("month", month);
		// bundle.putInt("day", day);
		// datePickerFragment.setArguments(bundle);
		// datePickerFragment.setDateSelectedListener(new
		// DatePickerFragment.OnDateSelectedListener() {
		//
		// @Override
		// public void onDateSelected(int year, int month, int day) {
		// // TODO Auto-generated method stub
		// String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
		// mStringBillDate = date;
		// mTvBillDate.setText(mStringBillDate);
		// }
		// });
		// datePickerFragment.show(((Activity) mContext).getFragmentManager(),
		// "Dialog");
		// }
		// });

		houseSp = (Spinner) view.findViewById(R.id.sp_house_name);

		// houseSpAdapter = new HouseSpAdapter(mContext, storeHouseList);

		houseSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				mStoreHouse = houseSpAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		operatorSp = (Spinner) view.findViewById(R.id.sp_hander_name);

		// operatorSpAdapter = new OperatorSpAdapter(mContext, operatorList);

		operatorSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				mOperator = operatorSpAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		attrValueSp = (Spinner) view.findViewById(R.id.sp_attr_value);

		// attrValueSpAdapter = new AttrValueSpAdapter(mContext, attrValueList);

		attrValueSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				mAttrValue = attrValueSpAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		// new GetNextSerialNumberTask(mContext).execute("17");
		GetNextSerialNumberTask getNextSerialNumberTask = new GetNextSerialNumberTask(mContext, false);

		getNextSerialNumberTask.setCompletedListener(new GetNextSerialNumberTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<String> result) {
				// TODO Auto-generated method stub
				mEtBillNum.setText(result.Data);
			}

			@Override
			public void resultFaild(BizResult<String> result) {
				// TODO Auto-generated method stub

			}
		});
		getNextSerialNumberTask.execute(SheetTypeEnum.InStore);

		// new GetSystemDateTask(mContext).execute("");
		GetSystemDateTask getSystemDateTask = new GetSystemDateTask(mContext, false);

		getSystemDateTask.setCompletedListener(new GetSystemDateTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<Date> result) {
				// TODO Auto-generated method stub
				readyApplyTime = result.Data;
				Log.d(TAG, "date = " + readyApplyTime);
				mDatePickerView.setDate(readyApplyTime);
			}

			@Override
			public void resultFaild(BizResult<Date> result) {
				// TODO Auto-generated method stub

			}
		});
		getSystemDateTask.execute("");

		StoreHouseArgs houseArgs = new StoreHouseArgs();

		// houseArgs.InputCode = "1234";

		// new GetStoreHouseListTask(mContext).execute(houseArgs);
		GetStoreHouseListTask getStoreHouseListTask = new GetStoreHouseListTask(mContext, false);
		getStoreHouseListTask.setCompletedListener(new GetStoreHouseListTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<List<StoreHouse>> result) {
				// TODO Auto-generated method stub
				storeHouseList = result.Data;
				houseSpAdapter = new HouseSpAdapter(mContext, storeHouseList);
				houseSp.setAdapter(houseSpAdapter);
			}

			@Override
			public void resultFaild(BizResult<List<StoreHouse>> result) {
				// TODO Auto-generated method stub

			}
		});
		getStoreHouseListTask.execute(houseArgs);

		OperatorArgs operatorArgs = new OperatorArgs();

		// new GetOperatorListTask(mContext).execute(operatorArgs);
		GetOperatorListTask getOperatorListTask = new GetOperatorListTask(mContext, false);
		getOperatorListTask.setCompletedListener(new GetOperatorListTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<List<Operator>> result) {
				// TODO Auto-generated method stub
				operatorList = result.Data;
				operatorSpAdapter = new OperatorSpAdapter(mContext, operatorList);
				operatorSp.setAdapter(operatorSpAdapter);
				for (int i = 0; i < operatorList.size(); i++) {
					Operator operator = operatorList.get(i);
					if (operator.Guid.equals(User.getInstance(mContext).Guid)) {
						operatorSp.setSelection(i);
					}
				}
			}

			@Override
			public void resultFaild(BizResult<List<Operator>> result) {
				// TODO Auto-generated method stub

			}
		});
		getOperatorListTask.execute(operatorArgs);

		// new GetAttrValueListTask(mContext).execute("00007");
		GetAttrValueListTask getAttrValueListTask = new GetAttrValueListTask(mContext, false);
		getAttrValueListTask.setCompletedListener(new GetAttrValueListTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<List<AttrValue>> result) {
				// TODO Auto-generated method stub
				attrValueList = result.Data;
				attrValueSpAdapter = new AttrValueSpAdapter(mContext, attrValueList);
				attrValueSp.setAdapter(attrValueSpAdapter);
			}

			@Override
			public void resultFaild(BizResult<List<AttrValue>> result) {
				// TODO Auto-generated method stub

			}
		});
		getAttrValueListTask.execute("00007");

		ProductArgs productArgs = new ProductArgs();

		// new GetProductInfoListTask(mContext).execute(productArgs);
		GetProductInfoListTask getProductInfoListTask = new GetProductInfoListTask(mContext);
		getProductInfoListTask.setCompletedListener(new GetProductInfoListTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<List<Product>> result) {
				// TODO Auto-generated method stub
				productList = result.Data;
				productSpAdapter = new ProductSpAdapter(mContext, productList);
				appendNewRow();
			}

			@Override
			public void resultFaild(BizResult<List<Product>> result) {
				// TODO Auto-generated method stub

			}
		});
		getProductInfoListTask.execute(productArgs);

		return view;
	}

	private void appendNewRow() {

		final int curCount = mProductItemCount++;

		Log.d(TAG, "appendNewRow curCount = " + curCount);

		TableRow row = new TableRow(mContext);

		row.setGravity(Gravity.CENTER);

		// android.view.ViewGroup.LayoutParams params;

		final TextView tvProductNum = new TextView(mContext);

		tvProductNum.setGravity(Gravity.CENTER);

		tvProductNum.setTextSize(12);

		// tvProductNum.setBackgroundColor(0xff00ff00);

		final TextView tvUnit = new TextView(mContext);

		tvUnit.setGravity(Gravity.CENTER);

		tvUnit.setTextSize(12);

		final EditText etCount = new EditText(mContext);

		etCount.setSingleLine(true);

		etCount.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

		etCount.setGravity(Gravity.CENTER);

		etCount.setTextSize(12);

		etCount.setText("1");

		productQuantityEtList.add(curCount, etCount);

		final EditText etMemo = new EditText(mContext);

		etMemo.setSingleLine(true);

		etMemo.setGravity(Gravity.CENTER);

		etMemo.setTextSize(12);

		productMemoEtList.add(curCount, etMemo);

		Spinner spProductName = new Spinner(mContext);

		spProductName.setGravity(Gravity.CENTER);

		spProductName.setAdapter(productSpAdapter);

		spProductName.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				tvProductNum.setText(productList.get(position).getIdNo());
				tvUnit.setText(productList.get(position).getUnitName());
				etCount.setText("1");
				productGuidList.add(curCount, productList.get(position).getGuid());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		row.addView(tvProductNum);
		row.addView(spProductName);
		row.addView(tvUnit);
		row.addView(etCount);
		row.addView(etMemo);

		mTable.addView(row);

	}

	private void cutLastRow() {
		Log.d(TAG, "cutLastRow mProductItemCount = " + mProductItemCount);
		Log.d(TAG, "mTable.getChildCount = " + mTable.getChildCount());
		if (mProductItemCount > 1) {
			mTable.removeViewAt(mProductItemCount);
			mProductItemCount--;
			productGuidList.remove(mProductItemCount);
			productQuantityEtList.remove(mProductItemCount);
			productMemoEtList.remove(mProductItemCount);
		} else {
			Utils.showStringToast(mContext, "至少需要一种商品！");
		}
	}

	private void backToLastFt() {
		FragmentManager fragmentManager = getFragmentManager();
		// FragmentTransaction ft = fragmentManager.beginTransaction();
		fragmentManager.popBackStack();
		// ft.commit();
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_save:
				saveApply();
				break;
			case R.id.btn_cancel:
				backToLastFt();
				break;
			case R.id.btn_add_row:
				appendNewRow();
				break;
			case R.id.btn_cut_row:
				cutLastRow();
				break;

			default:
				break;
			}
		}
	};

	private void saveApply() {
		Apply apply = new Apply();
		apply.Action = 1;
		apply.Status = "00006001";
		String billNum = mEtBillNum.getText().toString();
		if (billNum.equals("")) {
			Utils.showStringToast(mContext, "单据编号不能为空！");
			return;
		} else {
			apply.IdNo = billNum;
			Log.d(TAG, "apply.IdNo = " + apply.IdNo);
		}

		Date date = mDatePickerView.getDate();
		String dateStr = mDatePickerView.getDateStr();
		if (dateStr.equals("") || date == null) {
			Utils.showStringToast(mContext, "单据日期不能为空！");
			return;
		} else {
			apply.ApplyTime = mDatePickerView.getDate();
			Log.d(TAG, "apply.ApplyTime = " + apply.ApplyTime);
		}

		if (!(houseSp.getSelectedItemPosition() >= 0)) {
			Utils.showStringToast(mContext, "收货仓库不能为空！！");
			return;
		} else {
			apply.ToStoreGuid = mStoreHouse.Guid;
			Log.d(TAG, "apply.ToStoreGuid = " + apply.ToStoreGuid);
		}

		if (!(operatorSp.getSelectedItemPosition() >= 0)) {
			Utils.showStringToast(mContext, "经手人不能为空！！");
			return;
		} else {
			apply.HandlerGuid = mOperator.Guid;
			Log.d(TAG, "apply.HandlerGuid = " + apply.HandlerGuid);
		}

		if (!(attrValueSp.getSelectedItemPosition() >= 0)) {
			Utils.showStringToast(mContext, "入库类型不能为空！！");
			return;
		} else {
			apply.Type = mAttrValue.Code;// Text
			Log.d(TAG, "apply.Type = " + apply.Type);
		}

		apply.Memo = EtapplyMemo.getText().toString();

		Log.d(TAG, "apply.Memo = " + apply.Memo);

		for (int i = 0; i < mProductItemCount; i++) {
			ApplyItem applyItem = new ApplyItem();
			applyItem.ProductGuid = productGuidList.get(i);
			applyItem.Quantity = Integer.parseInt(productQuantityEtList.get(i).getText().toString());
			if (!(applyItem.Quantity > 0 && applyItem.Quantity < 1000)) {
				Utils.showStringToast(mContext, "第" + i + "行的商品数量要在1~999！");
				return;
			}
			applyItem.Memo = productMemoEtList.get(i).getText().toString();

			Log.d(TAG, i + ":applyItem.ProductGuid = " + applyItem.ProductGuid + ", applyItem.Quantity = "
					+ applyItem.Quantity + ", applyItem.Memo = " + applyItem.Memo);

			apply.ApplyItem.add(applyItem);
		}

		mApply = apply;

		// new AddApplyTask(mContext).execute(mApply);
		AddApplyTask addApplyTask = new AddApplyTask(mContext);
		addApplyTask.setCompletedListener(new AddApplyTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<Apply> result) {
				// TODO Auto-generated method stub
				mReturnApply = result.Data;
				Utils.showStringToast(mContext, "保存成功！");
				Log.d(TAG, "mReturnApply CreateTime = " + mReturnApply.CreateTime + " UpdateTime"
						+ mReturnApply.UpdateTime);
				backToLastFt();
			}

			@Override
			public void resultFaild(BizResult<Apply> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "保存失败！");
			}
		});
		addApplyTask.execute(mApply);
	}

	/*
	 * class GetNextSerialNumberTask extends BaseAsyncTask<String, String,
	 * BizResult<String>> {
	 * 
	 * public GetNextSerialNumberTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<String> doInBackground(String... params) {
	 * // TODO: attempt authentication against a network service.
	 * BizResult<String> Re = null; if (null != params && params.length > 0) {
	 * try { Re =
	 * SerialNumberProxy.getInstance(mContext).GetNextSerialNumber(params[0]); }
	 * catch (Exception e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } }
	 * return Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<String> result) {
	 * super.onPostExecute(result); if (result != null && result.Success) {
	 * Log.d(TAG, "GetNextSerialNumber success!");
	 * mEtBillNum.setText(result.Data); } else { Log.d(TAG,
	 * "GetNextSerialNumber failed!");
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * 
	 * class GetSystemDateTask extends BaseAsyncTask<String, String,
	 * BizResult<Date>> {
	 * 
	 * public GetSystemDateTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<Date> doInBackground(String... params) { //
	 * TODO: attempt authentication against a network service. BizResult<Date>
	 * Re = null; if (null != params && params.length > 0) { try { Re =
	 * SerialNumberProxy.getInstance(mContext).GetSystemDate(); } catch
	 * (Exception e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } } return
	 * Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<Date> result) {
	 * super.onPostExecute(result); if (result != null && result.Success) {
	 * Log.d(TAG, "GetSystemDate success!"); readyApplyTime = result.Data;
	 * Log.d(TAG, "date = " + readyApplyTime); // String dateTime = result.Data;
	 * // int year = 0, month = 0, day = 0; // if (!TextUtils.isEmpty(dateTime))
	 * { // String[] strings = dateTime.split("-"); // if (strings.length == 3)
	 * { // year = Integer.parseInt(strings[0]); // month =
	 * Integer.parseInt(strings[1]) - 1; // day =
	 * Integer.parseInt(strings[2].substring(0, 2)); // Log.d(TAG, "day:" +
	 * strings[2].substring(0, 2)); // } // }
	 * mDatePickerView.setDate(readyApplyTime); } else { Log.d(TAG,
	 * "GetSystemDate failed!");
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * class GetStoreHouseListTask extends BaseAsyncTask<StoreHouseArgs, String,
	 * BizResult<List<StoreHouse>>> {
	 * 
	 * public GetStoreHouseListTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<List<StoreHouse>>
	 * doInBackground(StoreHouseArgs... params) { // TODO: attempt
	 * authentication against a network service. BizResult<List<StoreHouse>> Re
	 * = null; if (null != params && params.length > 0) { try { Re =
	 * StoreHouseProxy.getInstance(mContext).GetStoreHouseList(params[0]); }
	 * catch (Exception e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } }
	 * return Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<List<StoreHouse>>
	 * result) { super.onPostExecute(result); if (result != null &&
	 * result.Success) { Log.d(TAG, "GetStoreHouseList success!");
	 * storeHouseList = result.Data; houseSp.setAdapter(houseSpAdapter); } else
	 * { Log.d(TAG, "GetStoreHouseList failed!"); } } }
	 * 
	 * class GetOperatorListTask extends BaseAsyncTask<OperatorArgs, String,
	 * BizResult<List<Operator>>> {
	 * 
	 * public GetOperatorListTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<List<Operator>>
	 * doInBackground(OperatorArgs... params) { // TODO: attempt authentication
	 * against a network service. BizResult<List<Operator>> Re = null; if (null
	 * != params && params.length > 0) { try { Re =
	 * OperatorProxy.getInstance(mContext).GetOperatorList(params[0]); } catch
	 * (Exception e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } } return
	 * Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<List<Operator>> result)
	 * { super.onPostExecute(result); if (result != null && result.Success) {
	 * Log.d(TAG, "GetOperatorList success!"); operatorList = result.Data;
	 * operatorSp.setAdapter(operatorSpAdapter); for (int i = 0; i <
	 * operatorList.size(); i++) { Operator operator = operatorList.get(i); if
	 * (operator.Guid.equals(User.getInstance(mContext).Guid)) {
	 * operatorSp.setSelection(i); } }
	 * 
	 * } else { Log.d(TAG, "GetOperatorList failed!"); } } }
	 * 
	 * class GetAttrValueListTask extends BaseAsyncTask<String, String,
	 * BizResult<List<AttrValue>>> {
	 * 
	 * public GetAttrValueListTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<List<AttrValue>> doInBackground(String...
	 * params) { // TODO: attempt authentication against a network service.
	 * BizResult<List<AttrValue>> Re = null; if (null != params && params.length
	 * > 0) { try { Re =
	 * AttrValueProxy.getInstance(mContext).GetAttrValueList(params[0]); } catch
	 * (Exception e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } } return
	 * Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<List<AttrValue>> result)
	 * { super.onPostExecute(result); if (result != null && result.Success) {
	 * Log.d(TAG, "GetAttrValueList success!"); attrValueList = result.Data;
	 * attrValueSp.setAdapter(attrValueSpAdapter); } else { Log.d(TAG,
	 * "GetAttrValueList failed!"); } } }
	 * 
	 * class GetProductInfoListTask extends BaseAsyncTask<ProductArgs, String,
	 * BizResult<List<Product>>> {
	 * 
	 * public GetProductInfoListTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<List<Product>>
	 * doInBackground(ProductArgs... params) { // TODO: attempt authentication
	 * against a network service. BizResult<List<Product>> Re = null; if (null
	 * != params && params.length > 0) { try { Re =
	 * ProductProxy.getInstance(mContext).GetProductInfoList(params[0]); } catch
	 * (Exception e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } } return
	 * Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<List<Product>> result) {
	 * super.onPostExecute(result); if (result != null && result.Success) {
	 * Log.d(TAG, "GetProductInfoList success!"); productList = result.Data;
	 * appendNewRow(); } else { Log.d(TAG, "GetProductInfoList failed!"); } } }
	 * 
	 * class AddApplyTask extends BaseAsyncTask<Apply, String, BizResult<Apply>>
	 * {
	 * 
	 * public AddApplyTask(Context context) { super(context); // TODO
	 * Auto-generated constructor stub }
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute(); }
	 * 
	 * @Override protected BizResult<Apply> doInBackground(Apply... params) { //
	 * TODO: attempt authentication against a network service. BizResult<Apply>
	 * Re = null; if (null != params && params.length > 0) { try { Re =
	 * ApplyProxy.getInstance(mContext).AddApply(params[0]); } catch (Exception
	 * e) { Log.e(TAG, ComFunc.getExceptionMessage(e), e); } } return Re; }
	 * 
	 * @Override protected void onPostExecute(BizResult<Apply> result) {
	 * super.onPostExecute(result); if (result != null && result.Success) {
	 * Log.d(TAG, "AddApply success!"); mReturnApply = result.Data;
	 * Utils.showStringToast(mContext, "保存成功！"); Log.d(TAG,
	 * "mReturnApply CreateTime = "+mReturnApply.CreateTime +" UpdateTime"
	 * +mReturnApply.UpdateTime); backToLastFt(); } else { Log.d(TAG,
	 * "AddApply failed!"); Utils.showStringToast(mContext, "保存失败！"); } } }
	 * 
	 * class HouseSpAdapter extends BaseAdapter {
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return storeHouseList.size(); }
	 * 
	 * @Override public StoreHouse getItem(int position) { // TODO
	 * Auto-generated method stub return storeHouseList.get(position); }
	 * 
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { // TODO Auto-generated method stub ViewHolder holder; if
	 * (convertView == null) { convertView =
	 * LayoutInflater.from(mContext).inflate(R.layout.house_sp_item, null);
	 * 
	 * holder = new ViewHolder(); holder.houseName = (TextView)
	 * convertView.findViewById(R.id.house_name); holder.houseMemo = (TextView)
	 * convertView.findViewById(R.id.house_memo);
	 * 
	 * convertView.setTag(holder); } else {
	 * 
	 * holder = (ViewHolder) convertView.getTag(); }
	 * 
	 * // Bind the data efficiently with the holder.
	 * holder.houseName.setText(storeHouseList.get(position).Title); String memo
	 * = storeHouseList.get(position).Memo; if (memo != null &&
	 * !memo.equals("")) { holder.houseMemo.setText("(" + memo + ")"); } else {
	 * holder.houseMemo.setText(""); }
	 * 
	 * return convertView; }
	 * 
	 * class ViewHolder { TextView houseName; TextView houseMemo; } }
	 * 
	 * class OperatorSpAdapter extends BaseAdapter {
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return operatorList.size(); }
	 * 
	 * @Override public Operator getItem(int position) { // TODO Auto-generated
	 * method stub return operatorList.get(position); }
	 * 
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { // TODO Auto-generated method stub ViewHolder holder; if
	 * (convertView == null) { convertView =
	 * LayoutInflater.from(mContext).inflate(R.layout.operator_sp_item, null);
	 * 
	 * holder = new ViewHolder(); holder.realName = (TextView)
	 * convertView.findViewById(R.id.real_name); holder.memo = (TextView)
	 * convertView.findViewById(R.id.memo); convertView.setTag(holder); } else {
	 * 
	 * holder = (ViewHolder) convertView.getTag(); }
	 * 
	 * // Bind the data efficiently with the holder.
	 * holder.realName.setText(operatorList.get(position).RealName); String memo
	 * = operatorList.get(position).Memo; if (memo != null && !memo.equals(""))
	 * { holder.memo.setText("(" + memo + ")"); } else {
	 * holder.memo.setText(""); } return convertView; }
	 * 
	 * class ViewHolder { TextView realName; TextView memo; } }
	 * 
	 * class AttrValueSpAdapter extends BaseAdapter {
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return attrValueList.size(); }
	 * 
	 * @Override public AttrValue getItem(int position) { // TODO Auto-generated
	 * method stub return attrValueList.get(position); }
	 * 
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { // TODO Auto-generated method stub ViewHolder holder; if
	 * (convertView == null) { convertView =
	 * LayoutInflater.from(mContext).inflate(R.layout.one_text_sp_item, null);
	 * 
	 * holder = new ViewHolder(); holder.typeText = (TextView)
	 * convertView.findViewById(R.id.tv_one);
	 * 
	 * convertView.setTag(holder); } else {
	 * 
	 * holder = (ViewHolder) convertView.getTag(); }
	 * 
	 * // Bind the data efficiently with the holder.
	 * holder.typeText.setText(attrValueList.get(position).Text);
	 * 
	 * return convertView; }
	 * 
	 * class ViewHolder { TextView typeText; } }
	 * 
	 * class ProductSpAdapter extends BaseAdapter {
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return productList.size(); }
	 * 
	 * @Override public Product getItem(int position) { // TODO Auto-generated
	 * method stub return productList.get(position); }
	 * 
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { // TODO Auto-generated method stub ViewHolder holder; if
	 * (convertView == null) { convertView =
	 * LayoutInflater.from(mContext).inflate(R.layout.one_text_sp_item, null);
	 * 
	 * holder = new ViewHolder(); holder.productName = (TextView)
	 * convertView.findViewById(R.id.tv_one);
	 * holder.productName.setTextSize(12); convertView.setTag(holder); } else {
	 * 
	 * holder = (ViewHolder) convertView.getTag(); }
	 * 
	 * // Bind the data efficiently with the holder.
	 * holder.productName.setText(productList.get(position).getTitle());
	 * 
	 * return convertView; }
	 * 
	 * class ViewHolder { TextView productName; } }
	 */
}

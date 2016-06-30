package com.hnao.warehouse.fragment;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Text;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.adapter.AttrValueSpAdapter;
import com.hnao.warehouse.adapter.HouseSpAdapter;
import com.hnao.warehouse.adapter.OperatorSpAdapter;
import com.hnao.warehouse.adapter.ProductSpAdapter;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.ApplyItem;
import com.hnao.warehouse.domain.AttrValue;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.Operator;
import com.hnao.warehouse.domain.Product;
import com.hnao.warehouse.domain.SheetTypeEnum;
import com.hnao.warehouse.domain.StoreHouse;
import com.hnao.warehouse.domain.User;
import com.hnao.warehouse.domain.args.OperatorArgs;
import com.hnao.warehouse.domain.args.ProductArgs;
import com.hnao.warehouse.domain.args.StoreHouseArgs;
import com.hnao.warehouse.task.AddApplyTask;
import com.hnao.warehouse.task.GetAttrValueListTask;
import com.hnao.warehouse.task.GetNextSerialNumberTask;
import com.hnao.warehouse.task.GetOperatorListTask;
import com.hnao.warehouse.task.GetProductInfoListTask;
import com.hnao.warehouse.task.GetStoreHouseListTask;
import com.hnao.warehouse.task.GetSystemDateTask;
import com.hnao.warehouse.view.OneDayPickerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class HouseOutAddFragment extends Fragment {

	public final static String TAG = "HouseOutAddFragment";

	private Context mContext;

	private EditText mEtBillNum;

	private CheckBox mCheckOrderBill;

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

	private EditText etLogisticsCompany;

	private EditText etLogisticsPrice;

	private EditText etLogisticsNo;

	private static final int DECIMAL_DIGITS = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.house_out_data, container, false);

		mContext = getActivity();

		mEtBillNum = (EditText) view.findViewById(R.id.et_bill_num);

		mDatePickerView = (OneDayPickerView) view.findViewById(R.id.date_picker_view);

		mCheckOrderBill = (CheckBox) view.findViewById(R.id.check_order_bill);

		EtapplyMemo = (EditText) view.findViewById(R.id.et_memo);

		mBtnSave = (Button) view.findViewById(R.id.btn_save);

		mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);

		mBtnAddProduct = (Button) view.findViewById(R.id.btn_add_row);

		mBtnCutProduct = (Button) view.findViewById(R.id.btn_cut_row);

		etLogisticsCompany = (EditText) view.findViewById(R.id.et_logistics_company);

		etLogisticsNo = (EditText) view.findViewById(R.id.et_logistics_no);

		etLogisticsPrice = (EditText) view.findViewById(R.id.et_logistics_price);

		etLogisticsPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onFocusChange hasFocus = " + hasFocus);
				if (!hasFocus) {
					String str = etLogisticsPrice.getText().toString();
					if (str.equals("")) {
						etLogisticsPrice.setText("0.00");
						return;
					}
					Double price = Double.parseDouble(str);
					Log.d("", "onFocusChange str = " + str + " price = " + price);

					DecimalFormat df = new DecimalFormat("#0.00");
					str = df.format(price);

					Log.d("", "format str = " + str);
					etLogisticsPrice.setText(str);
				}
			}
		});

		etLogisticsPrice.addTextChangedListener(new TextWatcher() {
			private boolean isChanged = false;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Log.d("", "onTextChanged");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				Log.d("", "beforeTextChanged");
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Log.d("", "afterTextChanged s = " + s.toString());
				if (isChanged) {
					isChanged = false;
					return;
				}
				String str = s.toString();
				if (str.equals("")) {
					return;
				}
				int len = str.length();
				if (str.startsWith(".")) {
					str = "0" + str;
				} else if (str.startsWith("0")) {
					if (len >= 2) {
						if (str.charAt(1) != '.') {
							str = str.substring(1);
						}
					}
				}

				if (str.contains(".")) {
					int index = str.indexOf(".");
					if (str.length() - 1 - index > 2) {
						str = str.substring(0, index + 3);
					}
				}

				Log.d("", "str = " + str);
				if (!str.equals(s.toString())) {
					isChanged = true;
					etLogisticsPrice.setText(str);
					etLogisticsPrice.setSelection(str.length());
				}
			}
		});

		mBtnSave.setOnClickListener(clickListener);

		mBtnCancel.setOnClickListener(clickListener);

		mBtnAddProduct.setOnClickListener(clickListener);

		mBtnCutProduct.setOnClickListener(clickListener);

		mTable = (TableLayout) view.findViewById(R.id.product_list);

		houseSp = (Spinner) view.findViewById(R.id.sp_house_name);

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
		getNextSerialNumberTask.execute(SheetTypeEnum.OutStore);

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
		getAttrValueListTask.execute("00008");

		ProductArgs productArgs = new ProductArgs();

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
		apply.Action = 2;
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
			Utils.showStringToast(mContext, "出货仓库不能为空！！");
			return;
		} else {
			apply.FromStoreGuid = mStoreHouse.Guid;
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
			Utils.showStringToast(mContext, "出库类型不能为空！！");
			return;
		} else {
			apply.Type = mAttrValue.Code;// Text
			Log.d(TAG, "apply.Type = " + apply.Type);
		}

		apply.LogisticsCompany = etLogisticsCompany.getText().toString();

		apply.LogisticsNo = etLogisticsNo.getText().toString();

		apply.LogisticsPrice = etLogisticsPrice.getText().toString();

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

}

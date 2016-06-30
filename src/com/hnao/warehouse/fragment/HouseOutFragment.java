package com.hnao.warehouse.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.adapter.PagedHScrollTableAdapter;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.ApplyItem;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.PageReturns;
import com.hnao.warehouse.domain.args.ApplyArgs;
import com.hnao.warehouse.fragment.HouseInFragment.TableDateAdapter;
import com.hnao.warehouse.task.DeleteApplyTask;
import com.hnao.warehouse.task.EditApplyStatusTask;
import com.hnao.warehouse.task.GetApplyItemListTask;
import com.hnao.warehouse.task.GetApplyListTask;
import com.hnao.warehouse.view.PagedHScrollTable;
import com.hnao.warehouse.view.SearchByDateView;

public class HouseOutFragment extends Fragment {

	public final static String TAG = "HouseOutFragment";

	private SearchByDateView dateView;

	private EditText mEtContent;

	private String mStringContent;

	private Button mBtnSearch;

	private Button mBtnAdd;

	private String mStartDate;

	private String mEndDate;

	private Context mContext;

	private ApplyArgs applyArgs;

	private final int PAGE_SIZE = 10;

	private int currentPage = 0;

	private GetApplyListTask getApplyListTask;

	private int applyTotal;

	private int pageCount;

	private PageReturns<Apply> pageReturns;

	private PagedHScrollTable mTable;

	private TextView tvPageInfo;
	private TextView tvFirstPage;
	private TextView tvLastPage;
	private TextView tvPretPage;
	private TextView tvNextPage;

	private EditText etPage;

	private TextView tvGoto;

	private Resources res;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_house_out, container, false);

		mContext = getActivity();

		res = getResources();

		dateView = (SearchByDateView) view.findViewById(R.id.date_view);

		mEtContent = (EditText) view.findViewById(R.id.et_search_content);

		mTable = (PagedHScrollTable) view.findViewById(R.id.table);

		tvPageInfo = (TextView) view.findViewById(R.id.tv_page_info);
		tvFirstPage = (TextView) view.findViewById(R.id.tv_first_page);
		tvLastPage = (TextView) view.findViewById(R.id.tv_last_page);
		tvPretPage = (TextView) view.findViewById(R.id.tv_pre_page);
		tvNextPage = (TextView) view.findViewById(R.id.tv_next_page);

		etPage = (EditText) view.findViewById(R.id.et_page);

		tvGoto = (TextView) view.findViewById(R.id.tv_goto);

		tvFirstPage.setOnClickListener(onClickListener);
		tvLastPage.setOnClickListener(onClickListener);
		tvPretPage.setOnClickListener(onClickListener);
		tvNextPage.setOnClickListener(onClickListener);
		tvGoto.setOnClickListener(onClickListener);

		mBtnSearch = (Button) view.findViewById(R.id.btn_search);

		mBtnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mStartDate = dateView.getStartDateStr();
				mEndDate = dateView.getEndDateStr();
				mStringContent = mEtContent.getText().toString();

				applyArgs.action = 2;
				applyArgs.searchText = mStringContent;
				applyArgs.CurrentPageIndex = 0;
				applyArgs.PageSize = PAGE_SIZE;

				currentPage = 0;

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					applyArgs.timeFrom = sdf.parse(mStartDate);
					applyArgs.timeTo = sdf.parse(mEndDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					applyArgs.timeFrom = null;
					applyArgs.timeTo = null;
				}

				searchApply(applyArgs);
			}
		});

		mBtnAdd = (Button) view.findViewById(R.id.btn_add);

		mBtnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "mBtnAdd onClick");
				Fragment fragment = new HouseOutAddFragment();
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction ft = fragmentManager.beginTransaction();
				// ft.hide(HouseInFragment.this);
				// ft.replace(R.id.content_frame, fragment);
				ft.hide(HouseOutFragment.this);
				ft.add(R.id.content_frame, fragment);
				ft.show(fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		applyArgs = new ApplyArgs();
		applyArgs.action = 2;
		applyArgs.searchText = "";
		applyArgs.CurrentPageIndex = 0;
		applyArgs.PageSize = PAGE_SIZE;

		currentPage = 0;

		searchApply(applyArgs);

		return view;
	}

	private void showApplyItemList(int row) {
		final Apply apply = pageReturns.pageData.get(row);
		// final String guid = apply.Guid;

		GetApplyItemListTask getApplyItemListTask = new GetApplyItemListTask(mContext);
		getApplyItemListTask.setCompletedListener(new GetApplyItemListTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<List<ApplyItem>> result) {
				// TODO Auto-generated method stub
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("apply", apply);

				ApplyItemListDialogFragment fragment = new ApplyItemListDialogFragment(apply, result.Data);
				FragmentManager fragmentManager = getFragmentManager();
				// fragment.setArguments(bundle);

				fragment.show(fragmentManager, "ApplyItemListDialog");
			}

			@Override
			public void resultFaild(BizResult<List<ApplyItem>> result) {
				// TODO Auto-generated method stub
				Utils.showStringToast(mContext, "获取该单据商品失败！");
			}
		});
		getApplyItemListTask.execute(apply.Guid);

	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_first_page:
				currentPage = 0;
				applyArgs.CurrentPageIndex = currentPage;
				searchApply(applyArgs);
				break;
			case R.id.tv_last_page:
				currentPage = pageCount - 1;
				applyArgs.CurrentPageIndex = currentPage;
				searchApply(applyArgs);
				break;
			case R.id.tv_pre_page:
				if (currentPage >= 1) {
					currentPage--;
					applyArgs.CurrentPageIndex = currentPage;
					searchApply(applyArgs);
				}
				break;
			case R.id.tv_next_page:
				if (currentPage < pageCount - 1) {
					currentPage++;
					applyArgs.CurrentPageIndex = currentPage;
					searchApply(applyArgs);
				}
				break;
			case R.id.tv_goto:
				int page = Integer.parseInt(etPage.getText().toString());
				if (page > 0 && page <= pageCount) {
					currentPage = page - 1;
					applyArgs.CurrentPageIndex = currentPage;
					searchApply(applyArgs);
				}
				break;
			default:
				break;
			}
		}
	};

	private void searchApply(ApplyArgs args) {
		getApplyListTask = new GetApplyListTask(mContext);

		getApplyListTask.setCompletedListener(new GetApplyListTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<PageReturns<Apply>> result) {
				// TODO Auto-generated method stub
				if (result.Data == null) {
					applyTotal = 0;
				} else {
					pageReturns = result.Data;
					applyTotal = pageReturns.total;
				}
				TableDateAdapter dateAdapter = new TableDateAdapter();
				mTable.setPageSize(applyArgs.PageSize);
				mTable.setAdapter(dateAdapter);
				mTable.setLayoutPageOpGone();
				if (applyTotal % applyArgs.PageSize == 0) {
					pageCount = applyTotal / applyArgs.PageSize;
				} else {
					pageCount = applyTotal / applyArgs.PageSize + 1;
				}

				tvPageInfo.setText(
						String.format(res.getString(R.string.page_info), applyTotal, applyArgs.PageSize, pageCount));
				etPage.setText(applyArgs.CurrentPageIndex + 1 + "");
				mTable.refreshTable();
			}

			@Override
			public void resultFaild(BizResult<PageReturns<Apply>> result) {
				// TODO Auto-generated method stub

			}
		});
		getApplyListTask.execute(args);
	}

	class TableDateAdapter implements PagedHScrollTableAdapter {

		Map<Integer, String[]> colHeaders;
		{
			colHeaders = new HashMap<Integer, String[]>();
			colHeaders.put(PagedHScrollTableAdapter.FIXED_COLUMN, new String[] { "行号", "单据编号" });
			colHeaders.put(PagedHScrollTableAdapter.SCROLLABLE_COLUMN,
					new String[] { "关联编号", "单据时间", "出库类型", "发货仓库", "状态", "物流备注", "出库确认", "删除", "编辑" });
		}

		@Override
		public Map<Integer, String[]> getColHeaders() {
			// TODO Auto-generated method stub
			return colHeaders;
		}

		@Override
		public int getTotalRows() {
			// TODO Auto-generated method stub
			return pageReturns.pageData.size();
		}

		@Override
		public List<Map<Integer, List<String>>> getRows(int startRow, int endRow) {
			// TODO Auto-generated method stub
			Log.d(TAG, "getRows startRow = " + startRow + " endRow = " + endRow);
			List<Map<Integer, List<String>>> rows = new ArrayList<Map<Integer, List<String>>>();
			for (int i = startRow; i <= endRow; i++) {
				Log.d(TAG, "getRows i = " + i);

				Map<Integer, List<String>> row = new HashMap<Integer, List<String>>();

				List<String> fCols = new ArrayList<String>();
				Apply apply = pageReturns.pageData.get(i);
				fCols.add((i + 1) + "");
				fCols.add(apply.IdNo);
				row.put(PagedHScrollTableAdapter.FIXED_COLUMN, fCols);

				List<String> sCols = new ArrayList<String>();
				sCols.add(apply.RelateIdNo);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				sCols.add(sdf.format(apply.ApplyTime));
				sCols.add(apply.TypeName);
				sCols.add(apply.FromStoreName);
				sCols.add(apply.StatusName);
				sCols.add(apply.Memo);
				if (apply.StatusName.equals("申请中") && apply.Status.equals("00006001")) {
					sCols.add("确认");
				} else {
					sCols.add("");
				}
				if (apply.RelateIdNo == null && !apply.Status.equals("00006002")) {
					sCols.add("删除");
				} else {
					sCols.add("");
				}
				sCols.add("编辑");
				row.put(PagedHScrollTableAdapter.SCROLLABLE_COLUMN, sCols);

				rows.add(row);
			}

			return rows;
		}

		@Override
		public OnClickListener getOnClickListener(final int row) {
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "你点击了第" + row + "行", Toast.LENGTH_SHORT).show();
				}
			};
		}

		@Override
		public OnClickListener getCellOnClickListener(final int row, final int col) {
			// TODO Auto-generated method stub
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(TAG, "row = " + row + " col = " + col);
					// Toast.makeText(mContext, "你点击了第" + row + "行,第" + col +
					// "列", Toast.LENGTH_SHORT).show();
					final Apply apply = pageReturns.pageData.get(row);
					if (col == 8 && apply.StatusName.equals("申请中") && apply.Status.equals("00006001")) {

						new AlertDialog.Builder(mContext).setTitle("提示").setMessage("确认" + apply.IdNo + "已经入库？")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										apply.Status = "00006002";
										EditApplyStatusTask editApplyStatusTask = new EditApplyStatusTask(mContext);
										editApplyStatusTask
												.setCompletedListener(new EditApplyStatusTask.TaskCompletedListener() {

													@Override
													public void resultSucceed(BizResult<Apply> result) {
														// TODO Auto-generated
														// method stub
														Utils.showStringToast(mContext, "确认成功！");
														searchApply(applyArgs);
													}

													@Override
													public void resultFaild(BizResult<Apply> result) {
														// TODO Auto-generated
														// method stub

													}
												});
										editApplyStatusTask.execute(apply);
									}

								}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub

									}
								}).setCancelable(false).show();
					} else if (col == 9 && apply.RelateIdNo == null && !apply.Status.equals("00006002")) {
						new AlertDialog.Builder(mContext).setTitle("提示").setMessage("确定删除：" + apply.IdNo + "？")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										DeleteApplyTask deleteApplyTask = new DeleteApplyTask(mContext);
										deleteApplyTask
												.setCompletedListener(new DeleteApplyTask.TaskCompletedListener() {

													@Override
													public void resultSucceed(BizResult<Boolean> result) {
														// TODO Auto-generated
														// method stub
														Utils.showStringToast(mContext, "删除成功！");
														searchApply(applyArgs);
													}

													@Override
													public void resultFaild(BizResult<Boolean> result) {
														// TODO Auto-generated
														// method stub

													}
												});
										deleteApplyTask.execute(apply);
									}

								}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub

									}
								}).setCancelable(false).show();
					} else if (col == 10) {
						new AlertDialog.Builder(mContext).setTitle("提示").setMessage("确定编辑：" + apply.IdNo + "？")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Log.d(TAG, "onClick 确定编辑");
										Fragment fragment = new HouseOutEditFragment(apply);
										FragmentManager fragmentManager = getFragmentManager();
										FragmentTransaction ft = fragmentManager.beginTransaction();
										// ft.hide(HouseInFragment.this);
										// ft.replace(R.id.content_frame, fragment);
										ft.hide(HouseOutFragment.this);
										ft.add(R.id.content_frame, fragment);
										ft.show(fragment);
										ft.addToBackStack(null);
										ft.commit();
									}

								}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub

									}
								}).setCancelable(false).show();
					} else {
						showApplyItemList(row);
					}
				}
			};
		}
	}
}

package com.hnao.warehouse.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;
import com.hnao.warehouse.adapter.PagedHScrollTableAdapter;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.ApplyItem;
import com.hnao.warehouse.task.GetApplyItemListTask;
import com.hnao.warehouse.view.PagedHScrollTable;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.Window;

public class ApplyItemListDialogFragment extends DialogFragment {

	public final static String TAG = "ApplyItemListDialogFragment";

	private Context mContext;

	private final int PAGE_SIZE = 10;

	private int currentPage = 0;

	private Apply mApply;

	private GetApplyItemListTask getApplyItemListTask;

	private List<ApplyItem> itemList;

	private PagedHScrollTable mTable;

	private TextView tvListInfo;

	public ApplyItemListDialogFragment(Apply apply, List<ApplyItem> list) {
		super();
		// TODO Auto-generated constructor stub
		mApply = apply;
		itemList = list;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_apply_item_list, container, false);

		mContext = getActivity();

		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		mTable = (PagedHScrollTable) view.findViewById(R.id.table);
		tvListInfo = (TextView) view.findViewById(R.id.tv_list_info);

		tvListInfo.setText("单据：" + mApply.IdNo + "的详情");

		// mApply = (Apply) getArguments().getSerializable("apply");

		ItemDateAdapter dateAdapter = new ItemDateAdapter();
		mTable.setPageSize(PAGE_SIZE);
		mTable.setAdapter(dateAdapter);
		mTable.refreshTable();

		return view;
	}

	class ItemDateAdapter implements PagedHScrollTableAdapter {

		Map<Integer, String[]> colHeaders;
		{
			colHeaders = new HashMap<Integer, String[]>();
			colHeaders.put(PagedHScrollTableAdapter.FIXED_COLUMN, new String[] { "行号", "商品编号" });
			colHeaders.put(PagedHScrollTableAdapter.SCROLLABLE_COLUMN, new String[] { "商品名称", "颜色", "尺码", "数量" });
		}

		@Override
		public Map<Integer, String[]> getColHeaders() {
			// TODO Auto-generated method stub
			return colHeaders;
		}

		@Override
		public int getTotalRows() {
			// TODO Auto-generated method stub
			return itemList.size();
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

				ApplyItem item = itemList.get(i);

				fCols.add((i + 1) + "");
				fCols.add(item.ProductIdNo);

				row.put(PagedHScrollTableAdapter.FIXED_COLUMN, fCols);

				List<String> sCols = new ArrayList<String>();
				sCols.add(item.ProductName);
				sCols.add(item.ExtColumn1Name);
				sCols.add(item.ExtColumn2Name);
				sCols.add(item.Quantity + "");

				row.put(PagedHScrollTableAdapter.SCROLLABLE_COLUMN, sCols);

				rows.add(row);

			}
			return rows;
		}

		@Override
		public OnClickListener getOnClickListener(int row) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OnClickListener getCellOnClickListener(final int row, final int col) {
			// TODO Auto-generated method stub
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			};
		}

	}
}

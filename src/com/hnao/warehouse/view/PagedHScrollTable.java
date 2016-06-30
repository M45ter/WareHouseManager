package com.hnao.warehouse.view;

import com.hnao.warehouse.adapter.PagedHScrollTableAdapter;
import com.hnao.warehouse.base.BaseAsyncTask;
import com.hnao.warehouse.beans.ComFunc;
import com.hnao.warehouse.domain.Apply;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.poxy.ApplyProxy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;

public class PagedHScrollTable extends FrameLayout {

	private final String TAG = "PagedHScrollTable";

	private float pixelPerDp;
	private final int PAGE_SIZE = 10;
	private int pageSize = PAGE_SIZE; // 表格每页显示记录条数，默认为 10
	private int totalNum = 0; // 待显示总记录条数
	private int startRow = 0; // 起始行号
	private int endRow = 0; // 结束行号
	private PagedHScrollTableAdapter adapter = null; // 使用方传入的数据适配器对象

	private LayoutInflater layoutInflater;
	// private ImageView upIndicator;
	// private ImageView downIndicator;
	private ImageView leftIndicator;
	private ImageView rightIndicator;

	private Context mContext;

	private Resources res;

	private int fixColCount;

	private int ScrollColCount;

	private int currentPage = -1;

	private int pageCount;

	private LinearLayout layoutPageOp;

	private TextView tvPageInfo;
	private TextView tvFirstPage;
	private TextView tvLastPage;
	private TextView tvPretPage;
	private TextView tvNextPage;

	private EditText etPage;

	private TextView tvGoto;

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_first_page:
				gotoFirstPage();
				break;
			case R.id.tv_last_page:
				gotoLastPage();
				break;
			case R.id.tv_pre_page:
				doPageUp();
				break;
			case R.id.tv_next_page:
				doPageDown();
				break;
			case R.id.tv_goto:
				int page = Integer.parseInt(etPage.getText().toString());
				gotoPage(page);
				break;
			default:
				break;
			}
		}
	};

	public PagedHScrollTable(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;

		pixelPerDp = getContext().getResources().getDisplayMetrics().density;

		layoutInflater = LayoutInflater.from(context);

		layoutInflater.inflate(R.layout.paged_hscroll_table, this);

		leftIndicator = (ImageView) findViewById(R.id.leftIndicator);
		rightIndicator = (ImageView) findViewById(R.id.rightIndicator);

		layoutPageOp = (LinearLayout) findViewById(R.id.layout_page_operate);

		tvPageInfo = (TextView) findViewById(R.id.tv_page_info);
		tvFirstPage = (TextView) findViewById(R.id.tv_first_page);
		tvLastPage = (TextView) findViewById(R.id.tv_last_page);
		tvPretPage = (TextView) findViewById(R.id.tv_pre_page);
		tvNextPage = (TextView) findViewById(R.id.tv_next_page);

		etPage = (EditText) findViewById(R.id.et_page);

		tvGoto = (TextView) findViewById(R.id.tv_goto);

		tvFirstPage.setOnClickListener(onClickListener);
		tvLastPage.setOnClickListener(onClickListener);
		tvPretPage.setOnClickListener(onClickListener);
		tvNextPage.setOnClickListener(onClickListener);
		tvGoto.setOnClickListener(onClickListener);

		res = getResources();

		HorizontalScrollIndicatorView scrollableArea = (HorizontalScrollIndicatorView) findViewById(
				R.id.scrollableArea);
		scrollableArea.setLeftIndicator(leftIndicator);
		scrollableArea.setRightIndicator(rightIndicator);
	}

	public void setLayoutPageOpGone() {
		if (layoutPageOp != null)
			layoutPageOp.setVisibility(View.GONE);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PagedHScrollTableAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(PagedHScrollTableAdapter adapter) {
		this.adapter = adapter;
	}

	public void refreshTable() {
		new InitPageTask(mContext).execute("");
	}

	private void clearHeadRow() {
		int[] tables = { R.id.fixedTable, R.id.scrollableTable };
		for (int id : tables) {
			TableLayout table = (TableLayout) findViewById(id);
			// 清空表头
			TableRow headRow = (TableRow) table.getChildAt(0);
			headRow.removeAllViews();
		}
	}

	private void setTableHead(PageData pageData) {
		clearHeadRow();

		Map<Integer, String[]> colHeaders = pageData.headData;
		TableRow fixedColHeadRow = (TableRow) findViewById(R.id.fixedColHeadRow);
		boolean firstCol = true;
		for (String colHead : colHeaders.get(PagedHScrollTableAdapter.FIXED_COLUMN)) {
			// 填充固定列表头
			TextView head = (TextView) layoutInflater.inflate(R.layout.zpaged_hscroll_table_head_view, null);
			head.setText(colHead);

			TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
					TableRow.LayoutParams.FILL_PARENT, 1.0f);

			if (firstCol) {
				firstCol = false;
				layoutParams.setMargins(0, 0, 0, 0);
			} else {
				layoutParams.setMargins(Utils.d2p(mContext, 1), 0, 0, 0);
			}
			head.setLayoutParams(layoutParams);

			fixedColHeadRow.addView(head);
		}

		TableRow scrollableColHeadRow = (TableRow) findViewById(R.id.scrollableColHeadRow);
		firstCol = true;
		for (String colHead : colHeaders.get(PagedHScrollTableAdapter.SCROLLABLE_COLUMN)) {
			TextView head = (TextView) layoutInflater.inflate(R.layout.zpaged_hscroll_table_head_view, null);
			head.setText(colHead);

			TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
					TableRow.LayoutParams.FILL_PARENT, 1.0f);
			if (firstCol) {
				firstCol = false;
				layoutParams.setMargins(0, 0, 0, 0);
			} else {
				layoutParams.setMargins(Utils.d2p(mContext, 1), 0, 0, 0);
			}
			head.setLayoutParams(layoutParams);

			scrollableColHeadRow.addView(head);
		}
	}

	private void clearDataRow() {
		int[] tables = { R.id.fixedTable, R.id.scrollableTable };
		for (int id : tables) {
			TableLayout table = (TableLayout) findViewById(id);
			// 删除除表头以外的所有数据行
			ArrayList<View> rows = new ArrayList<View>();
			for (int i = 1; i < table.getChildCount(); ++i) {
				rows.add(table.getChildAt(i));
			}
			for (View row : rows) {
				table.removeView(row);
			}
		}
	}

	private void setTableData(PageData pageData) {
		clearDataRow();
		// if (startRow > 0) {
		// upIndicator.setVisibility(VISIBLE);
		// } else {
		// upIndicator.setVisibility(INVISIBLE);
		// }
		// if (endRow < totalNum - 1) {
		// downIndicator.setVisibility(VISIBLE);
		// } else {
		// downIndicator.setVisibility(INVISIBLE);
		// }

		int[] layoutIds = { R.layout.zpaged_hscroll_table_fixed_row, R.layout.zpaged_hscroll_table_scrollable_row };
		Integer[] colTypes = { PagedHScrollTableAdapter.FIXED_COLUMN, PagedHScrollTableAdapter.SCROLLABLE_COLUMN };
		TableLayout[] tables = { (TableLayout) findViewById(R.id.fixedTable),
				(TableLayout) findViewById(R.id.scrollableTable) };

		// 准备表格填充空行数据
		Map<Integer, List<String>> dummyRow = new HashMap<Integer, List<String>>();
		String[] fHead = pageData.headData.get(PagedHScrollTableAdapter.FIXED_COLUMN);
		String[] sHead = pageData.headData.get(PagedHScrollTableAdapter.SCROLLABLE_COLUMN);
		if (fHead != null) {
			dummyRow.put(PagedHScrollTableAdapter.FIXED_COLUMN, Arrays.asList(fHead));
		} else {
			dummyRow.put(PagedHScrollTableAdapter.FIXED_COLUMN, null);
		}
		if (sHead != null) {
			dummyRow.put(PagedHScrollTableAdapter.SCROLLABLE_COLUMN, Arrays.asList(sHead));
		} else {
			dummyRow.put(PagedHScrollTableAdapter.SCROLLABLE_COLUMN, null);
		}

		int actualDataSize = pageData.rowData.size();
		boolean isRowA = true;
		for (int i = 0; i < pageSize; ++i) {
			Map<Integer, List<String>> rowData = null;
			if (i < actualDataSize) {
				rowData = pageData.rowData.get(i);
			} else {
				rowData = dummyRow;
			}

			// 顺序填充固定列和滑动列数据
			int colsCount = 0;// zey add
			for (int areaId = 0; areaId < layoutIds.length; ++areaId) {
				List<String> cols = rowData.get(colTypes[areaId]);

				TableRow rowLayout = (TableRow) layoutInflater.inflate(layoutIds[areaId], null);
				TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(
						TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.FILL_PARENT, 1.0f);
				if (colTypes[areaId] == PagedHScrollTableAdapter.FIXED_COLUMN) {
					// 固定列数据行布局属性
					rowLayoutParams.setMargins(Utils.d2p(mContext, 1), 0, Utils.d2p(mContext, 1),
							Utils.d2p(mContext, 1));
				} else {
					// 滑动列数据行布局属性
					rowLayoutParams.setMargins(0, 0, Utils.d2p(mContext, 1), Utils.d2p(mContext, 1));

				}
				rowLayout.setLayoutParams(rowLayoutParams);

				boolean firstCol = true;
				// for (String col : cols) {
				for (int j = 0; j < cols.size(); j++) {
					String col = cols.get(j);

					TextView cell = null;

					// 以不同的配色分隔相邻行
					if (isRowA) {
						cell = (TextView) layoutInflater.inflate(R.layout.zpaged_hscroll_table_col_view_a, null);
					} else {
						cell = (TextView) layoutInflater.inflate(R.layout.zpaged_hscroll_table_col_view_b, null);
					}
					cell.setText(col);
					// XXX: 让超出列宽的文本开始走马灯动画展示
					cell.setSelected(true);
					// 隐藏超过有效数据行范围的填充文本
					if (i >= actualDataSize) {
						cell.setTextColor(Color.argb(0, 0, 0, 0));
					}

					TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
							TableRow.LayoutParams.FILL_PARENT, 1.0f);
					if (firstCol) {
						firstCol = false;
						layoutParams.setMargins(0, 0, 0, 0);
					} else {
						layoutParams.setMargins(Utils.d2p(mContext, 1), 0, 0, 0);
					}
					cell.setLayoutParams(layoutParams);
					// zey add begin
					if (i < actualDataSize) {
						int index = i * (fixColCount + ScrollColCount) + colsCount + j;
						// Log.d(TAG, "i = " + i + " j = " + j + " index = " +
						// index);
						//cell.setClickable(true);
						cell.setOnClickListener(pageData.cellOnClick.get(index));
					}

					rowLayout.addView(cell);
				}

				// 为有效数据行范围内的行添加点击处理器
				if (i < actualDataSize) {
					rowLayout.setOnClickListener(pageData.rowOnClick.get(i));
				} else {
					// XXX: 空白区域的 TableRow 也必须有 OnClickListener，才能正常识别固定列向上翻页动作
					rowLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
						}
					});
				}
				colsCount += cols.size();
				tables[areaId].addView(rowLayout);
			}

			// 切换行背景配色
			isRowA = !isRowA;
		}
	}

	private void doPageDown() {
		if (currentPage < pageCount - 1) {
			// if (endRow >= 0 && endRow < totalNum - 1 && pageSize > 0) {
			startRow = endRow + 1;
			endRow += pageSize;
			currentPage++;
			if (endRow > totalNum - 1) {
				endRow = totalNum - 1;
			}
			new ShowPage(mContext).execute("");
		} else {
			String msg = "已经是最后一页";
			Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	private void doPageUp() {
		if (currentPage >= 1) {
			// if (startRow >= pageSize && pageSize > 0) {
			endRow = startRow - 1;
			startRow -= pageSize;
			currentPage--;
			new ShowPage(mContext).execute("");
		} else {
			String msg = "已经是第一页";
			Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	private void gotoPage(int page) {
		if (page > 0 && page <= pageCount) {
			currentPage = page - 1;
			startRow = pageSize * currentPage;
			endRow = startRow + pageSize - 1;
			if (endRow > totalNum - 1) {
				endRow = totalNum - 1;
			}
			new ShowPage(mContext).execute("");
		}
	}

	private void gotoFirstPage() {
		gotoPage(1);
	}

	private void gotoLastPage() {
		gotoPage(pageCount);
	}

	private class PageData {
		public Map<Integer, String[]> headData;
		public List<Map<Integer, List<String>>> rowData;
		public List<OnClickListener> rowOnClick;
		public List<OnClickListener> cellOnClick;
	}

	private class InitPageTask extends BaseAsyncTask<String, String, PageData> {

		public InitPageTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected PageData doInBackground(String... params) {
			// TODO: attempt authentication against a network service.
			totalNum = adapter.getTotalRows();

			startRow = 0;
			endRow = (totalNum < pageSize ? totalNum : pageSize) - 1;

			currentPage = startRow / pageSize;// zey add

			if (totalNum % pageSize == 0) {
				pageCount = totalNum / pageSize;
			} else {
				pageCount = totalNum / pageSize + 1;
			}

			PageData pageData = new PageData();
			pageData.headData = adapter.getColHeaders();
			pageData.rowData = adapter.getRows(startRow, endRow);
			pageData.rowOnClick = new ArrayList<OnClickListener>();
			pageData.cellOnClick = new ArrayList<OnClickListener>();

			fixColCount = pageData.headData.get(PagedHScrollTableAdapter.FIXED_COLUMN).length;

			ScrollColCount = pageData.headData.get(PagedHScrollTableAdapter.SCROLLABLE_COLUMN).length;

			int dataSize = pageData.rowData.size();
			for (int i = 0; i < dataSize; ++i) {
				pageData.rowOnClick.add(adapter.getOnClickListener(startRow + i));
			}
			Log.d(TAG,
					"fixColCount = " + fixColCount + " ScrollColCount = " + ScrollColCount + " dataSize = " + dataSize);
			for (int i = 0; i < dataSize; i++) {
				for (int j = 0; j < fixColCount + ScrollColCount; j++) {
					pageData.cellOnClick.add(adapter.getCellOnClickListener(startRow + i, j));
				}
			}
			endRow = startRow + dataSize - 1;
			return pageData;
		}

		@Override
		protected void onPostExecute(PageData pageData) {
			super.onPostExecute(pageData);
			TableLayout fixedTable = (TableLayout) findViewById(R.id.fixedTable);
			TableLayout scrollableTable = (TableLayout) findViewById(R.id.scrollableTable);
			fixedTable.setBackgroundColor(Color.argb(0, 0, 0, 0));
			scrollableTable.setBackgroundColor(Color.argb(0, 0, 0, 0));

			setTableHead(pageData);
			setTableData(pageData);
			tvPageInfo.setText(String.format(res.getString(R.string.page_info), totalNum, pageSize, pageCount));
			etPage.setText((currentPage + 1) + "");

			fixedTable.setBackgroundResource(R.color.PagedHScrollTableBorderColor);
			scrollableTable.setBackgroundResource(R.color.PagedHScrollTableBorderColor);

		}
	}

	private class ShowPage extends BaseAsyncTask<String, String, PageData> {

		public ShowPage(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected PageData doInBackground(String... params) {
			// TODO: attempt authentication against a network service.
			// totalNum = adapter.getTotalRows();
			//
			// startRow = 0;
			// endRow = (totalNum < pageSize ? totalNum : pageSize) - 1;
			//
			// currentPage = startRow / pageSize;//zey add

			Log.d(TAG, "ShowPage currentPage = " + currentPage + " startRow = " + startRow + " endRow = " + endRow);

			PageData pageData = new PageData();
			pageData.headData = adapter.getColHeaders();
			pageData.rowData = adapter.getRows(startRow, endRow);
			pageData.rowOnClick = new ArrayList<OnClickListener>();
			pageData.cellOnClick = new ArrayList<OnClickListener>();

			// fixColCount =
			// pageData.headData.get(PagedHScrollTableAdapter.FIXED_COLUMN).length;
			//
			// ScrollColCount =
			// pageData.headData.get(PagedHScrollTableAdapter.SCROLLABLE_COLUMN).length;

			int dataSize = pageData.rowData.size();
			for (int i = 0; i < dataSize; ++i) {
				pageData.rowOnClick.add(adapter.getOnClickListener(startRow + i));
			}
			// Log.d(TAG,
			// "fixColCount = " + fixColCount + " ScrollColCount = " +
			// ScrollColCount + " dataSize = " + dataSize);
			for (int i = 0; i < dataSize; i++) {
				for (int j = 0; j < fixColCount + ScrollColCount; j++) {
					pageData.cellOnClick.add(adapter.getCellOnClickListener(startRow + i, j));
				}
			}
			endRow = startRow + dataSize - 1;
			return pageData;
		}

		@Override
		protected void onPostExecute(PageData pageData) {
			super.onPostExecute(pageData);
			// TableLayout fixedTable = (TableLayout)
			// findViewById(R.id.fixedTable);
			// TableLayout scrollableTable = (TableLayout)
			// findViewById(R.id.scrollableTable);
			// fixedTable.setBackgroundColor(Color.argb(0, 0, 0, 0));
			// scrollableTable.setBackgroundColor(Color.argb(0, 0, 0, 0));
			//
			// setTableHead(pageData);

			setTableData(pageData);
			etPage.setText((currentPage + 1) + "");
			// fixedTable.setBackgroundResource(R.color.PagedHScrollTableBorderColor);
			// scrollableTable.setBackgroundResource(R.color.PagedHScrollTableBorderColor);

		}
	}

}

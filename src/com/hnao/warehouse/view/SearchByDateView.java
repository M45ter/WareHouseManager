package com.hnao.warehouse.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.hnao.warehouse.R;
import com.hnao.warehouse.TimeSet;
import com.hnao.warehouse.fragment.DatePickerFragment;

public class SearchByDateView extends LinearLayout {

	private Context context;

	private TextView startDateTv;

	private TextView endDateTv;

	private String startDateStr = "";

	private String endDateStr = "";

	private TextView startTodayTv;

	private TextView startClearTv;

	private TextView endTodayTv;

	private TextView endClearTv;

	public SearchByDateView(Context context) {
		this(context, null);
	}

	public SearchByDateView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SearchByDateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView(context, attrs, defStyleAttr);
	}

	private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
		LayoutInflater.from(context).inflate(R.layout.begin_start_date, this, true);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		startDateTv = (TextView) findViewById(R.id.start_date_tv);
		endDateTv = (TextView) findViewById(R.id.end_date_tv);

		startTodayTv = (TextView) findViewById(R.id.tv_start_today);
		startClearTv = (TextView) findViewById(R.id.tv_start_clear);
		endTodayTv = (TextView) findViewById(R.id.tv_end_today);
		endClearTv = (TextView) findViewById(R.id.tv_end_clear);

		startTodayTv.setOnClickListener(onClickListener);

		startClearTv.setOnClickListener(onClickListener);

		endTodayTv.setOnClickListener(onClickListener);

		endClearTv.setOnClickListener(onClickListener);

		startDateTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerFragment datePickerFragment = new DatePickerFragment();
				int year = 0, month = 0, day = 0;
				if (!TextUtils.isEmpty(startDateStr)) {
					String[] strings = startDateStr.split("-");
					if (strings.length == 3) {
						year = Integer.parseInt(strings[0]);
						month = Integer.parseInt(strings[1]) - 1;
						day = Integer.parseInt(strings[2]);
					}
				}
				Bundle bundle = new Bundle();
				bundle.putInt("year", year);
				bundle.putInt("month", month);
				bundle.putInt("day", day);
				datePickerFragment.setArguments(bundle);
				datePickerFragment.setDateSelectedListener(new StartDateSelectedListener());
				datePickerFragment.show(((Activity) context).getFragmentManager(), "Dialog");
			}
		});

		endDateTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerFragment datePickerFragment = new DatePickerFragment();
				int year = 0, month = 0, day = 0;
				if (!TextUtils.isEmpty(endDateStr)) {
					String[] strings = endDateStr.split("-");
					if (strings.length == 3) {
						year = Integer.parseInt(strings[0]);
						month = Integer.parseInt(strings[1]) - 1;
						day = Integer.parseInt(strings[2]);
					}
				}
				Bundle bundle = new Bundle();
				bundle.putInt("year", year);
				bundle.putInt("month", month);
				bundle.putInt("day", day);
				datePickerFragment.setArguments(bundle);
				datePickerFragment.setDateSelectedListener(new EndDateSelectedListener());
				datePickerFragment.show(((Activity) context).getFragmentManager(), "Dialog");
			}
		});
	}

	private View.OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_start_today:
				startDateStr = getTodayString();
				startDateTv.setText(startDateStr);
				break;
			case R.id.tv_end_today:
				endDateStr = getTodayString();
				endDateTv.setText(endDateStr);
				break;
			case R.id.tv_start_clear:
				startDateStr = "";
				startDateTv.setText(startDateStr);
				break;
			case R.id.tv_end_clear:
				endDateStr = "";
				endDateTv.setText(endDateStr);
				break;
			default:
				break;
			}
		}
	};

	private String getTodayString() {
		Calendar c = Calendar.getInstance();
		int year, month, day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
		return date;
	}

	private class StartDateSelectedListener implements DatePickerFragment.OnDateSelectedListener {
		@Override
		public void onDateSelected(int year, int month, int day) {
			String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
			String nowDate = TimeSet.getDate4();
			long resultNow = TimeSet.timeDateFormat(date, nowDate);
			long resultEnd = 0;
			if (!TextUtils.isEmpty(endDateStr)) {
				resultEnd = TimeSet.timeDateFormat(date, endDateStr);
			}
			if (resultEnd > 0 || resultNow > 0) {
				startDateStr = "";
				startDateTv.setText(startDateStr);
				Toast.makeText(context, context.getString(R.string.date_pick_tip_start), Toast.LENGTH_SHORT).show();
			} else {
				startDateStr = date;
				startDateTv.setText(startDateStr);
			}
		}
	}

	private class EndDateSelectedListener implements DatePickerFragment.OnDateSelectedListener {

		@Override
		public void onDateSelected(int year, int month, int day) {
			String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
			String nowDate = TimeSet.getDate4();
			long resultNow = TimeSet.timeDateFormat(date, nowDate);
			long resultStart = 0;
			if (!TextUtils.isEmpty(startDateStr)) {
				resultStart = TimeSet.timeDateFormat(date, startDateStr);
			}
			if (resultStart < 0 || resultNow > 0) {
				endDateStr = "";
				endDateTv.setText(endDateStr);
				Toast.makeText(context, context.getString(R.string.date_pick_tip_end), Toast.LENGTH_SHORT).show();
			} else {
				endDateStr = date;
				endDateTv.setText(endDateStr);
			}
		}
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}
}

package com.hnao.warehouse.view;

import java.util.Calendar;
import java.util.Date;

import com.hnao.warehouse.R;
import com.hnao.warehouse.TimeSet;
import com.hnao.warehouse.fragment.DatePickerFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OneDayPickerView extends LinearLayout {

	private Context context;

	private TextView dateTv;

	private String stringDate = "";

	private TextView todayTv;

	private TextView clearTv;

	private Date mDate;

	public OneDayPickerView(Context context) {
		this(context, null);
	}

	public OneDayPickerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OneDayPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView(context, attrs, defStyleAttr);
	}

	private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
		LayoutInflater.from(context).inflate(R.layout.one_day_picker, this, true);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();

		dateTv = (TextView) findViewById(R.id.date_tv);

		todayTv = (TextView) findViewById(R.id.tv_today);

		clearTv = (TextView) findViewById(R.id.tv_clear);

		todayTv.setOnClickListener(onClickListener);

		clearTv.setOnClickListener(onClickListener);

		dateTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerFragment datePickerFragment = new DatePickerFragment();
				int year = 0, month = 0, day = 0;
				if (!TextUtils.isEmpty(stringDate)) {
					String[] strings = stringDate.split("-");
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
				datePickerFragment.setDateSelectedListener(new DateSelectedListener());
				datePickerFragment.show(((Activity) context).getFragmentManager(), "Dialog");
			}
		});
	}

	private View.OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_today:
				stringDate = getTodayString();
				dateTv.setText(stringDate);
				break;
			case R.id.tv_clear:
				stringDate = "";
				dateTv.setText(stringDate);
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
		mDate.setYear(year-1900);
		mDate.setMonth(month);
		mDate.setDate(day);
		String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
		return date;
	}

	private class DateSelectedListener implements DatePickerFragment.OnDateSelectedListener {
		@Override
		public void onDateSelected(int year, int month, int day) {
			String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
			mDate.setYear(year-1900);
			mDate.setMonth(month);
			mDate.setDate(day);
			stringDate = date;
			dateTv.setText(stringDate);

		}
	}

	public String getDateStr() {
		return stringDate;
	}

	public Date getDate() {
		if (mDate != null) {
			return mDate;
		}
		return null;
	}

	public void setDate(int year, int month, int day) {
		String date = String.valueOf(year) + "-" + (month + 1) + "-" + day;
		mDate.setYear(year-1900);
		mDate.setMonth(month);
		mDate.setDate(day);
		stringDate = date;
		dateTv.setText(stringDate);
	}

	public void setDate(Date date) {
		mDate = date;
		String dateStr = mDate.getYear() + 1900 + "-" + (mDate.getMonth() + 1) + "-" + mDate.getDate();
		stringDate = dateStr;
		dateTv.setText(stringDate);
	}
}

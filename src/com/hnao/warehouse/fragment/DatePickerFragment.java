package com.hnao.warehouse.fragment;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	private int mYear, mMonth, mDay;

	private OnDateSelectedListener listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// ���ݴ���Ĳ������ó�ʼ����
		Bundle bundle = getArguments();
		if (bundle != null) {
			mYear = bundle.getInt("year", 0);
			mMonth = bundle.getInt("month", 0);
			mDay = bundle.getInt("day", 0);
		}
		// ���õ�ǰʱ��Ϊ��ʼ����
		if (mYear == 0 || mMonth == 0 || mDay == 0) {
			Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
		}
		return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onDateSelected(year, monthOfYear, dayOfMonth);
		}
	}

	public interface OnDateSelectedListener {
		void onDateSelected(int year, int month, int day);
	}

	public void setDateSelectedListener(OnDateSelectedListener listener) {
		this.listener = listener;
	}
}

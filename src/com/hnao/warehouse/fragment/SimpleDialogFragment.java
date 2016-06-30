package com.hnao.warehouse.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class SimpleDialogFragment extends DialogFragment {

	private String message;

	public SimpleDialogFragment(String message) {
		super();
		this.message = message;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Context context = getActivity();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);

		return builder.create();
	}
}

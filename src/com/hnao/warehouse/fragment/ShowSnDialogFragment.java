package com.hnao.warehouse.fragment;

import com.hnao.warehouse.Utils;
import com.hnao.warehouse.beans.ComConst;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ShowSnDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		SharedPreferences sp = getActivity().getSharedPreferences(ComConst.SP_LOGIN, Context.MODE_PRIVATE);

		final String sn = sp.getString(ComConst.SN, "");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("序列号，复制可发送！").setMessage(sn).setPositiveButton("复制", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ClipboardManager myClipboard;
				myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
				myClipboard.setText(sn);
				Utils.showStringToast(getActivity(), "已复制到粘贴板！");
			}
		}).setNegativeButton("取消", null);

		return builder.create();
	}

}

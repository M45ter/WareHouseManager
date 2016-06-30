package com.hnao.warehouse.fragment;

import com.hnao.warehouse.R;
import com.hnao.warehouse.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class StockTakingFragment extends Fragment {

	public final static String TAG = "StockTakingFragment";

	private Context mContext;

	private Button btnScan;

	private TextView tvResult;

	private TextView tvFormat;

	private TextView tvType;

	private ImageView ivResult;

	private final static int REQ_CODE = 100;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_stock_taking, container, false);

		mContext = getActivity();

		tvResult = (TextView) view.findViewById(R.id.tv_result);

		tvFormat = (TextView) view.findViewById(R.id.tv_format);

		tvType = (TextView) view.findViewById(R.id.tv_type);

		ivResult = (ImageView) view.findViewById(R.id.result_image);

		btnScan = (Button) view.findViewById(R.id.btn_scan_barcode);

		btnScan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent("com.zey.zxing.SCAN_CODE");
					startActivityForResult(intent, REQ_CODE);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "start intent failed!");
					Utils.showStringToast(mContext, "没有找到扫码的界面！");
				}

			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onActivityResult");
		if (requestCode == REQ_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {

					int width = extras.getInt("width");
					int height = extras.getInt("height");

					LayoutParams lps = new LayoutParams(width, height);
					lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
							getResources().getDisplayMetrics());
					lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
							getResources().getDisplayMetrics());
					lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
							getResources().getDisplayMetrics());

					ivResult.setLayoutParams(lps);

					String result = extras.getString("result");
					tvResult.setText("Result:" + result);

					String format = extras.getString("format");
					tvFormat.setText("Format:" + format);

					String type = extras.getString("type");
					tvType.setText("Type:" + type);

					Bitmap barcode = null;

					if (extras != null) {
						byte[] compressedBitmap = extras.getByteArray("barcode_bitmap");
						if (compressedBitmap != null) {
							barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
							// Mutable copy:
							barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
						}
					}

					ivResult.setImageBitmap(barcode);
				}
			}
		}
	}

}

package com.example.worldtrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyMingPianActivity extends Activity {

	private RelativeLayout mRlgs1;
	private TextView mTvmmp1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mymingpian);
		mTvmmp1 =(TextView)this.findViewById(R.id.mTvmmp1);
		mTvmmp1.setOnClickListener(listener);
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		
}
	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlgs1:
				finish();
				break;
			case R.id.mTvmmp1:
				startActivity(new Intent(getApplicationContext(), MingPianShouCangActivity.class));
				break;

			default:
				break;
			}
		}
	};
}
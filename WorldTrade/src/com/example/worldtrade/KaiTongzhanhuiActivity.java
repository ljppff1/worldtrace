package com.example.worldtrade;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class KaiTongzhanhuiActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private LinearLayout mLL1;
	private LinearLayout mLL2;
	private String CHINESE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.kaitongzhanhui);
		}else{
			setContentView(R.layout.kaitongzhanhuie);
		}

		
		
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		mLL1 =(LinearLayout)this.findViewById(R.id.mLL1);
		mLL1.setOnClickListener(listener);
		mLL2 =(LinearLayout)this.findViewById(R.id.mLL2);
		mLL2.setOnClickListener(listener);
}
	
	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlgs1:
				finish();
				break;
			case R.id.mLL2:
				startActivity(new Intent(getApplicationContext(), ZhiFuActivity.class));
				
				break;
			case R.id.mLL1:
				startActivity(new Intent(getApplicationContext(), ZhiFuActivity.class));
				
				break;

			default:
				break;
			}
		}
	};

}
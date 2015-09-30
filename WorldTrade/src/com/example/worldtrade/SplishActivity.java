package com.example.worldtrade;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;

public class SplishActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splish);
		SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("zz", "lj"); 
		editor.commit(); 
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
				String wechatNo = mySharedPreferences.getString("wechatNo", ""); 
		         if(TextUtils.isEmpty(wechatNo)){
				startActivity(new Intent(getApplicationContext(), MainActivityl1.class));
				finish();
		         }else if(wechatNo.equals("1")){
						startActivity(new Intent(getApplicationContext(),MainActivity.class));
						finish();

		         }else{
						startActivity(new Intent(getApplicationContext(), MainActivity2.class));
						finish();

		         }
			}
		}, 2000);
	}
private Handler handler =new Handler();
	
}

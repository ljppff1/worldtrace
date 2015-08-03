package com.example.worldtrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplishActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splish);
	
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		}, 2000);
	}
private Handler handler =new Handler();
	
}

package com.example.worldtrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class ChanPingXiangQingActivity extends Activity {

	private RelativeLayout mRlgs1;
	private ImageView mIvwhat1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chanpinxiangqing);
		
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		mIvwhat1 =(ImageView)this.findViewById(R.id.mIvwhat1);
		mIvwhat1.setOnClickListener(listener);
		
}
	
	private View layout;
	private PopupWindow mPop;


	
	private void showWindow() {
		
		
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		 layout = mLayoutInflater.inflate(R.layout.window1, null);
			layout.setFocusable(true); // 这个很重要  
			layout.setFocusableInTouchMode(true);  
		 mPop = new PopupWindow(layout,320, LayoutParams.WRAP_CONTENT);
		 mPop.setFocusable(true);
		 mPop.setOutsideTouchable(true);
		 mPop.update();
			//位置在R.id.button的正下方
		 mPop.showAsDropDown(findViewById(R.id.mIvwhat1), 54,14);
		// mPop.showAtLocation(this.findViewById(R.id.mLLf3n), Gravity.RIGHT|Gravity.TOP,30, 60);
		//popwindow的宽度和按钮的宽度相同都是230
		layout.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					if (mPop != null && mPop.isShowing()) {
						mPop.dismiss();
						mPop = null;
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 if (mPop != null && mPop.isShowing()) {
					 mPop.dismiss();
				  mPop = null; }
				 return true;
			}
		});
		
		
	}
	

	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlgs1:
				finish();
				break;
			case R.id.mIvwhat1:
				showWindow();
				break;

			default:
				break;
			}
		}
	};
}
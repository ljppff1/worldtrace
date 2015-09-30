package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.easemob.chatuidemo.activity.ChatActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChanPingXiangQingActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private ImageView mIvwhat1;
	private String id;
	private String title;
	private String introduction;
	private String img;
	private String address;
	private ImageView mIvwww;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private TextView mTv1;
	private TextView mWTv12;
	private String number;
	private RelativeLayout mRl11;
	private String CC;
	private String url;
	private ProgressBar progressBar_sale;
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.chanpinxiangqing);
		}else{
			setContentView(R.layout.chanpinxiangqinge);
		}

		id =getIntent().getExtras().getString("id");
		CC =getIntent().getExtras().getString("CC");

		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);
		if(CC.equals("1")){
			url ="http://pine.i3.com.hk/trade/json/purchaseshow.php";
		}else{
			url ="http://pine.i3.com.hk/trade/json/productshow.php";

		}
		initData1();
		
		
		
		mTv1 =(TextView)this.findViewById(R.id.mTv1);
		mWTv12 =(TextView)this.findViewById(R.id.mWTv12);
		mIvwww=(ImageView)this.findViewById(R.id.mIvwww);
		initImageLoaderOptions();
		imageLoader.displayImage("http://pine.i3.com.hk/trade/UPFILE/UserPhoto/small/"+img,
				mIvwww, options);
		mTv1.setText(title);
		mWTv12.setText(introduction);
		
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		mRl11 =(RelativeLayout)this.findViewById(R.id.mRl11);
		mRl11.setOnClickListener(listener);
		mIvwhat1 =(ImageView)this.findViewById(R.id.mIvwhat1);
		mIvwhat1.setOnClickListener(listener);
		
		 
		
		
}
	
	
	private void initData1() {
		downloadsearch1("0");
	}
	public void downloadsearch1(String area11){
		 RequestParams params = new RequestParams();
       List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
       nameValuePairs.add(new BasicNameValuePair("id",id));
       params.addBodyParameter(nameValuePairs);
       HttpUtils http = new HttpUtils();
       http.send(HttpRequest.HttpMethod.POST,
    		   url,
               params,
               new RequestCallBack<String>() {

					private String msg;

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0.result);
							String string_code = jsonObject.getString("code");
							 msg = jsonObject.getString("msg");
							 int  num_code=Integer.valueOf(string_code);
							 if (num_code==1) {
								 //保存到本地
								 JSONObject array = jsonObject.getJSONObject("data");

								 
								 
								 
								 
								 
								 Toast.makeText(getApplicationContext(), msg, 0).show();

							}
							 else {
								 Toast.makeText(getApplicationContext(), msg, 0).show();
									progressBar_sale.setVisibility(View.GONE);
								//new AlertInfoDialog(SaleActivity.this).show();
							}
						} catch (JSONException e) {
							 Toast.makeText(getApplicationContext(), msg, 0).show();

							progressBar_sale.setVisibility(View.GONE);
							e.printStackTrace();
						}
							
					
						
					}

				
         
       });
	}
	
	private void initImageLoaderOptions() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.a)
				.cacheInMemory()
				.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
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
				//showWindow();
				break;
			case R.id.mRl11:
				if(!TextUtils.isEmpty(number)){
					Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
					intent.putExtra("userId", number);
				 startActivity(intent);	
					}else{
						startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
					}

				break;

			default:
				break;
			}
		}
	};
}
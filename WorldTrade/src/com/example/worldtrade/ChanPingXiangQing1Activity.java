package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.easemob.chatuidemo.activity.ChatActivity;
import com.example.utils.Content;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChanPingXiangQing1Activity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private ImageView mIvwhat1;
	private String CHINESE;
	private String ID;
	private ProgressBar progressBar_sale;
	private ImageView mIvww;
	private DisplayImageOptions options;
	private TextView mTv2;
	private TextView mTv1;
	private RelativeLayout mRlww;
	private String number;
	private LinearLayout mLLind2;
	private String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.chanpinxiangqing1);
		}else{
			setContentView(R.layout.chanpinxiangqing1e);
		}
		userid =mySharedPreferences1.getString("id", "");

		mLLind2 =(LinearLayout)this.findViewById(R.id.mLLind2);
		mLLind2.setOnClickListener(listener);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);
		ID =getIntent().getStringExtra("ID");
	
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		mIvwhat1 =(ImageView)this.findViewById(R.id.mIvwhat1);
		mIvwhat1.setOnClickListener(listener);
		mIvww =(ImageView)this.findViewById(R.id.mIvww);
		mTv2 =(TextView)this.findViewById(R.id.mTv2);
		mTv1 =(TextView)this.findViewById(R.id.mTv1);
		mRlww=(RelativeLayout)this.findViewById(R.id.mRlww);
		mRlww.setOnClickListener(listener);
		initData1();
}
	private void initImageLoaderOptions() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.a)
				.cacheInMemory()
				.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	
	private void initData1() {
		downloadsearch1("0");
	}
	public void downloadsearch1(String area11){
		 RequestParams params = new RequestParams();
       List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
       nameValuePairs.add(new BasicNameValuePair("id",ID));
       params.addBodyParameter(nameValuePairs);
       HttpUtils http = new HttpUtils();
       http.send(HttpRequest.HttpMethod.POST,
    		   "http://pine.i3.com.hk/trade/json/productshow.php",
               params,
               new RequestCallBack<String>() {

					private String msg;

					@Override
					public void onFailure(HttpException arg0, String arg1) {
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
								 String title = array.getString("title");
								 String introduction = array.getString("introduction");
								 String img = array.getString("img");
								 String content = array.getString("content");
								 number =array.getString("number");
								 
									initImageLoaderOptions();
									imageLoader.displayImage(Content.ImageUrl+img,
											mIvww, options);
									mTv1.setText(title);
									mTv2.setText(content);
									progressBar_sale.setVisibility(View.GONE);
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
	


private void initDataos() {
	downloadsearchos("0");
}
public void downloadsearchos(String area11){
	progressBar_sale.setVisibility(View.VISIBLE);
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id", ID));
   nameValuePairs.add(new BasicNameValuePair("userid", userid));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/addicolle.php",
           params,
           new RequestCallBack<String>() {

				private String msg;
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(arg0.result);
						String string_code = jsonObject.getString("code");
						 msg = jsonObject.getString("msg");
						
						 int  num_code=Integer.valueOf(string_code);
						 progressBar_sale.setVisibility(View.GONE);
					} catch (JSONException e) {
						progressBar_sale.setVisibility(View.GONE);
						 Toast.makeText(getApplicationContext(),msg, 0).show();
					}
				}
   });
}
public void choiceWhat(View v){
	   new AlertDialog.Builder(ChanPingXiangQing1Activity.this).setTitle(R.string.zg5)//设置对话框标题  
	     .setMessage(R.string.zg6)//设置显示的内容  
	     .setPositiveButton(R.string.zg7,new DialogInterface.OnClickListener() {//添加确定按钮  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
	        		startActivity(new Intent(ChanPingXiangQing1Activity.this,huiyuandengluActivity.class));
	  
	         }  
	     }).setNegativeButton(R.string.zg8,new DialogInterface.OnClickListener() {//添加返回按钮  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//响应事件  
	  
	         }  
	     }).show();
	  
}

	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mLLind2:
				if(!TextUtils.isEmpty(number)){

				initDataos();
				}else{
					//startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
					choiceWhat(v);

				}
				break;
			case R.id.mRlww:
				if(!TextUtils.isEmpty(number)){
					Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
					intent.putExtra("userId", number);
				 startActivity(intent);	
					}else{
						
						startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
					}


				break;
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
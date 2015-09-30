package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.Content;
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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyMingPianActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private TextView mTvmmp1;
	private ProgressBar progressBar_sale;
	private String id;
	private ImageView mIvw1;
	private TextView mV5;
	private TextView mV4;
	private TextView mV3;
	private TextView mV2;
	private TextView mV1;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.mymingpian);
		}else{
			setContentView(R.layout.mymingpiane);
		}

		mTvmmp1 =(TextView)this.findViewById(R.id.mTvmmp1);
		mTvmmp1.setOnClickListener(listener);
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);
		SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		id=mySharedPreferences.getString("id",""); 
		
		mIvw1 =(ImageView)this.findViewById(R.id.mIvw1);
		mV5 =(TextView)this.findViewById(R.id.mVf5);
		mV1 =(TextView)this.findViewById(R.id.mVf1);
		mV2 =(TextView)this.findViewById(R.id.mVf2);
		mV3 =(TextView)this.findViewById(R.id.mVf3);
		mV4 =(TextView)this.findViewById(R.id.mVf4);
		
		initData();
    }
	
	private void initData() {
		downloadsearch("0");
	}
	public void downloadsearch(String area11){
		progressBar_sale.setVisibility(View.VISIBLE);
		 RequestParams params = new RequestParams();
	   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
	   nameValuePairs.add(new BasicNameValuePair("userid",id));	   
	   params.addBodyParameter(nameValuePairs);
	   HttpUtils http = new HttpUtils();
	   http.send(HttpRequest.HttpMethod.POST,
	  		 "http://pine.i3.com.hk/trade/json/business_card.php",
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
							 JSONObject data =jsonObject.getJSONObject("data");
							 String img = data.getString("img");
							 String companyname = data.getString("companyname");
							 String name = data.getString("name");
							 String tell = data.getString("tell");
							 String emall = data.getString("emall");
							 String Locationone = data.getString("Locationone");
							 String Locationtwo = data.getString("Locationtwo");
							 String Locationthree = data.getString("Locationthree");
							 String address = data.getString("address");
							 int  num_code=Integer.valueOf(string_code);
							 if (num_code==1) {
								 mV1.setText(companyname);
								 mV2.setText(name);
								 mV3.setText(tell);
								 mV4.setText(emall);
								 mV5.setText(address);
									initImageLoaderOptions();
									imageLoader.displayImage(Content.ImageUrl+img,
											mIvw1, options);

									progressBar_sale.setVisibility(View.GONE);
							 }
							 else {
									 Toast.makeText(getApplicationContext(),msg, 0).show();
										progressBar_sale.setVisibility(View.GONE);
							}
						} catch (JSONException e) {
							progressBar_sale.setVisibility(View.GONE);
							 Toast.makeText(getApplicationContext(),  msg, 0).show();
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
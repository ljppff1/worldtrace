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

public class GongsijianjieActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private String CHINESE;
	private RelativeLayout mLoginOut;
	private String id;
	private ImageView mIv12;
	private TextView Tv1;
	private TextView Tv2;
	private TextView Tv3;
	private TextView Tv4;
	private TextView Tv5;
	private TextView Tv6;
	private TextView Tv7;
	private TextView Tv8;
	private TextView Tv9;
	private ProgressBar progressBar_sale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.gongsijianjie);
		}else{
			setContentView(R.layout.gongsijianjiee);
		}
		mLoginOut =(RelativeLayout)this.findViewById(R.id.mLoginOut);
		mLoginOut.setOnClickListener(listener);
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);
		mIv12 =(ImageView)this.findViewById(R.id.mIv12);
		Tv1   =(TextView)this.findViewById(R.id.Tv1);
		Tv2   =(TextView)this.findViewById(R.id.Tv2);
		Tv3   =(TextView)this.findViewById(R.id.Tv3);
		Tv4   =(TextView)this.findViewById(R.id.Tv4);
		Tv5   =(TextView)this.findViewById(R.id.Tv5);
		Tv6   =(TextView)this.findViewById(R.id.Tv6);
		Tv7   =(TextView)this.findViewById(R.id.Tv7);
		Tv8   =(TextView)this.findViewById(R.id.Tv8);
		Tv9   =(TextView)this.findViewById(R.id.Tv9);
		
		SharedPreferences mySharedPreferences=getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		id=	mySharedPreferences.getString("id",""); 

		initData();

		
}

private void initData() {
	downloadsearch("0");
}
public void downloadsearch(String area11){
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id", id));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/companyshow.php",
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
					          String conpany = array.getString("conpany");
					          String introduction = array.getString("introduction");
					          String username = array.getString("username");
					          String tell = array.getString("tell");
					          String emall = array.getString("emall");
					          
					          String web = array.getString("web");
					          String number = array.getString("number");
					          String img = array.getString("img");
					          String group = array.getString("group");
					          String locationone = array.getString("locationone");
					          String categoryone = array.getString("categoryone");
					          String categorytwe = array.getString("categorytwe");
					          String address = array.getString("address");
							 
					          Tv1.setText(conpany);
					          Tv2.setText(locationone);
					          Tv3.setText(categoryone+">"+categorytwe);
					          Tv4.setText(group);
					          Tv5.setText(username);
					          Tv6.setText(tell);
					          Tv7.setText(emall);
					          Tv8.setText(address);
					          Tv9.setText(web);
					      
							initImageLoaderOptions();
							imageLoader.displayImage(Content.ImageUrl+img,
									mIv12, options);

								progressBar_sale.setVisibility(View.GONE);

						}
						 else {
								progressBar_sale.setVisibility(View.GONE);
								 Toast.makeText(getApplicationContext(), msg, 0).show();

						}
					} catch (JSONException e) {
								progressBar_sale.setVisibility(View.GONE);
								 Toast.makeText(getApplicationContext(), msg, 0).show();
						e.printStackTrace();
					}
				}     
   });
}protected ImageLoader imageLoader = ImageLoader.getInstance();
private DisplayImageOptions options;
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
			case R.id.mLoginOut:
				SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
				SharedPreferences.Editor editor = mySharedPreferences.edit(); 
				editor.putString("number", ""); 
				editor.putString("name",""); 
				editor.putString("username",""); 
				editor.putString("wechatNo",""); 
				editor.putString("id",""); 

				editor.commit(); 
				startActivity(new Intent(getApplicationContext(), MainActivityl3.class));
                

				break;
			case R.id.mRlgs1:
				finish();
				break;

			default:
				break;
			}
		}
	};
}
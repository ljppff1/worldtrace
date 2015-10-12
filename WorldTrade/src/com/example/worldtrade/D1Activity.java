package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class D1Activity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private LinearLayout mLL1;
	private LinearLayout mLL2;
	private String CHINESE;
	private TextView mTvv1;
	private String what;
	private TextView mTv1;
	private TextView mTv2;
	private TextView mTv3;
	private ProgressBar progressBar_sale;
	private TextView mTvww1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		setContentView(R.layout.d1);
		mTvv1 =(TextView)this.findViewById(R.id.mTvv1);
		mTvww1 =(TextView)this.findViewById(R.id.mTvww1);
		what =getIntent().getExtras().getString("WHAT");
		if(CHINESE.equals("1")){
			if("a".equals(what)){
				mTvv1.setText("關於環貿");
				mTvww1.setText("返回");
			}
			if("b".equals(what)){
				mTvv1.setText("聯絡客服");				mTvww1.setText("返回");

			}
			if("c".equals(what)){
				mTvv1.setText("廣告查詢");				mTvww1.setText("返回");

			}
		}else{
			if("a".equals(what)){
				
				mTvv1.setText("About Worldwide Trade");				mTvww1.setText("back");
				mTvv1.setTextSize(16);
			}
			if("b".equals(what)){
				mTvv1.setText("Customer Service");	mTvww1.setText("back");mTvv1.setTextSize(16);
			}
			if("c".equals(what)){
				mTvv1.setText("Advertising Enquiry");	mTvww1.setText("back");mTvv1.setTextSize(16);
			}

		}

		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);
		mTv1 =(TextView)this.findViewById(R.id.mTv1);
		mTv2 =(TextView)this.findViewById(R.id.mTv2);
		mTv3 =(TextView)this.findViewById(R.id.mTv3);
		initData();
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
}
	private void initData() {
		downloadsearch("0");
	}
	public void downloadsearch(String area11){
		progressBar_sale.setVisibility(View.VISIBLE);
       String url ="http://pine.i3.com.hk/trade/json/infoshow.php?infoid=1";
       if("a".equals(what)){
    	   url ="http://pine.i3.com.hk/trade/json/infoshow.php?infoid=1";
       }
       if("b".equals(what)){
    	   url ="http://pine.i3.com.hk/trade/json/infoshow.php?infoid=2";
       }
       if("c".equals(what)){
    	   url ="http://pine.i3.com.hk/trade/json/infoshow.php?infoid=3";
       }
		 RequestParams params = new RequestParams();
       List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
       params.addBodyParameter(nameValuePairs);
       HttpUtils http = new HttpUtils();
       http.send(HttpRequest.HttpMethod.POST,
    		   url,
               params,
               new RequestCallBack<String>() {

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
							 int  num_code=Integer.valueOf(string_code);
							 if (num_code==1) {
								 //保存到本地
								JSONObject js =jsonObject.getJSONObject("data");
								String title = js.getString("title");
								String introduction = js.getString("introduction");
								String time = js.getString("time");
								mTv1.setText(title);
								mTv2.setText(introduction);
								mTv3.setText(time);
								progressBar_sale.setVisibility(View.GONE);

								}

							 else {
									progressBar_sale.setVisibility(View.GONE);

							}
						} catch (JSONException e) {								progressBar_sale.setVisibility(View.GONE);

						}
					}});
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
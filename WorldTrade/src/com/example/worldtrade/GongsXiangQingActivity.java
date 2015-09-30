package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GongsXiangQingActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private String ID;
	private ProgressBar progressBar_sale;
	private TextView mTv2;
	private TextView mTv4;
	private TextView mTv5;
	private TextView mTv7;
	private TextView mTv9;
	private TextView mTv10;
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.gongsixiangqing);
		}else{
			setContentView(R.layout.gongsixiangqinge);
		}
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);
		mTv2 =(TextView)this.findViewById(R.id.mTv2);
		mTv4 =(TextView)this.findViewById(R.id.mTv4);
		mTv5 =(TextView)this.findViewById(R.id.mTv5);
		mTv7 =(TextView)this.findViewById(R.id.mTv7);
		mTv9 =(TextView)this.findViewById(R.id.mTv9);
		mTv10 =(TextView)this.findViewById(R.id.mTv10);
		
		ID =getIntent().getExtras().getString("ID");
		initData();
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		
}
	

private void initData() {
	downloadsearch("0");
}
public void downloadsearch(String area11){
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id", ID));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/evaluationshow.php",
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
					          String title = array.getString("title");
					          String user = array.getString("user");
					          String content = array.getString("content");
					          String addtime = array.getString("addtime");
					          String answeruser = array.getString("answeruser");
					          String answer = array.getString("answer");
					          String answertime = array.getString("answertime");
							 
					          mTv2.setText(title);
					          mTv4.setText(content);
					          mTv5.setText(addtime);
					          
					          mTv7.setText(answeruser);
					          mTv9.setText(answer);
					          mTv10.setText(answertime);
					          
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
}
	
	OnClickListener listener =new  OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlgs1:
				finish();
				break;

			default:
				break;
			}
		}
	};
}
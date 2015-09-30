package com.example.worldtrade;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yanzi.ui.HorizontalListView;
import org.yanzi.ui.HorizontalListViewAdapter;

import com.easemob.chatuidemo.activity.ChatActivity;
import com.example.domain.Data1;
import com.example.fragment.Fragment1;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class F3NextActivity extends BaseActivity {
	private ImageView mTvback;
	private ListView mLvf11;
	   private ArrayList<String> mDataList =new ArrayList<String>();
	   private Myadapter adapter;
	private HorizontalListView hListView;
	private HorizontalListViewAdapter hListViewAdapter;
	private ImageView mIvright1;
	private TextView mTvrightdetail;
	private TextView mTvrightdetailas;
	private RelativeLayout mRlf3n1;
	private String ID;
	private ProgressBar progressBar_sale1;
	   private List<Data1> mlist =new ArrayList<Data1>();
	private TextView mVf1;
	private TextView mVf2;
	private TextView mVf3;
	private TextView mVf4;
	private TextView mVf5;
	private TextView mVf6;
	private LinearLayout mLLind2;
	private String userid;
	private ProgressBar progressBar_sale;
	private LinearLayout mLLind3;
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");

		if(CHINESE.equals("1")){
		setContentView(R.layout.f3next);
		}else{
			setContentView(R.layout.f3nexte);
		}

		ID =getIntent().getStringExtra("ID");
		SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 

		mLLind3 =(LinearLayout)this.findViewById(R.id.mLLind3);
		mLLind3.setOnClickListener(listener);
		userid =mySharedPreferences.getString("id", "");
		hListView = (HorizontalListView)this.findViewById(R.id.horizon_listview);
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent =new Intent(getApplicationContext(), com.example.worldtrade.ChanPingXiangQing1Activity.class);
				intent.putExtra("ID", mlist.get(position).oid);
				startActivity(intent);
			}
		});

		final int[] ids = {R.drawable.abc2, R.drawable.abc2,
				R.drawable.abc2, R.drawable.abc2,R.drawable.abc2, R.drawable.abc2,
				R.drawable.abc2, R.drawable.abc2
				};
		final  String[] titles ={"d","d","d","d","d","d","d","d"};
	
		
		mVf1 =(TextView)this.findViewById(R.id.mVf1);
		mVf2 =(TextView)this.findViewById(R.id.mVf2);
		mVf3 =(TextView)this.findViewById(R.id.mVf3);
		mVf4 =(TextView)this.findViewById(R.id.mVf4);
		mVf5 =(TextView)this.findViewById(R.id.mVf5);
		mVf6 =(TextView)this.findViewById(R.id.mVf6);
		mLLind2 =(LinearLayout)this.findViewById(R.id.mLLind2);
		mLLind2.setOnClickListener(listener);

		mTvrightdetail= (TextView)this.findViewById(R.id.mTvrightdetail);
		mTvrightdetail.setOnClickListener(listener);
		mTvback =(ImageView)this.findViewById(R.id.mTvback);
		mIvright1 =(ImageView)this.findViewById(R.id.mIvright1);
		mIvright1.setOnClickListener(listener);
		mRlf3n1 =(RelativeLayout)this.findViewById(R.id.mRlf3n1);
		mRlf3n1.setOnClickListener(listener);
		
		mTvrightdetailas=(TextView)this.findViewById(R.id.mTvrightdetailas);
		mTvrightdetailas.setOnClickListener(listener);
		mTvback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		progressBar_sale1 =(ProgressBar)this.findViewById(R.id.progressBar_sale1);
		progressBar_sale1.setVisibility(View.VISIBLE);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);
		
		initDatao2();
		initDatao1();
		
		

		
	}

	private String number;


private void initDatao2() {
	downloadsearcho2("0");
}
public void downloadsearcho2(String area11){
	progressBar_sale.setVisibility(View.VISIBLE);
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id", ID));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/companyshow.php",
           params,
           new RequestCallBack<String>() {
				private String msg;
				private String conpany;
				private String introduction;
				private String username;
				private String tell;
				private String emall;
				private String locationone;
						private String address;
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
							 mlist.clear();
							 
							 JSONObject array = jsonObject.getJSONObject("data");
							 number = array.getString("number");
							 conpany = array.getString("conpany");
							 introduction = array.getString("introduction");
							 username = array.getString("username");
							 tell = array.getString("tell");
							 emall = array.getString("emall");
							 address = array.getString("address");
							 progressBar_sale.setVisibility(View.GONE);
                            initView();
						 }
						 else {
								 Toast.makeText(getApplicationContext(),msg, 0).show();
								 progressBar_sale.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						progressBar_sale.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(),"fail", 0).show();
					}
				}

				private void initView() {
					mVf1.setText(introduction);
					mVf2.setText(getString(R.string.bbaz5)+username);
					mVf3.setText(getString(R.string.bbaz6)+tell);
					mVf4.setText(getString(R.string.bbaz7)+emall);
					mVf5.setText(getString(R.string.bbaz8)+address);
					mVf6.setText(conpany);
					
				}
     
   });
}
	
private void initDatao1() {
	downloadsearcho1("0");
}
public void downloadsearcho1(String area11){
	progressBar_sale1.setVisibility(View.VISIBLE);
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id", ID));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/companyproduct.php",
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
							 mlist.clear();
							 JSONArray array = jsonObject.getJSONArray("data");
							 for(int i=0;i<array.length();i++){
								 Data1 d1 =new Data1();
								 d1.oid=array.getJSONObject(i).getString("id");
								 d1.oname=array.getJSONObject(i).getString("title");
								 d1.pic=array.getJSONObject(i).getString("img");
								 mlist.add(d1);
							 }
							 progressBar_sale1.setVisibility(View.GONE);
                            initListView();
						 }
						 else {
								 Toast.makeText(getApplicationContext(),msg, 0).show();
								 progressBar_sale1.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						progressBar_sale1.setVisibility(View.GONE);
						 Toast.makeText(getApplicationContext(),msg, 0).show();
					}
				}

				private void initListView() {
					hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),mlist);
					hListView.setAdapter(hListViewAdapter);

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
   nameValuePairs.add(new BasicNameValuePair("anid", ID));
   nameValuePairs.add(new BasicNameValuePair("userid", userid));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/addcollection.php",
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

	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mLLind3:
				if(!TextUtils.isEmpty(number)){
				Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
				intent.putExtra("userId", number);
             startActivity(intent);	
				}else{
					startActivity(new Intent(getApplicationContext(),MainActivityl3.class));

				}
				break;
			case R.id.mLLind2:
				if(!TextUtils.isEmpty(number)){

				initDataos();
				}else{
					startActivity(new Intent(getApplicationContext(),MainActivityl3.class));

				}
				break;
			case R.id.mIvright1:
				showWindow();
				break;
			case R.id.mTvrightdetail:
				startActivity(new Intent(getApplicationContext(), GongsijianjieActivity.class));
				break;
			case R.id.mTvrightdetailas:
				Intent intent =new Intent(getApplicationContext(), ChanPinLieBiaoActivity.class);
				intent.putExtra("ID", ID);
				startActivity(intent);

				break;
			case R.id.mRlf3n1:
				startActivity(new Intent(getApplicationContext(), PingJiaActivity.class));

				break;
			default:
				break;
			}
		}
	};
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
		 mPop.showAsDropDown(findViewById(R.id.mIvright1), 54,14);
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
	

	
	private void initData() {
      for(int i=0;i<28;i++){
    	  mDataList.add(getResources().getString(R.string.ac12)+i);
      }
	}


	class Holder{
		TextView mTvri10,mTvri11,mTvri12;
		ImageView imageView;
	}
	class  Myadapter extends   BaseAdapter{
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			        
			
			Holder holder = null;
			if(convertView==null){
				convertView = LayoutInflater.from(F3NextActivity.this)
						.inflate(R.layout.item_listview_4, null);
				holder = new Holder();
/*				holder.mTvri10 =(TextView)convertView.findViewById(R.id.mRl112);
				holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
*/				convertView.setTag(holder);

			}else{
				holder =(Holder)convertView.getTag();
			}

			return convertView;

		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataList.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}



private Handler handler =new Handler();
	
} 


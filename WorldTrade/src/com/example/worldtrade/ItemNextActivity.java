package com.example.worldtrade;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.easemob.chatuidemo.activity.ChatActivity;
import com.example.fragment.Fragment1;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ItemNextActivity extends BaseActivity {
	private ImageView mTvback;
	private RelativeLayout mRlf11;
	private RelativeLayout mRlf12;
    private boolean flag =false;
	private ListView mLvf11;
    private ArrayList<Data> mDataList_origin=new ArrayList<ItemNextActivity.Data>();
    private ArrayList<Data> mDataList=new ArrayList<ItemNextActivity.Data>();
	private DisplayImageOptions options;
	private Myadapter myadapter;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private RelativeLayout mRlgs1;
	private String TID;
	private String wechatNo;
	private String userid;
	private ProgressBar progressBar_sale;
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.itemlistnext);
		}else{
			setContentView(R.layout.itemlistnexte);
		}

		TID =getIntent().getStringExtra("TID");
		SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		wechatNo =mySharedPreferences.getString("wechatNo","");
		userid =mySharedPreferences.getString("id", "");
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	
		
		initView();
		
		

		
	}
	

	

private void initView() {
	mRlf11 =(RelativeLayout)this.findViewById(R.id.mRlf11);
	mRlf12 =(RelativeLayout)this.findViewById(R.id.mRlf12);
	mRlf11.setOnClickListener(listener);
	mRlf12.setOnClickListener(listener);
	mLvf11 =(ListView)this.findViewById(R.id.mLvf11);
	initData();

	}

private void initDataos(int position) {
	downloadsearchos(position);
}
public void downloadsearchos(int area11){
	progressBar_sale.setVisibility(View.VISIBLE);
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("anid", mDataList.get(area11).ID));
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

private void initData() {
	downloadsearch("0");
}

public void downloadsearch(String area11){
	progressBar_sale.setVisibility(View.VISIBLE);
 if(flag){
	 wechatNo=2+"";
 }else{
	 wechatNo=1+"";

 }
	 RequestParams params = new RequestParams();
  List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
  nameValuePairs.add(new BasicNameValuePair("type", wechatNo));
  nameValuePairs.add(new BasicNameValuePair("categorytwo", TID));
  params.addBodyParameter(nameValuePairs);
  HttpUtils http = new HttpUtils();
  http.send(HttpRequest.HttpMethod.POST,
 		"http://pine.i3.com.hk/trade/json/userlist.php",
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
							 mDataList_origin.clear();
							 JSONArray array = jsonObject.getJSONArray("data");
							  for (int i = 0; i < array.length(); i++) {
								  Data  data=new Data();
								  
								 JSONObject jsonObject2 = array.getJSONObject(i);
								 data.ID= jsonObject2.getString("id");
								 data.number= jsonObject2.getString("number");
								 data.Name= jsonObject2.getString("title");
								 data.StreetName = jsonObject2.getString("address");
								 data.AreaNet=jsonObject2.getString("introduction");
								 data.CoverPic=jsonObject2.getString("img");
								 mDataList_origin.add(data);
		                          data.toString();						 
							}
							  mDataList.clear();
							  mDataList.addAll(mDataList_origin);
									progressBar_sale.setVisibility(View.GONE);
							  initListView();
						}
						 else {
								progressBar_sale.setVisibility(View.GONE);
							 Toast.makeText(getApplicationContext(), msg, 0).show();
						}
					} catch (JSONException e) {
						progressBar_sale.setVisibility(View.GONE);
							 Toast.makeText(getApplicationContext(), msg, 0).show();
					}
					
				}
  });
}

private void initListView() {
	 this.findViewById(R.id.progressBar_sale).setVisibility(View.GONE);
	 mLvf11.setVisibility(View.VISIBLE);
       myadapter = new Myadapter();
       mLvf11.setAdapter(myadapter);
       mLvf11.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				Intent intent =new Intent(getApplicationContext(), F3NextActivity.class);
				intent.putExtra("ID", mDataList.get(position).ID);
				startActivity(intent);
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
class Holder{
	TextView mTvri10,mTvri11,mTvri12;
	LinearLayout mLLww1,mLLww2;
	ImageView imageView;
}
class  Myadapter extends   BaseAdapter{
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		        
		
		Holder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(ItemNextActivity.this)
					.inflate(R.layout.item_listview_2, null);
			holder = new Holder();
			holder.mTvri10 =(TextView)convertView.findViewById(R.id.mTvri11);
			holder.mTvri11 =(TextView)convertView.findViewById(R.id.mTvri12);
			holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvri13);
			holder.mLLww1=(LinearLayout)convertView.findViewById(R.id.mLLww1);
			holder.mLLww2=(LinearLayout)convertView.findViewById(R.id.mLLww2);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
			convertView.setTag(holder);

		}else{
			holder =(Holder)convertView.getTag();
		}

		holder.mLLww1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(!TextUtils.isEmpty(wechatNo)){
             initDataos(position);			
			}else{
				startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
			}
			}
		});
		holder.mLLww2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(wechatNo)){
					Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
					intent.putExtra("userId", mDataList.get(position).number);
	             startActivity(intent);	
					}else{
						startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
					}

			}
		});
		holder.mTvri10.setText(mDataList.get(position).Name);
		holder.mTvri11.setText(mDataList.get(position).AreaNet);
		
		holder.mTvri12.setText(mDataList.get(position).StreetName);
		initImageLoaderOptions();
		imageLoader.displayImage(Content.ImageUrl+mDataList.get(position).CoverPic,
				holder.imageView, options);
		
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
class Data{
	String   ID;
	String number;
	String   Name;
	String   StreetName;
	String   AreaGross;
	String   AreaNet;
	String   SellingPrice;
	String   RentPrice;
	String   CoverPic;
	@Override
	public String toString() {
		return "Data [ID=" + ID + ", Name=" + Name + ", StreetName="
				+ StreetName + ", AreaGross=" + AreaGross + ", AreaNet="
				+ AreaNet + ", SellingPrice=" + SellingPrice
				+ ", RentPrice=" + RentPrice + ", CoverPic=" + CoverPic
				+ "]";
	}
	
}

OnClickListener listener =new OnClickListener() {
	
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mRlf11:
			if(flag){
				flag =false;

				mRlf11.setBackground(getResources().getDrawable(R.drawable.ax1));
				mRlf12.setBackground(getResources().getDrawable(R.drawable.ax2));
				initData();
			}
			break;
		case R.id.mRlf12:
			flag =true;
			mRlf11.setBackground(getResources().getDrawable(R.drawable.ax2));
			mRlf12.setBackground(getResources().getDrawable(R.drawable.ax1));
			initData();
			break;

		default:
			break;
		}
	}
};



private Handler handler =new Handler();
	
}

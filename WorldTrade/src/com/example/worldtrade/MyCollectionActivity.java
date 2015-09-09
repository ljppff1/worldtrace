package com.example.worldtrade;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyCollectionActivity extends Activity {
	private ImageView mTvback;
	private RelativeLayout mRlf11;
	private RelativeLayout mRlf12;
    private boolean flag =false;
	private ListView mLvf11;
    private ArrayList<Data> mDataList_origin=new ArrayList<MyCollectionActivity.Data>();
    private ArrayList<Data> mDataList=new ArrayList<MyCollectionActivity.Data>();
	private DisplayImageOptions options;
	private Myadapter myadapter;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private RelativeLayout mRlgs1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mycollection);
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

private void initData() {
	downloadsearch("0");
}

public void downloadsearch(String area11){
	 RequestParams params = new RequestParams();
  List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
  nameValuePairs.add(new BasicNameValuePair("PropertyLocation", area11));
  nameValuePairs.add(new BasicNameValuePair("RentSale", "1"));
  params.addBodyParameter(nameValuePairs);
  HttpUtils http = new HttpUtils();
  http.send(HttpRequest.HttpMethod.POST,
 		 com.example.utils.Content.URL_Search,
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
							 mDataList_origin.clear();
							 JSONArray array = jsonObject.getJSONArray("data");
							  for (int i = 0; i < array.length(); i++) {
								  
								  Data  data=new Data();
								  
								 JSONObject jsonObject2 = array.getJSONObject(i);
								 data.ID= jsonObject2.getString("ID");
								 data.Name= jsonObject2.getString("Name");
								 data.StreetName = jsonObject2.getString("StreetName");
								 data.AreaGross=jsonObject2.getString("AreaGross");
								 data.AreaNet=jsonObject2.getString("AreaNet");
								 data.CoverPic=jsonObject2.getString("CoverPic");
								 data.SellingPrice=jsonObject2.getString("SellingPrice");
								 data.RentPrice=jsonObject2.getString("RentPrice");
								 mDataList_origin.add(data);
								 
		                          data.toString();						 
							}
							  mDataList.clear();
							  mDataList.addAll(mDataList_origin);
								if (mDataList_origin.size()==0) {
									 Toast.makeText(getApplicationContext(), "暫無相關內容", 0).show();
								}
								else {
									//findViewById(R.id.tv_noresut).setVisibility(View.GONE);
								}

							  initListView();
						}
						 else {
							findViewById(R.id.progressBar_sale).setVisibility(View.GONE);
							//new AlertInfoDialog(SaleActivity.this).show();
						}
					} catch (JSONException e) {
						 if(mDataList.isEmpty())
						//new Dialog_noInternet(SaleActivity.this).show();
							 Toast.makeText(getApplicationContext(), "暫無相關內容", 0).show();
						e.printStackTrace();
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
				startActivity(new Intent(getApplicationContext(), ItemNextDetailActivity.class));
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
	ImageView imageView;
}
class  Myadapter extends   BaseAdapter{
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		        
		
		Holder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(MyCollectionActivity.this)
					.inflate(R.layout.item_listview_2, null);
			holder = new Holder();
			holder.mTvri10 =(TextView)convertView.findViewById(R.id.mTvri11);
			holder.mTvri11 =(TextView)convertView.findViewById(R.id.mTvri12);
			holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvri13);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
			convertView.setTag(holder);

		}else{
			holder =(Holder)convertView.getTag();
		}
		holder.mTvri10.setText(mDataList.get(position).Name);
		initImageLoaderOptions();
		imageLoader.displayImage(mDataList.get(position).CoverPic,
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
				mRlf11.setBackground(getResources().getDrawable(R.drawable.ax1));
				mRlf12.setBackground(getResources().getDrawable(R.drawable.ax2));
			}
			break;
		case R.id.mRlf12:
			flag =true;
			mRlf11.setBackground(getResources().getDrawable(R.drawable.ax2));
			mRlf12.setBackground(getResources().getDrawable(R.drawable.ax1));
			break;

		default:
			break;
		}
	}
};



private Handler handler =new Handler();
	
}

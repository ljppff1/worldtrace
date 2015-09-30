package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class MingPianShouCangActivity extends BaseActivity {

	private ProgressBar progressBar_sale;
	   private ListView mLv1;
	   private Myadapter myadapter;
	   private DisplayImageOptions options;
	   private ArrayList<Data> mDataList_origin=new ArrayList<MingPianShouCangActivity.Data>();
	   private ArrayList<Data> mDataList=new ArrayList<MingPianShouCangActivity.Data>();
	   protected ImageLoader imageLoader = ImageLoader.getInstance();
	private RelativeLayout mRlgs1;
	private String userid;
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.ziliaochefang2);
		}else{
			setContentView(R.layout.ziliaochefang2e);
		}

		mLv1 =(ListView)this.findViewById(R.id.mLv1);
		SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		userid =mySharedPreferences.getString("id", "");

	//	initData();
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);
		
			mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
			mRlgs1.setOnClickListener(listener);
			initData();
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

private void initData() {
	downloadsearch("0");
}
public void downloadsearch(String area11){
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("userid", userid));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/collectionlist.php",
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
								 data.ID= jsonObject2.getString("id");
								 data.Name= jsonObject2.getString("title");
								 data.StreetName = jsonObject2.getString("name");
								 data.RentPrice = jsonObject2.getString("tell");
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
       myadapter = new Myadapter();
       
       mLv1.setAdapter(myadapter);
       mLv1.setOnItemClickListener(new OnItemClickListener() {

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
	TextView mVi2,mVi1,mVi3;
	ImageView imageView;
}
class  Myadapter extends   BaseAdapter{
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		        
		
		Holder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.item_listview_71, null);
			holder = new Holder();
			
			holder.mVi1 =(TextView)convertView.findViewById(R.id.mVi1);
			holder.mVi2 =(TextView)convertView.findViewById(R.id.mVi2);
			holder.mVi3 =(TextView)convertView.findViewById(R.id.mVi3);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);

/*			holder.mTvri10 =(TextView)convertView.findViewById(R.id.mTvri10);
			holder.mTvri11 =(TextView)convertView.findViewById(R.id.mTvri11);
			holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvri12);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
*/			convertView.setTag(holder);

		}else{
			holder =(Holder)convertView.getTag();
		}
		
		holder.mVi1.setText(mDataList.get(position).Name);
		holder.mVi2.setText(mDataList.get(position).StreetName);
		holder.mVi3.setText(mDataList.get(position).RentPrice);
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

   
}
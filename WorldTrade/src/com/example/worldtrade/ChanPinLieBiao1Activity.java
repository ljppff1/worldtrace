package com.example.worldtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yanzi.ui.HorizontalListViewAdapter;

import com.example.domain.Data1;
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



public class ChanPinLieBiao1Activity extends BaseActivity {

	private ProgressBar progressBar_sale;
	   private ListView mLv1;
	   private Myadapter myadapter;
	   private DisplayImageOptions options;
	   protected ImageLoader imageLoader = ImageLoader.getInstance();
	private RelativeLayout mRlgs1;
	private ProgressBar progressBar_sale1;
	   private List<Data1> mlist =new ArrayList<Data1>();
	private String ID;
	private String CHINESE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.ziliaochefang3);
		}else{
			setContentView(R.layout.ziliaochefang3e);
		}

		ID =mySharedPreferences1.getString("id","");
		mLv1 =(ListView)this.findViewById(R.id.mLv1);
	//	initData();
		progressBar_sale1 =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale1.setVisibility(View.GONE);
		 
			mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
			mRlgs1.setOnClickListener(listener);
    initDatao1();
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
     
   });
}
private void initListView() {
       myadapter = new Myadapter();
       
       mLv1.setAdapter(myadapter);
       mLv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				Intent intent =new Intent(getApplicationContext(), com.example.worldtrade.ChanPingXiangQing2Activity.class);
				intent.putExtra("ID", mlist.get(position).oid);
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
	ImageView imageView;
}
class  Myadapter extends   BaseAdapter{
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		        
		
		Holder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.item_listview_7, null);
			holder = new Holder();
			holder.mTvri10 =(TextView)convertView.findViewById(R.id.mTvri10);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
			convertView.setTag(holder);

		}else{
			holder =(Holder)convertView.getTag();
		}
		
		holder.mTvri10.setText(mlist.get(position).oname);
		initImageLoaderOptions();
		imageLoader.displayImage(Content.ImageUrl+mlist.get(position).pic,
				holder.imageView, options);
		
		
		return convertView;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
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
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

public class F3NextActivity extends Activity {
	private ImageView mTvback;
	private ListView mLvf11;
	   private ArrayList<String> mDataList =new ArrayList<String>();
	   private Myadapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.f3next);
		mTvback =(ImageView)this.findViewById(R.id.mTvback);
		mTvback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mLvf11 =(ListView)this.findViewById(R.id.mLvf11);
		initData();
		adapter =new Myadapter();
		mLvf11.setAdapter(adapter);
		mLvf11.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getApplicationContext(), ItemNextDetailActivity.class));
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

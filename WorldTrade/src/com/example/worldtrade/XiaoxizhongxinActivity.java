package com.example.worldtrade;


import java.util.ArrayList;
import java.util.List;

import com.example.fragment.FragmentFJ1a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class XiaoxizhongxinActivity extends FragmentActivity {

	private RelativeLayout mIvtt1;
	private ListView mLv1;
	private ProgressBar progressBar_sale;
	private Myadapter myadapter;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiaoxizhongxin);
		mIvtt1 =(RelativeLayout)this.findViewById(R.id.mRL123);
		mIvtt1.setOnClickListener(listener);

		mLv1 =(ListView)this.findViewById(R.id.mLv1);
		initData();
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);

	}

private void initData() {
	//downloadsearch("0");
	//progressBar_sale.setVisibility(View.GONE);
	  initListView();

}

private void initListView() {
       myadapter = new Myadapter();
       
       mLv1.setAdapter(myadapter);
       mLv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
			}
		});

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
					.inflate(R.layout.item_listview_6, null);
/*			holder = new Holder();
			holder.mTvri10 =(TextView)convertView.findViewById(R.id.mTvri10);
			holder.mTvri11 =(TextView)convertView.findViewById(R.id.mTvri11);
			holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvri12);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
			convertView.setTag(holder);
*/
		}else{
			holder =(Holder)convertView.getTag();
		}
/*		holder.mTvri12.setText(mDataList.get(position).Name);
		holder.mTvri11.setText(mDataList.get(position).StreetName);
		initImageLoaderOptions();
		imageLoader.displayImage(mDataList.get(position).CoverPic,
				holder.imageView, options);
*/		
		return convertView;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 16;
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

	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRL123:
				finish();
				break;

			default:
				break;
			}
		}
	};
private Handler handler =new Handler();
	
}

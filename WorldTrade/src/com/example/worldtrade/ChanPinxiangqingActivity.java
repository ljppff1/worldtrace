package com.example.worldtrade;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.fragment.Fragment3;
import com.example.fragment.FragmentFJ1a;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class ChanPinxiangqingActivity extends BaseActivity {

	private RelativeLayout mIvtt1;
	private ListView mLv1;
	private ProgressBar progressBar_sale;
	private Myadapter myadapter;
	   private List<Data1> mlist =new ArrayList<ChanPinxiangqingActivity.Data1>();
	private DisplayImageOptions options;
	private String ID;
	private TextView mTv1;
	private TextView mTv2;
	private TextView mTv3;
	private ImageView mIv1;
	private String CHINESE;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.xiaoxixiangqing);
		}else{
			setContentView(R.layout.xiaoxixiangqinge);
		}

		mIvtt1 =(RelativeLayout)this.findViewById(R.id.mRL123);
		mIvtt1.setOnClickListener(listener);

		ID=getIntent().getExtras().getString("ID");
        
		mTv1 =(TextView)this.findViewById(R.id.mTv1);
		mTv2 =(TextView)this.findViewById(R.id.mTv2);
		mTv3 =(TextView)this.findViewById(R.id.mTv3);
		mIv1 =(ImageView)this.findViewById(R.id.mIv1);
		
		
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);

		initDatao1();
	}

private void initDatao1() {
	downloadsearcho1("0");
}
public void downloadsearcho1(String area11){
	progressBar_sale.setVisibility(View.VISIBLE);
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id",ID));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/newsshow.php",
           params,
           new RequestCallBack<String>() {

				private ViewPager mLvf11;
				private Myadapter adapter;
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
						String msg = jsonObject.getString("msg");
						
						 int  num_code=Integer.valueOf(string_code);
						 if (num_code==1) {
							 //保存到本地
							 mlist.clear();
							 JSONObject array = jsonObject.getJSONObject("data");
							 String title = array.getString("title");
							 String content = array.getString("content");
							 String addtime = array.getString("addtime");
							 String img = array.getString("img");
							  String str = content.replaceAll("\\<p>|</p>","");  
								mTv1.setText(title);
								mTv2.setText(str);
								mTv3.setText(addtime);
								initImageLoaderOptions();
								imageLoader.displayImage(Content.ImageUrl+img,
										mIv1, options);

							 
								progressBar_sale.setVisibility(View.GONE);
						 }
						 else {
								 Toast.makeText(getApplicationContext(),msg, 0).show();
								 progressBar_sale.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						progressBar_sale.setVisibility(View.GONE);
							 Toast.makeText(getApplicationContext(),  getResources().getString(R.string.abc44), 0).show();
					}
				}
		
     
   });
}
class Data1 {
	public String oid;
	public String oname;
	public String addTime;
	public String img;
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
			holder = new Holder();
			holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvi2);
			holder.mTvri11 =(TextView)convertView.findViewById(R.id.mTvi3);
			
			holder.imageView =(ImageView)convertView.findViewById(R.id.mIvw1);
			convertView.setTag(holder);

		}else{
			holder =(Holder)convertView.getTag();
		}
		holder.mTvri12.setText(mlist.get(position).oname);
		holder.mTvri11.setText(mlist.get(position).addTime);
		initImageLoaderOptions();
		imageLoader.displayImage(mlist.get(position).img,
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
}	private void initImageLoaderOptions() {
	options = new DisplayImageOptions.Builder()
	.showImageForEmptyUri(R.drawable.a)
	.cacheInMemory()
	.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
	.bitmapConfig(Bitmap.Config.RGB_565).build();
}
protected ImageLoader imageLoader = ImageLoader.getInstance();


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

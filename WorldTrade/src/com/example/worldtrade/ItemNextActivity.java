package com.example.worldtrade;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chatuidemo.activity.ChatActivity;
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
	private String HG;
	private TextView mTvw1;
	private TextView mTvreg244;
	private List<Data3> listd3 =new ArrayList<ItemNextActivity.Data3>();
	private String[] listd3s =new String[]{};
	private int ilistd3=-1;
    private String locationone ="";
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
				HG =getIntent().getStringExtra("HG");
		TID =getIntent().getStringExtra("TID");
		mTvw1 =(TextView)this.findViewById(R.id.mTvw1);
		mTvw1.setText(HG);
		mTvreg244 = (TextView)this.findViewById(R.id.mTvreg244);
		mTvreg244.setOnClickListener(listener);

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
  nameValuePairs.add(new BasicNameValuePair("locationone", locationone));
  
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
	TextView mTvri10,mTvri11,mTvri12,mEE1,mEE2;
	LinearLayout mLLww1,mLLww2;
	ImageView imageView;
}
public void choiceWhat(View v){
	   new AlertDialog.Builder(ItemNextActivity.this).setTitle(R.string.zg5)//设置对话框标题  
	     .setMessage(R.string.zg6)//设置显示的内容  
	     .setPositiveButton(R.string.zg7,new DialogInterface.OnClickListener() {//添加确定按钮  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
	        		startActivity(new Intent(ItemNextActivity.this,huiyuandengluActivity.class));
	  
	         }  
	     }).setNegativeButton(R.string.zg8,new DialogInterface.OnClickListener() {//添加返回按钮  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//响应事件  
	  
	         }  
	     }).show();
	  
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
			holder.mEE1 =(TextView)convertView.findViewById(R.id.mEE1);
			holder.mEE2 =(TextView)convertView.findViewById(R.id.mEE2);
			holder.mLLww1=(LinearLayout)convertView.findViewById(R.id.mLLww1);
			holder.mLLww2=(LinearLayout)convertView.findViewById(R.id.mLLww2);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
			convertView.setTag(holder);

		}else{
			holder =(Holder)convertView.getTag();
		}

		if(CHINESE.equals("1")){
			holder.mEE1.setText(R.string.ax1);
			holder.mEE2.setText(R.string.ax2);
		}else{
			holder.mEE1.setText(R.string.ax1e);
			holder.mEE2.setText(R.string.ax2e);
		}

		holder.mLLww1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(!TextUtils.isEmpty(wechatNo)){
             initDataos(position);			
			}else{
			//	startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
				choiceWhat(v);
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
					//	startActivity(new Intent(getApplicationContext(),MainActivityl3.class));
						choiceWhat(v);
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
		case R.id.mTvreg244:
		     initDatao3();
			break;

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



private void initDatao3() {
	downloadsearcho3("0");
}
public void downloadsearcho3(String area11){
	progressBar_sale.setVisibility(View.VISIBLE);
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/locationonelist.php",
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
							 listd3.clear();
							
							 JSONArray array = jsonObject.getJSONArray("data");
							 listd3s=new String[array.length()];
							 for(int i=0;i<array.length();i++){
								
								 Data3 d1 =new Data3();
								 d1.oid=array.getJSONObject(i).getString("locationoneid");
								 d1.oname=array.getJSONObject(i).getString("locationonename");
								 listd3.add(d1);
								 listd3s[i]=d1.oname;
							 }
								progressBar_sale.setVisibility(View.GONE);
                            initd1();
						 }
						
						 else {
								 Toast.makeText(getApplicationContext(),msg, 0).show();
									progressBar_sale.setVisibility(View.GONE);

							//new AlertInfoDialog(SaleActivity.this).show();
						}
					} catch (JSONException e) {
						//new Dialog_noInternet(SaleActivity.this).show();
						progressBar_sale.setVisibility(View.GONE);
						 Toast.makeText(getApplicationContext(),msg, 0).show();

						e.printStackTrace();
					}
						
				
					
				}

				private void initd1() {
					 Dialog alertDialog = new AlertDialog.Builder(ItemNextActivity.this). 
				                setTitle(R.string.ac4)
				                .setItems(listd3s, new DialogInterface.OnClickListener() { 
				  
				                    @Override 
				                    public void onClick(DialogInterface dialog, int which) { 
				                    //    Toast.makeText(Dialog_AlertDialogDemoActivity.this, arrayFruit[which], Toast.LENGTH_SHORT).show(); 
				                    	ilistd3=which;
				                    	mTvreg244.setText(listd3s[which]);
				                    	locationone =listd3.get(which).oid;
				                    	initData();
				                    } 
				                }). 
				                setNegativeButton(R.string.ac3, new DialogInterface.OnClickListener() { 
				 
				                    @Override 
				                    public void onClick(DialogInterface dialog, int which) { 
				                        // TODO Auto-generated method stub  
				                    } 
				                }). 
				                create(); 
				        alertDialog.show(); 
				}
     
   });
}
class Data3 {
	public String oid;
	public String oname;
}
private Handler handler =new Handler();
	
}

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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class F3NextActivity extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.f3next);
		hListView = (HorizontalListView)this.findViewById(R.id.horizon_listview);
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getApplicationContext(), com.example.worldtrade.ChanPingXiangQingActivity.class));
			}
		});

		final int[] ids = {R.drawable.abc2, R.drawable.abc2,
				R.drawable.abc2, R.drawable.abc2,R.drawable.abc2, R.drawable.abc2,
				R.drawable.abc2, R.drawable.abc2
				};
		final  String[] titles ={"d","d","d","d","d","d","d","d"};
		hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),titles,ids);
		hListView.setAdapter(hListViewAdapter);
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
		

		
		
		

		
	}
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mIvright1:
				showWindow();
				break;
			case R.id.mTvrightdetail:
				startActivity(new Intent(getApplicationContext(), GongsijianjieActivity.class));
				break;
			case R.id.mTvrightdetailas:
				startActivity(new Intent(getApplicationContext(), ChanPinLieBiaoActivity.class));

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

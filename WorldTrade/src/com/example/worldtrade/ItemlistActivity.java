package com.example.worldtrade;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ItemlistActivity extends Activity {
    private int[] a ={R.string.c1,R.string.c2,R.string.c3,R.string.c4,R.string.c5,R.string.c6,R.string.c7,R.string.c8,R.string.c9};
	private GridView mGvm1;
	private Myadapter adapter;
	private ImageView mTvback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.itemlist);
		mTvback =(ImageView)this.findViewById(R.id.mTvback);
		mTvback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mGvm1 =(GridView)this.findViewById(R.id.mGvm1);
		mGvm1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
   startActivity(new Intent(getApplicationContext(), ItemNextActivity.class));
			}
		});
		adapter= new Myadapter();
		mGvm1.setAdapter(adapter);

		

		
	}
	class Holder{
		TextView mTvri12;
	}
	class  Myadapter extends   BaseAdapter{
		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			        
			
			Holder holder = null;
			if(convertView==null){
				convertView = LayoutInflater.from(ItemlistActivity.this)
						.inflate(R.layout.item_gridview_2, null);
				holder = new Holder();
				holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvg1);
				convertView.setTag(holder);
	
			}else{
				holder =(Holder)convertView.getTag();
			}
			holder.mTvri12.setText(a[position]);
			return convertView;

		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return a.length;
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

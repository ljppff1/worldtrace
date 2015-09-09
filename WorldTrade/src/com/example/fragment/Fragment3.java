package com.example.fragment;import java.util.ArrayList;import com.example.worldtrade.F3Next1Activity;import com.example.worldtrade.F3NextActivity;import com.example.worldtrade.ItemNextActivity;import com.example.worldtrade.R;import android.content.Intent;import android.os.Bundle;import android.support.annotation.Nullable;import android.support.v4.app.Fragment;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.AdapterView.OnItemClickListener;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.ListView;import android.widget.TextView;public class Fragment3 extends Fragment{	private View parentView;	private ListView mLvf11;   private ArrayList<String> mDataList =new ArrayList<String>();private Myadapter adapter;	@Override	@Nullable	public View onCreateView(LayoutInflater inflater,			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {		parentView = inflater.inflate(R.layout.fragment3, container, false);				mLvf11 =(ListView)parentView.findViewById(R.id.mLvf11);		initData();		adapter =new Myadapter();		mLvf11.setAdapter(adapter);		mLvf11.setOnItemClickListener(new OnItemClickListener() {			@Override			public void onItemClick(AdapterView<?> parent, View view,					int position, long id) {			startActivity(new Intent(getActivity(), F3Next1Activity.class));				}		});				return parentView;	}			private void initData() {      for(int i=0;i<28;i++){    	  mDataList.add(getResources().getString(R.string.ac12)+i);      }	}	class Holder{		TextView mTvri10,mTvri11,mTvri12;		ImageView imageView;	}	class  Myadapter extends   BaseAdapter{		@Override		public View getView(int position, View convertView, ViewGroup parent) {			        						Holder holder = null;			if(convertView==null){				convertView = LayoutInflater.from(getActivity())						.inflate(R.layout.item_listview_3, null);				holder = new Holder();				holder.mTvri10 =(TextView)convertView.findViewById(R.id.mRl112);				holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);				convertView.setTag(holder);			}else{				holder =(Holder)convertView.getTag();			}			return convertView;		}		@Override		public int getCount() {			// TODO Auto-generated method stub			return mDataList.size();		}		@Override		public Object getItem(int position) {			// TODO Auto-generated method stub			return null;		}		@Override		public long getItemId(int position) {			// TODO Auto-generated method stub			return 0;		}	}		}
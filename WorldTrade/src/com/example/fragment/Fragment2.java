package com.example.fragment;import com.example.worldtrade.ItemlistActivity;import com.example.worldtrade.R;import android.annotation.SuppressLint;import android.content.Intent;import android.os.Bundle;import android.support.annotation.Nullable;import android.support.v4.app.Fragment;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.BaseAdapter;import android.widget.GridView;import android.widget.ImageView;import android.widget.TextView;import android.widget.AdapterView.OnItemClickListener;public class Fragment2 extends Fragment{	private View parentView;	private GridView mGvm1;    private int[] a ={R.string.b1,R.string.b2,R.string.b3,R.string.b4,R.string.b5,R.string.b6,R.string.b7,R.string.b8,R.string.b9,R.string.b10};	private Myadapter adapter;	@Override	@Nullable	public View onCreateView(LayoutInflater inflater,			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {		parentView = inflater.inflate(R.layout.fragment2, container, false);		mGvm1 =(GridView)parentView.findViewById(R.id.mGvm1);		mGvm1.setOnItemClickListener(new OnItemClickListener() {			@Override			public void onItemClick(AdapterView<?> parent, View view,					int position, long id) {				startActivity(new Intent(getActivity(), ItemlistActivity.class));			}		});		adapter= new Myadapter();		mGvm1.setAdapter(adapter);										return parentView;	}	class Holder{		TextView mTvri12;		ImageView imageView;	}	class  Myadapter extends   BaseAdapter{		@SuppressLint("NewApi")		@Override		public View getView(int position, View convertView, ViewGroup parent) {			        						Holder holder = null;			if(convertView==null){				convertView = LayoutInflater.from(getActivity())						.inflate(R.layout.item_gridview_1, null);				holder = new Holder();				holder.mTvri12 =(TextView)convertView.findViewById(R.id.mTvg1);				convertView.setTag(holder);				}else{				holder =(Holder)convertView.getTag();			}			holder.mTvri12.setText(a[position]);			return convertView;		}		@Override		public int getCount() {			// TODO Auto-generated method stub			return a.length;		}		@Override		public Object getItem(int position) {			// TODO Auto-generated method stub			return null;		}		@Override		public long getItemId(int position) {			// TODO Auto-generated method stub			return 0;		}	}	}
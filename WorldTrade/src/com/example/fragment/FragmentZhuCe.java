package com.example.fragment;


import java.util.ArrayList;
import java.util.List;

import com.example.fragment.FragmentFJ1a;
import com.example.fragment.FragmentLogin;
import com.example.fragment.FragmentRegister;
import com.example.worldtrade.R;
import com.tianshicoffeeom.app.imgscroll.MyImgScroll;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentZhuCe extends Fragment{

	private ViewPager vp;
	private RadioGroup rg1;
	private RadioButton rb1;
	private RadioButton rb2;
	private ArrayList<Fragment> list;
	private FragmentLogin fa;
	private FragmentRegister fb;
	private TextView mTvhy1;
	private View parentView;
	private String CHINESE;
	@SuppressLint("ResourceAsColor")
	

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		SharedPreferences mySharedPreferences1= getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
			parentView = inflater.inflate(R.layout.fragmentzhuce, container, false);
		}else{
			parentView = inflater.inflate(R.layout.fragmentzhucee, container, false);
		}

		

		rg1 = (RadioGroup) parentView.findViewById(R.id.rg1);
		rb1 = (RadioButton) parentView.findViewById(R.id.rb1);
		rb2 = (RadioButton) parentView.findViewById(R.id.rb2);
		mTvhy1 =(TextView)parentView.findViewById(R.id.mTvhy1);

		vp=(ViewPager)parentView.findViewById(R.id.vp1z);
	    vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if (arg0==0) {
		             rb1.setChecked(true);
		     		if(CHINESE.equals("1")){
		     			  mTvhy1.setText(getResources().getString(R.string.za1));
		    		}else{
		    			  mTvhy1.setText("Member Login");
		    		}
					}
					if(arg0==1){
						rb2.setChecked(true);
				     		if(CHINESE.equals("1")){
					             mTvhy1.setText(getResources().getString(R.string.za1a));
				    		}else{
				    			  mTvhy1.setText("Member Register");
				    		}

					}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		list = new ArrayList<Fragment>();
		 fa=new FragmentLogin();
		 fb=new FragmentRegister();
		list.add(fa);
		list.add(fb);
        
		ZxzcAdapter zxzc = new ZxzcAdapter(getChildFragmentManager(), list);
		vp.setAdapter(zxzc);
		zxzc.notifyDataSetChanged();
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int cheakedId) {
				if (cheakedId == rb1.getId()) {
					vp.setCurrentItem(0);
				} else if (cheakedId == rb2.getId()) {
					vp.setCurrentItem(1);
				}
							
			}
		});
		rb1.setChecked(true);
		vp.setCurrentItem(0);
		
	
		return parentView;
	}
	    
	

	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {}
		}
	};

	

class ZxzcAdapter extends FragmentStatePagerAdapter {
		   
		
		List<Fragment> list;			
		public ZxzcAdapter(FragmentManager fm,List<Fragment> list) {
			super(fm);
			this.list=list;			
		}
		@SuppressLint("ResourceAsColor")
		@Override
		public Fragment getItem(int arg0) {		
		/*	if(arg0==0){
				return new FragmentLogin();
			}else {
				return new FragmentRegister();
			}*/
			
			return list.get(arg0);
			

			
		}
		@Override
		public int getCount() {
		
			return list.size();
		}

	}

private Handler handler =new Handler();
	
}

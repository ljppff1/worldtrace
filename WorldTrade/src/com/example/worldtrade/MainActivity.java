package com.example.worldtrade;


import java.util.ArrayList;

import com.example.fragment.*;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

	private Fragment1 f1;
	private Fragment2 f2;
	private Fragment3 f3;
	private Fragment4 f4;
	private Fragment5 f5;
	private RadioGroup group;
	private ArrayList<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViews();
		
		group = (RadioGroup) findViewById(R.id.main_tab_bar);
		group.setOnCheckedChangeListener(this);
		fragments = new ArrayList<Fragment>();
		fragments.add(f1);
		fragments.add(f2);
		fragments.add(f3);
		fragments.add(f4);
		fragments.add(f5);
		

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment fragment = null;
		fragment = fragments.get(0);
		transaction.replace(R.id.main_framelayout, fragment);
		transaction.commit();

		
	}

	private void initViews() {
		f1 =new Fragment1();
		f2 =new Fragment2();
		f3 =new Fragment3();
		f4 =new Fragment4();
		f5 =new Fragment5();
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btnButton = null;
		for (int i = 0; i < childCount; i++) {
			btnButton = (RadioButton) group.getChildAt(i);
			if (btnButton.isChecked()) {
				checkedIndex = i;
				break;
			}
		}


		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment fragment = null;
		switch (checkedIndex) {
		case 0:
			fragment = fragments.get(0);
			transaction.replace(R.id.main_framelayout, new Fragment1());
			transaction.commit();
			break;
		case 1:
			fragment = fragments.get(1);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			break;
		case 2:
			fragment = fragments.get(2);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();

			break;
		case 3:
			fragment = fragments.get(3);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			break;
		case 4:
			fragment = fragments.get(4);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			break;

		default:
			break;
		}

	}

	
}

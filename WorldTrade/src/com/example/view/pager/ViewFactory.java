package com.example.view.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.example.worldtrade.R;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * ImageView��������
 */
public class ViewFactory {

	/**
	 * ��ȡImageView��ͼ��ͬʱ������ʾurl
	 * 
	 * @param text
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}

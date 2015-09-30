package org.yanzi.ui;



import java.util.ArrayList;
import java.util.List;

import com.example.domain.Data1;
import com.example.utils.Content;
import com.example.worldtrade.F3NextActivity;
import com.example.worldtrade.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalListViewAdapter extends BaseAdapter{
	private int[] mIconIDs;
	private String[] mTitles;
	private Context mContext;
	private LayoutInflater mInflater;
	Bitmap iconBitmap;
	private int selectIndex = -1;
	   protected ImageLoader imageLoader = ImageLoader.getInstance();

	   private List<Data1> mlist =new ArrayList<Data1>();
	private DisplayImageOptions options;

	public HorizontalListViewAdapter(Context context,List<Data1> mlist ){
		this.mContext = context;
        this.mlist =mlist;
		mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
		
	}
	@Override
	public int getCount() {
		return mlist.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_listview_h8, null);
			holder.mTvri10 =(TextView)convertView.findViewById(R.id.mTvri10);
			holder.imageView =(ImageView)convertView.findViewById(R.id.iv_listview_rent_pic);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		if(position == selectIndex){
			convertView.setSelected(true);
		}else{
			convertView.setSelected(false);
		}
		
		
		
		
		
		
		holder.mTvri10.setText(mlist.get(position).oname);
		initImageLoaderOptions();
		imageLoader.displayImage(Content.ImageUrl+mlist.get(position).pic,
				holder.imageView, options);
		

		
		
	//	holder.mTitle.setText(mTitles[position]);
	//	iconBitmap = getPropThumnail(mIconIDs[position]);
	//	holder.mImage.setImageBitmap(iconBitmap);

		return convertView;
	}
	private void initImageLoaderOptions() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.a)
				.cacheInMemory()
				.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	private static class ViewHolder {
		private TextView mTvri10 ;
		private ImageView imageView;
	}
	private Bitmap getPropThumnail(int id){
		Drawable d = mContext.getResources().getDrawable(id);
		Bitmap b = BitmapUtil.drawableToBitmap(d);
//		Bitmap bb = BitmapUtil.getRoundedCornerBitmap(b, 100);
		int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);
		int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);
		
		Bitmap thumBitmap = ThumbnailUtils.extractThumbnail(b, w, h);
		
		return thumBitmap;
	}
	public void setSelectIndex(int i){
		selectIndex = i;
	}
}
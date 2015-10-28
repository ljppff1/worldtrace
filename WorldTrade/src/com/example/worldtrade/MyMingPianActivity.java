package com.example.worldtrade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.Content;
import com.example.utils.HttpFileUpTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MyMingPianActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private TextView mTvmmp1;
	private ProgressBar progressBar_sale;
	private String id;
	private ImageView mIvw1;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private String CHINESE;
	private LinearLayout mRlww1;
	private EditText mEt1;
	private EditText mEt2;
	private EditText mEt4;
	private EditText mEt5;
	private EditText mEt7;
	private String img;

	private Handler handler =new Handler(){

		public void handleMessage(android.os.Message msg) {
		JSONObject jsonObject;
		switch (msg.what) {
		case 2:
        dialoga.cancel();
			Log.e("MY", (String) msg.obj);
			 Toast.makeText(getApplicationContext(),(String)msg.obj, 0).show();
          break;
		case 1:
			String str = (String)msg.obj;
			Log.e("MY1", (String) msg.obj);

		    try {
				jsonObject = new JSONObject(str);
				String string_code = jsonObject.getString("code");
				//String msg1 = jsonObject.getString("msg");
				 int  num_code=Integer.valueOf(string_code);
				 if (num_code==1) {
					  String message = jsonObject.getString("message");
					Log.e("MY1","num_code==1");
					img=message;
					   dialoga.cancel();
				 }else{
						Toast.makeText(getApplicationContext(), "fail", 0).show();
						 dialoga.cancel();
				 }

		    }catch (Exception e) {
					Toast.makeText(getApplicationContext(), "fail", 0).show();
					 dialoga.cancel();
				}
		    
			 Looper.getMainLooper().loop();

	break;

		default:
			break;
		}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		CHINESE =mySharedPreferences1.getString("CHINESE","1");
		if(CHINESE.equals("1")){
		setContentView(R.layout.mymingpian);
		}else{
			setContentView(R.layout.mymingpianee);
		}
		mRlww1 =(LinearLayout)this.findViewById(R.id.mRlww1);
		mRlww1.setOnClickListener(listener);
		mTvmmp1 =(TextView)this.findViewById(R.id.mTvmmp1);
		mTvmmp1.setOnClickListener(listener);
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);
		SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		id=mySharedPreferences.getString("id",""); 
		
		mIvw1 =(ImageView)this.findViewById(R.id.mIvw1);
		mIvw1.setOnClickListener(listener);
		mEt1 =(EditText)this.findViewById(R.id.mEt1);
		mEt2 =(EditText)this.findViewById(R.id.mEt2);
		mEt4 =(EditText)this.findViewById(R.id.mEt4);
		mEt5 =(EditText)this.findViewById(R.id.mEt5);
		mEt7 =(EditText)this.findViewById(R.id.mEt7);
		
		
		initData();
    }
	
	private void initData() {
		downloadsearch("0");
	}
	public void downloadsearch(String area11){
		progressBar_sale.setVisibility(View.VISIBLE);
		 RequestParams params = new RequestParams();
	   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
	   nameValuePairs.add(new BasicNameValuePair("userid",id));	   
	   params.addBodyParameter(nameValuePairs);
	   HttpUtils http = new HttpUtils();
	   http.send(HttpRequest.HttpMethod.POST,
	  		 "http://pine.i3.com.hk/trade/json/business_card.php",
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
							 JSONObject data =jsonObject.getJSONObject("data");
							  img = data.getString("img");
							 String companyname = data.getString("companyname");
							 String name = data.getString("name");
							 String tell = data.getString("tell");
							 String emall = data.getString("emall");
							 String address = data.getString("address");
							 int  num_code=Integer.valueOf(string_code);
							 if (num_code==1) {
								 mEt1.setText(companyname);
								 mEt2.setText(name);
								 mEt4.setText(tell);
								 mEt5.setText(emall);
								 mEt7.setText(address);
									initImageLoaderOptions();
									imageLoader.displayImage(Content.ImageUrl+img,
											mIvw1, options);

									progressBar_sale.setVisibility(View.GONE);
							 }
							 else {
									 Toast.makeText(getApplicationContext(),msg, 0).show();
										progressBar_sale.setVisibility(View.GONE);
							}
						} catch (JSONException e) {
							progressBar_sale.setVisibility(View.GONE);
							 Toast.makeText(getApplicationContext(),  msg, 0).show();
						}
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
   private boolean flag =false;
	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mIvw1:
				if(flag){
					showCustomAlertDialog();
				}
				break;
			case R.id.mRlgs1:
				finish();
				break;
			case R.id.mTvmmp1:
				//startActivity(new Intent(getApplicationContext(), MingPianShouCangActivity.class));
				if(CHINESE.equals("1")){
					if (mTvmmp1.getText().toString().equals(getResources().getString(R.string.dd2))) {
						flag =true;
						mEt1.setFocusable(true);
						mEt1.setFocusableInTouchMode(true);
						mEt2.setFocusable(true);
						mEt2.setFocusableInTouchMode(true);
						mEt7.setFocusable(true);
						mEt7.setFocusableInTouchMode(true);
						mEt4.setFocusable(true);
						mEt4.setFocusableInTouchMode(true);
						mEt5.setFocusable(true);
						mEt5.setFocusableInTouchMode(true);
						mEt1.requestFocus(3);
						InputMethodManager inputManager =(InputMethodManager)mEt1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(mEt1, 0);
						mTvmmp1.setText(getResources().getString(R.string.dd3));
					} else {
						downloadsearch1("");
					}
				}else{
					if (mTvmmp1.getText().toString().equals("Edit")) {
						flag =true;
						mEt1.setFocusable(true);
						mEt1.setFocusableInTouchMode(true);
						mEt2.setFocusable(true);
						mEt2.setFocusableInTouchMode(true);
						mEt7.setFocusable(true);
						mEt7.setFocusableInTouchMode(true);
						mEt4.setFocusable(true);
						mEt4.setFocusableInTouchMode(true);
						mEt5.setFocusable(true);
						mEt5.setFocusableInTouchMode(true);
						mEt1.requestFocus(3);
						InputMethodManager inputManager =(InputMethodManager)mEt1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(mEt1, 0);
						mTvmmp1.setText("Finish");
					} else {
						downloadsearch1("");
					}
				}
				break;
			case R.id.mRlww1:
				startActivity(new Intent(getApplicationContext(), MingPianShouCangActivity.class));
				break;

			default:
				break;
			}
		}
	};
	
	

	private AlertDialog alertDialog;
	
	private void showCustomAlertDialog() {
		 alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.show();
		Window win = alertDialog.getWindow();

		WindowManager.LayoutParams lp = win.getAttributes();
		win.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		//lp.alpha = 0.7f;
		win.setAttributes(lp);
		win.setContentView(R.layout.dialog);

		Button cancelBtn = (Button) win.findViewById(R.id.camera_cancel);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.cancel();
			}
		});
		Button camera_phone = (Button) win.findViewById(R.id.camera_phone);
		camera_phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				systemPhoto();
			}

		});
		Button camera_camera = (Button) win.findViewById(R.id.camera_camera);
		camera_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraPhoto();
			}

		});

	}	private final int SYS_INTENT_REQUEST = 0XFF01;
	private final int CAMERA_INTENT_REQUEST = 0XFF02;
	private Bitmap bitmap;

	/**
	 * 打开系统相册
	 */
	private void systemPhoto() {

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SYS_INTENT_REQUEST);

	}

	/**
	 * 调用相机拍照
	 */
	private void cameraPhoto() {
		String sdStatus = Environment.getExternalStorageState();
		/* 检测sd是否可用 */
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			//Toast.makeText(this, "SD卡不可用！", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, CAMERA_INTENT_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SYS_INTENT_REQUEST && resultCode == RESULT_OK
				&& data != null) {
			try {
				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null,
						null, null);
				cursor.moveToFirst();

				String imageFilePath = cursor.getString(1);
				System.out.println("File path is----->" + imageFilePath);

				FileInputStream fis = new FileInputStream(imageFilePath);
				bitmap = BitmapFactory.decodeStream(fis);
				file=new File(imageFilePath);

				int width = bitmap.getWidth();
				int height = bitmap.getHeight();

				/* 压缩获取的图像 */
				showImgs(bitmap, false);

				fis.close();
				cursor.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (requestCode == CAMERA_INTENT_REQUEST
				&& resultCode == RESULT_OK && data != null) {
			cameraCamera(data);
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * @param bitmap
	 * @return 压缩后的bitmap
	 */
	private Bitmap compressionBigBitmap(Bitmap bitmap, boolean isSysUp) {
		Bitmap destBitmap = null;
		/* 图片宽度调整为100，大于这个比例的，按一定比例缩放到宽度为100 */
		if (bitmap.getWidth() > 80) {
			float scaleValue = (float) (80f / bitmap.getWidth());
			System.out.println("缩放比例---->" + scaleValue);

			Matrix matrix = new Matrix();
			/* 针对系统拍照，旋转90° */
		//	if (isSysUp)
		//		matrix.setRotate(90);
			matrix.postScale(scaleValue, scaleValue);

			destBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
			int widthTemp = destBitmap.getWidth();
			int heightTemp = destBitmap.getHeight();
			Log.i("zhiwei.zhao", "压缩后的宽高----> width: " + heightTemp
					+ " height:" + widthTemp);
		} else {
			return bitmap;
		}
		return destBitmap;

	}

	/**
	 * @param data
	 *            拍照后获取照片
	 */
	private File file;
	private com.example.utils.LoadingDialog dialoga;
	private void cameraCamera(Intent data) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name = formatter.format(System.currentTimeMillis()) + ".jpg";
		Log.i("zhiwei.zhao", "image name:" + name);
		Bundle bundle = data.getExtras();
		/* 获取相机返回的数据，并转换为Bitmap图片格式 */
		Bitmap bitmap = (Bitmap) bundle.get("data");
		FileOutputStream b = null;

		String path = Environment.getExternalStorageDirectory().getPath();
		File file1 = new File(path + "/myImage/");
		/** 检测文件夹是否存在，不存在则创建文件夹 **/
		if (!file1.exists() && !file1.isDirectory())
			file1.mkdirs();
		String fileName = file1.getPath() + "/" + name;
		file=new File(fileName);
		Log.i("zhiwei.zhao", "camera file path:" + fileName);
		try {
			b = new FileOutputStream(fileName);
			/* 把数据写入文件 */
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (b == null)
					return;
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		showImgs(bitmap, true);
	}
	/**
	 * 展示选择的图片
	 * 
	 * @param bitmap
	 * @param isSysUp
	 */
	private void showImgs(Bitmap bitmap, boolean isSysUp) {
		if(alertDialog.isShowing()){
			alertDialog.cancel();
		}
		mIvw1.setImageBitmap(bitmap);
		sendFile();
	}


	private void sendFile(){
		dialoga = new com.example.utils.LoadingDialog(MyMingPianActivity.this, getString(R.string.abc43aa5));
		dialoga.show();

			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					Map<String, File> files=new HashMap<String, File>();
					files.put(file.getName(), file);
					 Map<String, String> params =new HashMap<String, String>();
					try {
						Looper.prepare(); 
						HttpFileUpTool.Upload(file.getPath(),handler);
					} catch (IOException e) {
						Message msg =new Message();
						msg.what=2;
						msg.obj=e.getMessage().toString();
					    handler.sendMessage(msg);

						e.printStackTrace();
					}}
			}).start();

	}
	
	
	
	public void downloadsearch1(String area11){
		progressBar_sale.setVisibility(View.VISIBLE);
		 RequestParams params = new RequestParams();
	   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
	   nameValuePairs.add(new BasicNameValuePair("userid",id));	   
	   nameValuePairs.add(new BasicNameValuePair("companyname",mEt1.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("name",mEt2.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("tell",mEt4.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("emall",mEt5.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("address",mEt7.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("img",img));	   
	   
	   params.addBodyParameter(nameValuePairs);
	   HttpUtils http = new HttpUtils();
	   http.send(HttpRequest.HttpMethod.POST,
	  		 "http://pine.i3.com.hk/trade/json/businessedit.php",
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
								 Toast.makeText(getApplicationContext(),msg, 0).show();
									if(CHINESE.equals("1")){
										mTvmmp1.setText(getResources().getString(R.string.dd2));
									}else{
										mTvmmp1.setText("Edit");
									}
									flag =false;
									mEt1.setFocusableInTouchMode(false);
									mEt1.setFocusable(false);
									mEt2.setFocusable(false);
									mEt2.setFocusableInTouchMode(false);
									mEt4.setFocusable(false);
									mEt4.setFocusableInTouchMode(false);
									mEt5.setFocusable(false);
									mEt5.setFocusableInTouchMode(false);
									mEt7.setFocusable(false);
									mEt7.setFocusableInTouchMode(false);
									 View view = getWindow().peekDecorView();
								        if (view != null) {
								            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
								        }
									progressBar_sale.setVisibility(View.GONE);
							 }
							 else {
									 Toast.makeText(getApplicationContext(),msg, 0).show();
										progressBar_sale.setVisibility(View.GONE);
							}
						} catch (JSONException e) {
							progressBar_sale.setVisibility(View.GONE);
							 Toast.makeText(getApplicationContext(),  msg, 0).show();
						}
					}
	   });
	}
}
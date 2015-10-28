package com.example.worldtrade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.Content;
import com.example.utils.HttpFileUpTool;
import com.example.utils.ImageTools;
import com.example.utils.LoadingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GongsijianjieActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private String CHINESE;
	private RelativeLayout mLoginOut;
	private String id;
	private ImageView mIv12;
	private TextView Tv1;
	private TextView Tv2;
	private TextView Tv3;
	private TextView Tv4;
	private TextView Tv5;
	private TextView Tv6;
	private TextView Tv7;
	private TextView Tv8;
	private TextView Tv9;
	private ProgressBar progressBar_sale;
	private EditText mEt1;
	private EditText mEt2;
	private EditText mEt3;
	private EditText mEt4;
	private EditText mEt5;
	private EditText mEt6;
	private EditText mEt7;
	private ImageView mIvw1;
	private TextView mTvmmp1;
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
		setContentView(R.layout.gongsijianjiem1);
		}else{
			setContentView(R.layout.gongsijianjiem1e);
		}
		mLoginOut =(RelativeLayout)this.findViewById(R.id.mLoginOut);
		mLoginOut.setOnClickListener(listener);
		mLoginOut.setVisibility(View.GONE);
		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.VISIBLE);

		
		SharedPreferences mySharedPreferences=getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		id=	mySharedPreferences.getString("id",""); 

		
		mEt1 =(EditText)this.findViewById(R.id.mEt1);
		mEt2 =(EditText)this.findViewById(R.id.mEt2);
		mEt3 =(EditText)this.findViewById(R.id.mEt3);
		mEt4 =(EditText)this.findViewById(R.id.mEt4);
		mEt5 =(EditText)this.findViewById(R.id.mEt5);
		mEt6 =(EditText)this.findViewById(R.id.mEt6);
		mEt7 =(EditText)this.findViewById(R.id.mEt7);
		mIvw1=(ImageView)this.findViewById(R.id.mIvw1);
		mIvw1.setOnClickListener(listener);
		
		Tv2=(TextView)this.findViewById(R.id.Tv2);
		Tv3=(TextView)this.findViewById(R.id.Tv3);
		Tv4=(TextView)this.findViewById(R.id.Tv4);
		mTvmmp1 =(TextView)this.findViewById(R.id.mTvmmp1);
		mTvmmp1.setOnClickListener(listener);

		initData();

		
}
	private String img;

private void initData() {
	downloadsearch("0");
}
public void downloadsearch(String area11){
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("id", id));
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/companyshow.php",
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
							 JSONObject array = jsonObject.getJSONObject("data");
					          String conpany = array.getString("conpany");
					          String introduction = array.getString("introduction");
					          String username = array.getString("username");
					          String tell = array.getString("tell");
					          String emall = array.getString("emall");
					          
					          String web = array.getString("web");
					          String number = array.getString("number");
					           img = array.getString("img");
					          String group = array.getString("group");
					          String locationone = array.getString("locationone");
					          String categoryone = array.getString("categoryone");
					          String categorytwe = array.getString("categorytwe");
					          String address = array.getString("address");
							 
					          Tv2.setText(locationone);
					          Tv4.setText(group);
					          Tv3.setText(categoryone+">"+categorytwe);
					          mEt1.setText(conpany);
					          mEt2.setText(username);
					          mEt3.setText(tell);
					          mEt4.setText(emall);
					          mEt5.setText(address);
					          mEt6.setText(web);
					          mEt7.setText(introduction);
					      
							initImageLoaderOptions();
							imageLoader.displayImage(Content.ImageUrl+img,
									mIvw1, options);

								progressBar_sale.setVisibility(View.GONE);

						}
						 else {
								progressBar_sale.setVisibility(View.GONE);
								 Toast.makeText(getApplicationContext(), msg, 0).show();

						}
					} catch (JSONException e) {
								progressBar_sale.setVisibility(View.GONE);
								 Toast.makeText(getApplicationContext(), msg, 0).show();
						e.printStackTrace();
					}
				}     
   });
}

protected ImageLoader imageLoader = ImageLoader.getInstance();
private DisplayImageOptions options;
private void initImageLoaderOptions() {
options = new DisplayImageOptions.Builder()
.showImageForEmptyUri(R.drawable.a)
.cacheInMemory()
.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
.bitmapConfig(Bitmap.Config.RGB_565).build();
}   private boolean flag =false;

	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mIvw1:
				if(flag){
					showCustomAlertDialog();
				}
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
						mEt3.setFocusable(true);
						mEt3.setFocusableInTouchMode(true);
						mEt7.setFocusable(true);
						mEt7.setFocusableInTouchMode(true);
						mEt6.setFocusable(true);
						mEt6.setFocusableInTouchMode(true);
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
						mEt3.setFocusable(true);
						mEt3.setFocusableInTouchMode(true);
						mEt6.setFocusable(true);
						mEt6.setFocusableInTouchMode(true);
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

			case R.id.mLoginOut:
				SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
				SharedPreferences.Editor editor = mySharedPreferences.edit(); 
				editor.putString("number", ""); 
				editor.putString("name",""); 
				editor.putString("username",""); 
				editor.putString("wechatNo",""); 
				editor.putString("id",""); 

				editor.commit(); 
				startActivity(new Intent(getApplicationContext(), MainActivityl3.class));
                

				break;
			case R.id.mRlgs1:
				finish();
				break;

			default:
				break;
			}
		}
	};

	
	private AlertDialog alertDialog;
	
	private void showCustomAlertDialog() {
		

        AlertDialog.Builder builder = new AlertDialog.Builder(GongsijianjieActivity.this);
        builder.setTitle("加入照片");
        //    指定下拉列表的显示数据
        final String[] cities = {"拍摄照片", "选择照片"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  

                if(which==0){
                	cameraPhoto();
                }else{
                	systemPhoto();
                }
            }
        });
        alertDialog = builder.create();
		alertDialog.show();		
		

	}
    private final int SYS_INTENT_REQUEST = 0XFF01;
	private final int CAMERA_INTENT_REQUEST = 0XFF02;
	private Bitmap bitmap;
	private static final int CROP_PICTURE = 3;

	/**
	 * 打开系统相册
	 */
	private void systemPhoto() {

		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
		openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(openAlbumIntent, 2);
	}

	/**
	 * 调用相机拍照
	 */
	private void cameraPhoto() {
		String sdStatus = Environment.getExternalStorageState();
		
		Uri imageUri = null;
		String fileName = null;
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//删除上一次截图的临时文件
			SharedPreferences sharedPreferences =getSharedPreferences("temp",Context.MODE_WORLD_WRITEABLE);
			ImageTools.deletePhotoAtPathAndName(Environment.getExternalStorageDirectory().getAbsolutePath(), sharedPreferences.getString("tempName", ""));
			//保存本次截图临时文件名字
			fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
			Editor editor = sharedPreferences.edit();
			editor.putString("tempName", fileName);
			editor.commit();
		imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
		//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent,2);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2 ) {
			Uri uri = null;
			if (data != null) {
				uri = data.getData();
				System.out.println("Data");
			}else {
				System.out.println("File");
				String fileName = getSharedPreferences("temp",Context.MODE_WORLD_WRITEABLE).getString("tempName", "");
				uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
			}
			cropImage(uri, 500, 500, CROP_PICTURE);

	}  else if (requestCode == CROP_PICTURE) {
		Bitmap photo = null;
		Uri photoUri = data.getData();
		if (photoUri != null) {
			photo = BitmapFactory.decodeFile(photoUri.getPath());
		}
		if (photo == null) {
			Bundle extra = data.getExtras();
			if (extra != null) {
                photo = (Bitmap)extra.get("data");  
                ByteArrayOutputStream stream = new ByteArrayOutputStream();  
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }  
		}
		showImgs(photo, false);
         
	}
	super.onActivityResult(requestCode, resultCode, data);
}
	private final int PHOTO_PICKED_WITH_DATA = 0XFF03;
	//截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, "image/*");  
        intent.putExtra("crop", "true");  
        intent.putExtra("aspectX", 4);  
        intent.putExtra("aspectY", 3);  
        intent.putExtra("outputX", 400);   
        intent.putExtra("outputY", 300); 
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);  
	    startActivityForResult(intent, requestCode);
	}

	/**
	 * @param data
	 *            拍照后获取照片
	 */
	private File file;

	private LoadingDialog dialoga;

	private File f;
	/**
	 * 展示选择的图片
	 * 
	 * @param bitmap
	 * @param isSysUp
	 */
	private void showImgs(Bitmap bitmap, boolean isSysUp) {
		mIvw1.setImageBitmap(bitmap);
		SaveBitmap(bitmap);

	}	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/WorldTrade/";

	
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
}

    public void SaveBitmap(Bitmap bmp) {  
  	  Date date = new Date();
  	  SimpleDateFormat format = new SimpleDateFormat("hh-mm-ss");
  	  String newDate = format.format(date);
  	  String path = "/sdcard/namecard";
/*    	  if (!destDir.exists()) {
  	   destDir.mkdirs();
  	  }
*/    	  makeRootDirectory(SDPATH);
  	   f = new File(SDPATH+"pic"+newDate+".png");
  	  try {
	    	   FileOutputStream out = new FileOutputStream(f);
	    	   bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	    	   out.flush();
	    	   out.close();
	    	 //  Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
	    	  	  sendFile();

  	  } catch (FileNotFoundException e) {
  	       e.printStackTrace();

  	    //   Toast.makeText(getApplicationContext(), "保存失败1", Toast.LENGTH_SHORT).show();
  	  } catch (IOException e) {

 	     //  Toast.makeText(getApplicationContext(), "保存失败2", Toast.LENGTH_SHORT).show();

  	       e.printStackTrace();
  	  }
  }


	private void sendFile(){
		dialoga = new com.example.utils.LoadingDialog(GongsijianjieActivity.this, getString(R.string.abc43aa5));
		dialoga.show();

			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					Map<String, File> files=new HashMap<String, File>();
					files.put(f.getName(), f);
					 Map<String, String> params =new HashMap<String, String>();
					try {
						Looper.prepare(); 
						HttpFileUpTool.Upload(f.getPath(),handler);
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
	   nameValuePairs.add(new BasicNameValuePair("id",id));	   
	   nameValuePairs.add(new BasicNameValuePair("title",mEt1.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("name",mEt2.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("tell",mEt3.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("emall",mEt4.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("web",mEt6.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("address",mEt5.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("intro",mEt7.getEditableText().toString()));	   
	   nameValuePairs.add(new BasicNameValuePair("img",img));	   
	   
	   params.addBodyParameter(nameValuePairs);
	   HttpUtils http = new HttpUtils();
	   http.send(HttpRequest.HttpMethod.POST,
	  		 "http://pine.i3.com.hk/trade/json/grxg.php",
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
									mEt3.setFocusableInTouchMode(false);
									mEt3.setFocusable(false);
									mEt6.setFocusableInTouchMode(false);
									mEt6.setFocusable(false);
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
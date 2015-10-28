package com.example.worldtrade;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.utils.HttpFileUpTool;
import com.example.utils.ImageTools;
import com.example.utils.LoadingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class ChanPinFabuActivity extends BaseActivity {

	private RelativeLayout mRlgs1;
	private EditText mTvwhat1c;
	private EditText mTvwhat1b;
	private EditText mTvwhat1a;
	private RelativeLayout mRlb1;
	private View progressBar_sale;
	private String id;
	private String CHINESE;
	private ImageView mIvww1;
	private String message;

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
					  message = jsonObject.getString("message");
					Log.e("MY1","num_code==1");
					initData();
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
		setContentView(R.layout.chanpinfabu);
		}else{
			setContentView(R.layout.chanpinfabue);
		}

		SharedPreferences mySharedPreferences=getSharedPreferences("USER", Activity.MODE_PRIVATE); 
		id=	mySharedPreferences.getString("id",""); 
		mTvwhat1c =(EditText)this.findViewById(R.id.mTvwhat1c);
		mTvwhat1c.setOnClickListener(listener);
		mTvwhat1b =(EditText)this.findViewById(R.id.mTvwhat1b);
		mTvwhat1b.setOnClickListener(listener);
		mTvwhat1a =(EditText)this.findViewById(R.id.mTvwhat1a);
		mTvwhat1a.setOnClickListener(listener);
		mRlb1 =(RelativeLayout)this.findViewById(R.id.mRlb1);
		mRlb1.setOnClickListener(listener);
		progressBar_sale =(ProgressBar)this.findViewById(R.id.progressBar_sale);
		progressBar_sale.setVisibility(View.GONE);
		mIvww1 =(ImageView)this.findViewById(R.id.mIvww1);
		mIvww1.setOnClickListener(listener);

		mRlgs1 =(RelativeLayout)this.findViewById(R.id.mRlgs1);
		mRlgs1.setOnClickListener(listener);
}
	OnClickListener listener =new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mIvww1:
				showCustomAlertDialog();
				break;

			case R.id.mRlb1:
				if(TextUtils.isEmpty(mTvwhat1a.getEditableText().toString())){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.abc42aa2), 0).show();
				}else if(TextUtils.isEmpty(mTvwhat1b.getEditableText().toString())){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.abc41aa1), 0).show();
				}else if(TextUtils.isEmpty(mTvwhat1c.getEditableText().toString())){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.abc43aa3), 0).show();
				}else if(f==null){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.abc43aa4), 0).show();
				}
				else{
					sendFile();
				}
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
		
        AlertDialog.Builder builder = new AlertDialog.Builder(ChanPinFabuActivity.this);
        builder.setTitle("加入照片");
        //    指定下拉列表的显示数据
        final String[] cities = {"拍摄照片", "选择照片"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(which==0){
                	cameraPhoto();
                }else{
                	systemPhoto();
                }
            }
        });
        builder.show();
		
		

	}	
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

	private LoadingDialog dialoga;

	private File f;
	
	/**
	 * 展示选择的图片
	 * 
	 * @param bitmap
	 * @param isSysUp
	 */
	private void showImgs(Bitmap bitmap, boolean isSysUp) {
		mIvww1.setImageBitmap(bitmap);
		SaveBitmap(bitmap);
	}



	
	public static String SDPATH = Environment.getExternalStorageDirectory()
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

  	  } catch (FileNotFoundException e) {
  	       e.printStackTrace();

  	    //   Toast.makeText(getApplicationContext(), "保存失败1", Toast.LENGTH_SHORT).show();
  	  } catch (IOException e) {

 	     //  Toast.makeText(getApplicationContext(), "保存失败2", Toast.LENGTH_SHORT).show();

  	       e.printStackTrace();
  	  }
  }


	private void sendFile(){
		dialoga = new com.example.utils.LoadingDialog(ChanPinFabuActivity.this, getString(R.string.abc43aa5));
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
	
	
private void initData() {
	downloadsearch("0");
}
public void downloadsearch(String area11){
	 RequestParams params = new RequestParams();
   List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(10);
   nameValuePairs.add(new BasicNameValuePair("title", mTvwhat1a.getEditableText().toString()));
   nameValuePairs.add(new BasicNameValuePair("introduction", mTvwhat1b.getEditableText().toString()));
   nameValuePairs.add(new BasicNameValuePair("content", mTvwhat1c.getEditableText().toString()));
   nameValuePairs.add(new BasicNameValuePair("userID", id));
   nameValuePairs.add(new BasicNameValuePair("img", message));
   
   params.addBodyParameter(nameValuePairs);
   HttpUtils http = new HttpUtils();
   http.send(HttpRequest.HttpMethod.POST,
  		 "http://pine.i3.com.hk/trade/json/addproduct.php",
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
						 dialoga.cancel();
							Log.e("reader.readLine()--", arg0.result);

						 int  num_code=Integer.valueOf(string_code);
						 if (num_code==1) {
							 Toast.makeText(getApplicationContext(),msg, 0).show();
								progressBar_sale.setVisibility(View.GONE);
						 }
						 else {
								 Toast.makeText(getApplicationContext(),msg, 0).show();
								progressBar_sale.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						 Toast.makeText(getApplicationContext(),msg, 0).show();
							progressBar_sale.setVisibility(View.GONE);
						e.printStackTrace();
					}
				}
   });
}


	
}
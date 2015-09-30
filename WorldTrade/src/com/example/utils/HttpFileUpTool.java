package com.example.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpFileUpTool {
	private static StringBuilder sb2;
	private static String str;

	/**
	 * ͨ��ƴ�ӵķ�ʽ�����������ݣ�ʵ�ֲ��������Լ��ļ�����
	 * 
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	
	public static void Upload( String path, Handler handler) throws IOException{
		
       String end = "\r\n";  
       String twoHyphens = "--";  
       String boundary = "******";  
		URL url = new URL("http://pine.i3.com.hk/trade/json/rss.php");
       HttpURLConnection httpURLConnection = (HttpURLConnection) url  
               .openConnection();  
       httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K  
       // �������������  
       httpURLConnection.setDoInput(true);  
       httpURLConnection.setDoOutput(true);  
       httpURLConnection.setUseCaches(false);  
       // ʹ��POST����  
       httpURLConnection.setRequestMethod("POST");  
       httpURLConnection.setRequestProperty("Connection", "Keep-Alive");  
       httpURLConnection.setRequestProperty("Charset", "UTF-8");  
       httpURLConnection.setRequestProperty("Content-Type",  
               "multipart/form-data;boundary=" + boundary);  
 
       DataOutputStream dos = new DataOutputStream(  
               httpURLConnection.getOutputStream());  
       dos.writeBytes(twoHyphens + boundary + end);  
       dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""  
               + path.substring(path.lastIndexOf("/") + 1) + "\"" + end);  
       dos.writeBytes(end);  
 
       FileInputStream fis = new FileInputStream(path);  
       byte[] buffer = new byte[8192]; // 8k  
       int count = 0;  
       // ��ȡ�ļ�  
       while ((count = fis.read(buffer)) != -1) {  
           dos.write(buffer, 0, count);  
       }  
       fis.close();  
       dos.writeBytes(end);  
       dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
       dos.flush();  
       InputStream is = httpURLConnection.getInputStream();  
       InputStreamReader isr = new InputStreamReader(is, "utf-8");  
       BufferedReader br = new BufferedReader(isr);  
       String result = br.readLine();  
       
		StringBuffer sb1 =new StringBuffer();
		while ((str = br.readLine()) != null) {
			Log.e("reader.readLine()--", str);
			sb1.append(br.readLine());
		}
       dos.close();  
       is.close();  
		Message msg =new Message();
		msg.what=1;
		msg.obj=sb1.toString();
		handler.dispatchMessage(msg);

		
	}

	public static void post(String actionUrl, Map<String, String> params,
			Map<String, File> files,Handler handler) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
  
		URL uri = new URL("http://pine.i3.com.hk/trade/json/rss.php");
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 10000); // ������ʱ��
		conn.setDoInput(true);// ��������
		conn.setDoOutput(true);// �������
		conn.setUseCaches(false); // ������ʹ�û���
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
/*
		// ������ƴ�ı����͵Ĳ���
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		outStream.write(sb.toString().getBytes());*/
		DataOutputStream outStream = new DataOutputStream(conn
				.getOutputStream());

		InputStream in = null;
		// �����ļ�����
		if (files != null){
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1
						.append("Content-Disposition: form-data; name=\"uploadfile\"; UPFILE=\""
								+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

		// ���������־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// �õ���Ӧ��
		int res = conn.getResponseCode();
		if (res == 200) {
			Log.e("MY",res+"");

			in = conn.getInputStream();
			int ch;
			 sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
			Log.e("MY",sb2.toString()+"");

		}
		outStream.close();
		conn.disconnect();
		Message msg =new Message();
		msg.what=1;
		msg.obj=sb2.toString();
		handler.dispatchMessage(msg);
		}
	}
}

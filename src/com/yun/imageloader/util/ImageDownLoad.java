package com.yun.imageloader.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片下载
 * 
 * @author yunye
 * 
 */
public class ImageDownLoad {
	public Bitmap downLoad(String url, int width, int height) {
		HttpGet httpRequest = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream is = httpEntity.getContent();
				Bitmap bitmap = null;
				if (is == null) {
					return null;
				} else {
					try {
						byte[] data = readStream(is);
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = true;
						BitmapFactory.decodeByteArray(data, 0, data.length,
								options);
						if (options.outWidth > width
								&& options.outHeight > height
								&& (width != 0 && height != 0)) {
							options.inSampleSize = options.outWidth / width >= options.outHeight
									/ height ? options.outWidth / width
									: options.outHeight / height;
						}
						options.inJustDecodeBounds = false;
						bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length, options);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				is.close();
				return bitmap;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 得到图片字节流 数组大小
	 */
	public byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}
}

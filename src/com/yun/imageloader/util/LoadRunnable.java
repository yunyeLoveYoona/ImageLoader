package com.yun.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * 图片获取线程
 * 
 * @author yunye
 * 
 */
public class LoadRunnable implements Runnable {
	private ImageView imageView;
	private String url;
	private int defaultBackround;
	private int width;
	private int height;
	private boolean isCache;
	private Handler handler;
	private Drawable errorImage;

	public LoadRunnable(ImageView imageView, String url, int defaultBackround,
			Drawable errorImage, int width, int height, boolean isCache,
			Handler handler) {
		this.imageView = imageView;
		this.url = url;
		this.defaultBackround = defaultBackround;
		this.width = width;
		this.height = height;
		this.isCache = isCache;
		this.handler = handler;
		this.errorImage = errorImage;
	}

	@Override
	public void run() {
		if (url.contains("http://")) {
			// 网络图片
			final Bitmap bitmap = new ImageDownLoad().downLoad(url, width,
					height);
			if (bitmap != null) {
				ImageCache.getInstance().put(url, bitmap);
				if (isCache) {
					Message message = handler.obtainMessage();
					message.obj = url;
					handler.sendMessage(message);
				}
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (bitmap == null && errorImage != null) {
						imageView.setImageDrawable(errorImage);
					} else {
						imageView.setImageBitmap(bitmap);
					}

				}
			});
		} else {
			// 本地图片
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(url, options);
			options.inSampleSize = width / options.outWidth >= height
					/ options.outHeight ? width / options.outWidth
					: options.outHeight;
			options.inJustDecodeBounds = false;
			final Bitmap bitmap = BitmapFactory.decodeFile(url, options);
			ImageCache.getInstance().put(url, bitmap);
			handler.post(new Runnable() {
				@Override
				public void run() {
					imageView.setImageBitmap(bitmap);
				}
			});
		}
	}
}

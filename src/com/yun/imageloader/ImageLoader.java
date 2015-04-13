package com.yun.imageloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.yun.imageloader.util.ImageCache;
import com.yun.imageloader.util.ImageDiskLruCache;
import com.yun.imageloader.util.LoadRunnable;

/**
 * 图片加载缓存 使用了内存缓存和磁盘缓存 可以加载网络图片，也可加载本地图片
 * 
 * @author yunye
 * 
 */
public class ImageLoader {
	private static ImageLoader imageLoader;
	private final int LOAD_COUNT = 5;// 同时加载图片的最大线程数
	private ExecutorService executorServie;
	private final int DEFAULT_WIDTH = 150;
	private final int DEFAULT_HEIGHT = 150;
	private ImageDiskLruCache diskLruCache;

	public static ImageLoader getInstance(Context context) {
		if (imageLoader == null) {
			imageLoader = new ImageLoader(context);
		}
		return imageLoader;
	}

	private ImageLoader(Context context) {
		executorServie = Executors.newFixedThreadPool(LOAD_COUNT);
		diskLruCache = new ImageDiskLruCache(context, "image",
				50 * 1024 * 1024, CompressFormat.JPEG, 100);
	}

	public void load(ImageView imageView, String url, int defaultBackround,
			int width, int height, boolean isCache, final Drawable errorImage) {
		if (ImageCache.getInstance().get(url) != null) {
			imageView.setImageBitmap(ImageCache.getInstance().get(url));
		} else if (diskLruCache.containsKey(url)) {
			imageView.setImageBitmap(diskLruCache.getBitmap(url));
		} else {
			if (defaultBackround != 0) {
				imageView.setImageResource(defaultBackround);
			}
			final LoadRunnable run = new LoadRunnable(imageView, url,
					defaultBackround, errorImage, width, height, isCache,
					handler);
			executorServie.execute(run);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ImageView imageView = (ImageView) v;
					if (imageView.getDrawable() == null
							|| imageView.getDrawable() == errorImage) {
						executorServie.execute(run);
					}
				}
			});
		}
	}

	public void load(ImageView imageView, String url, int defaultBackround,
			int width, int height, Drawable errorImage) {
		load(imageView, url, defaultBackround, width, height, false, errorImage);
	}

	public void load(ImageView imageView, String url, int width, int height,
			Drawable errorImage) {
		load(imageView, url, 0, width, height, false, errorImage);
	}

	public void load(ImageView imageView, String url, Drawable errorImage) {
		load(imageView, url, 0, imageView.getWidth() > 0 ? imageView.getWidth()
				: DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, false, errorImage);
	}

	public void load(ImageView imageView, String url, int defaultBackround,
			Drawable errorImage) {
		load(imageView,
				url,
				defaultBackround,
				imageView.getWidth() > 0 ? imageView.getWidth() : DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, false, errorImage);
	}

	public void load(ImageView imageView, String url, Drawable errorImage,
			boolean isCache) {
		load(imageView, url, 0, imageView.getWidth() > 0 ? imageView.getWidth()
				: DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, isCache, errorImage);
	}

	public void load(ImageView imageView, String url, int defaultBackround,
			boolean isCache) {
		load(imageView,
				url,
				defaultBackround,
				imageView.getWidth() > 0 ? imageView.getWidth() : DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, isCache, null);
	}

	public void load(ImageView imageView, String url, int defaultBackround,
			int width, int height) {
		load(imageView, url, defaultBackround, width, height, false, null);
	}

	public void load(ImageView imageView, String url, int width, int height) {
		load(imageView, url, 0, width, height, false, null);
	}

	public void load(ImageView imageView, String url) {
		load(imageView, url, 0, imageView.getWidth() > 0 ? imageView.getWidth()
				: DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, false, null);
	}

	public void load(ImageView imageView, String url, int defaultBackround) {
		load(imageView,
				url,
				defaultBackround,
				imageView.getWidth() > 0 ? imageView.getWidth() : DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, false, null);
	}

	public void load(ImageView imageView, String url, boolean isCache) {
		load(imageView, url, 0, imageView.getWidth() > 0 ? imageView.getWidth()
				: DEFAULT_WIDTH,
				imageView.getHeight() > 0 ? imageView.getHeight()
						: DEFAULT_HEIGHT, isCache, null);
	}

	private Handler handler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				if (!diskLruCache.containsKey(msg.obj.toString())) {
					diskLruCache.putBitmap(msg.obj.toString(), ImageCache
							.getInstance().get(msg.obj.toString()));
				}
			}
			super.handleMessage(msg);
		}
	};
	public void stopAllLoad(){
		executorServie.shutdownNow();
	}
	public void clearDiskLruCache(){
		diskLruCache.clearCache();
	}

}

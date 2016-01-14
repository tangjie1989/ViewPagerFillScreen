package com.tj.viewpagerfillscreen.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

public class ViewPagerFillScreenApplication extends Application {

	LocalStorageUtil photoPickerLocalStorageUtil;

	@Override
	public void onCreate() {
		super.onCreate();

		photoPickerLocalStorageUtil = new LocalStorageUtil();
		photoPickerLocalStorageUtil.initLocalDir(this);

		initImageLoader();
	}

	
	public void initImageLoader() {

		File cacheDir = new File(photoPickerLocalStorageUtil.getImageCacheAbsoluteDir());

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(new UnlimitedDiskCache(cacheDir))
//				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

}

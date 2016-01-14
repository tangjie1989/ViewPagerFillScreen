package com.tj.viewpagerfillscreen.application;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class LocalStorageUtil {

	// SD卡文件根目录
	private static String BASE_DIR = "ViewPagerFillScreen";
	
	// 缓存目录
	private static final String IMAGE_CACHE_DIR = "caches";

	// sd卡根目录
	private String sdcardCacheBaseAbsolutePath;

	private String imageCacheAbsoluteDir;// 图片缓存目录

	public String getSdcardCacheBaseAbsolutePath() {
		return sdcardCacheBaseAbsolutePath;
	}

	public void setSdcardCacheBaseAbsolutePath(String sdcardCacheBaseAbsolutePath) {
		this.sdcardCacheBaseAbsolutePath = sdcardCacheBaseAbsolutePath;
	}

	public String getImageCacheAbsoluteDir() {
		return imageCacheAbsoluteDir;
	}

	public void setImageCacheAbsoluteDir(String imageCacheAbsoluteDir) {
		this.imageCacheAbsoluteDir = imageCacheAbsoluteDir;
	}

	public void initLocalDir(Context context) {

		long availableSDCardSpace = getExternalStorageSpace();// 获取SD卡可用空间
		
		String sdcardBasePath;
		
		if (availableSDCardSpace != -1L) {// 如果存在SD卡
			sdcardBasePath = Environment.getExternalStorageDirectory() + File.separator + BASE_DIR;
		} else if (getInternalStorageSpace() != -1L) {
			sdcardBasePath = context.getFilesDir().getPath() + File.separator + BASE_DIR;
		} else {// sd卡不存在
			// 没有可写入位置
			return;
		}
		
		setSdcardCacheBaseAbsolutePath(sdcardBasePath);
		
		// 图片缓存目录
		setImageCacheAbsoluteDir(getSdcardCacheBaseAbsolutePath() + File.separator + IMAGE_CACHE_DIR);

		// 初始化根目录
		File basePath = new File(getSdcardCacheBaseAbsolutePath());
		if (!basePath.exists()) {
			basePath.mkdir();
		}

	}

	/**
	 * 获取SD卡可用空间
	 *
	 * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
	 */
	public static long getExternalStorageSpace() {
		long availableSDCardSpace = -1L;
		// 存在SD卡
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			long blockSize = sf.getBlockSize();// 块大小,单位byte
			long availCount = sf.getAvailableBlocks();// 可用块数量
			availableSDCardSpace = availCount * blockSize / 1024 / 1024;// 可用SD卡空间，单位MB
		}

		return availableSDCardSpace;
	}

	/**
	 * 获取机器内部可用空间
	 *
	 * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
	 */
	public static long getInternalStorageSpace() {
		long availableInternalSpace = -1L;

		StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
		long blockSize = sf.getBlockSize();// 块大小,单位byte
		long availCount = sf.getAvailableBlocks();// 可用块数量
		availableInternalSpace = availCount * blockSize / 1024 / 1024;// 可用SD卡空间，单位MB

		return availableInternalSpace;
	}
	
}

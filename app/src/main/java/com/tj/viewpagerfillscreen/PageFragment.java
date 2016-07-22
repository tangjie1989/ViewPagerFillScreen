package com.tj.viewpagerfillscreen;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.tj.viewpagerfillscreen.widget.ProgressWheel;

@SuppressLint("ValidFragment")
public class PageFragment extends Fragment {

    private ImageView mUserBigPhotoImg;
    private ProgressWheel mProgressWheel;

    private static final String PIC_URL = "pic_url";
    private String mPicUrl;

    private PageFragment() {}

    public static PageFragment newInstance(String picUrl){
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicUrl = getArguments().getString(PIC_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout viewContainer = (RelativeLayout) inflater.inflate(R.layout.activity_view_page_multi_view_item, null);
        mUserBigPhotoImg = (ImageView) viewContainer.findViewById(R.id.user_big_photo_img);

        mProgressWheel = (ProgressWheel) viewContainer.findViewById(R.id.img_load_progress);
        return viewContainer;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageLoader.getInstance().displayImage(mPicUrl, mUserBigPhotoImg,
                generateDisplayImageOptionsNoCatchDisk(),
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        mProgressWheel.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                }, new ImageLoadingProgressListener() {

                    @Override
                    public void onProgressUpdate(String imageUri, View view,
                                                 int current, int total) {

                        int preProgress = 90;

                        int picLoadPro = (int) (current * 270.0 / total);

                        mProgressWheel.setProgress(picLoadPro + preProgress);
                        mProgressWheel.setText((int) ((picLoadPro + preProgress) * 100.0 / 360.0) + "%");

                    }
                });
    }

    private static DisplayImageOptions generateDisplayImageOptionsNoCatchDisk() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

}

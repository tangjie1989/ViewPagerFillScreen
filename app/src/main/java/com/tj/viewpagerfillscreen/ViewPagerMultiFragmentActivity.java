package com.tj.viewpagerfillscreen;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewPagerMultiFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager_multi_view_layout);
		
		pagerContainer = (LinearLayout)findViewById(R.id.pager_layout);
		
		initPhotoListViewPager();
		
	}
	
	private int currViewPagerIndex = 0;
	
	private void initPhotoListViewPager() {

		photoListPagerAdapter = new PhotoListPagerAdapter(getSupportFragmentManager());
		
		photoListViewPager = (ViewPager) findViewById(R.id.view_pager);
		photoListViewPager.setAdapter(photoListPagerAdapter);
		
		photoListViewPager.setCurrentItem(0);
		photoListViewPager.setOffscreenPageLimit(3);
		photoListViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
		
		photoListViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// to refresh frameLayout
	            if (pagerContainer != null) {
	            	pagerContainer.invalidate();
	            }
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				int index = photoListViewPager.getCurrentItem();
				if(arg0 == 0 && currViewPagerIndex != index){ 
					currViewPagerIndex = index;
				}
			}
		});
		
		pagerContainer.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return photoListViewPager.dispatchTouchEvent(event);
            }
        });

	}

	private LinearLayout pagerContainer;
	private ViewPager photoListViewPager;
	private PhotoListPagerAdapter photoListPagerAdapter;
	
	private class PhotoListPagerAdapter extends FragmentPagerAdapter {
		
		public PhotoListPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			return new PageFragment(IMAGES[position]);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			FragmentManager manager = ((Fragment)object).getFragmentManager();
			if(manager != null){
				FragmentTransaction trans = manager.beginTransaction();
			    trans.remove((Fragment)object);
			    trans.commit();
			}
		}

		@Override
		public int getCount() {
			return IMAGES.length;
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

	}
	
	public static final String[] IMAGES = new String[] {
		"http://imgt8.bdstatic.com/it/u=1,2294437144&fm=19&gp=0.jpg",
		"http://imgt7.bdstatic.com/it/u=2,972689175&fm=19&gp=0.jpg",
		"http://imgt9.bdstatic.com/it/u=2,972596945&fm=19&gp=0.jpg",
		"http://imgt9.bdstatic.com/it/u=2,967767831&fm=19&gp=0.jpg",
		"http://imgt6.bdstatic.com/it/u=2,967788223&fm=19&gp=0.jpg",
		"http://imgt6.bdstatic.com/it/u=2,972593351&fm=19&gp=0.jpg",
		"http://imgt6.bdstatic.com/it/u=2,967790111&fm=19&gp=0.jpg"
	};
}

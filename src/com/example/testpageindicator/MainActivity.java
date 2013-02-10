package com.example.testpageindicator;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements View.OnTouchListener{
	
	private ViewFlipper flipper;
	private int currentViewNr_=0;
	private GestureDetector gestureDetector;
	private CirclePageIndicator mIndicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		gestureDetector = new GestureDetector(new MyGestureDetector());
		//this.setGestureDetector(gestureDetector);

		OnTouchListener touchListener = new OnTouchListener(){
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		findViewById(R.id.scrollview1).setOnTouchListener(touchListener);
		findViewById(R.id.scrollview2).setOnTouchListener(touchListener);
		findViewById(R.id.scrollview3).setOnTouchListener(touchListener);
		
		mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewFlipper(flipper,0);
        mIndicator.setFillColor(getResources().getColor(android.R.color.darker_gray));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private class MyGestureDetector extends SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 100;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {

				/*if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;*/
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && currentViewNr_<3) {
					//flipper.setInAnimation(AnimationUtils.loadAnimation(activity_, R.anim.slide_in));
					//flipper.setOutAnimation(AnimationUtils.loadAnimation(activity_, R.anim.slide_out));
					currentViewNr_++;
					
					flipper.showNext();
					mIndicator.setCurrentItem(currentViewNr_);
					return true;
				}else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && currentViewNr_>0) {
					currentViewNr_--;
					flipper.showPrevious();
					mIndicator.setCurrentItem(currentViewNr_);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public boolean 	onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
	        return false;
		}

		@Override
		public void onShowPress(MotionEvent e){
			super.onShowPress(e);
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return false;
	}


}

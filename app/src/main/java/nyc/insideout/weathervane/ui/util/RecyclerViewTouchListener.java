package nyc.insideout.weathervane.ui.util;


import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

    GestureDetector mGestureDetector;
    RecyclerView mRecyclerView;

    public RecyclerViewTouchListener(GestureDetector gestureDetector){
        mGestureDetector = gestureDetector;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e){
        mRecyclerView = rv;
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowedIntercept){

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e){

    }
}

package com.adrian.tecmilenio.touchevents;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.TextView.*;

public class MainActivity extends Activity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private ConstraintLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        this.view = (ConstraintLayout) findViewById(R.id.home_view);
        Intent myIntent = new Intent(this, SensorService.class);
        startService(myIntent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        TextView textView = findViewById(R.id.hello_world);
        textView.setText("Touch coordinates : " +
                String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()) + "y");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.view.setBackgroundColor(Color.YELLOW);
        TextView textView = findViewById(R.id.textView);
        textView.setTextSize(24);
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        this.view.setBackgroundColor(Color.BLUE);
        TextView textView = findViewById(R.id.textView);
        textView.setTextSize(36);
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        this.view.setBackgroundColor(Color.RED);
        TextView textView = findViewById(R.id.textView);
        textView.setTextSize(40);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        this.view.setBackgroundColor(Color.CYAN);
        TextView textView = findViewById(R.id.textView);
        textView.setTextSize(48);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.view.setBackgroundColor(Color.GREEN);
        TextView textView = findViewById(R.id.textView);
        textView.setTextSize(56);
        return false;
    }
}

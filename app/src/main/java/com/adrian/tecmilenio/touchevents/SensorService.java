package com.adrian.tecmilenio.touchevents;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.hardware.SensorEventListener;

public class SensorService extends Service implements SensorEventListener{

    float xAccel;
    float yAccel;
    float zAccel;
    float xPreviousAccel;
    float yPreviousAccel;
    float zPreviousAccel;

    boolean firstUpdate = true;
    boolean shakeInitiated = false;
    float shakeThreshlod = 12.5f;

    Sensor accelerometer;
    SensorManager sm;


    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateAccelParameters(event.values[0],event.values[1],event.values[3]);
        if((!shakeInitiated) && isAccelerationChanged()){
            shakeInitiated = true;
        }
        else if ((shakeInitiated) && isAccelerationChanged()) {
            executeShakeAction();
        }
        else if ( (shakeInitiated) && !isAccelerationChanged()) {
            shakeInitiated = false;
        }

    }

    private void executeShakeAction() {
        Intent ii = new Intent(this,SecondActivity.class);
        startActivity(ii);
    }

    private boolean isAccelerationChanged() {
        float deltaX = Math.abs(xPreviousAccel - xAccel);
        float deltaY = Math.abs(yPreviousAccel - yAccel);
        float deltaZ = Math.abs(zPreviousAccel - zAccel);

        return (deltaX > shakeThreshlod && deltaY > shakeThreshlod)
                || (deltaX > shakeThreshlod && deltaZ > shakeThreshlod)
                || (deltaY > shakeThreshlod && deltaZ > shakeThreshlod);
    }

    private void updateAccelParameters(float xNewAccel, float yNewAccel, float zNewAccel) {
        if (firstUpdate)
        {
            xPreviousAccel = xNewAccel;
            yPreviousAccel = yNewAccel;
            zPreviousAccel = zNewAccel;
            firstUpdate = false;
        } else {
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;
        }
        xAccel = xNewAccel;
        yAccel = yNewAccel;
        zAccel = zNewAccel;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

package com.wakemeup;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.os.PowerManager;
import android.util.Log;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    float mPreviousValue = -1;
    private PowerManager.WakeLock mFullWakeLock;

    final static float VALUE_DIFFERENCE_TO_ACTIVATE_DISPLAY = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "MainActivity started");

        // Initialize sensor
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Prepare wake lock
        initWakeLock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume called");
    }

    /**
     * @brief Initializes wake lock to use it later to wake up the device
     */
    protected void initWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mFullWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag:test");
    }

    /**
     * @brief Turns on display using wake lock.
     */
    private void turnOnDisplay() {
        Log.d("MainActivity", "Turning on the display");

        // Acquire lock and release it immediately as we want to turn on it only for a minute
        mFullWakeLock.acquire();
        mFullWakeLock.release();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("LightSensor", "Value has changed to " + sensorEvent.values[0]);

        if (mPreviousValue >= 0)
        {
            if (Math.abs(mPreviousValue - sensorEvent.values[0]) > VALUE_DIFFERENCE_TO_ACTIVATE_DISPLAY) {
                turnOnDisplay();
            }
        }

        mPreviousValue = sensorEvent.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
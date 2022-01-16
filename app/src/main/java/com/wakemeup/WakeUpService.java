package com.wakemeup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

public class WakeUpService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    float mPreviousValue = -1;

    private PowerManager.WakeLock mPartialWakeLock;
    private PowerManager.WakeLock mFullWakeLock;

    final static float VALUE_DIFFERENCE_TO_ACTIVATE_DISPLAY = 1;
    final static int WAKEUP_SERVICE_NOTIFICATION_ID = 1;

    private NotificationManager mNotificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        Log.i("WakeUpService", "WakeUpService started");

        // Register event listener
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_FASTEST);

        // Prepare wake lock
        initWakeLock();

        startForeground(WAKEUP_SERVICE_NOTIFICATION_ID, buildNotification());
        return START_STICKY;
    }

    /**
     * Show a notification while this service is running.
     */
    private Notification buildNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "Current value is " + mPreviousValue + " lux";

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("WakeUp service")  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .build();

        return notification;
    }

    /**
     * @brief Initializes wake lock to use it later to wake up the device
     */
    protected void initWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mFullWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "WakeUpService:FullWakeUpLockTag");

        // Prevent service to be stopped for the whole time
        mPartialWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "WakeUpService::PatialWakelockTag");
        mPartialWakeLock.acquire();
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
        Log.d("WakeUpService", "Value has changed to " + sensorEvent.values[0]);

        if (mPreviousValue >= 0) {
            if (Math.abs(mPreviousValue - sensorEvent.values[0]) > VALUE_DIFFERENCE_TO_ACTIVATE_DISPLAY) {
                turnOnDisplay();
            }
        }

        mPreviousValue = sensorEvent.values[0];

        // Update notification
        mNotificationManager.notify(WAKEUP_SERVICE_NOTIFICATION_ID, buildNotification());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

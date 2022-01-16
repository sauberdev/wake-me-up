package com.wakemeup;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WakeMeUpService extends IntentService
{
    /**
     * @brief Default ctor of WakeMeUp service
     * @deprecated
     */
    public WakeMeUpService() {
        super("WakeMeUpService");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
        Toast.makeText(getApplicationContext(), "WakeMeUp service has started", Toast.LENGTH_LONG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        try
        {
            while (true)
            {
                Log.d("WakeMeUpService", "Working");

                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            Log.e("WakeMeUpService", "Exception caught, WakeMeUp service stopped");
        }
    }
}

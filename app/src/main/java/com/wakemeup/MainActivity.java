package com.wakemeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "MainActivity started");

        Intent i = new Intent(this, WakeUpService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("MainActivity", "Starting foreground service");
            getApplicationContext().startForegroundService(i);
        }
        else
        {
            Log.d("MainActivity", "Starting service");
            getApplicationContext().startService(i);
        }

        Log.d("MainActivity", "MainActivity finished");
        finish();
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
}
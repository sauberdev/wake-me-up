package com.wakemeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "MainActivity started");

        Intent intent = new Intent(this, WakeMeUpService.class);
        startService(intent);

        finish();
    }
}
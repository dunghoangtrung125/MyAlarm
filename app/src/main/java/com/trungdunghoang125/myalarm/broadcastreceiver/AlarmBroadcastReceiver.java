package com.trungdunghoang125.myalarm.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.trungdunghoang125.myalarm.services.AlarmService;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {
        // start service alarm once
        if (!intent.getBooleanExtra("RECURRING", false)) {
            startAlarmService(context, intent);
        }
        // start service alarm recurring

    }

    void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra("TITLE", intent.getStringExtra("TITLE"));
        context.startService(intentService);
    }

}

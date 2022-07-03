package com.trungdunghoang125.myalarm.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.trungdunghoang125.myalarm.services.AlarmService;

import java.util.Calendar;

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
        else if(isToday(intent)) {
            startAlarmService(context, intent);
        }
    }

    void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra("TITLE", intent.getStringExtra("TITLE"));
        context.startService(intentService);
    }

    boolean isToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        if (today == Calendar.MONDAY && intent.getBooleanExtra("MONDAY", false)) {
            return true;
        }
        else if (today == Calendar.TUESDAY && intent.getBooleanExtra("TUESDAY", false)) {
            return true;
        }
        else if (today == Calendar.WEDNESDAY && intent.getBooleanExtra("WEDNESDAY", false)) {
            return true;
        }
        else if (today == Calendar.THURSDAY && intent.getBooleanExtra("THURSDAY", false)) {
            return true;
        }
        else if (today == Calendar.FRIDAY && intent.getBooleanExtra("FRIDAY", false)) {
            return true;
        }
        else if (today == Calendar.SATURDAY && intent.getBooleanExtra("SATURDAY", false)) {
            return true;
        }
        else if (today == Calendar.SUNDAY && intent.getBooleanExtra("SUNDAY", false)) {
            return true;
        }

        else return false;
    }
}

package com.trungdunghoang125.myalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.trungdunghoang125.myalarm.database.AlarmItem;
import com.trungdunghoang125.myalarm.services.AlarmService;

import java.util.Calendar;
import java.util.Random;

public class RingActivity extends AppCompatActivity {
    Button alarmStopButton;
    Button alarmSnoozeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        // binding view
        alarmStopButton = findViewById(R.id.stop_alarm_button);
        alarmSnoozeButton = findViewById(R.id.snooze_alarm_button);
        // Stop alarm service
        alarmStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopAlarmIntent = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(stopAlarmIntent);
                finish();;
            }
        });
        // Snooze alarm (alarm again 5 minutes later)
        alarmSnoozeButton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, 5);

            AlarmItem alarmItem = new AlarmItem(
                    new Random().nextInt(Integer.MAX_VALUE),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    "Alarm snooze 5 minitues",
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true
            );
            alarmItem.scheduleAlarm(getApplicationContext());
            // stop current alarm
            Intent stopAlarmIntent = new Intent(getApplicationContext(), AlarmService.class);
            getApplicationContext().stopService(stopAlarmIntent);
            finish();
        });
    }
}

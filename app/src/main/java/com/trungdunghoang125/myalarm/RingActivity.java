package com.trungdunghoang125.myalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.trungdunghoang125.myalarm.services.AlarmService;

public class RingActivity extends AppCompatActivity {
    Button alarmStopButton;
    Button alarmSnoozeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        // binding
        alarmStopButton = findViewById(R.id.stop_alarm_button);
        alarmSnoozeButton = findViewById(R.id.snooze_alarm_button);
        // set onClickListener
        alarmStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intent);
                finish();;
            }
        });

        alarmSnoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

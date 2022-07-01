package com.trungdunghoang125.myalarm.shedulealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.trungdunghoang125.myalarm.R;
import com.trungdunghoang125.myalarm.broadcastreceiver.AlarmBroadcastReceiver;
import com.trungdunghoang125.myalarm.database.AlarmItem;

import java.util.Calendar;
import java.util.Random;


public class ScheduleAlarm extends Fragment {
    private TimePicker timePicker;
    private EditText title;
    private TextView scheduleAlarmButton, cancelButton;
    private LinearLayout recurringOptions;
    private CheckBox recurring, mon, tue, wed, thu, fri, sat, sun;
    private ScheduleAlarmViewModel scheduleAlarmViewModel;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public ScheduleAlarm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleAlarmViewModel = ViewModelProviders.of(this).get(ScheduleAlarmViewModel.class);
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_alarm, container, false);

        // Binding view for all view of this fragment
        timePicker = view.findViewById(R.id.fragment_createalarm_timePicker);
        title = view.findViewById(R.id.fragment_createalarm_title);
        scheduleAlarmButton = view.findViewById(R.id.scheduleAlarm_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        recurring = view.findViewById(R.id.fragment_createalarm_recurring);
        recurringOptions = view.findViewById(R.id.fragment_createalarm_recurring_options);
        mon = view.findViewById(R.id.fragment_createalarm_checkMon);
        tue = view.findViewById(R.id.fragment_createalarm_checkTue);
        wed = view.findViewById(R.id.fragment_createalarm_checkWed);
        thu = view.findViewById(R.id.fragment_createalarm_checkThu);
        fri = view.findViewById(R.id.fragment_createalarm_checkFri);
        sat = view.findViewById(R.id.fragment_createalarm_checkSat);
        sun = view.findViewById(R.id.fragment_createalarm_checkSun);

        // Schedule Button OnclickListener
        scheduleAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAlarm();
                Navigation.findNavController(view).navigate(R.id.action_scheduleAlarm_to_alarmList);
            }
        });

        // Cancel Button OnclickListener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleAlarm_to_alarmList);
            }
        });

        // Set recurring option visible when check recurring checkbox
        recurring.setOnCheckedChangeListener((compoundButton, isCheck) -> {
            if (isCheck) {
                recurringOptions.setVisibility(View.VISIBLE);
            } else recurringOptions.setVisibility(View.GONE);
        });

        return view;
    }

    // function to create a new alarm
    private void createNewAlarm() {
        int alarmID = new Random().nextInt(Integer.MAX_VALUE);
        // implement this function below
        timePicker.setIs24HourView(true);
        AlarmItem alarmItem = new AlarmItem(
                alarmID,
                timePicker.getHour(),
                timePicker.getMinute(),
                title.getText().toString(),
                recurring.isChecked(),
                mon.isChecked(),
                tue.isChecked(),
                wed.isChecked(),
                thu.isChecked(),
                fri.isChecked(),
                sat.isChecked(),
                sun.isChecked(),
                true
        );
        // insert this item in database
        scheduleAlarmViewModel.insert(alarmItem);
        // set the alarm here
        alarmItem.scheduleAlarm(getContext());
    }
}
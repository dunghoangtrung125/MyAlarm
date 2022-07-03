package com.trungdunghoang125.myalarm.editalarm;

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

import com.trungdunghoang125.myalarm.R;
import com.trungdunghoang125.myalarm.database.AlarmItem;

public class EditAlarm extends Fragment {
    private TimePicker timePicker;
    private EditText title;
    private CheckBox recurring, mon, tue, wed, thu, fri, sat, sun;
    private LinearLayout recurringOptions;
    private TextView editAlarmButton, cancelEditButton;
    private EditAlarmViewModel editAlarmViewModel;
    private int alarmID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editAlarmViewModel = ViewModelProviders.of(this).get(EditAlarmViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_alarm, container, false);

        // binding
        timePicker = view.findViewById(R.id.fragment_editalarm_timePicker);
        title = view.findViewById(R.id.fragment_editalarm_title);
        editAlarmButton = view.findViewById(R.id.edit_alarm_button);
        cancelEditButton = view.findViewById(R.id.cancel_edit_button);
        recurring = view.findViewById(R.id.fragment_editalarm_recurring);
        recurringOptions = view.findViewById(R.id.fragment_editalarm_recurring_options);
        mon = view.findViewById(R.id.fragment_editalarm_checkMon);
        tue = view.findViewById(R.id.fragment_editalarm_checkTue);
        wed = view.findViewById(R.id.fragment_editalarm_checkWed);
        thu = view.findViewById(R.id.fragment_editalarm_checkThu);
        fri = view.findViewById(R.id.fragment_editalarm_checkFri);
        sat = view.findViewById(R.id.fragment_editalarm_checkSat);
        sun = view.findViewById(R.id.fragment_editalarm_checkSun);

        // set value from Bundle
        timePicker.setHour(getArguments().getInt("hour"));
        timePicker.setMinute(getArguments().getInt("minute"));
        title.setText(getArguments().getString("title"));
        recurring.setChecked(getArguments().getBoolean("recurring"));
        if (getArguments().getBoolean("recurring")) {
            recurringOptions.setVisibility(View.VISIBLE);
        } else recurringOptions.setVisibility(View.GONE);
        mon.setChecked(getArguments().getBoolean("mon"));
        tue.setChecked(getArguments().getBoolean("tue"));
        wed.setChecked(getArguments().getBoolean("wed"));
        thu.setChecked(getArguments().getBoolean("thu"));
        fri.setChecked(getArguments().getBoolean("fri"));
        sat.setChecked(getArguments().getBoolean("sat"));
        sun.setChecked(getArguments().getBoolean("sun"));
        alarmID = getArguments().getInt("alarmID");

        // Set recurring option visible when check recurring checkbox
        recurring.setOnCheckedChangeListener((compoundButton, isCheck) -> {
            if (isCheck) {
                recurringOptions.setVisibility(View.VISIBLE);
            } else recurringOptions.setVisibility(View.GONE);
        });

        // set onClickListener
        editAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAlarmInformation();
                Navigation.findNavController(view).navigate(R.id.action_editAlarm_to_alarmList);
            }
        });

        cancelEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_editAlarm_to_alarmList);
            }
        });

        return view;
    }

    // update new information to database
    public void editAlarmInformation() {
        AlarmItem alarmItem = editAlarmViewModel.queryThisItem(alarmID);
        alarmItem.setHour(timePicker.getHour());
        alarmItem.setMinute(timePicker.getMinute());
        alarmItem.setTitle(title.getText().toString());
        alarmItem.setRecurring(recurring.isChecked());
        alarmItem.setMon(mon.isChecked());
        alarmItem.setTue(tue.isChecked());
        alarmItem.setWed(wed.isChecked());
        alarmItem.setThu(thu.isChecked());
        alarmItem.setFri(fri.isChecked());
        alarmItem.setSat(sat.isChecked());
        alarmItem.setSun(sun.isChecked());
        alarmItem.setSetAlarm(true);

        // update alarm item data in database
        editAlarmViewModel.update(alarmItem);

        // set the new alarm time here
        AlarmItem newAlarm = new AlarmItem(
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
        newAlarm.scheduleAlarm(getContext());
    }
}
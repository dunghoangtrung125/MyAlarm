package com.trungdunghoang125.myalarm.shedulealarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.trungdunghoang125.myalarm.database.AlarmItem;
import com.trungdunghoang125.myalarm.database.AlarmRepository;

public class ScheduleAlarmViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;

    public ScheduleAlarmViewModel(@NonNull Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
    }
    public void insert(AlarmItem alarmItem) {
        alarmRepository.insert(alarmItem);
    }
}

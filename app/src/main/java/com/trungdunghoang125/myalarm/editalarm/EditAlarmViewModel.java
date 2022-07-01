package com.trungdunghoang125.myalarm.editalarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.trungdunghoang125.myalarm.database.AlarmItem;
import com.trungdunghoang125.myalarm.database.AlarmRepository;

public class EditAlarmViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;

    public EditAlarmViewModel(@NonNull Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
    }
    public void update(AlarmItem alarmItem) {
        alarmRepository.update(alarmItem);
    }

    public AlarmItem queryThisItem(int alarmID) {
        return alarmRepository.queryThisItem(alarmID);
    }
}
package com.trungdunghoang125.myalarm.alarmlist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trungdunghoang125.myalarm.database.AlarmItem;
import com.trungdunghoang125.myalarm.database.AlarmRepository;

import java.util.List;

public class AlarmListViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;

    private final LiveData<List<AlarmItem>> allAlarms;

    public AlarmListViewModel(Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
        allAlarms = alarmRepository.getAllAlarms();
    }
    LiveData<List<AlarmItem>> getAllAlarms() {
        return allAlarms;
    }


    public void delete(int alarmID) {
        alarmRepository.delete(alarmID);
    }

    public AlarmItem queryThisItem(int alarmID) {
        return alarmRepository.queryThisItem(alarmID);
    }
}

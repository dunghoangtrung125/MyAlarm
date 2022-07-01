package com.trungdunghoang125.myalarm.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private AlarmDAO alarmDAO;
    private LiveData<List<AlarmItem>> allAlarms;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        alarmDAO = db.alarmDAO();
        allAlarms = alarmDAO.getAlarm();
    }

    public LiveData<List<AlarmItem>> getAllAlarms() {
        return allAlarms;
    }

    public void insert(AlarmItem alarmItem) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmDAO.insert(alarmItem);
        });
    }

    public void update(AlarmItem alarmItem) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
//            AlarmItem alarmItem = alarmDAO.getThisAlarm(alarmID);
//            alarmDAO.update(alarmItem);
            alarmDAO.update(alarmItem);
        });
    }

    public void delete(int alarmID) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            // query this alarmItem with this ID
            AlarmItem alarmItem = alarmDAO.getThisAlarm(alarmID);
            // delete this item
            alarmDAO.delete(alarmItem);
        });
    }

    public AlarmItem queryThisItem(int alarmID) {
        // query this alarmItem with this ID
        AlarmItem alarmItem = alarmDAO.getThisAlarm(alarmID);
        return alarmItem;
    }
}

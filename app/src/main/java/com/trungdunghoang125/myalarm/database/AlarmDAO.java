package com.trungdunghoang125.myalarm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface AlarmDAO {
    @Insert
    void insert(AlarmItem alarmItem);

    @Update
    void update(AlarmItem alarmItem);

    @Query("SELECT * FROM alarm_table ORDER BY hour, minute ASC")
    LiveData<List<AlarmItem>> getAlarm();

    @Query("SELECT * FROM alarm_table WHERE alarmID = :ID")
    AlarmItem getThisAlarm(int ID);

    @Delete
    void delete(AlarmItem alarmItem);
}

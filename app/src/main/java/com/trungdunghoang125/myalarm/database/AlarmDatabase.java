package com.trungdunghoang125.myalarm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AlarmItem.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    public abstract AlarmDAO alarmDAO();

    private static volatile AlarmDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AlarmDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AlarmDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AlarmDatabase.class,
                            "alarm_database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}

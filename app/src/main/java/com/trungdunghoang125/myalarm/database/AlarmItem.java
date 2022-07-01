package com.trungdunghoang125.myalarm.database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.trungdunghoang125.myalarm.broadcastreceiver.AlarmBroadcastReceiver;

import java.util.Calendar;

@Entity(tableName = "alarm_table")
public class AlarmItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int alarmID;

    private int hour, minute;
    private String title;
    private boolean recurring, isSetAlarm;
    private boolean mon, tue, wed, thu, fri, sat, sun;

    public AlarmItem(int alarmID, int hour, int minute, String title, boolean recurring, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun, boolean isSetAlarm) {
        this.alarmID = alarmID;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.recurring = recurring;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.isSetAlarm = isSetAlarm;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isMon() {
        return mon;
    }

    public boolean isTue() {
        return tue;
    }

    public boolean isWed() {
        return wed;
    }

    public boolean isThu() {
        return thu;
    }

    public boolean isFri() {
        return fri;
    }

    public boolean isSat() {
        return sat;
    }

    public boolean isSun() {
        return sun;
    }

    public boolean isSetAlarm() {
        return isSetAlarm;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public void setSetAlarm(boolean setAlarm) {
        isSetAlarm = setAlarm;
    }

    public String getRecurringText() {
        if (!recurring) return null;
        String recurringText = "";
        if (mon) recurringText += "Mo ";
        if (tue) recurringText += "Tu ";
        if (wed) recurringText += "We ";
        if (thu) recurringText += "Th ";
        if (fri) recurringText += "Fr ";
        if (sat) recurringText += "Sa ";
        if (sun) recurringText += "Su ";
        return recurringText;
    }

    public void scheduleAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("RECURRING", recurring);
        intent.putExtra("MONDAY", mon);
        intent.putExtra("TUESDAY", tue);
        intent.putExtra("WEDNESDAY", wed);
        intent.putExtra("THURSDAY", thu);
        intent.putExtra("FRIDAY", fri);
        intent.putExtra("SATURDAY", sat);
        intent.putExtra("SUNDAY", sun);

        intent.putExtra("TITLE", title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmID, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            Toast.makeText(context, "One time alarm", Toast.LENGTH_LONG).show();

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
        }
//        else {
//            Toast.makeText(context, "Recurring alarm", Toast.LENGTH_LONG).show();
//
//            final long RUN_DAILY = 24 * 60 * 60 * 1000;
//            alarmManager.setRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.getTimeInMillis(),
//                    RUN_DAILY,
//                    alarmPendingIntent
//            );
//        }
        this.isSetAlarm = true;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmID, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.isSetAlarm = false;

        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmID);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }
}

package com.trungdunghoang125.myalarm.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trungdunghoang125.myalarm.alarmlist.ItemClick;
import com.trungdunghoang125.myalarm.R;
import com.trungdunghoang125.myalarm.database.AlarmItem;

import java.util.ArrayList;
import java.util.List;

public class AlarmItemAdapter extends RecyclerView.Adapter<AlarmItemAdapter.AlarmItemHolder> {
    private List<AlarmItem> alarmItemItemList;
    private ItemClick itemClick;

    // constructor
    public AlarmItemAdapter(ItemClick itemClick) {
        this.alarmItemItemList = new ArrayList<AlarmItem>();
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public AlarmItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new AlarmItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmItemHolder holder, @SuppressLint("RecyclerView") int position) {
        AlarmItem alarmItem = alarmItemItemList.get(position);
        int alarmID = alarmItem.getAlarmID();
        holder.bind(alarmItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(alarmID);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemClick.onItemLongClick(alarmID);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarmItemItemList.size();
    }

    public void setAlarms(List<AlarmItem> alarmItemList) {
        this.alarmItemItemList = alarmItemList;
        notifyDataSetChanged();
    }

    // inner class for AlarmItemHolder
    class AlarmItemHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvFrequency, tvRemindMessage;
        Switch swSetAlarm;

        public AlarmItemHolder(@NonNull View itemView) {
            super(itemView);
            // Anh xa
            tvTime = itemView.findViewById(R.id.alarm_time);
            tvFrequency = itemView.findViewById(R.id.frequency_alarm);
            tvRemindMessage = itemView.findViewById(R.id.remind_message);
            swSetAlarm = itemView.findViewById(R.id.set_alarm);
        }

        public void bind(AlarmItem alarmItem) {
            // set text for tvTime
            String timeText = String.format("%02d:%02d", alarmItem.getHour(), alarmItem.getMinute());
            tvTime.setText(timeText);
            // set text for tvFrequency
            String recurringDay = alarmItem.getRecurringText();
            tvFrequency.setText(recurringDay);
            // set remind message
            tvRemindMessage.setText(alarmItem.getTitle());
            // set switch button
            swSetAlarm.setChecked(alarmItem.isSetAlarm());
        }

    }
}

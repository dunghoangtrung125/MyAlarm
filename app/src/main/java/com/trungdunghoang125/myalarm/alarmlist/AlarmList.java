package com.trungdunghoang125.myalarm.alarmlist;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trungdunghoang125.myalarm.R;
import com.trungdunghoang125.myalarm.adapter.AlarmItemAdapter;
import com.trungdunghoang125.myalarm.database.AlarmItem;

import java.util.List;

public class AlarmList extends Fragment implements ItemClick {
    private FloatingActionButton fabAddAlarm;
    private RecyclerView rcAlarm;
    private AlarmItemAdapter alarmItemAdapter;
    private AlarmListViewModel alarmListViewModel;

    public AlarmList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmListViewModel = new ViewModelProvider(this).get(AlarmListViewModel.class);
        alarmListViewModel.getAllAlarms().observe(this, new Observer<List<AlarmItem>>() {
            @Override
            public void onChanged(List<AlarmItem> alarmItems) {
                if (alarmItems != null) {
                    alarmItemAdapter.setAlarms(alarmItems);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        // set support action bar
        MaterialToolbar toolbar = (MaterialToolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.BLACK);
        //
        rcAlarm = view.findViewById(R.id.rc_alarm);
        // adapter init
        alarmItemAdapter = new AlarmItemAdapter(this);
        // set type of ui for view
        rcAlarm.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // set adapter for view
        rcAlarm.setAdapter(alarmItemAdapter);
        // add alarm onClickListener Button
        fabAddAlarm = view.findViewById(R.id.fab_add_alarm);
        fabAddAlarm.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_alarmList_to_scheduleAlarm));

        return view;
    }

    @Override
    public void onItemClick(int alarmID) {
        AlarmItem alarmItem = alarmListViewModel.queryThisItem(alarmID);
        Bundle bundle = new Bundle();
        bundle.putInt("alarmID", alarmID);
        bundle.putInt("hour", alarmItem.getHour());
        bundle.putInt("minute", alarmItem.getMinute());
        bundle.putString("title", alarmItem.getTitle());
        bundle.putBoolean("recurring", alarmItem.isRecurring());
        bundle.putBoolean("mon", alarmItem.isMon());
        bundle.putBoolean("tue", alarmItem.isTue());
        bundle.putBoolean("wed", alarmItem.isWed());
        bundle.putBoolean("thu", alarmItem.isThu());
        bundle.putBoolean("fri", alarmItem.isFri());
        bundle.putBoolean("sat", alarmItem.isSat());
        bundle.putBoolean("sun", alarmItem.isSun());
        bundle.putBoolean("isSetAlarm", alarmItem.isSetAlarm());
        Navigation.findNavController(getView()).navigate(R.id.action_alarmList_to_editAlarm, bundle);
        Toast.makeText(getContext(), "Edit this alarm", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(int alarmID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogeTheme);
        builder.setMessage("Do you want to delete this alarm ?");
        builder.setTitle("Delete alarm !");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                (dialogInterface, i) -> {
                    AlarmItem alarmItem = alarmListViewModel.queryThisItem(alarmID);
                    alarmItem.cancelAlarm(getContext());
                    alarmListViewModel.delete(alarmID);
                }
        );
        builder.setNegativeButton(
                "No",
                (dialogInterface, i) -> {
                    dialogInterface.cancel();
                }
        );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void changeSwitchState(int alarmID) {

    }
}
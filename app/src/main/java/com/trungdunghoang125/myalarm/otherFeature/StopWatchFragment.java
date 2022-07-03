package com.trungdunghoang125.myalarm.otherFeature;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.trungdunghoang125.myalarm.R;

public class StopWatchFragment extends Fragment {
    private boolean running = false;
    private long offset = 0;
    // key string for using Bundle
    private String OFF_SET_KEY = "offset";
    private String RUNNING_KEY = "running";
    private String BASE_KEY = "base";

    private Chronometer stopwatch;
    private TextView startButton, pauseButton, resetButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stop_watch, container, false);
        // binding
        stopwatch = view.findViewById(R.id.stopwatch);
        startButton = view.findViewById(R.id.start_button);
        pauseButton = view.findViewById(R.id.pause_button);
        resetButton = view.findViewById(R.id.reset_button);

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFF_SET_KEY);
            running = savedInstanceState.getBoolean(RUNNING_KEY);
            if (running) {
                stopwatch.setBase(savedInstanceState.getLong(BASE_KEY));
                stopwatch.start();
            }
            else setBaseTime();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    setBaseTime();
                    stopwatch.start();
                    running = true;
                }
            }
        });

        pauseButton.setOnClickListener(view12 -> {
            if (running) {
                saveOffset();
                stopwatch.stop();
                running = false;
            }
        });

        resetButton.setOnClickListener(view1 -> {
            offset = 0;
            setBaseTime();
            running = false;
            stopwatch.stop();
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (running) {
            saveOffset();
            stopwatch.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (running) {
            setBaseTime();
            stopwatch.start();
            offset = 0;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(OFF_SET_KEY, offset);
        outState.putBoolean(RUNNING_KEY, running);
        outState.putLong(BASE_KEY, stopwatch.getBase());
        super.onSaveInstanceState(outState);
    }

    private void setBaseTime() {
        stopwatch.setBase(SystemClock.elapsedRealtime() - offset);
    }

    private void saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.getBase();
    }
}
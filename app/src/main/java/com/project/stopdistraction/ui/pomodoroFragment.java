package com.project.stopdistraction.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.TextView;

import com.project.stopdistraction.R;

import java.util.List;
import java.util.Locale;

public class pomodoroFragment extends Fragment {

    private int sec = 0;
    private boolean is_running;
    private boolean was_running;
    Button btnStart, btnPause, btnRest;

    public pomodoroFragment() {
        // Required empty public constructor
    }

    public static pomodoroFragment newInstance() {
        pomodoroFragment fragment = new pomodoroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", sec);
        savedInstanceState.putBoolean("running", is_running);
        savedInstanceState.putBoolean("wasRunning", was_running);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        btnStart = (Button) view.findViewById(R.id.button);
        btnPause = (Button) view.findViewById(R.id.button2);
        btnRest = (Button) view.findViewById(R.id.button3);

        if (savedInstanceState != null) {
            sec = savedInstanceState.getInt("seconds");
            is_running = savedInstanceState.getBoolean("running");
            was_running = savedInstanceState.getBoolean("wasRunning");
        }
        running_Timer(view);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_running = true;
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_running = false;
            }
        });
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_running = false;
                sec = 0;
            }
        });

        return view;
    }

    private void running_Timer(View view) {
        final TextView t_View = (TextView) view.findViewById(R.id.textView);
        final TextView tbreakTimeView = (TextView) view.findViewById(R.id.textView_break_time);
        final Handler handle = new Handler();
        handle.post(new Runnable() {
            @Override
            public void run() {
                int hrs = sec / 3600;
                int mins = (sec % 3600) / 60;
                int secs = sec % 60;
                String time_t = String.format(Locale.getDefault(), "    %d:%02d:%02d   ", hrs, mins, secs);

                if (t_View.getVisibility() == View.VISIBLE && secs == 10) {
                    was_running = is_running;
                    is_running = false;
                    sec = 0;
                    t_View.setVisibility(View.GONE);
                    tbreakTimeView.setVisibility(View.VISIBLE);
                }
                if (t_View.getVisibility() == View.GONE && secs == 5) {
                    was_running = is_running;
                    is_running = true;
                    sec = 0;
                    t_View.setVisibility(View.VISIBLE);
                    tbreakTimeView.setVisibility(View.GONE);
                }
                t_View.setText(time_t);
                tbreakTimeView.setText(time_t);

                if (is_running) {
                    sec++;
                }
                handle.postDelayed(this, 1000);
            }
        });
    }
}
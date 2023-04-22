package com.project.stopdistraction.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.stopdistraction.R;

public class InOutTimeFragment extends Fragment {


    public InOutTimeFragment() {
        // Required empty public constructor
    }


    public static InOutTimeFragment newInstance() {
        InOutTimeFragment fragment = new InOutTimeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_out_time, container, false);
    }
}
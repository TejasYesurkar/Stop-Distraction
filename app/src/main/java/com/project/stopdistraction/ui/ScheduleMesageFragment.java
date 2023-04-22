package com.project.stopdistraction.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.stopdistraction.R;

import java.net.URLEncoder;


public class ScheduleMesageFragment extends Fragment {


    public ScheduleMesageFragment() {
        // Required empty public constructor
    }

    public static ScheduleMesageFragment newInstance() {
        ScheduleMesageFragment fragment = new ScheduleMesageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_mesage, container, false);

    }

    private void sendMessage(){
        try {
            Intent sendMsg = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + "+919822740116" + "&text=" + URLEncoder.encode("Your Message to Contact Number", "UTF-8");
            sendMsg.setPackage("com.whatsapp");
            sendMsg.setData(Uri.parse(url));
            if (sendMsg.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(sendMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.project.stopdistraction.ui;

import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_INFO_SRC;
import static com.project.stopdistraction.common.Constants.TAG_FRAGMENT_WEBVIEW;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.stopdistraction.R;
import com.project.stopdistraction.common.UIHelper;

public class InformationSrcFragment extends Fragment {
 Button btnZeeNews,btnIndianTimes,btnHindustanTimes;

    public InformationSrcFragment() {
        // Required empty public constructor
    }

    public static InformationSrcFragment newInstance() {
        InformationSrcFragment fragment = new InformationSrcFragment();
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
        View root = inflater.inflate(R.layout.fragment_information_src, container, false);
        btnZeeNews = root.findViewById(R.id.btnZeeNews);
        btnIndianTimes = root.findViewById(R.id.btntimesofIndia);
        btnHindustanTimes = root.findViewById(R.id.btnhindutasntimes);

        btnZeeNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.getInstance().replaceFragment(getActivity(), WebViewFragment.newInstance("https://zeenews.india.com/"), TAG_FRAGMENT_WEBVIEW, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
            }
        });
        btnIndianTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.getInstance().replaceFragment(getActivity(), WebViewFragment.newInstance("https://timesofindia.indiatimes.com/"), TAG_FRAGMENT_WEBVIEW, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
            }
        });
        btnHindustanTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.getInstance().replaceFragment(getActivity(), WebViewFragment.newInstance("https://www.hindustantimes.com/"), TAG_FRAGMENT_WEBVIEW, R.id.home_container,
                        true, UIHelper.CustomAnimationType.CUSTOM_ANIMATION_LEFT_AND_RIGHT);
            }
        });


        return root;
    }
}
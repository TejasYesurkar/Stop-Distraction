package com.project.stopdistraction.informationSrc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.project.stopdistraction.R;
import com.project.stopdistraction.common.WebViewController;

public class ZeeNewsFragment extends Fragment {

    public ZeeNewsFragment() {
        // Required empty public constructor
    }


    public static ZeeNewsFragment newInstance() {
        ZeeNewsFragment fragment = new ZeeNewsFragment();
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
        View root = inflater.inflate(R.layout.fragment_zee_news, container, false);

        // Created a WebView and used the loadurl method
        // to give url to WebView Controller class
        WebView webView = root.findViewById(R.id.web_view_zee);

        // Url of website is passed here
        webView.loadUrl("https://zeenews.india.com/");

        // WebViewController is used
        webView.setWebViewClient(new WebViewController());
        // Inflate the layout for this fragment
        return root;
    }
}
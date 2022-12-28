package com.project.stopdistraction.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.project.stopdistraction.R;
import com.project.stopdistraction.common.WebViewController;

public class WebViewFragment extends Fragment {


    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString("BASE_URL", url);
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
        String url =requireArguments().getString("BASE_URL");

        View root = inflater.inflate(R.layout.fragment_web_view, container, false);

        // Created a WebView and used the loadurl method
        // to give url to WebView Controller class
        WebView webView = root.findViewById(R.id.web_view_zee);

        // Url of website is passed here
        webView.loadUrl(url);

        // WebViewController is used
        webView.setWebViewClient(new WebViewController());

        return root;
    }
}
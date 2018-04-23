package com.minhduc.planttracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoistureFragment extends Fragment {


    public MoistureFragment() {
        // Required empty public constructor
    }
    WebView webview_moischart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moisture, container, false);
        webview_moischart = (WebView) view.findViewById(R.id.moisture_chart);
        webview_moischart.getSettings().setJavaScriptEnabled(true);
        webview_moischart.getSettings().setUseWideViewPort(true);
        webview_moischart.getSettings().setLoadWithOverviewMode(true);
        webview_moischart.loadUrl("file:///android_asset/moisture_chart.html");
        return view;
    }

}

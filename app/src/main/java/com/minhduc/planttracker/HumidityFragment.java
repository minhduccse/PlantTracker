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
public class HumidityFragment extends Fragment {


    public HumidityFragment() {
        // Required empty public constructor
    }
    WebView webview_humchart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_humidity, container, false);
        webview_humchart = (WebView) view.findViewById(R.id.humidity_chart);
        webview_humchart.getSettings().setJavaScriptEnabled(true);
        webview_humchart.getSettings().setUseWideViewPort(true);
        webview_humchart.getSettings().setLoadWithOverviewMode(true);
        webview_humchart.loadUrl("file:///android_asset/humidity_chart.html");
        return view;
    }

}

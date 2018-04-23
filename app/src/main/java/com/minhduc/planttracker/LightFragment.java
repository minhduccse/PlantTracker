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
public class LightFragment extends Fragment {


    public LightFragment() {
        // Required empty public constructor
    }
    WebView webview_lightchart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        webview_lightchart = (WebView) view.findViewById(R.id.light_chart);
        webview_lightchart.getSettings().setJavaScriptEnabled(true);
        webview_lightchart.getSettings().setUseWideViewPort(true);
        webview_lightchart.getSettings().setLoadWithOverviewMode(true);
        webview_lightchart.loadUrl("file:///android_asset/light_chart.html");
        return view;
    }

}

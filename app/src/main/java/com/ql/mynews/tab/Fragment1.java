package com.ql.mynews.tab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ql.mynews.R;
import com.ql.mynews.adapter.NewsListAdapter;
import com.ql.mynews.card.RecyclerViewAdapter;
import com.ql.mynews.card.RecyclerViewForFirstAdapter;
import com.ql.mynews.custom.ChromeFloatingCirclesDrawable;
import com.ql.mynews.http.HttpConst;
import com.ql.mynews.http.Https;
import com.ql.mynews.utils.NewsAllBean;
import com.ql.mynews.utils.NewsOtherBean;
import com.ql.mynews.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment implements AdapterView.OnItemClickListener {
    private ProgressBar chromeFloatingCircles_progress;
    public static final String TAG = null;
    private View v;

    private List<NewsAllBean> list = new ArrayList<NewsAllBean>();
    private RecyclerView recyclerView;
    private RecyclerViewForFirstAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment1, null);


        recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        chromeFloatingCircles_progress = (ProgressBar)v.findViewById(R.id.chromeFloatingCircles_progress);
//        chromeFloatingCircles_progress.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(getActivity()).colors(getProgressDrawableColors()).build());

recyclerView.isShown();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Https.getRequestForNewsAll(getActivity(), "", myHandler);
            }
        }).start();

        return v;
    }

    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        colors[0] = prefs.getInt(getString(R.string.firstcolor_pref_key),getResources().getColor(R.color.red));
        colors[1] = prefs.getInt(getString(R.string.secondcolor_pref_key),getResources().getColor(R.color.blue));
        colors[2] = prefs.getInt(getString(R.string.thirdcolor_pref_key),getResources().getColor(R.color.yellow));
        colors[3] = prefs.getInt(getString(R.string.fourthcolor_pref_key), getResources().getColor(R.color.green));
        return colors;
    }


    private Handler myHandler = new Handler() {

        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HttpConst.MSG_WHAT_NEWS:
                    if (msg.obj == null) {

                    } else {
                        list = (List<NewsAllBean>) msg.obj;
                        adapter=new RecyclerViewForFirstAdapter(list,getActivity());

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsOtherBean news = (NewsOtherBean) parent.getItemAtPosition(position);
        Intent in = new Intent(getActivity(), WebViewActivity.class);
        in.putExtra("url", news.getUrl());
        startActivity(in);
    }

}
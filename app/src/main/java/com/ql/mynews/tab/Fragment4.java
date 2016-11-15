package com.ql.mynews.tab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ql.mynews.R;
import com.ql.mynews.adapter.NewsOtherListAdapter;
import com.ql.mynews.card.RecyclerViewAdapter;
import com.ql.mynews.http.HttpConst;
import com.ql.mynews.http.Https;
import com.ql.mynews.utils.NewsOtherBean;
import com.ql.mynews.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment implements AdapterView.OnItemClickListener{
	private View v;

	private List<NewsOtherBean> list = new ArrayList<NewsOtherBean>();
	private RecyclerView recyclerView;
	private RecyclerViewAdapter adapter;
	LinearLayoutManager layoutManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment4, null);

		recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Https.getRequestForNewsOther(getActivity(), "shishang", myHandler);
			}
		}).start();

		return v;
	}

	private Handler myHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case HttpConst.MSG_WHAT_NEWS_TIYU:
					if (msg.obj == null) {

					} else {
						list = (List<NewsOtherBean>) msg.obj;
						adapter=new RecyclerViewAdapter(list,getActivity());

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
